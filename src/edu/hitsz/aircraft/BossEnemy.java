package edu.hitsz.aircraft;


import edu.hitsz.application.Main;
import edu.hitsz.application.music.MusicThread;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.strategy.RingShootStrategy;

import java.util.LinkedList;
import java.util.List;

// 2024/4/17 17:38 实验3 第六次添加 添加boss机
public class BossEnemy extends AbstractAircraft{
//2024/4/18 15:09 实验3 修改Boss机存活的判断方式，本来在Game类里设置了一个bossalive的标识，但有点不恰当，因为在game类里添加了修改boss是否存活的代码和判断
//    现在修改方式是 在Boss机里面设置一个全局是否有Boss的标识，重写boss类的vanish方法，让这个方法修改boss机是否存活,把boss机

    /**
     * Boss机 是否存活的标识
     */
    private static boolean bossAlive = false;


//
//    /**攻击方式 */
//
//    /**
//     * 子弹一次发射数量, boss机是20个
//     */
//    private int shootNum = 20;
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

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        bossAlive = true;
        //攻击方式
        setShootNum(20);
        setPower(10);
        setDirection(1);
        setShootStrategy(new RingShootStrategy());
        this.bulletType = "EnemyBullet";



    }

    /**
     * boss机的移动方法，主要是左右移动
     */
    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界 , 实际上boss机不会在Y轴上有速度
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }


    /**
     * Boss 机的摧毁方法，除了继承自抽象飞行物父类的方法之外，还要修改bossAlive的功能
     */
    @Override
    public void vanish() {
        super.vanish();
        bossAlive = false;
        Main.game.addScore(20);

    }
    //    boss机射击应该用三角函数控制它的方向和速度，例如速度的大小是10 ， 那就 x y速度 10cosΘ 10sinθ，θ就是一个圆等分20分

//2024/5/6 22：39 实验4修改 这个shoot方法提到父类，不需要在子类重写，子类设置好射击策略和子弹类型即可
//    /**
//     * Boss机的射击方法
//     * @return Boss机射出子弹的List，里面有20个方向不同的子弹
//     */
//    @Override
//    public List<BaseBullet> shoot() {
////        List<BaseBullet> res = new LinkedList<>();
//////        int x = this.getLocationX();
//////        int y = this.getLocationY() + direction*2;
////////        speed 为速度的大小，用speed乘三角函数得到xy上具体速度
//////        int speed = 10;
//////        double gap = 2 * Math.PI / shootNum;
//////        double theta = 0;
//////
//////        BaseBullet bullet;
//////        for (int i = 0; i < shootNum; i++ , theta += gap) {
////////            因为是圆形散射弹道，就让所有的子弹在同一x、y坐标发射，不做偏移
//////            bullet = new EnemyBullet(x, y, (int)(speed * Math.cos(theta)) , (int)(speed * Math.sin(theta)), power);
//////            res.add(bullet);
//////        }
//////
//////        return res;
//        return shootStrategy.doshoot(this, "EnemyBullet");
//    }

    /**
     * Boss机是否存在的判断方法 外界访问私有变量bossAlive的方法
     *
     */
    public static boolean isBossAlive() {
        return bossAlive;
    }

    /**
     * 重写Boss机的炸弹道具更新方法，Boss机也会作为炸弹道具观察者，炸弹道具对Boss机没有影响，所以重写成空的方法
     * 如果炸弹类需要对boss机有什么作用的话，直接在该方法加入语句即可
     */
    @Override
    public void updateBomb() {

    }
}
