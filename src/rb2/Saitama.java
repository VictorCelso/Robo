package rb2;


import robocode.AdvancedRobot;
import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;
import robocode.HitByBulletEvent;
import robocode.*;
import java.awt.*;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Germano Vasconcelos
 */
public class Saitama extends AdvancedRobot {

    @Override
   public void run() {
	  //Definem as cores do robô	
      setBodyColor(Color.black);
	  setGunColor(Color.black);
	  setRadarColor(Color.red);
	  setScanColor(Color.red);
	  //setBulletColor(Color.writh);
 
	  while(true){	
	     turnRadarRight(360);
	     ahead(100);
	     turnGunRight(360);
	     back(100);
       	 
	  }
   }
   //Detecta os outros robôs

    /**
     *
     * @param e
     */
    @Override
   public void onScannedRobot(ScannedRobotEvent e) {
      double max = 100;
	 
	
	  //Faz um controle da energia que é gasta no que diz 
	  //respeito à potência do tiro
      if(e.getEnergy() < max){
         max = e.getEnergy();
         miraCanhao(e.getBearing(), max, getEnergy());
      }else if(e.getEnergy() >= max){
         max = e.getEnergy();
         miraCanhao(e.getBearing(), max, getEnergy());
      }else if(getOthers() == 1){
         max = e.getEnergy();
         miraCanhao(e.getBearing(), max, getEnergy());
      }
    }
    //quando o seu robo colide com outro robo
    @Override
    public void onHitRobot(HitRobotEvent e) {
	   tiroFatal(e.getBearing(), e.getEnergy(), getEnergy());	
	
    }
    //Quando seu roboô leva um tiro
    @Override
    public void onHitByBullet(HitByBulletEvent e) { 
	   turnLeft(90);
	   back(100); 
    }
    //Fornece as coordenadas para o ajuste do canhão.

    /**
     *
     * @param PosIni
     * @param energiaIni
     * @param minhaEnergia
     */
    public void miraCanhao(double PosIni, double energiaIni, double minhaEnergia) {
       double Distancia = PosIni;
	   double Coordenadas = getHeading() + PosIni - getGunHeading();
	   double PontoQuarenta = (energiaIni / 4) + .1;
	   if (!(Coordenadas > -180 && Coordenadas <= 180)) {
	      while (Coordenadas <= -180) {
		     Coordenadas += 360;
		  }
		  while (Coordenadas > 180) {
		     Coordenadas -= 360;
		  }
	   }
	   turnGunRight(Coordenadas);
		
	   if (Distancia > 200 || minhaEnergia < 15 || energiaIni > minhaEnergia){
          fire(1);
       } else if (Distancia > 50 ) {
          fire(2);
       } else {
          fire(PontoQuarenta);
       }
   }
   //É chamado quando o robô bate na parede,
    @Override
   public void onHitWall(HitWallEvent e) {
      turnLeft(90);
      ahead(200);
   }
   //Dança da vitória
    @Override
   public void onWin(WinEvent e) {	
      turnRight(72000);
   }
   public void tiroFatal(double PosIni, double energiaIni, double minhaEnergia) {
      double Distancia = PosIni;
	  double Coordenadas = getHeading() + PosIni - getGunHeading();
	  double PontoQuarenta = (energiaIni / 4) + .1;
	  if (!(Coordenadas > -180 && Coordenadas <= 180)) {
	     while (Coordenadas <= -180) {
	        Coordenadas += 360;
		 }
		 while (Coordenadas > 180) {
	        Coordenadas -= 360;
	     }
	  }
	  turnGunRight(Coordenadas);
	  fire(PontoQuarenta);
       
   }
			
}