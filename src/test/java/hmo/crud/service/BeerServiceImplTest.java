package hmo.crud.service;

import hmo.crud.HmoAppMessageLoader;
import hmo.crud.UserMessage;
import hmo.crud.domain.dto.BeerDto;
import hmo.crud.domain.exception.BadRequestException;
import hmo.crud.domain.mapper.BeerMapper;
import hmo.crud.repository.BeerRepository;
import hmo.crud.repository.entity.Beer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static hmo.crud.mother.BeerMother.getUnicornWittBeer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BeerServiceImplTest {

    @InjectMocks
    private BeerServiceImpl fixture;

    @Mock
    private BeerRepository beerRepository;

    @Mock
    private HmoAppMessageLoader hmoAppMessageLoader;

    private ArgumentCaptor<Beer> beerArgumentCaptor = ArgumentCaptor.forClass(Beer.class);
    private BeerMapper beerMapper = Mappers.getMapper(BeerMapper.class);

    @BeforeEach
    private void beforeEach() {
        MockitoAnnotations.initMocks(this);
        fixture.setBeerMapper(beerMapper);
    }

    @Test
    public void createBeer() {
        Beer beer = getUnicornWittBeer(84896L);
        when(beerRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(beerRepository.save(any(Beer.class))).thenReturn(beer);

        fixture.createBeer( beerMapper.getBeerDto(beer) );

        verify(beerRepository).findByName(beer.getName());
        verify(beerRepository).save(beerArgumentCaptor.capture());
        assertEquals(beer.getName(), beerArgumentCaptor.getValue().getName());
    }

    @Test
    public void createBeer_beer_already_exists() {
        Beer beer = getUnicornWittBeer(84896L);
        when(beerRepository.findByName(anyString())).thenReturn(Optional.of(beer));
        String userMessage = "User test message";
        when(hmoAppMessageLoader.getUserMessage(any(UserMessage.class))).thenReturn(userMessage);

        BadRequestException e = assertThrows(BadRequestException.class, () -> {
            fixture.createBeer(beerMapper.getBeerDto(beer));
        });

        assertEquals(userMessage, e.getUserMessage());
        verify(beerRepository).findByName(beer.getName());
        verify(hmoAppMessageLoader).getUserMessage(UserMessage.BEER_ALREADY_EXISTS);
    }

    @Test
    public void getBeerByName() {
        Beer beer = getUnicornWittBeer(84896L);
        when(beerRepository.findByName(anyString())).thenReturn(Optional.of(beer));

        Optional<BeerDto> beerDto = fixture.getBeerByName(beer.getName());

        assertTrue(beerDto.isPresent());
        assertEquals(beerMapper.getBeerDto(beer), null);
    }
}
