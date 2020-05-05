package hmo.crud.mother;

import hmo.crud.controller.request.CreateBeerRequest;

public class CreateBeerRequestMother {

    private CreateBeerRequestMother() {}

    public static CreateBeerRequest getCreateBeerRequest(String beerName) {
        return CreateBeerRequest.builder()
                .beerName(beerName)
                .build();
    }
}
