package hmo.example.validation.mother;

import hmo.crud.repository.entity.Beer;

import java.time.LocalDateTime;

public class BeerMother {

    private BeerMother() {}

    public static Beer getUnicornWittBeer() {
        Beer beer = Beer.builder().build();
        beer.setBeerUid(2819L);
        beer.setCreateDate(LocalDateTime.now());
        beer.setName("Unicorn witt beer");
        beer.setUpdateDate(LocalDateTime.now());
        return beer;
    }
}
