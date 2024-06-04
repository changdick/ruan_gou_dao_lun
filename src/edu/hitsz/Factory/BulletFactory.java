package edu.hitsz.Factory;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;


public class BulletFactory {
    public static BaseBullet getBullet(String bulletType , int locationX , int locationY , int speedX, int speedY, int power) {
        if (bulletType == null) {
            return null;
        }
        if (bulletType.equalsIgnoreCase("HeroBullet")) {
            // 等一下写参数
            return new HeroBullet(locationX , locationY , speedX ,speedY, power);
        } else if (bulletType.equalsIgnoreCase("EnemyBullet")) {
            return new EnemyBullet(locationX , locationY , speedX ,speedY, power);
        }

        return null;
    }
}
