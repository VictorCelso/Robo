/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rb;

import robocode.AdvancedRobot;
import robocode.HitByBulletEvent;
import robocode.ScannedRobotEvent;
import static robocode.util.Utils.normalRelativeAngleDegrees;

/**
 *
 * @author Victor Tassinari
 */
public class Sniper extends AdvancedRobot {

    int movDir = 1;
    double lastPos = 10;
    int turnSearching = 0;

    @Override
    public void run() {
        setAdjustGunForRobotTurn(true);
        while (true) {
//            scan();
            movDir *=-1;
            turnGunRight(lastPos);
            turnSearching++;
            if (turnSearching > 2) {
                lastPos = -10;
            } else if (turnSearching > 5) {
                lastPos = 10;
            } else if (turnSearching > 8) {
                lastPos= 360;
            }
//            scan();
        }
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
		double absBearing=e.getBearingRadians()+getHeadingRadians();//enemies absolute bearing
		double latVel=e.getVelocity() * Math.sin(e.getHeadingRadians() -absBearing);//enemies later velocity
		double gunTurnAmt;//amount to turn our gun
		setTurnRadarLeftRadians(getRadarTurnRemainingRadians());//lock on the radar
		if(Math.random()>.9){
			setMaxVelocity((12*Math.random())+12);//randomly change speed
		}
		if (e.getDistance() > 150) {//if distance is greater than 150
			gunTurnAmt = robocode.util.Utils.normalRelativeAngle(absBearing- getGunHeadingRadians()+latVel/22);//amount to turn our gun, lead just a little bit
			setTurnGunRightRadians(gunTurnAmt); //turn our gun
			setTurnRightRadians(robocode.util.Utils.normalRelativeAngle(absBearing-getHeadingRadians()+latVel/getVelocity()));//drive towards the enemies predicted future location
			setAhead((e.getDistance() - 140)*movDir);//move forward
			setFire(3);//fire
		}
		else{//if we are close enough...
			gunTurnAmt = robocode.util.Utils.normalRelativeAngle(absBearing- getGunHeadingRadians()+latVel/15);//amount to turn our gun, lead just a little bit
			setTurnGunRightRadians(gunTurnAmt);//turn our gun
			setTurnLeft(-90-e.getBearing()); //turn perpendicular to the enemy
			setAhead((e.getDistance() - 140)*movDir);//move forward
			setFire(3);//fire
		}	
	}
    
   /* @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        turnSearching = 0;
        lastPos = normalRelativeAngleDegrees(e.getBearing()+(getHeading() - getRadarHeading()));
        setTurnGunRight(lastPos);
        setTurnRight(e.getBearing()+90-30*movDir);

        System.out.println(e.getDistance());
        if (e.getDistance() < 60) {
            back(60);
        } else {
            ahead(e.getDistance() - 140);
        }
        
        setTurnGunRight(lastPos);
        if (getGunTurnRemaining() < 10) {
            if (Math.abs(e.getDistance()) < 150) {
                fire(3);
            } else {
                fire(1);
            }
        }
        scan();
        lastPos = normalRelativeAngleDegrees(e.getBearing()+(getHeading() - getRadarHeading()));
        setTurnGunRight(lastPos);
        fire(1);
        turnSearching=0;
    }*/

    @Override
    public void onHitByBullet(HitByBulletEvent e) {
        setTurnRight(e.getBearing()+90-30*e.getHeading()*movDir);
        back(70*movDir);
        lastPos=normalRelativeAngleDegrees(e.getBullet().getHeading()-e.getBearing());
    }

}
