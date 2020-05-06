package hmo.crud.controller;

import hmo.crud.domain.mapper.BeerMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ControllerTestConfig {

    @Bean
    @ConditionalOnMissingBean
    public BeerMapper getBeerMapper() {
        return Mappers.getMapper(BeerMapper.class);
    }
}
