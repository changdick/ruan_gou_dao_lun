package edu.hitsz.Factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;


// 2024/4/10 17:06 实验2 第二次修改 先创建敌机的工厂
//
//public class EnemyFactory {
//    /**
//     *
//     * @param enemyType
//     * @return 返回创建的敌机实例
//     */
//    // 创建方法 返回的应该是抽象产品 AbstractAircraft
//    public AbstractAircraft getEnemyAircraft(String enemyType) {
//        if (enemyType == null) {
//            return null;
//        }
//        if (enemyType.equalsIgnoreCase("MobEnemy")) {
//            // 2024/4/10 17:06 实验2 第二次修改 创建MobEnemy型的时候读入的参数从game里的复制过来
//
//            return new MobEnemy(
//                    (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
//                    (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
//                    0,
//                    10,
//                    30);
//
//        } else if (enemyType.equalsIgnoreCase("EliteEnemy")) {
//
//            return new EliteEnemy(
//                    (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
//                    (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
//                    0,
//                    10,
//                    50
//            );
//
//        }
//
//        return null;
//    }
//
//}

// 上边是简单工厂模式，改成实验指导书的思路
public abstract class EnemyFactory {
    protected  int hp ;
    protected  int speedX;
    protected  int speedY;
    abstract public AbstractAircraft getEnemyAircraft();

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public void increaseHp(int increase) {
        this.hp += increase;
    }
    public void increaseSpeedY(int increase) {
        this.speedY += increase;
    }
}

// 2024/5/16 10：57 考虑到随难度变化想动态地调整敌机飞机的速度和血量等参数，想法是作为工厂的静态变量，在需要改的时候就改变工厂的静态变量，
//从而实现血量速度的动态增长

// 为了实现，把敌机工厂由接口改成抽象类
