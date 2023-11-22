package us.l4_4.dp1.end_of_line.enums;

public enum Reaction {
    HI("hi"),
    WHAT_A_PITY("what a pitty"),
    SORRY("sorry"),
    THANKS("thanks"),
    NICE("nice"),
    JAHAJAHA("jajajaja"),
    GG("gg"),
    GOOD_LUCK("good luck");

    private String value;

    Reaction(String value) {
        this.value = value + "!";
    }

    public String getValue() {
        return value;
    }
}
