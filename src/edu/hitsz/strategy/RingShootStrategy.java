package edu.hitsz.strategy;

import edu.hitsz.Factory.BulletFactory;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;

import java.util.LinkedList;
import java.util.List;

public class RingShootStrategy extends ShootStrategy{

    @Override
    public List<BaseBullet> doshoot(AbstractAircraft abstractAircraft, String bulletType) {
        List<BaseBullet> res = new LinkedList<>();
        int x = abstractAircraft.getLocationX();
        int y = abstractAircraft.getLocationY() + abstractAircraft.getDirection()*2;
//        speed 为速度的大小，用speed乘三角函数得到xy上具体速度
        int speed = 10;
        double gap = 2 * Math.PI / abstractAircraft.getShootNum();
        double theta = 0;

        BaseBullet bullet;
        for (int i = 0; i < abstractAircraft.getShootNum(); i++ , theta += gap) {
//            因为是圆形散射弹道，就让所有的子弹在同一x、y坐标发射，不做偏移
            bullet = BulletFactory.getBullet(bulletType,x, y, (int)(speed * Math.cos(theta)) , (int)(speed * Math.sin(theta)), abstractAircraft.getPower());
            res.add(bullet);
        }

        return res;
    }
}
