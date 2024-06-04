
// 2024/4/17 17:00 实验3 第一次添加（做完矩阵测试后） 添加超级精英机类型

package edu.hitsz.aircraft;


import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.strategy.ScatterShootStrategy;

import java.util.LinkedList;
import java.util.List;

public class SuperEliteEnemy extends AbstractAircraft {
//    /**攻击方式 */
//
//    /**
//     * 子弹一次发射数量, 超级精英机是3个
//     */
//    private int shootNum = 3;
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

    /**
     * 超级精英机的构造方法
     */
    public SuperEliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        setShootNum(3);
        setPower(10);
        setDirection(1);
        setShootStrategy(new ScatterShootStrategy());
        this.bulletType = "EnemyBullet" ;
    }




//    超级精英机的forward方法
    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    @Override
    public void vanish() {
        super.vanish();
        Main.game.addScore(10);
    }
/**
     * 超级精英机的射击
     * @return
     */
//    为了发射3个子弹并且散射，应该创建3个子弹对象，每个子弹创建时给不同X方向速度

    //2024/5/6 22：39 实验4修改 这个shoot方法提到父类，不需要在子类重写，子类设置好射击策略和子弹类型即可
//    @Override
//    public List<BaseBullet> shoot() {
////        List<BaseBullet> res = new LinkedList<>();
////        int x = this.getLocationX();
////        int y = this.getLocationY() + direction*2;
//////        为了实现子弹散射，设置一个子弹横向速度间隔，并且设置speedX初始的值，在循环中按照gap改变speedX
////        int speedXgap = 3;
////        int speedX = -3;
////        int speedY = this.getSpeedY() + direction*3;
////        BaseBullet bullet;
//////        2024/4/17 实验3 添加子弹散射的方式
////        for (int i = 0; i < shootNum; i++, speedX += speedXgap) {
////            // 子弹发射位置相对飞机位置向前偏移
////            // 多个子弹横向分散
////            bullet = new EnemyBullet(x + (i*2 - shootNum + 1)*10, y, speedX, speedY, power);
////            res.add(bullet);
////        }
////        return res;
//        return shootStrategy.doshoot(this,"EnemyBullet");
//    }

    /**
     * 超级精英敌机对炸弹的update方法，超级精英机不会因炸弹生效而摧毁，只是扣一点血量
     */
    @Override
    public void updateBomb() {
        decreaseHp((int)(this.getHp() * 0.5));
    }
}
