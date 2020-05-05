package hmo.crud.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@ApiModel(description = "Create beer request")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBeerRequest {

    @NotEmpty(message = "Required parameter: beerName")
    @ApiModelProperty("Beer name")
    private String beerName;
}
