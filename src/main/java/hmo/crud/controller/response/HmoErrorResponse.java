package hmo.crud.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@ApiModel("Error Response")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class HmoErrorResponse extends HmoResponse {

    @ApiModelProperty("Developer message, provided for development support")
    private String developerMessage;

    @ApiModelProperty("User message, can be exhibited for final user")
    private String userMessage;

    @Builder
    public HmoErrorResponse(String url, String developerMessage, String userMessage) {
        super(url);
        this.developerMessage = developerMessage;
        this.userMessage = userMessage;
    }
}
