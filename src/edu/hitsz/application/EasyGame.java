package edu.hitsz.application;

public class EasyGame extends Game{
    public EasyGame() {
        super();
    }

    //*****************************************************
    //     控制难度的关键方法， 子类里面重写即可体现难度差别
    //*****************************************************
    /**
     * scoreForBoss 方法用score属性判断当前分数是否符合Boss机生成的条件，把Boss机生成条件中的分数部分专门抽出来，各种难度进行重写即可
     * @return false 简单难度不生成Boss
     */
    protected Boolean scoreForBoss () {
        return false;
    }
    /**
     * 难度初始化，构造类的时候调用一次，会设置好各种频率、数据
     */
    protected void difficultyInitial(){
        enemyCreateDuration = 600;                        // 设置敌机产生周期
        enemyShootDuration = 600;                         // 设置敌机射击周期
        difficultyChangeDuration = Integer.MAX_VALUE;     // 设置难度动态增加的周期 无穷大不会动态增加难度

        heroShootDuration = 280;

        mobEnemyFactory.setHp(30);
        eliteEnemyFactory.setHp(50);                   // 初始化敌机的血量、速度等
        superEliteEnemyFactory.setHp(50);

        boundSuperElite = 0.2;                           //初始化精英机生成概率，简单难度各0.2
        boundElite = 0.2;


    }
    /**
     * 在时间到一定条件时，动态的改变难度
     */
    protected void difficultyChangeAction() {
        //2024/5/16 11:46 简单难度什么都不做

    }

}
