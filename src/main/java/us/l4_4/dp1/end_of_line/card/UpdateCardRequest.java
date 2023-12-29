package us.l4_4.dp1.end_of_line.card;

import jakarta.validation.constraints.NotNull;
import us.l4_4.dp1.end_of_line.enums.Orientation;


public class UpdateCardRequest {

    @NotNull
    private Integer row;

    @NotNull    
    private Integer column;

    @NotNull
    private String orientation;

    public Integer getRow() {
        return row;
    }

    public Integer getColumn() {
        return column;
    }

    public String getOrientation() {
        return Orientation.valueOf(orientation).toString();
    }
}
