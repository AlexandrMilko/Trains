public enum HeavyGoods {
    IRON, GOLD;
    public static HeavyGoods pickRandomGoods(){
        int goodsChoice = (int)Math.round(Math.random()*HeavyGoods.values().length);
        switch(goodsChoice){
            case 0  -> { return IRON; }
            default -> { return GOLD; }
        }
    }
}
