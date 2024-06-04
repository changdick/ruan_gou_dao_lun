package edu.hitsz.Factory;


import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.application.Main;



// 2024/4/17 18:41 第7次添加 加入boss的工厂
public class BossEnemyFactory extends EnemyFactory {

    public BossEnemyFactory() {
        this.speedY = 0;
        this.speedX = 4;
        this.hp = 120;
    }

    public  AbstractAircraft getEnemyAircraft() {
        return new BossEnemy(
                Main.WINDOW_WIDTH / 2,
                (int) (Main.WINDOW_HEIGHT * 0.15),
                speedX,
                speedY,
                hp
        );
    }

}
