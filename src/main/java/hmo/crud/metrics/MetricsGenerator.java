package hmo.crud.metrics;

import hmo.crud.controller.request.CreateBeerRequest;
import hmo.crud.controller.response.CreateBeerResponse;
import hmo.crud.controller.response.GetBeerResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Random;

@Log4j2
@Component
public class MetricsGenerator {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String BEER_V1_URL = "http://localhost:8080/v1/beer";

    private Random random = new Random();
    private String[] suggestion = {
            "Magic Trap", "Interstellar", "Unicorn Witbier", "Unicor Session IPA", "Abroba", "Easy IPA", "Guinness",
            "Opa Pilson", "Dama QI", "Imperio Lager", "Martina Witbier", "Martina IPA", "Barco Thai", "Serena Session"};

    @Scheduled(fixedRate = 3*60*1000) //each 3 min
    private void generateMetricsForPostBeer() {
        String beer = suggestion[random.nextInt(suggestion.length)];
        log.info("Trying to create new beer "+beer);

        log.info(
                restTemplate.postForEntity(BEER_V1_URL, new CreateBeerRequest(beer), CreateBeerResponse.class)
        );
    }

    @Scheduled(fixedRate = 3*1000) //each 3 sec
    private void generateMetricsForGetBeerById() {
        int beerId = 1 + random.nextInt(suggestion.length);
        log.info(
                restTemplate.getForEntity(BEER_V1_URL + "/" + beerId, GetBeerResponse.class)
        );
    }

    @Scheduled(fixedRate = 4*1000) //each 4 sec
    private void generateMetricsForGetBeerByName() {
        String beer = suggestion[random.nextInt(suggestion.length)];
        log.info(
                restTemplate.getForEntity(BEER_V1_URL+"?beerName="+URLEncoder.encode(beer, StandardCharsets.UTF_8), GetBeerResponse.class)
        );
    }
}
