package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HeroAircraftTest {

    private HeroAircraft heroAircraft;


    @AfterAll
    static void Afterall() { System.out.println("Test Complete");}
//    @BeforeEacha
//    void setUp() {
//        heroAircraft = HeroAircraft.getInstance();
//    }

    @AfterEach
    void tearDown() {
        heroAircraft = null;
    }

    @DisplayName("Test getInstance method")
    @Test
    void getInstance() {
        // 调用getInstance()方法获取HeroAircraft实例
        HeroAircraft firstInstance = HeroAircraft.getInstance();

        // 再次调用getInstance()方法获取HeroAircraft实例
        HeroAircraft secondInstance = HeroAircraft.getInstance();

        // 验证两次获取的是否为同一个实例
        assertSame(firstInstance, secondInstance);

        // 验证血量
        assertEquals(1000, firstInstance.getHp());

    }


    @DisplayName("Test shoot method")
    @Test
    void shoot() {
        heroAircraft = HeroAircraft.getInstance();
        // 为了测试，把飞机单词发射子弹调成3个，以测试发射子弹横移是否正确
        heroAircraft.setShootNum(3);

        // 调用shoot方法返回子弹列表
        int shootNum = heroAircraft.getShootNum();
        List<BaseBullet> bullets = heroAircraft.shoot();
        // 检查子弹数目是否正确
        assertEquals(shootNum, bullets.size());
        // 检查每个子弹的方向、初始位置、伤害，先获取飞机的位置
        int aircraftLocationX = heroAircraft.getLocationX();
        int aircraftLocationY = heroAircraft.getLocationY();
        for (int i = 0; i < bullets.size(); i++) {
            // 测试子弹的初始位置 第一行测x坐标，第二行y坐标,之所以会有减2是白盒测试照看了shoot方法里面获得子弹y坐标的方法
            assertEquals(aircraftLocationX + (i*2 - shootNum + 1) * 10 , bullets.get(i).getLocationX());
            assertEquals(aircraftLocationY - 2 , bullets.get(i).getLocationY());
            // 测试子弹伤害
            assertEquals(30 , bullets.get(i).getPower());

            //测试子弹速度,提前知道的是-8 和0
            assertEquals(-8,bullets.get(i).getSpeedY());
            assertEquals(0,bullets.get(i).getSpeedX());


        }



    }

    @DisplayName("Test decreaseHP method")
    @Test
    void decreaseHP() {
        heroAircraft = HeroAircraft.getInstance();
        // 测试掉血，掉血100后应该有900的血量
        heroAircraft.decreaseHp(100);
        assertEquals(900 , heroAircraft.getHp());
        //把血掉完， 应该会出发vanish方法，把isvalid改了
        assertFalse(heroAircraft.notValid());
        heroAircraft.decreaseHp(1000);  // 这一次调用后vanish方法也调用了
        assertTrue(heroAircraft.notValid());




    }
}