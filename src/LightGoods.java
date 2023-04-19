public enum LightGoods{
    COAL, WOOD, SAND;
    public static LightGoods pickRandomGoods(){
        int goodsChoice = (int)Math.round(Math.random()*LightGoods.values().length);
        switch(goodsChoice){
            case 0  -> { return COAL; }
            case 1  -> { return WOOD; }
            default -> { return SAND; }
        }
    }
}
