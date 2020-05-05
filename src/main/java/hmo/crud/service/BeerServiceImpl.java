package hmo.crud.service;

import hmo.crud.HmoAppMessageLoader;
import hmo.crud.UserMessage;
import hmo.crud.domain.dto.BeerDto;
import hmo.crud.domain.exception.BadRequestException;
import hmo.crud.domain.mapper.BeerMapper;
import hmo.crud.repository.BeerRepository;
import hmo.crud.repository.entity.Beer;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.lang.String.format;

@Log4j2
@Setter
@Service
public class BeerServiceImpl {

    @Autowired
    private BeerRepository beerRepository;

    @Autowired
    private BeerMapper beerMapper;

    @Autowired
    private HmoAppMessageLoader hmoAppMessageLoader;

    public BeerDto createBeer(BeerDto beerDto) {
        log.info("Creating beer... ");
        beerRepository.findByName(beerDto.getName()).ifPresent(
                beer -> {
                    throw BadRequestException.builder()
                            .developerMessage(format("Beer [%s] already registered.", beerDto.getName()))
                            .userMessage(format(
                                    hmoAppMessageLoader.getUserMessage(UserMessage.BEER_ALREADY_EXISTS),
                                    beerDto.getName()))
                            .build();
                }
        );

        Beer beer = beerMapper.getBeer(beerDto);
        beer = beerRepository.save(beer);

        return beerMapper.getBeerDto(beer);
    }

    public Optional<BeerDto> getBeer(Long beerId) {
        return beerRepository.findById(beerId)
                .map(beerMapper::getBeerDto);
    }
}
