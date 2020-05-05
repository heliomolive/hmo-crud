package hmo.crud.mother;

import hmo.crud.repository.entity.Beer;

import java.time.LocalDateTime;

public class BeerMother {

    private BeerMother() {}

    public static Beer getUnicornWittBeer() {
        return getUnicornWittBeer(2819L);
    }

    public static Beer getUnicornWittBeer(Long beerUid) {
        Beer beer = Beer.builder().build();
        beer.setBeerUid(beerUid);
        beer.setCreateDate(LocalDateTime.now());
        beer.setName("Unicorn witt beer");
        beer.setUpdateDate(LocalDateTime.now());
        return beer;
    }
}
