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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static hmo.crud.UserMessage.BEER_NOT_FOUND;
import static java.lang.String.format;

@Api(produces = "application/json;charset=UTF-8")
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

    @GetMapping("/test-get")
    public HmoResponse testResponse() {
        throw BadRequestException.builder()
                .developerMessage("Developer message test")
                .userMessage("User message test")
                .build();
    }

    @Timed
    @ApiOperation("Create a new beer")
    @PostMapping(V1 + "/beer")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateBeerResponse createBeer(@RequestBody @Valid CreateBeerRequest request) {
        BeerDto beerDto = beerService.createBeer(beerMapper.getBeerDto(request));
        return beerMapper.getCreateBeerResponse(beerDto);

    }

    @Timed(value = "beer.get.timed")
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

    @ApiOperation("Find a beer by its ID")
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
}
