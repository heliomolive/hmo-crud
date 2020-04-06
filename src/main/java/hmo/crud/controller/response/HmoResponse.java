package hmo.crud.controller.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HmoResponse {

    @ApiModelProperty("API URL called")
    private String url;
}
