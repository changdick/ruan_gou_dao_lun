package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.Main;
import edu.hitsz.application.music.MusicThread;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.BaseBullet;

import java.util.ArrayList;
import java.util.List;

/**
 * 炸弹类 2024/5/13 实验6 用观察者模式实现炸弹道具
 * 会受到炸弹影响的对象包括普通敌机、精英敌机、超级精英敌机、敌机子弹，他们的共同父类是AbstractFlyingObject
 * 所以维护一个他们的ArrayList
 */
public class PropBomb extends AbstractProp{

    private List<AbstractFlyingObject> observers = new ArrayList<>();
//    private List<AbstractAircraft> enemyAircrafts;
//    private List<BaseBullet> enemyBullets;
    public PropBomb(int locationX, int locationY, int speedX, int speedY) {

        super(locationX, locationY, speedX, speedY);
        // 2024/5/13 11:08 实验6 增加下面两句，构造方法利用传参传入敌机列表和敌机子弹列表，把他们全部加入到观察者列表
//        observers.addAll(enemyAircrafts);
//        observers.addAll(enemyBullets);
        //有bug，这个只会记住炸弹道具自己创建的时候屏幕上有的对象，吃到炸弹的时候可能已经有新的观察者对象产生，但是没有被清除
        // 先用私有属性把他们保存下来，等道具要work的时候再把他们加进去观察作者列表
//        this.enemyAircrafts = enemyAircrafts;
//        this.enemyBullets = enemyBullets;


    }

    /**
     * 增加观察者对象的方法，用多态性实现了两个，既可以用单个对象加入
     * 因为以及用私有属性获取了game里面的敌机列表和子弹列表，所以实现一个不读入参数的默认的存放方法，直接调用addAll方法
     * 缺省的registerObserver直接从Main里面获取game里的敌机列表和子弹列表
     * @param observer
     */
    private void registerObserver(AbstractFlyingObject observer) {
        observers.add(observer);
    }
    private void registerObserver() {
        observers.addAll(Main.game.getEnemyAircrafts());
        observers.addAll(Main.game.getEnemyBullets());
    }

    private void notifyObservers(){
        for (AbstractFlyingObject observer: observers) {
            observer.updateBomb();
        }
    }

    /**
     * 移除某个观察者
     * @param observer
     */
    public void removeObserver(AbstractFlyingObject observer) {
        observers.remove(observer);
    }
// 2024/4/4 15:32 加入该方法， 因为第一次实验没要求实现，就先输出语句

    public void work(HeroAircraft heroAircraft){
        System.out.println("BombSupply active!");
        new MusicThread("src/videos/bomb_explosion.wav", false).start();

        // 2024/5/13 11:13 实验6 炸弹的生效，只需要调用方法通知所有观察对象
        // 2024/5/13 11:24 实验6 炸弹再要生效的时候，再把要通知的对象维护到观察者列表里面
        registerObserver();

        notifyObservers();

    }
}
