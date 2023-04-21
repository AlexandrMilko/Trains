public enum Passengers{
    PEOPLE, ELVES, ALIENS;
    public static Passengers pickRandomPassengers(){
        int goodsChoice = (int)Math.round(Math.random()*LightGoods.values().length);
        switch(goodsChoice){
            case 0  -> { return PEOPLE; }
            case 1  -> { return ELVES; }
            default -> { return ALIENS; }
        }
    }
}
