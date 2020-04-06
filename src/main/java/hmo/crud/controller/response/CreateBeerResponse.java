package hmo.crud.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@ApiModel(description = "Create beer response")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBeerResponse extends HmoResponse {

    @ApiModelProperty("Beer unique ID")
    private Long beerUid;

    @ApiModelProperty("Beer creation date")
    private LocalDateTime createDate;

    @ApiModelProperty("Beer name")
    private String beerName;
}
