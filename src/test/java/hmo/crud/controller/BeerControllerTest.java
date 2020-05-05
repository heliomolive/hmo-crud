package hmo.crud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hmo.crud.HmoAppMessageLoader;
import hmo.crud.UserMessage;
import hmo.crud.controller.request.CreateBeerRequest;
import hmo.crud.domain.dto.BeerDto;
import hmo.crud.domain.mapper.BeerMapper;
import hmo.crud.mother.BeerMother;
import hmo.crud.mother.CreateBeerRequestMother;
import hmo.crud.repository.entity.Beer;
import hmo.crud.service.BeerServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BeerControllerTest extends HmoAbstractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BeerServiceImpl beerService;

    @MockBean
    private HmoAppMessageLoader hmoAppMessageLoader;

    @Autowired
    private BeerMapper beerMapper;

    private ArgumentCaptor<BeerDto> beerDtoArgumentCaptor = ArgumentCaptor.forClass(BeerDto.class);

    private static final String CREATE_BEER_V1_URI = "/v1/beer";
    private static final String GET_BEER_V1_URI = "/v1/beer/{beerUid}";

    @Test
    public void createBeer() throws Exception {
        BeerDto beerDto = beerMapper.getBeerDto(BeerMother.getUnicornWittBeer());
        CreateBeerRequest request = CreateBeerRequestMother.getCreateBeerRequest(beerDto.getName());
        when(beerService.createBeer(any(BeerDto.class))).thenReturn(beerDto);

        mockMvc.perform(post(CREATE_BEER_V1_URI)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.beerUid").value(beerDto.getBeerUid()))
                .andExpect(jsonPath("$.beerName").value(beerDto.getName()));

        verify(beerService).createBeer(beerDtoArgumentCaptor.capture());
        assertEquals(beerDto.getName(), beerDtoArgumentCaptor.getValue().getName());
    }

    @Test
    public void createBeer_missing_name() throws Exception {
        CreateBeerRequest request = new CreateBeerRequest();

        mockMvc.perform(post(CREATE_BEER_V1_URI)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.developerMessage").value("Required parameter: beerName"));

        verify(beerService, never()).createBeer(any(BeerDto.class));
    }

    @Test
    public void getBeer() throws Exception {
        Beer beer = BeerMother.getUnicornWittBeer();
        when(beerService.getBeer(anyLong()))
                .thenReturn(Optional.of(beerMapper.getBeerDto(beer)));

        mockMvc.perform(get(GET_BEER_V1_URI.replace("{beerUid}", String.valueOf(beer.getBeerUid()))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.beerUid").value(String.valueOf(beer.getBeerUid())))
                .andExpect(jsonPath("$.beerName").value(beer.getName()));

        verify(beerService).getBeer(beer.getBeerUid());
    }

    @Test
    public void getBeer_not_found() throws Exception {
        long beerUid = 123456L;
        String userMessage = "Cerveja não encontrada";
        when(beerService.getBeer(anyLong())).thenReturn(Optional.empty());
        when(hmoAppMessageLoader.getUserMessage(any(UserMessage.class))).thenReturn(userMessage);

        mockMvc.perform(get(GET_BEER_V1_URI.replace("{beerUid}", String.valueOf(beerUid))))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.developerMessage").value(format("Beer [%d] not found", beerUid)))
                .andExpect(jsonPath("$.userMessage").value(userMessage));

        verify(beerService).getBeer(beerUid);
        verify(hmoAppMessageLoader).getUserMessage(UserMessage.BEER_NOT_FOUND);
    }

}
