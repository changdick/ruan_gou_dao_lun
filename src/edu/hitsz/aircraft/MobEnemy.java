package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.strategy.NullShootStrategy;

import java.util.LinkedList;
import java.util.List;

/**
 * 普通敌机
 * 不可射击
 *
 * @author hitsz
 */
public class MobEnemy extends AbstractAircraft {

    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        setShootStrategy(new NullShootStrategy());
        this.bulletType = "null";
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }
//

    //2024/5/6 22：39 实验4修改 这个shoot方法提到父类，不需要在子类重写，子类设置好射击策略和子弹类型即可
//    @Override
//    public List<BaseBullet> shoot() {
////        return new LinkedList<>();
//        return shootStrategy.doshoot(this, "null");
//    }



    @Override
    public void vanish() {
        super.vanish();
        Main.game.addScore(10);
    }
}
