package robos;
import java.awt.Color;

import robocode.AdvancedRobot;
import robocode.BulletHitEvent;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

public class BesouroDoido extends AdvancedRobot {

	double altura;
	double largura;
	double limite;
	
	public void run() {
	
		//DIMENSÕES  DO CAMPO
		limite = 100;
		altura = getBattleFieldHeight() - limite;
		largura = getBattleFieldWidth() - limite;
		//DIMENSÕES DO CAMPO
		double area = largura*altura;
		//AJUSTES DO ROBO
		setAllColors(Color.BLACK);
		setBulletColor(Color.green);
		
		
		while(true) {
			
			
			//MOVIMENTO CIRCULARES
			setAhead(1000);
			setTurnRight(180);
			setTurnGunLeft(90);
			setTurnLeft(180);
			
			//MOVIMENTOS RETOS 
			setAhead(100);
			setTurnRight(90);
			setAhead(100);
			setTurnLeft(90);
			
			
			
			/*
			 * if(getY() < altura || getX() < largura) {
				
				setBack(1000);
				
			}
			if(getX() < 50 && getY() < 50 && 
				getBattleFieldHeight() - getY() < 50 && 
				getBattleFieldWidth() - getX() < 50  ) {
				
				setBack(1000);
				
			}
			*/			
			 execute();
			
			
		}
		
		
	}
	
	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		// TODO Auto-generated method stub
		
		setFire(1);
	}
	
	//SE FOR ATINGIDO RECUA 10px
	public void onHitByBullet(HitByBulletEvent e) {
		
		setBack(10);
		
	}
	
	//SE HOVER COLISÃO COM OUTRO ROBO RECUA 100px
	@Override
	public void onHitRobot(HitRobotEvent event) {
		// TODO Auto-generated method stub
		super.onHitRobot(event);
		
		setBack(100);
		
	}
	
	
	//SE COLIDIR COM A PAREDE, RECUA 100PX
	public void onHitWall(HitWallEvent e) {
		
		setBack(100);
		
	}
	@Override
	public void onBulletHit(BulletHitEvent event) {
		// TODO Auto-generated method stub
		super.onBulletHit(event);
		
		back(100);
	}
}
