package hmo.crud.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@ApiModel(description = "Get beer response")
@Data
@NoArgsConstructor
public class GetBeerResponse extends HmoResponse {

    @ApiModelProperty("Beer unique ID")
    private Long beerUid;

    @ApiModelProperty("Beer creation date")
    private LocalDateTime createDate;

    @ApiModelProperty("Beer update date")
    private LocalDateTime updateDate;

    @ApiModelProperty("Beer name")
    private String beerName;

    @Builder
    public GetBeerResponse(String url, Long beerUid, LocalDateTime createDate, LocalDateTime updateDate, String name) {
        super(url);
        this.beerUid = beerUid;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.beerName = name;
    }
}
