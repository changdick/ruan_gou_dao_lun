package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;



public abstract class ShootStrategy {
    /**
     * 发射子弹的方法，策略本身与发射者是什么飞机、发射什么子弹无关，方法读入发射的飞机和子弹类型，飞机本身保存发射子弹用到的数据
     * @param abstractAircraft
     * @param bulletType
     * @return
     */
    abstract public List<BaseBullet> doshoot(AbstractAircraft abstractAircraft, String bulletType);
}
