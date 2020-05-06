package hmo.crud.inttest;

import com.fasterxml.jackson.databind.ObjectMapper;
import hmo.crud.controller.request.CreateBeerRequest;
import hmo.crud.mother.CreateBeerRequestMother;
import hmo.crud.repository.BeerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BeerIntTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BeerRepository beerRepository;

    private static final String BEER_V1_URI = "/v1/beer";

    @Test
    public void createAndGetBeer() throws Exception {
        CreateBeerRequest createBeerRequest = CreateBeerRequestMother.getCreateBeerRequest("Leuven Witbeer");

        mockMvc.perform(post(BEER_V1_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createBeerRequest)))
                .andExpect(status().isCreated());

        mockMvc.perform(get(BEER_V1_URI+"?beerName="+createBeerRequest.getBeerName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.beerName").value(createBeerRequest.getBeerName()))
                .andExpect(jsonPath("$.beerUid").isNotEmpty());
    }
}
