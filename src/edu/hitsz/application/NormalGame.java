package edu.hitsz.application;

public class NormalGame extends Game{
    public NormalGame() {
        super();
        this.BACKGROUND_IMAGE = ImageManager.BACKGROUND_IMAGE_2;
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
        return score % 200 == 50;
    }
    /**
     * 难度初始化，构造类的时候调用一次，会设置好各种频率、数据
     */
    @Override
    protected void difficultyInitial(){
        enemyCreateDuration = 600;                        // 设置敌机产生周期
        enemyShootDuration = 600;                         // 设置敌机射击周期
        difficultyChangeDuration = 12000;     // 设置难度动态增加的周期 一般难度设置12000

        mobEnemyFactory.setHp(30);
        eliteEnemyFactory.setHp(50);                   // 初始化敌机的血量、速度等
        superEliteEnemyFactory.setHp(50);

        boundSuperElite = 0.33;
        boundElite = 0.33;                         //初始化精英机和超级精英机生成概率， 各三分之1


    }
    /**
     * 在时间到一定条件时，动态的改变难度
     */
    @Override
    protected void difficultyChangeAction() {
        //2024/5/13 15：22 实验6 简单地把敌机创建、低级发射子弹的周期减少40ms
        enemyShootDuration -= 40;
        enemyCreateDuration -= 40;
        // 调整生成的敌机的速度、血量，后面敌机会变成高速导弹
        eliteEnemyFactory.increaseHp(10);
        eliteEnemyFactory.increaseSpeedY(2);
        superEliteEnemyFactory.increaseHp(10);
        superEliteEnemyFactory.increaseSpeedY(2);

        // 调整屏幕上敌机的数量
        enemyMaxNumber += 1;

        // 增加精英机和超级精英机的生成概率
        boundElite += 0.02;
        boundSuperElite += 0.02;


        System.out.println(STR."难度提升！当前的敌机射击周期为\{enemyShootDuration};当前敌机创建的周期为\{enemyCreateDuration};"
                         + STR."\n精英机、超级精英机的速度提高了2，血量增加了10, 屏幕上敌机最大数量增加至\{enemyMaxNumber}\n"
                         + STR."精英机的生成概率为\{boundElite},超级精英机的生成概率为\{boundSuperElite}");
    }

}
