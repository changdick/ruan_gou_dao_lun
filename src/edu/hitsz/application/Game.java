package edu.hitsz.application;


import edu.hitsz.Dao.PlayerRecord;
import edu.hitsz.Dao.PlayerRecordDao;
import edu.hitsz.Dao.PlayerRecordDaoImpl;
import edu.hitsz.Factory.*;
import edu.hitsz.aircraft.*;
import edu.hitsz.application.music.MusicThread;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.prop.AbstractProp;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
abstract public class Game extends JPanel {

    protected int backGroundTop = 0;

    /**
     *  背景图片，通过设置不同的BACKGROUND_IMAGE，即可改变背景图片
     *  是难度设置的一个部分
     */
    protected BufferedImage BACKGROUND_IMAGE = ImageManager.BACKGROUND_IMAGE;

    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    private int timeInterval = 40;


    // 2024/4/10 16：42 实验2 第一次修改，因为改了单例模式，这边就去掉(前面理解错误，其实上不是去掉，而是改变了它的创建方式，在该文件中仍然是使用heroAircraft指代英雄机，但是不是在这里实例化)
    protected final HeroAircraft heroAircraft;
    protected final List<AbstractAircraft> enemyAircrafts;
    protected final List<BaseBullet> heroBullets;
    protected final List<BaseBullet> enemyBullets;

    // 2024/4/4 14:53 在这里添加道具的list，并在game对象里初始化
    protected final List<AbstractProp> props;

    //2024/5/5 21:50
//2024/5/8 18:39 实验5 这两个不再game里面做，到Main里面没做
//    public PlayerRecordDao playerRecordDao = new PlayerRecordDaoImpl();
//    public PlayerRecord playerRecord = new PlayerRecord();
    /**
     * 屏幕中出现的敌机最大数量，可作为难度设置的一个部分
     */
    protected int enemyMaxNumber = 5;

    /**
     * 当前得分
     */
    //2024/5/5 21:50 实验4 改数据对象访问模式的分数
            //2024/5/8  18：40  实验5 game里面的score不应该改， Dao在Main里面做，score用get方法送出去
    protected int score = 0;
    /**
     * 当前时刻，这个时间会一直按照timeInterval增加，并且在cyclejudge判断到的时候，会在控制台输出一次
     */
    private int time = 0;

    /**
     * 生成超级精英机、精英敌机的概率值
     */
    protected double boundSuperElite=0.33;
    protected double boundElite=0.33;

    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    private int cycleDuration = 600;
    private int cycleTime = 0;
    /**
     * 敌机产生的周期控制量
     */
    protected int enemyCreateDuration = 600;
    protected int enemyCreatecycleTime = 0;
    /**
     * 敌机射击的周期控制量
     */
    protected int enemyShootDuration = 600;
    protected int enemyShootCycleTime = 0;
    /**
     * 英雄机射击的周期控制量
     * 2024/5/16 10:47 实验6 把英雄机和敌机射击分开来做，可以实现英雄机和敌机以不同频率进行射击
     */
    protected int heroShootDuration = 400;
    protected int heroShootCycleTime = 0;
    /**
     * 难度动态增加的周期控制量
     */
    protected int difficultyChangeDuration = 12000;
    protected int difficultyChangeCycleTime;

    /**
     * 游戏结束标志
     */
    protected boolean gameOverFlag = false;

    /**
     * 这是用于生成敌机精英或普通的随机数
     */
    Random randomNewEnemy= new Random();
    /**
     * 这是用于生成随即道具判断的随机数
     */
    Random randomProp = new Random();


//    2024/4/10 17:33 改工厂模式创建敌机和道具所以实例化工厂
//    2024/4/17 19:07 实验3 第九次修改 把所有飞机工厂全改成静态方法了，这些实例化不再需要,又不改了这句注释没有
    protected EnemyFactory mobEnemyFactory = new MobEnemyFactory();
    protected EnemyFactory eliteEnemyFactory = new EliteEnemyFactory();
//    2024/4/17 17：20 实验3 game文件里添加生成超级精英机的内容
    protected EnemyFactory superEliteEnemyFactory = new SuperEliteEnemyFactory();
    protected EnemyFactory bossEnemyFactory = new BossEnemyFactory();

    //2024/5/9 12:55 实验5 多线程播放音乐， 设置bgm的线程，循环播放
    MusicThread bgm_musicThread = new MusicThread("src/videos/bgm.wav", true);
    MusicThread boss_musicThread = new MusicThread("src/videos/bgm_boss.wav",true);


//    2024/4/18 15:16 实验3 修改了bossalive标识，把本来写game类里的全部移到boss机的类里去做，所以这边可以删去
    /**
     * boss生存的标识
     */
//    private Boolean bossAlive = false;

//    2024/4/14 16:13 实验2 更改 把PropFactory改成了静态方法，简单工厂模式用静态的方法，所以不再需要实例化
//    PropFactory propFactory = new PropFactory();
    public Game() {

        // 2024/4/10 16:40 实验2 第一次修改 到HeroAircraft类做了单例模式的单例实例化方法，这边就去掉
        // 现在game里面 heroAircraft  应该改用 HeroAircraft.getInstance()，访问这一个实例即可
//        heroAircraft = new HeroAircraft(
//                Main.WINDOW_WIDTH / 2,
//                Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight() ,
//                0, 0, 100);
        heroAircraft = HeroAircraft.getInstance();
        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();

//        2024/5/16 14:16 实验6 在构造方法内进行难度初始化，每个具体的子类的初始化方法内容不同，就会出现难度初始差异
        difficultyInitial();

        /**
         * Scheduled 线程池，用于定时任务调度
         * 关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
         * apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
        this.executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

        // 开始游戏播放bgm（但是只有这句会一直播到游戏结束排行榜页面还在播）
//        bgm_musicThread.start();

    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public final void action() {
        bgm_musicThread.start();

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            time += timeInterval;


            // 周期性执行（控制频率）
            if (enemyCreateTimeCountAndNewCycleJudge()) {
                System.out.println(time);
                // 新敌机产生
                // 2024/4/3 20：58 第四次添加，增加了随机数import了Random，在上面创建了Random类的对象random，在下面调用，做了一个if判断

                // 2024/4/10 17:10 实验2 添加完了EnemyFactory， 现在来改新建敌机的代码 应该先创建一个工厂
//                if (enemyAircrafts.size() < enemyMaxNumber) {
//                    int randomWithNextInt = randomNewEnemy.nextInt();
//                    if (randomWithNextInt % 3 == 0) {
//                        enemyAircrafts.add(mobEnemyFactory.getEnemyAircraft());
//                    }else if (randomWithNextInt % 3 == 1) {
//                        // 本来按简单工厂模式的实现是这样
//                        //enemyAircrafts.add(enemyFactory.getEnemyAircraft("EliteEnemy"));
//                        enemyAircrafts.add(eliteEnemyFactory.getEnemyAircraft());
//                    }
//                    else {
//                        enemyAircrafts.add(superEliteEnemyFactory.getEnemyAircraft());
//                    }
//
//                    //2024/5/13 14：14 实验6 用模板模式设计难度变化， 把这段生成敌机的代码抽象到一个方法里
//                }
                createNewEnemy();


            }
            //2024/5/13 14:46 实验6 把敌机创建和子弹射击的逻辑分别用各自的时间周期判断，可以分别控制
            // 本来使用同一个周期判断，子弹射击和新敌机创建一定同频率，现在可以根据难度需要修改频率
            if(enemyShootTimeCountAndNewCycleJudge()) {
                // 飞机射出子弹
                enemyShootAction();
            }
            //2024/5/16 10:50 实验6 将敌机和英雄机的射击分开成两个shootAction，以实现二者分开射击频率
            if(heroShootTimeCountAndNewCycleJudge()) {
                heroShootAction();
            }
            if(difficultyChangeTimeCountAndNewCycleJudge()){
                //TODO
                difficultyChangeAction();
            }

            //  检查boss生成
            checkBoss();

            // 子弹移动
            bulletsMoveAction();

            // 飞机移动
            aircraftsMoveAction();
            // 道具移动
            propsMoveAction();

            // 撞击检测
            crashCheckAction();

            // 后处理
            postProcessAction();

            //每个时刻重绘界面
            repaint();

            // 游戏结束检查英雄机是否存活
            if (heroAircraft.getHp() <= 0) {

                // 游戏结束
                bgm_musicThread.interrupt();
                boss_musicThread.interrupt(); //本来这句有bug，因为最初game()方法外面是只声明MusicThread boss_musicThread，每次播放bossBgm的时候new一个，这样的话当boss还未生成游戏结束的时候，未必有boss_musicThread


                executorService.shutdown();
                gameOverFlag = true;
                System.out.println("Game Over!");
                new MusicThread("src/videos/game_over.wav", false).start();

                //以下为实验4添加  询问玩家是否存储，并调用DAO，把本次记录存入并打印。
                //2024/5/8 16：55 实验5 因为要改有ui的排行榜，Main那边启动game后会叫一次LOCK的等待，这边唤醒Main，Main里边的逻辑就继续
                // 询问玩家是否存储
//                System.out.println("是否存储本局游戏得分 输入 y or n 回车");
//                Scanner scanner = new Scanner(System.in);
//                String saveorNot = scanner.next();
//                if (saveorNot.equalsIgnoreCase("y")) {
//                    playerRecordDao.addRecord(playerRecord);
//                    playerRecordDao.saveRecords();
//                }
//                List<PlayerRecord> records = playerRecordDao.getAllRecords();
//                for (int i = 0; i < records.size(); i++) {
//                    System.out.println("rank: "+ (i+1)
//                                     + "\tusername:" + records.get(i).getUserName()
//                                     + "\tscore: " + records.get(i).getScore()
//                                     + "\t"+records.get(i).getLocalDateTime());
//
//                }
                synchronized (Main.LOCK) {
                    Main.LOCK.notify();
                }
            }

        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);

    }

    //*****************************************************
    //     控制难度的关键方法， 子类里面重写即可体现难度差别
    //*****************************************************
    /**
     * scoreForBoss 方法用score属性判断当前分数是否符合Boss机生成的条件，把Boss机生成条件中的分数部分专门抽出来，各种难度进行重写即可
     * @return
     */
    abstract protected Boolean scoreForBoss ();
    /**
     * 难度初始化，构造类的时候调用一次，会设置好各种频率、数据
     */
    abstract protected void difficultyInitial();//{
//        enemyCreateDuration = 600;                        // 设置敌机产生周期
//        enemyShootDuration = 600;                         // 设置敌机射击周期
//        difficultyChangeDuration = 15000;     // 设置难度动态增加的周期 无穷大不会增加难度
//
//        boundElite = 0.33;
//        boundSuperElite = 0.33;
//
//    }
    /**
     * 在时间到一定条件时，动态的改变难度
     */
    abstract protected void difficultyChangeAction();// {
//        //2024/5/13 15：22 实验6 简单地把敌机创建、低级发射子弹的周期减少40ms
//        enemyShootDuration -= 40;
//        enemyCreateDuration -= 40;
//        // 调整生成的敌机的速度、血量，后面敌机会变成高速导弹
//        eliteEnemyFactory.increaseHp(10);
//        eliteEnemyFactory.increaseSpeedY(5);
//        superEliteEnemyFactory.increaseHp(10);
//        superEliteEnemyFactory.increaseSpeedY(5);
//
//        // 调整屏幕上敌机的数量
//        enemyMaxNumber += 1;
//
//
//        System.out.println(STR."难度提升！当前的敌机射击周期为\{enemyShootDuration};当前敌机创建的周期为\{enemyCreateDuration};"
//                + STR."\n精英机、超级精英机的速度提高了5，血量增加了10, 屏幕上敌机最大数量增加至\{enemyMaxNumber}\n");
//    }





    //***********************
    //      Action 各部分
    //***********************


    private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }
    private boolean enemyCreateTimeCountAndNewCycleJudge() {
        enemyCreatecycleTime += timeInterval;
        if (enemyCreatecycleTime >= enemyCreateDuration && enemyCreatecycleTime - timeInterval < enemyCreatecycleTime) {
            // 跨越到新的周期
            enemyCreatecycleTime %= enemyCreateDuration;
            return true;
        } else {
            return false;
        }
    }
    private boolean enemyShootTimeCountAndNewCycleJudge() {
        enemyShootCycleTime += timeInterval;
        if (enemyShootCycleTime >= enemyShootDuration && enemyShootCycleTime - timeInterval < enemyShootCycleTime) {
            // 跨越到新的周期
           enemyShootCycleTime %= enemyShootDuration;
            return true;
        } else {
            return false;
        }
    }
    private boolean difficultyChangeTimeCountAndNewCycleJudge() {
        difficultyChangeCycleTime += timeInterval;
        if (difficultyChangeCycleTime >= difficultyChangeDuration && difficultyChangeCycleTime - timeInterval < difficultyChangeCycleTime) {
            // 跨越到新的周期
            difficultyChangeCycleTime %= difficultyChangeDuration;
            return true;
        } else {
            return false;
        }
    }
    private boolean heroShootTimeCountAndNewCycleJudge() {
        heroShootCycleTime += timeInterval;
        if (heroShootCycleTime >= heroShootDuration && heroShootCycleTime - timeInterval < heroShootCycleTime) {
            // 跨越到新的周期
            heroShootCycleTime %= heroShootDuration;
            return true;
        } else {
            return false;
        }
    }


// 2024/4/17 19：15 加入产生Boss的语句
    protected void checkBoss() {
        // 检查score的大小
//        2024/4/18 15：19 实验3 修改bossAlive的判断，这个bossAlive 改成了boss类里面的静态私有变量, 用它的访问方法来调用
        if (scoreForBoss() && !BossEnemy.isBossAlive()) {
            enemyAircrafts.add(bossEnemyFactory.getEnemyAircraft());
//            bossAlive = true;  2024/4/17 15：17  实验3 修改删去，这些功能不应该在game里判断
//          // 2024/5/9 为了让boss机的bgm在boss机每次生成的时候播放，每次boss机摧毁或者游戏结束时停止，必须每次新建boss机的时候new一个线程，不能只new一次重复开启是不行的
            boss_musicThread = new MusicThread("src/videos/bgm_boss.wav",true);
            boss_musicThread.start();
        }
    }


    private void enemyShootAction() {
        // TODO 敌机射击
//      2024/4/3 17：25 第一次添加修改
        for(AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyBullets.addAll(enemyAircraft.shoot());
        }

//        // 英雄射击
//        // 2024/4/10 16：43 实验2 第一次修改 改了单例模式后，这边就更改访问的方式
//        heroBullets.addAll(heroAircraft.shoot());
    }
//    2024/5/16 10：52 实验6 修改，把英雄机射击和敌机射击分开
    private void heroShootAction() {
                // 英雄射击
        // 2024/4/10 16：43 实验2 第一次修改 改了单例模式后，这边就更改访问的方式
        heroBullets.addAll(heroAircraft.shoot());
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
        // 道具也要飞行

    }
    private void propsMoveAction() {
        for (AbstractProp prop : props) {
            prop.forward();
        }
    }
    /**
     * 随机生成道具，用在 敌机碰撞检测发现损毁后
     */
    private void createprops(AbstractAircraft enemyAircraft) {
        int randint = randomProp.nextInt();
        if(randint % 9 < 2){
            props.add(PropFactory.getProp("PropBlood" , enemyAircraft.getLocationX(),enemyAircraft.getLocationY()));
        }else if (randint % 9 < 4) {
            props.add(PropFactory.getProp("PropBomb" ,enemyAircraft.getLocationX(),enemyAircraft.getLocationY() ));
        }else if (randint % 9 < 6){
            props.add(PropFactory.getProp("PropBullet" , enemyAircraft.getLocationX(),enemyAircraft.getLocationY()));
        }else if (randint % 9 < 8) {
            props.add(PropFactory.getProp("PropBulletPlus" , enemyAircraft.getLocationX(),enemyAircraft.getLocationY()));
        }
    }

    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // TODO 敌机子弹攻击英雄
        for (BaseBullet bullet : enemyBullets){
            if(bullet.notValid()){
                continue;
            }
            if (heroAircraft.crash(bullet)){
                // 英雄机撞击到敌机子弹
                // 英雄机损失一定生命值
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }
        // 4/3 17：31 第二次添加   上面是todo增加的内容

        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    //2024/5/9 12：37 实验5 用线程增加音效
                    new MusicThread("src/videos/bullet_hit.wav", false).start();

                    if (enemyAircraft.notValid()) {
                        // TODO 获得分数，产生道具补给

//                        score += 10;
                        // 是否产生道具要对enemyAircraft到底是什么对象做一个判断，2024/4/4 15：18 添加条件判断 产生道具
                        // 2024/4/4 15:33 产生什么道具 应该做一个随机判断

                        //2024/4/10 17:38 产生道具的方法改成调用道具工厂的方法
                        // 2024/4/17 17:24 实验3 第4次添加 两种精英敌机的道具掉落都判断
                        if (enemyAircraft instanceof EliteEnemy || enemyAircraft instanceof SuperEliteEnemy){

//                            int randint = randomProp.nextInt();
//                            if(randint % 10 < 3){
//                                props.add(PropFactory.getProp("PropBlood" , enemyAircraft.getLocationX(),enemyAircraft.getLocationY()));
//                            }else if (randint % 10 < 6) {
//                                props.add(PropFactory.getProp("PropBomb" ,enemyAircraft.getLocationX(),enemyAircraft.getLocationY() ));
//                            }else if (randint % 10 < 9){
//                                props.add(PropFactory.getProp("PropBullet" , enemyAircraft.getLocationX(),enemyAircraft.getLocationY()));
//                            }
                            createprops(enemyAircraft);

                        } else if (enemyAircraft instanceof BossEnemy) {
//                                props.add(PropFactory.getProp("PropBullet" , enemyAircraft.getLocationX()-20,enemyAircraft.getLocationY()));
//                                props.add(PropFactory.getProp("PropBlood" , enemyAircraft.getLocationX(),enemyAircraft.getLocationY()));
//                                props.add(PropFactory.getProp("PropBomb" ,enemyAircraft.getLocationX()+20,enemyAircraft.getLocationY() ));
                            for (int i = 0; i < 3; i++) {
//                                2024/4/17 20:05 实验3 第九次修改 把生成道具所需要用到的随机判断和add语句全放在了creatprop方法里面，调用方法
                                createprops(enemyAircraft);
                            }
//                            2024/418 15：18 实验3 修改删去 这句话在boss机vanish的时候会做
//                            bossAlive = false;
                            boss_musicThread.interrupt();
                        }


                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // Todo: 我方获得道具，道具生效
        // 英雄机是否与道具相撞
        for (AbstractProp prop : props) {
            if(prop.crash(heroAircraft)){
                // 2024/5/9 实验5 增加获得道具时播放音效功能
                //  根据音乐播放的使用方法，只需加上以下一行代码，新建一个MusicThread并调用start即可.反正musicThread不需要保存下去
                // MusicThread musicThread = new MusicThread("src/videos/get_supply.wav");
                // musicThread.start();
                new MusicThread("src/videos/get_supply.wav", false).start();

                prop.work(heroAircraft);
                prop.vanish();
            }
        }

    }




    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 删除无效的道具 2024/4/4 15：十几分 (添加)
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }


    //***********************
    //      Paint 各部分
    //***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     * @param  g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(this.BACKGROUND_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(this.BACKGROUND_IMAGE, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);

       // 还要绘制道具 2024/4/4 15:08添加下面这一行
        paintImageWithPositionRevised(g, props);

        paintImageWithPositionRevised(g, enemyAircrafts);



        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }


    /**
     * 2024/5/8 实验5增加
     * 返回game里统计的score，用于在main里调用，并且用于送入DAO储存
     * @return
     */
    public int getScore() {
        return score;
    }

    public List<AbstractAircraft> getEnemyAircrafts() {
        return enemyAircrafts;
    }

    public List<BaseBullet> getEnemyBullets() {
        return enemyBullets;
    }

    /**
     * 分数增加的方法，可以在game里面替换掉score+=10的语句，因为这些语句不够抽象
     * 同时用于敌机对炸弹道具更新的方法调用，敌机因炸弹道具或者子弹击毁都会带来加分，不如把加分的语句放到敌机的vanish中去调用
     * @param increasement
     */
     public void addScore(int increasement) {
        score += increasement;
     }

    /**
     * 生成敌机的代码，包装到一个方法里，本来使用摇出一个随机整数，按照他的值对3取余数判断应该出什么飞机
     * 2024/5/16 14：37 改成浮点数，用浮点数比范围好用一点
     */
     private void createNewEnemy() {
         if (enemyAircrafts.size() < enemyMaxNumber) {
//             int randomWithNextInt = randomNewEnemy.nextInt();
             // 2024/5/16 14:18 实验6 为了设置动态改变不同敌机生成的概率
             // 根据源码，nextDouble方法返回0到1间的数字，我们分成三个部分，设置两个属性作为分界用于控制随机，
             // boundSuperElite  和 boundElite   |--------|-------|-------|
             //                                  0       bSE    bE+bSE    1
             // boundSuperElite 和 boundElite都是概率值double型的
             // 如果随机数在0到bSE之间，生成超级精英机
             // 如果随机数在bE+bSE到1之间，生成普通敌机
             // 其余 生成精英敌机
             // 可以动态的直接提高bSE和bE来改变难度
             double randomDouble = randomNewEnemy.nextDouble();
             if (randomDouble < boundSuperElite) {
                 enemyAircrafts.add(superEliteEnemyFactory.getEnemyAircraft());
             }else if (randomDouble > boundSuperElite + boundElite) {
                 enemyAircrafts.add(mobEnemyFactory.getEnemyAircraft());
             } else {
                 enemyAircrafts.add(eliteEnemyFactory.getEnemyAircraft());
             }

             //2024/5/13 14：14 实验6 用模板模式设计难度变化， 把这段生成敌机的代码抽象到一个方法里
         }
     }

}
