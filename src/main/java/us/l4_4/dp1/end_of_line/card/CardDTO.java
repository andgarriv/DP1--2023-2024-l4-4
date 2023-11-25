package us.l4_4.dp1.end_of_line.card;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import us.l4_4.dp1.end_of_line.enums.Orientation;


@Getter
@Setter
public class CardDTO {
    @NotNull
    Integer id;

    @NotNull
    @Range(min = 0, max = 5)
    Integer initiative;

    @NotBlank
    String exit;

    //@NotBlank
    //String image;
    
    @NotNull
    @Range(min = 0, max = 6)
    Integer card_row;
    @NotNull
    @Range(min = 0, max = 6)
    Integer card_column;


    @NotBlank
    String orientation;
    @NotBlank
    String card_statu;    
}
