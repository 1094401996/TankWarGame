package com.gengchen.www;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;
import java.util.*;


public class TankClient extends Frame {
	
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	Wall w1 = null;
	Wall w2 = null;
	Tank myTank = null;
	List<Missile> missiles  = null;
	List<Explosion> explosions = null;
	List<Tank> enemyTanks = null;
	Image offScreenImage = null;
	BloodMedicine bm = new BloodMedicine();
	public void initial(){

		Integer initialTankCount = Integer.parseInt(PropertyManager.getInstance().getProperty("initialTankCount"));
		 w1 = new Wall (100,300,20,150,this);
		 w2 = new Wall (300,100,300,20,this);
		 myTank = new Tank(700,500,true,Direction.STOP,this);
		 missiles  = new ArrayList<Missile>();
		 explosions = new ArrayList<Explosion>();
		 enemyTanks = new ArrayList<Tank>();
		 offScreenImage = null;
		 for(int i = 0 ; i < initialTankCount; i++){
				enemyTanks.add(new Tank(50 + 40 *(i + 1), 50,false,Direction.D, this));
		}
	}
	public void paint(Graphics g) {
		w1.draw(g);
		w2.draw(g);
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.drawString("missiles count:  " + missiles.size(), 10, 50);
		g.drawString("explosions count:  " + explosions.size(), 10, 70);
		g.drawString("enemyTanks count:  " + enemyTanks.size(), 10, 90);
		g.drawString("MyTank blood :  " + myTank.getBlood(), 10, 110);
		g.setColor(c);
		if(myTank.isLive() == false){
			this.initial();
		}
		myTank.hitOtherTanks(enemyTanks);
		myTank.eatBloodMedicine(bm);
		myTank.draw(g);
		for(int i = 0 ; i < missiles.size() ; i++){
			Missile m = missiles.get(i);
			m.hitTanks(enemyTanks);
			m.hitTank(myTank);
			m.hitWall(w1);
			m.hitWall(w2);
			m.draw(g);
		}
		for(int i = 0 ; i < explosions.size(); i++){
			explosions.get(i).draw(g);
		}
		if(enemyTanks.size() == 0){
			this.initial();
		}
		for(int i = 0 ; i < enemyTanks.size();i ++){
			Tank t = enemyTanks.get(i);
			t.hitWall(w1);
			t.hitWall(w2);
			t.hitOtherTanks(enemyTanks);
			t.draw(g);
		}
		if(bm.isLive())
		bm.draw(g);
	}
	
	@Override
	public void update(Graphics g) {
		if(offScreenImage == null){
			offScreenImage = this.createImage(GAME_WIDTH,GAME_HEIGHT);
		}
		Graphics  gOffScreen = offScreenImage.getGraphics();
		
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.BLACK);
		gOffScreen.fillRect(0, 0, GAME_WIDTH,GAME_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	public void lanchFrame(){
		initial();
		this.setLocation(400,300);
		this.setSize(GAME_WIDTH,GAME_HEIGHT);
		this.setBackground(Color.GREEN);
		this.setTitle("TankWar");
		this.addWindowListener(new WindowAdapter(){

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
		this.setResizable(false);
		this.addKeyListener(new KeyMonitor());
		this.setVisible(true);
		
		new Thread(new PaintThread()).start();
		
	}
	public static void main(String[] args){
		TankClient tc = new TankClient();
		tc.lanchFrame();
	}
	
	private class PaintThread implements Runnable{
		public void run(){
			while(true){
				TankClient.this.repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	private class KeyMonitor extends KeyAdapter{

		@Override
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			myTank.keyPressed(e);
		}
		
	}
	
}
