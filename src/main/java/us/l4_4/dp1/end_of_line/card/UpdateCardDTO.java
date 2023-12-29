package us.l4_4.dp1.end_of_line.card;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCardDTO{

    Integer colum;
    Integer row;
    String orientation;
    Integer gamePlayerId;


}