package edu.hitsz.application;

import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.application.music.MusicThread;

public class HardGame extends Game{
    public HardGame() {
        super();
        this.BACKGROUND_IMAGE = ImageManager.BACKGROUND_IMAGE_3;
    }

    //*****************************************************
    //     控制难度的关键方法， 子类里面重写即可体现难度差别
    //*****************************************************
    /**
     * scoreForBoss 方法用score属性判断当前分数是否符合Boss机生成的条件，把Boss机生成条件中的分数部分专门抽出来，各种难度进行重写即可
     * @return score满足 50、250、450、650 等生成boss
     */
    @Override
    protected Boolean scoreForBoss () {
        return score % 100 >= 50 && score % 100 <= 60;
    }
    /**
     * 难度初始化，构造类的时候调用一次，会设置好各种频率、数据
     */
    @Override
    protected void difficultyInitial(){
        enemyCreateDuration = 600;                        // 设置敌机产生周期
        enemyShootDuration = 600;                         // 设置敌机射击周期
        difficultyChangeDuration = 9000;     // 设置难度动态增加的周期 困难模式设9000

        mobEnemyFactory.setHp(30);
        eliteEnemyFactory.setHp(50);                   // 初始化敌机的血量、速度等
        superEliteEnemyFactory.setHp(50);
        bossEnemyFactory.setHp(800);


        boundSuperElite = 0.36;
        boundElite = 0.36;                         //初始化精英机和超级精英机生成概率， 各0.36


    }
    /**
     * 在时间到一定条件时，动态的改变难度
     */
    @Override
    protected void difficultyChangeAction() {
        //2024/5/13 15：22 实验6 简单地把敌机创建、低级发射子弹的周期减少40ms
        enemyShootDuration = enemyShootDuration - 40;
        enemyCreateDuration = enemyCreateDuration - 40;
        // 调整生成的敌机的速度、血量，后面敌机会变成高速导弹
        eliteEnemyFactory.increaseHp(10);
        eliteEnemyFactory.increaseSpeedY(5);
        superEliteEnemyFactory.increaseHp(10);
        superEliteEnemyFactory.increaseSpeedY(5);

        // 调整屏幕上敌机的数量
        enemyMaxNumber += 1;

        // 增加精英怪生成概率
        boundSuperElite += 0.02;
        boundElite += 0.02;

        System.out.println(STR."难度提升！当前的敌机射击周期为\{enemyShootDuration};当前敌机创建的周期为\{enemyCreateDuration};"
                + STR."\n精英机、超级精英机的速度提高了5，血量增加了10, 屏幕上敌机最大数量增加至\{enemyMaxNumber}\n"
                + STR."精英机的生成概率为\{boundElite},超级精英机的生成概率为\{boundSuperElite}");


    }

    // 重写Boss生成的方法


    @Override
    protected void checkBoss() {
        // 检查score的大小
//        2024/4/18 15：19 实验3 修改bossAlive的判断，这个bossAlive 改成了boss类里面的静态私有变量, 用它的访问方法来调用
        if (scoreForBoss() && !BossEnemy.isBossAlive()) {
            enemyAircrafts.add(bossEnemyFactory.getEnemyAircraft());
//            bossAlive = true;  2024/4/17 15：17  实验3 修改删去，这些功能不应该在game里判断
//          // 2024/5/9 为了让boss机的bgm在boss机每次生成的时候播放，每次boss机摧毁或者游戏结束时停止，必须每次新建boss机的时候new一个线程，不能只new一次重复开启是不行的
            boss_musicThread = new MusicThread("src/videos/bgm_boss.wav",true);
            boss_musicThread.start();
            bossEnemyFactory.increaseHp(30);  //每一只boss的血量都增加30
            System.out.println("下一只Boss机的血量将增加30");
        }

    }
}
