package hmo.crud.metrics;

import hmo.crud.controller.BeerController;
import hmo.crud.controller.request.CreateBeerRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

@Log4j2
@Component
public class MetricsGenerator {

    @Autowired
    private BeerController beerController;

    private Random random = new Random();
    private String[] suggestion = {
            "Magic Trap", "Interstellar", "Unicorn Witbier", "Unicor Session IPA", "Abroba", "Easy IPA", "Guinness",
            "Opa Pilson", "Dama QI", "Imperio Lager", "Martina Witbier", "Martina IPA", "Barco Thai", "Serena Session"};

    @Scheduled(fixedRate = 3*60*1000) //each 3 min
    private void generateMetricsForPostBeer() {
        String beer = suggestion[random.nextInt(suggestion.length)];
        log.info("Trying to create new beer "+beer);

        beerController.createBeer(new CreateBeerRequest(beer));
    }

    @Scheduled(fixedRate = 3*1000) //each 3 sec
    private void generateMetricsForGetBeerById() {
        beerController.getBeer(1L + random.nextInt(suggestion.length));
    }

    @Scheduled(fixedRate = 4*1000) //each 4 sec
    private void generateMetricsForGetBeerByName() {
        String beer = suggestion[random.nextInt(suggestion.length)];
        beerController.getBeerByName(beer);
    }
}
