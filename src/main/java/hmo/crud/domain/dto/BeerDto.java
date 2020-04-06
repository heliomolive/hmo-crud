package hmo.crud.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerDto {
    private Long beerUid;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String name;
}
