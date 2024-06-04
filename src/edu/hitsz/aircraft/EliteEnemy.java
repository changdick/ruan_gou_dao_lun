package edu.hitsz.aircraft;


import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.strategy.DirectShootStratege;
import edu.hitsz.strategy.ScatterShootStrategy;

import java.util.LinkedList;
import java.util.List;

/**
 * 精英敌机
 * 可以射击
 *
 * @author hitsz
 */
public class EliteEnemy extends AbstractAircraft{
//    /**攻击方式 */
//
//    /**
//     * 子弹一次发射数量
//     */
//    private int shootNum = 1;
//
//    /**
//     * 子弹伤害
//     */
//    private int power = 10;
//
//    /**
//     * 子弹射击方向 (向上发射：1，向下发射：-1)
//     */
//    private int direction = 1;


    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        // 攻击方式
        setShootNum(1);
        setPower(10);
        setDirection(1);
        setShootStrategy(new DirectShootStratege());
        this.bulletType = "EnemyBullet";
    }


    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }


//    @Override
//
//    public List<BaseBullet> shoot() {
////        List<BaseBullet> res = new LinkedList<>();
////        int x = this.getLocationX();
////        int y = this.getLocationY() + direction*2;
////        int speedX = 0;
////        int speedY = this.getSpeedY() + direction*3;
////        BaseBullet bullet;
////        for(int i=0; i<shootNum; i++){
////            // 子弹发射位置相对飞机位置向前偏移
////            // 多个子弹横向分散
////            bullet = new EnemyBullet(x + (i*2 - shootNum + 1)*10, y, speedX, speedY, power);
////            res.add(bullet);
////        }
////        return res;
//        return shootStrategy.doshoot(this, "EnemyBullet");
//    }

    /**
     * 精英敌机的vanish方法，调用vanish的同时会进行加分，并且可以直接在这里修改加分的分值
     */
    @Override
    public void vanish() {
        super.vanish();
        Main.game.addScore(10);
    }
}
