package edu.hitsz.Factory;

import edu.hitsz.application.Main;
import edu.hitsz.prop.*;

public class PropFactory {


    // 2024/4/10 17:24 实验2 第三次修改 增加道具工厂
    public static AbstractProp getProp(String propType , int locationX , int locationY) {
        if (propType == null) {
            return null;
        }
        if (propType.equalsIgnoreCase("PropBlood")) {
            // 等一下写参数
            return new PropBlood(locationX , locationY , 0 ,10);
        } else if (propType.equalsIgnoreCase("PropBomb")) {
            return new PropBomb(locationX , locationY , 0 ,10);
        } else if (propType.equalsIgnoreCase("PropBullet")) {
            return new PropBullet(locationX , locationY , 0 ,10);
        } else if (propType.equalsIgnoreCase("PropBulletPlus")) {
            return new PropBulletPlus(locationX , locationY , 0 ,10);
        }

        return null;
    }
}
// 如果加新道具，要对这个PropFactory.java做修改 如果想只增加新文件，那得用敌机那样的方法来构造道具工厂