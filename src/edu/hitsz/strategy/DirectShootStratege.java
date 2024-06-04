package edu.hitsz.strategy;

import edu.hitsz.Factory.BulletFactory;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class DirectShootStratege extends ShootStrategy{

    @Override
    public List<BaseBullet> doshoot(AbstractAircraft abstractAircraft, String bulletType) {
        List<BaseBullet> res = new LinkedList<>();
        int x = abstractAircraft.getLocationX();
        int y = abstractAircraft.getLocationY() + abstractAircraft.getDirection()*2;
        int speedX = 0;
        int speedY = abstractAircraft.getSpeedY() + abstractAircraft.getDirection()*8;
        BaseBullet bullet;
        for(int i=0; i<abstractAircraft.getShootNum(); i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            bullet = BulletFactory.getBullet(bulletType,x + (i*2 - abstractAircraft.getShootNum() + 1)*10, y, speedX, speedY, abstractAircraft.getPower());
            res.add(bullet);
        }
        return res;

    }

}
