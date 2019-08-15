package Input;

public enum ValidCommands {
    NEWPRODUCT,
    PURCHASE,
    DEMAND,
    SALESREPORT;

    public static boolean contains(String s) {
        for(ValidCommands item : ValidCommands.values()){
            if(item.name().equals(s)){
                return true;
            }
        }
        return false;
    }
}
