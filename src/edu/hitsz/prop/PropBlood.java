package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;

public class PropBlood extends AbstractProp {
    public PropBlood (int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }


    //2024/4/4 15:04 加入道具类的work方法，在父类中加抽象方法并在子类中重写实现，传入英雄机并调用英雄机的加血方法
    @Override
    public void work(HeroAircraft heroAircraft){
        heroAircraft.addHp(100);
    }
}
