package edu.hitsz.Factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class EliteEnemyFactory extends EnemyFactory {

    public EliteEnemyFactory() {
        this.speedX = 0;
        this.speedY = 10;
        this.hp = 50;
    }

    public AbstractAircraft getEnemyAircraft() {
        return new EliteEnemy(
                    (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                    (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                    speedX,
                    speedY,
                    hp);
    }

}
