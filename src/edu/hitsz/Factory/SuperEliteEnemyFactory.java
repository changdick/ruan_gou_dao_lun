package edu.hitsz.Factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.SuperEliteEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

import java.util.Random;

// 2024/4/17 17:15 实验3 第二次添加 加完超级精英机，添加他的创建方法，以使用工厂模式创建
// 2024/4/17 17：28 实验3 第五次修改 精英敌机左右移动，要设置speedX，并且要有个随机左右
public class SuperEliteEnemyFactory extends EnemyFactory{


    public SuperEliteEnemyFactory() {
        this.speedX = 3;
        this.speedY = 10;
        this.hp = 50;
    }

    private static Random random = new Random();


    public AbstractAircraft getEnemyAircraft() {
//        加一个正负1的随机
        int right = random.nextBoolean() ? 1 : -1;
        return new SuperEliteEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                speedX * right,
                speedY,
                hp);
    }


}
