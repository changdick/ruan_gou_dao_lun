package edu.hitsz.prop;


import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.basic.AbstractFlyingObject;

/**
 * 所有道具类的父类
 * 加血道具
 */
public abstract class AbstractProp extends AbstractFlyingObject {

    /*
     * 2024/5/10 9：02 实验5 增加了propGotten、ID这几个量，本来子弹类生效统计生效时间的时候，连续吃到多个道具时，第一个道具计时线程结束就会
     * 把射击方式改回来，导致后吃的道具实际生效时间没有达到预期
     * 解决方法：线程计时到的时候，先核对当前生效道具ID
     */


    /**
     * propGotten 是由道具类拥有的私有静态变量，每创建一个道具，这个量自增一，实际上就是统计道具的个数，用于构建所有道具的ID
     */
    private static int propGotten;
    /**
     * ID是每个道具对象的编号,第几个生成的对象，它的ID就是多少，ID用于子弹类生效时间的判断
     */
    private int ID;
    /**
     * currentWorkingID protected的，当前生效的道具的ID 在子弹道具用到，子弹道具生效时更新，子弹道具计时到的时候，检查ID是否与当前工作ID一致，一致才改回直射
     */
    protected static int currentWorkingID;


    /**
     *
     * @param locationX
     * @param locationY
     * @param speedX
     * @param speedY
     */
    public AbstractProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
        propGotten += 1;
        this.ID = propGotten;
    }
    public abstract void work(HeroAircraft heroAircraft);

    public int getID() {
        return ID;
    }
}
