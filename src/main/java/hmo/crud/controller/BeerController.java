package hmo.crud.controller;

import hmo.crud.HmoAppMessageLoader;
import hmo.crud.controller.request.CreateBeerRequest;
import hmo.crud.controller.response.CreateBeerResponse;
import hmo.crud.controller.response.GetBeerResponse;
import hmo.crud.controller.response.HmoResponse;
import hmo.crud.domain.dto.BeerDto;
import hmo.crud.domain.exception.BadRequestException;
import hmo.crud.domain.exception.NotFoundException;
import hmo.crud.domain.mapper.BeerMapper;
import hmo.crud.service.BeerServiceImpl;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.Random;

import static hmo.crud.UserMessage.BEER_NOT_FOUND;
import static java.lang.String.format;

@Api(produces = "application/json;charset=UTF-8")
@Log4j2
@Setter
@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class BeerController {

    private static final String V1 = "/v1";

    @Autowired
    private BeerServiceImpl beerService;

    @Autowired
    private HmoAppMessageLoader hmoAppMessageLoader;

    @Autowired
    private BeerMapper beerMapper;

    private Random random = new Random();
    private String[] suggestion = {
            "Magic Trap", "Interstellar", "Unicorn Witbier", "Unicor Session IPA", "Abroba", "Easy IPA", "Guinness",
            "Opa Pilson", "Dama QI", "Imperio Lager", "Martina Witbier", "Martina IPA", "Barco Thai", "Serena Session"};

    @GetMapping("/test-get")
    public HmoResponse testResponse() {
        throw BadRequestException.builder()
                .developerMessage("Developer message test")
                .userMessage("User message test")
                .build();
    }

    @Timed("beer.create")
    @ApiOperation("Create a new beer")
    @PostMapping(V1 + "/beer")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateBeerResponse createBeer(@RequestBody @Valid CreateBeerRequest request) {
        BeerDto beerDto = beerService.createBeer(beerMapper.getBeerDto(request));
        return beerMapper.getCreateBeerResponse(beerDto);
    }

    @Scheduled(fixedRate = 3*60*1000) //each 3 min
    private void generateMetricsForPostBeer() {
        String beer = suggestion[random.nextInt(suggestion.length)];
        log.info("Trying to create new beer "+beer);

        createBeer(new CreateBeerRequest(beer));
    }

    @Timed("beer.find-by-id")
    @ApiOperation("Find a beer by its ID")
    @GetMapping(V1 + "/beer/{beerUid}")
    @ResponseStatus(HttpStatus.OK)
    public GetBeerResponse getBeer(@PathVariable Long beerUid) {
        BeerDto beerDto = beerService.getBeer(beerUid).orElseThrow(() ->
                        NotFoundException.builder()
                                .developerMessage(format("Beer [%d] not found", beerUid))
                                .userMessage(hmoAppMessageLoader.getUserMessage(BEER_NOT_FOUND))
                                .build() );
        GetBeerResponse response = beerMapper.getGetBeerResponse(beerDto);
        return  response;
    }

    @Scheduled(fixedRate = 3*1000) //each 3 sec
    private void generateMetricsForGetBeerById() {
        getBeer(1L + random.nextInt(suggestion.length));
    }

    @Timed("beer.find-by-name")
    @ApiOperation("Find a beer by its name")
    @GetMapping(V1 + "/beer")
    @ResponseStatus(HttpStatus.OK)
    public GetBeerResponse getBeerByName(@RequestParam String beerName) {
        BeerDto beerDto = beerService.getBeerByName(beerName).orElseThrow(() ->
                NotFoundException.builder()
                        .developerMessage(format("Beer [%d] not found", beerName))
                        .userMessage(hmoAppMessageLoader.getUserMessage(BEER_NOT_FOUND))
                        .build() );
        GetBeerResponse response = beerMapper.getGetBeerResponse(beerDto);
        return  response;
    }

    @Scheduled(fixedRate = 4*1000) //each 4 sec
    private void generateMetricsForGetBeerByName() {
        String beer = suggestion[random.nextInt(suggestion.length)];
        getBeerByName(beer);
    }
}
