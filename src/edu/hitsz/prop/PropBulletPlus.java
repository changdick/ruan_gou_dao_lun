package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.strategy.DirectShootStratege;
import edu.hitsz.strategy.RingShootStrategy;

public class PropBulletPlus extends AbstractProp{

    public PropBulletPlus(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX,  speedY);
    }
    @Override
    public void work(HeroAircraft heroAircraft) {
        AbstractProp.currentWorkingID = this.getID();
        heroAircraft.setShootNum(20);
        heroAircraft.setShootStrategy(new RingShootStrategy());
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
