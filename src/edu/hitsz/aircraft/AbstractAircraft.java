package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.strategy.ShootStrategy;

import java.util.List;

/**
 * 所有种类飞机的抽象父类：
 * 敌机（BOSS, ELITE, MOB），英雄飞机
 *
 * @author hitsz
 */
public abstract class AbstractAircraft extends AbstractFlyingObject {
    /**
     * 生命值
     */
    protected int maxHp;
    protected int hp;

    /**
     * 飞机类射击的子弹数量、伤害、子弹方向
     */
    // 2024/4/26 20：56 实验4 第一次修改 本来是在几个子类飞机中的量，只有普通敌机不需要，就提到上面来
    protected int shootNum;
    protected int power;
    protected int direction;

    /**
     * 飞机的射击策略
     */
    protected ShootStrategy shootStrategy;
    protected String bulletType;
    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
    }

    public void decreaseHp(int decrease){
        hp -= decrease;
        if(hp <= 0){
            hp=0;
            vanish();
        }
    }

//    2024/4/4 14:40 添加加血方法，用于吃到加血道具时调用该方法
    public void addHp(int add){
        hp += add;
        if (hp > maxHp) {
            hp = maxHp;
        }
    }

    public int getHp() {
        return hp;
    }


//    /**
//     * 所有敌机类（普通低级、精英敌机、超级精英敌机、boss机的移动方法）
//     * 增加了y轴出界的判断
//     */
//    // 2024/4/26 实验4 发现 forward方法所有敌机都一样，应该提取出来到飞机类先放着不动，因为英雄机不一样
//    @Override
//    public void forward() {
//        super.forward();
//        // 判定 y 轴向下飞行出界
//        if (locationY >= Main.WINDOW_HEIGHT ) {
//            vanish();
//        }
//    }

    /**
     * 飞机射击方法，可射击对象必须实现
     * @return
     *  可射击对象需实现，返回子弹
     *  非可射击对象空实现，返回null
     */
    // 2024/5/6 22：38 实验4修改，改用策略模式，shoot方法直接提到父类而且不是抽象方法，子类具体飞机只要设置好射击策略即可
//    public abstract List<BaseBullet> shoot();

    public void setShootNum(int shootNum) {
        this.shootNum = shootNum;
    }

    public int getShootNum() {
        return shootNum;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getDirection() {
        return direction;
    }

    public void setShootStrategy(ShootStrategy shootStrategy) {
        this.shootStrategy = shootStrategy;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public List<BaseBullet> shoot() {
        return this.shootStrategy.doshoot(this, bulletType);
    }

}


