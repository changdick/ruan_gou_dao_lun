package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.strategy.DirectShootStratege;
import edu.hitsz.strategy.ScatterShootStrategy;

public class PropBullet extends AbstractProp{
    public PropBullet(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }
    //2024/4/4 15:27 加入道具类的work方法，在父类中加抽象方法并在子类中重写实现，传入英雄机并调用英雄机的加子弹数量方法
    // 因为第一次实验没要求实现，就先做一个输出语句
    @Override
    public void work(HeroAircraft heroAircraft){
//        System.out.println("FireSupply active!");
        AbstractProp.currentWorkingID = this.getID();
        heroAircraft.setShootNum(3);
        heroAircraft.setShootStrategy(new ScatterShootStrategy());
        Runnable r = () -> {
            try {
                for (int i = 0; i < 3; i++) {
                    Thread.sleep(2000);
                }
                if (this.getID() == AbstractProp.currentWorkingID) {
                    heroAircraft.setShootNum(1);
                    heroAircraft.setShootStrategy(new DirectShootStratege());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        // 启动线程
        new Thread(r).start();




    }
}
