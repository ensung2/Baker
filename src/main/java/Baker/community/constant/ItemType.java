package Baker.community.constant;

public enum ItemType {
    BREAD("BREAD"),COOKIE("COOKIE"),CAKE("CAKE");

    private final String description;

    ItemType(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
