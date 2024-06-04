package edu.hitsz.Factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class MobEnemyFactory extends EnemyFactory{

    public MobEnemyFactory() {
        this.hp = 30;
        this.speedX = 0;
        this.speedY = 10;
    }

    public AbstractAircraft getEnemyAircraft() {

        return new MobEnemy(
                    (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                    (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                    speedX,
                    speedY,
                    hp);
    }

}
