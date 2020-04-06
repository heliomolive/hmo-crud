package hmo.crud.domain.mapper;


import hmo.crud.controller.request.CreateBeerRequest;
import hmo.crud.controller.response.CreateBeerResponse;
import hmo.crud.controller.response.GetBeerResponse;
import hmo.crud.domain.dto.BeerDto;
import hmo.crud.repository.entity.Beer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BeerMapper {

    BeerDto getBeerDto(Beer beer);

    Beer getBeer(BeerDto beerDto);

    @Mapping(target = "name", source = "beerName")
    BeerDto getBeerDto(CreateBeerRequest createBeerRequest);

    @Mapping(target = "beerName", source = "name")
    CreateBeerResponse getCreateBeerResponse(BeerDto beerDto);

    @Mapping(target = "beerName", source = "name")
    GetBeerResponse getGetBeerResponse(BeerDto beerDto);
}
