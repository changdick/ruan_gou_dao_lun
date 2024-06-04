package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.strategy.DirectShootStratege;

import java.util.LinkedList;
import java.util.List;

/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

//    /**攻击方式 */
//
//    /**
//     * 子弹一次发射数量
//     */
//    shootNum = 1;
//
//    /**
//     * 子弹伤害
//     */
//    private int power = 30;
//
//    /**
//     * 子弹射击方向 (向上发射：1，向下发射：-1)
//     */
//    private int direction = -1;


    // 实验2 第一次修改 改成单例模式 增加一个单例静态实例
    private static HeroAircraft instance;
    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp    初始生命值
     */

    // 实验2 第一次修改 改成单例模式，把构造方法设置成私有 public ->  private
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        //2024/4/26 21:02 本来设置的这些值放在构造函数里面设置即可
        setShootNum(1);
        setPower(30);
        setDirection(-1);
        setShootStrategy(new DirectShootStratege());
        this.bulletType = "HeroBullet";
    }

    // 实验2 第一次修改 改成单例模式，增加一个给外部访问唯一实例的静态方法.  在外面用HeroAircraft.getInstance() 访问实例
    public static HeroAircraft getInstance() {
        if (instance == null) {
            // 双重检查锁定 保证线程安全
            synchronized (HeroAircraft.class) {
                if(instance == null) {
                    // 在这里创建唯一实例 ， 括号里应该填各项创建的参数
                    instance = new HeroAircraft(
                            Main.WINDOW_WIDTH / 2,
                            Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight() ,
                            0, -2 , 1000);

                }
            }
        }
        return instance;
    }
    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }


    //2024/5/6 22：39 实验4修改 这个shoot方法提到父类，不需要在子类重写，子类设置好射击策略和子弹类型即可
//    @Override
//    /**
//     * 通过射击产生子弹
//     * @return 射击出的子弹List
//     */
//    public List<BaseBullet> shoot() {
////        List<BaseBullet> res = new LinkedList<>();
////        int x = this.getLocationX();
////        int y = this.getLocationY() + direction*2;
////        int speedX = 0;
////        int speedY = this.getSpeedY() + direction*8;
////        BaseBullet bullet;
////        for(int i=0; i<shootNum; i++){
////            // 子弹发射位置相对飞机位置向前偏移
////            // 多个子弹横向分散
////            bullet = new HeroBullet(x + (i*2 - shootNum + 1)*10, y, speedX, speedY, power);
////            res.add(bullet);
////        }
////        return res;
//        return shootStrategy.doshoot(this, "HeroBullet");
//    }


    // 2024/4/19 21:46 实验3 修改 为了搞测试添加一个访问shootnum的方法，还加修改的方法
//    public int getShootNum() {
//        return shootNum;
//}

}
