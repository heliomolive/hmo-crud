package hmo.crud.domain.mapper;


import hmo.crud.controller.request.CreateBeerRequest;
import hmo.crud.controller.response.CreateBeerResponse;
import hmo.crud.controller.response.GetBeerResponse;
import hmo.crud.domain.dto.BeerDto;
import hmo.crud.repository.entity.Beer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE) //ignore unmapped properties on compilation report
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
