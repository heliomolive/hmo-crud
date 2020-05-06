package hmo.crud.repository;

import hmo.crud.mother.BeerMother;
import hmo.crud.repository.entity.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BeerRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private BeerRepository fixture;

    @Test
    public void saveBeer() {
        Beer beer = BeerMother.getUnicornWittBeer(null);

        Beer savedBeer = fixture.save(beer);

        assertNotNull(savedBeer);
        assertEquals(beer, savedBeer);
    }

    @Test
    public void findByName() {
        Beer beer = fixture.save(BeerMother.getUnicornWittBeer());

        Optional<Beer> beer1 = fixture.findByName(beer.getName());

        assertTrue(beer1.isPresent());
        assertEquals(beer.getBeerUid(), beer1.get().getBeerUid());
    }

    @Test
    public void findByName_beerNotFound() {
        Optional<Beer> beer = fixture.findByName("test beer");

        assertTrue(beer.isEmpty());
    }

}
