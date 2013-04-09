package com.gengchen.www;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Missile {
	public static final int XSPEED = 10;
	public static final int YSPEED = 10;
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	private int x;
	private int y;
	Direction dir;
	private boolean isLive = true;
	private static Toolkit  tk = Toolkit.getDefaultToolkit();
	private static Image[] missileImages = null;
	private static Map<String,Image> imgs = new HashMap<String, Image>();
	static{
		missileImages = new Image[]{
				tk.getImage(Explosion.class.getClassLoader().getResource("images/MissileL.gif")),
				tk.getImage(Explosion.class.getClassLoader().getResource("images/MissileLU.gif")),
				tk.getImage(Explosion.class.getClassLoader().getResource("images/MissileU.gif")),
				tk.getImage(Explosion.class.getClassLoader().getResource("images/MissileRU.gif")),
				tk.getImage(Explosion.class.getClassLoader().getResource("images/MissileR.gif")),
				tk.getImage(Explosion.class.getClassLoader().getResource("images/MissileRD.gif")),
				tk.getImage(Explosion.class.getClassLoader().getResource("images/MissileD.gif")),
				tk.getImage(Explosion.class.getClassLoader().getResource("images/MissileLD.gif")),
				
		};
		imgs.put("L", missileImages[0]);
		imgs.put("LU", missileImages[1]);
		imgs.put("U", missileImages[2]);
		imgs.put("RU", missileImages[3]);
		imgs.put("R", missileImages[4]);
		imgs.put("RD", missileImages[5]);
		imgs.put("D", missileImages[6]);
		imgs.put("LD", missileImages[7]);
	}
	private TankClient tc;
	private boolean isMyMissile;
	public boolean isLive() {
		return isLive;
	}

	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}

	public Missile(int x, int y,Direction dir){
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	public Missile(int x, int y , boolean isMyMissile, Direction dir, TankClient tc){
		this(x,y,dir);
		this.isMyMissile = isMyMissile;
		this.tc = tc;
	}
	public void draw (Graphics g){
		if(this.isLive == false){
			tc.missiles.remove(this);
		}
//		Color c = g.getColor();
//		if(this.isMyMissile){
//			g.setColor(Color.RED);
//		}else
//		g.setColor(Color.black);
		g.fillOval(x,y,WIDTH,HEIGHT);
//		g.setColor(c);
		switch(dir){
		case L : 
			g.drawImage(imgs.get("L"), x, y, null);
			break;
		case LU :
			g.drawImage(imgs.get("LU"), x, y, null);

			break;
		case U:
			g.drawImage(imgs.get("U"), x, y, null);
			break;
		case RU:
			g.drawImage(imgs.get("RU"), x, y, null);
			break;
		case R:
			g.drawImage(imgs.get("R"), x, y, null);
			break;
		case RD:
			g.drawImage(imgs.get("RD"), x, y, null);
			break;
		case D:
			g.drawImage(imgs.get("D"), x, y, null);
			break;
		case LD : 
			g.drawImage(imgs.get("LD"), x, y, null);
			break;
		case STOP:
			break;
		}
		move();
	}

	private void move() {
		
		
		switch(dir){
		case L : 
			x -= XSPEED;
			break;
		case LU :
			x -= XSPEED;
			y -= YSPEED;
			break;
		case U:
			y -= YSPEED;
			break;
		case RU:
			x += XSPEED;
			y -= YSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case RD:
			x += XSPEED;
			y += YSPEED;
			break;
		case D:
			y += YSPEED;
			break;
		case LD : 
			x -= XSPEED;
			y += YSPEED;
			break;
	}
		if (x < 0 || y < 0|| x >TankClient.GAME_WIDTH || y >TankClient.GAME_HEIGHT){
			isLive = false;
			tc.missiles.remove(this);
		}
	}
	public Rectangle getRect(){
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}
	
	
	public boolean hitTank(Tank t){
		if(this.isLive&& t.isLive() && this.getRect().intersects(t.getRect())&& (this.isMyMissile != t.isMytank())){
			if(t.isMytank()){
				t.setBlood(t.getBlood() - 20);
				if(t.getBlood() <= 0){
					t.setLive(false);
				}
			}else 
				t.setLive(false);
			this.setLive(false);
			tc.explosions.add(new Explosion(x,y,this.tc));
			return true;
		}
		return false;
	}
	
	public boolean hitTanks(List<Tank> tanks){
		for(int i = 0; i < tanks.size(); i++){
			if(hitTank(tanks.get(i))){
				return true;
			}
		}
		return false;
	}
	
	public boolean hitWall(Wall w){
		if(this.isLive && this.getRect().intersects(w.getRect())){
			this.isLive = false;
			return true;
		}
		else 
			return false;
	}
	
	
	
}
