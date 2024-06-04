package edu.hitsz.strategy;

import edu.hitsz.Factory.BulletFactory;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;

import java.util.LinkedList;
import java.util.List;

public class ScatterShootStrategy extends ShootStrategy{

    @Override
    public List<BaseBullet> doshoot(AbstractAircraft abstractAircraft, String bulletType) {
        List<BaseBullet> res = new LinkedList<>();
        int x = abstractAircraft.getLocationX();
        int y = abstractAircraft.getLocationY() + abstractAircraft.getDirection()*2;
//        为了实现子弹散射，设置一个子弹横向速度间隔，并且设置speedX初始的值，在循环中按照gap改变speedX
        int speedXgap = 3;
        int speedX = -3;
        int speedY = abstractAircraft.getSpeedY() +abstractAircraft.getDirection()*8;
        BaseBullet bullet;
//        2024/4/17 实验3 添加子弹散射的方式
        for (int i = 0; i < abstractAircraft.getShootNum(); i++, speedX += speedXgap) {
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            bullet = BulletFactory.getBullet(bulletType,x + (i*2 - abstractAircraft.getShootNum() + 1)*10, y, speedX, speedY, abstractAircraft.getPower());
            res.add(bullet);
        }
        return res;
    }

}
