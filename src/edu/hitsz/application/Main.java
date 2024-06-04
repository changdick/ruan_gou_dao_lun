package edu.hitsz.application;

import edu.hitsz.Dao.PlayerRecord;
import edu.hitsz.Dao.PlayerRecordDao;
import edu.hitsz.Dao.PlayerRecordDaoImpl;
import edu.hitsz.application.gui.RankBoardFrame;
import edu.hitsz.application.gui.StartFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 程序入口
 * @author hitsz
 */
public class Main {

    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;

    public static final Object LOCK = new Object();

    //2024/5/8 18:55 实验5 把DAO在这里声明
    public static PlayerRecordDao playerRecordDao;
    public static PlayerRecord playerRecord ;
    private static String difficulty;

    public static Game game;

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Hello Aircraft War");

        // 获得屏幕的分辨率，初始化 Frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Aircraft War");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        //设置窗口的大小和位置,居中放置
        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 2024/5/6 21：26 实验5 做启动时的难度选择页面, 第一个画面
        StartFrame startFrame = new StartFrame();
        // 此句将显示内容设置为启动的难度选择页面
        frame.setContentPane(startFrame.getMainPanel());
        frame.setVisible(true);
//        LOCK对象在Main里边叫了等待，会导致当前线程等待直到其他线程调用此对象的notify( ) 方法或 notifyAll( ) 方法。
//        notify会在startFrame里边调用，而LOCK在两个大类里边同时存在是竞争资源，必须加锁
        synchronized (LOCK) {
            LOCK.wait();
        }
        frame.remove(startFrame.getMainPanel());


        if (difficulty.equalsIgnoreCase("Easy")) {
            playerRecordDao = new PlayerRecordDaoImpl("Easy");
            game = new EasyGame();
        } else if (difficulty.equalsIgnoreCase("Normal")){
            playerRecordDao = new PlayerRecordDaoImpl("Normal");
            game = new NormalGame();
        } else {
            playerRecordDao = new PlayerRecordDaoImpl("Hard");
            game = new HardGame();
        }
//        Game game = new Game();
        frame.setContentPane(game);
        frame.setVisible(true);
        game.action();

        //2024/5/8 16：51 实验5增加 game启动后开始
        synchronized (LOCK) {
            LOCK.wait();
        }
        // game结束，会有唤醒，在这边继续运行
        //2024/5/8 16：58 实验5增加  game结束，移除游戏页面. 为了防止画面太快改变，设置500单位的停止
        synchronized (LOCK) {
            LOCK.wait(500);
        }
        frame.remove(game);
        String userName = JOptionPane.showInputDialog(
                frame,
                 "请输入名字:",
                JOptionPane.PLAIN_MESSAGE
        );
        if (Objects.equals(userName, "") || userName == null) {
            userName = "未命名";
        }
        //初始化playerRecord，从game里读取出本次的分数
        playerRecordDao.addRecord(new PlayerRecord(userName,game.getScore()));
        playerRecordDao.saveRecords();


        // 此时game结束，下面是第三个画面，排行榜页面

        RankBoardFrame rankBoardFrame = new RankBoardFrame();
        frame.setContentPane(rankBoardFrame.getMainPanel());
        frame.setVisible(true);


    }


    public static void setDifficulty(String difficulty) {
        Main.difficulty = difficulty;
    }

    public static String getDifficulty() {
        return difficulty;
    }
}
