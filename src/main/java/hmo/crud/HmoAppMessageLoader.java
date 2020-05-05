package hmo.crud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import static hmo.crud.UserMessage.BEER_ALREADY_EXISTS;
import static hmo.crud.UserMessage.BEER_NOT_FOUND;

@Component
public class HmoAppMessageLoader {
    private static final String BEER_NOT_FOUND_KEY = "hmo-crud.beer.not.found";
    private static final String BEER_ALREADY_EXISTS_KEY = "hmo-crud.beer.already.exists";

    @Value("${"+BEER_NOT_FOUND_KEY+"}")
    private String beerNotFoundMessage;

    @Value("${"+BEER_ALREADY_EXISTS_KEY+"}")
    private String beerAlreadyExistsMessage;

    private Map<UserMessage, String> userMessage;

    @PostConstruct
    public void setup() {
        userMessage = new HashMap<>();
        userMessage.put(BEER_NOT_FOUND, beerNotFoundMessage);
        userMessage.put(BEER_ALREADY_EXISTS, beerAlreadyExistsMessage);
    }

    public String getUserMessage(UserMessage userMessage) {
        return this.userMessage.get(userMessage);
    }
}
