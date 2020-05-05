package hmo.crud.config;

import hmo.crud.domain.mapper.BeerMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource(value = "classpath:/application.properties", encoding = "UTF-8")
})
public class HmoTestConfig {

    @Bean
    public BeerMapper getBeerMapper() {
        return Mappers.getMapper(BeerMapper.class);
    }
}
