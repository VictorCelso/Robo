/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rb;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import robocode.AdvancedRobot;
import robocode.HitByBulletEvent;
import robocode.HitWallEvent;
import robocode.Rules;
import robocode.ScannedRobotEvent;
import static robocode.util.Utils.normalRelativeAngle;

/**
 *
 * @author Victor Tassinari
 */
public class Dodger extends AdvancedRobot {

    int moveDirection = 1;
    double energy = 100;
    double pos = 360;
    int count = 0;

    @Override
    public void run() {
        setAdjustGunForRobotTurn(true);
        while (true) {
            if (count > 3) {
                pos = 360;
            }
            turnGunLeft(pos);
            count++;
            if (((Math.random() * 10) % 2) == 0) {
                moveDirection = -moveDirection;
            } else {
                moveDirection = 1;
            }
        }
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        count = 0;
        pos = normalRelativeAngle(e.getBearingRadians() + e.getHeadingRadians() - getRadarHeadingRadians());
        turnGunRight(pos * moveDirection);
        setTurnLeft((-90 - e.getBearing()) * moveDirection);
        ahead(e.getDistance() - 100);
        
        pos = normalRelativeAngle(e.getBearingRadians() + e.getHeadingRadians() - getRadarHeadingRadians());
        turnGunLeft(pos);
        fire(3); 
        System.out.println("Last energy: " + energy + "\nNow energy: " + e.getEnergy());
        double enDiff = Math.abs(energy - e.getEnergy());
        energy = e.getEnergy();

        if (enDiff > 0.09 && enDiff < 3.1) {
            System.out.println("Dodging");
            turnRight(-90 - e.getBearing());
            setAhead((e.getDistance() - 150) * moveDirection);
        }
    }

//    @Override
//    public void onHitByBullet(HitByBulletEvent e) {
//        setAhead(e.getBearing() + e.getPower() * moveDirection);
//        turnLeft((e.getBearingRadians() + e.getPower()) * moveDirection);
//    }

    @Override
    public void onHitWall(HitWallEvent event) {
        moveDirection = -moveDirection;
        turnLeft(-30 + event.getBearing());
        ahead(30 * moveDirection);
    }

}
