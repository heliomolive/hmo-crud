package hmo.crud.domain.mapper;

import hmo.crud.domain.dto.BeerDto;
import hmo.crud.mother.BeerMother;
import hmo.crud.repository.entity.Beer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class BeerMapperTest {

    private BeerMapper beerMapper = new BeerMapperImpl();

    @Test
    public void convertToBeerDto() {

        Beer beer = BeerMother.getUnicornWittBeer();

        BeerDto beerDto = beerMapper.getBeerDto(beer);

        assertNotNull(beerDto);
        assertEquals(beer.getBeerUid(), beerDto.getBeerUid());
        assertEquals(beer.getCreateDate(), beerDto.getCreateDate());
        assertEquals(beer.getName(), beerDto.getName());
        assertEquals(beer.getUpdateDate(), beerDto.getUpdateDate());
    }
}
