package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;

import java.util.LinkedList;
import java.util.List;

public class NullShootStrategy extends ShootStrategy{

    @Override
    public List<BaseBullet> doshoot(AbstractAircraft abstractAircraft, String bulletType) {
        return new LinkedList<>();
    }
}
