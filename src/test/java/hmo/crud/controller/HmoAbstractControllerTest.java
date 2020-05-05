package hmo.crud.controller;

import hmo.crud.config.HmoTestConfig;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;

@WebMvcTest
@Import(HmoTestConfig.class)
public abstract class HmoAbstractControllerTest {

}
