package hmo.crud.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;

@WebMvcTest
@Import(ControllerTestConfig.class)
public abstract class AbstractControllerTest {

}
