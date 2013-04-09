package com.gengchen.www;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class Tank {
	private int x , y;
	private int previous_x, previous_y;
	private boolean bl = false;
	private boolean br = false;
	private boolean bu = false;
	private boolean bd = false;
	private boolean isMytank;
	private int blood = 100;
	private BloodBar bb = new BloodBar();
	public int getBlood() {
		return blood;
	}
	public void setBlood(int blood) {
		this.blood = blood;
	}
	public boolean isMytank() {
		return isMytank;
	}
	private boolean isLive = true;
	public static final int XSPEED = 5;
	public static final int YSPEED = 5;
	
	
	private static Random r = new Random();
	private int step = r.nextInt(12) + 3;
	TankClient tc;

	
	private Direction dir = Direction.STOP;
	private Direction pt_dir = Direction.D;
	private static Toolkit  tk = Toolkit.getDefaultToolkit();
	private static Image[] tankImages = null;
	static Map<String,Image> imgs = new HashMap<String, Image>();
	static{
		tankImages = new Image[]{
				tk.getImage(Explosion.class.getClassLoader().getResource("images/tankL.gif")),
				tk.getImage(Explosion.class.getClassLoader().getResource("images/TankLU.gif")),
				tk.getImage(Explosion.class.getClassLoader().getResource("images/TankU.gif")),
				tk.getImage(Explosion.class.getClassLoader().getResource("images/TankRU.gif")),
				tk.getImage(Explosion.class.getClassLoader().getResource("images/TankR.gif")),
				tk.getImage(Explosion.class.getClassLoader().getResource("images/TankRD.gif")),
				tk.getImage(Explosion.class.getClassLoader().getResource("images/TankD.gif")),
				tk.getImage(Explosion.class.getClassLoader().getResource("images/TanKLD.gif")),
				
		};
		imgs.put("L", tankImages[0]);
		imgs.put("LU", tankImages[1]);
		imgs.put("U", tankImages[2]);
		imgs.put("RU", tankImages[3]);
		imgs.put("R", tankImages[4]);
		imgs.put("RD", tankImages[5]);
		imgs.put("D", tankImages[6]);
		imgs.put("LD", tankImages[7]);
	}
	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;
	
	public Tank(int x, int y,boolean isMytank) {
		super();
		this.x = x;
		this.y = y;
		this.isMytank = isMytank;
	}
	public Tank(int x, int y , boolean good,Direction dir,TankClient tc){
		this(x, y,good);
		this.tc = tc;
		this.dir = dir;
	}
	
	public boolean isLive() {
		return isLive;
	}
	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}
	public void draw(Graphics g){
		if(isLive == false){
			if(!isMytank){
				this.tc.enemyTanks.remove(this);
			}
			return ;
		}
//		Color c = g.getColor();
//		if(this.isMytank ){
//			g.setColor(Color.RED);
//		}else{
//			g.setColor(Color.BLUE);
//		}
//		g.fillOval(x, y, WIDTH, HEIGHT);
//		g.setColor(c);
		
		if(this.isMytank)
		bb.draw(g);
		switch(pt_dir){
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
	
	private void move(){
	    this.previous_x = x;
	    this.previous_y = y;
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
		case STOP:
			break;
		}
		if(this.dir != Direction.STOP){
			this.pt_dir = this.dir;
		}
		if(x < 0) x = 0;
		if(y < 30) y = 30;
		if(x + Tank.WIDTH > TankClient.GAME_WIDTH ) x = TankClient.GAME_WIDTH - Tank.WIDTH;
		if(y + Tank.HEIGHT > TankClient.GAME_HEIGHT) y = TankClient.GAME_HEIGHT - Tank.HEIGHT; 
	
	    if(!this.isMytank){
	    	Direction[] dirs = Direction.values();
	    	if(step == 0){
	    		step = r.nextInt(12) + 3;
	    		int rn = r.nextInt(dirs.length);
	    		dir = dirs[rn];
	    	}
	    	step --;
	    	if(r.nextInt(40)> 38)
		    this.fire();
	    }
	
	}
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		switch(key){
		
		case KeyEvent.VK_D :
			br = true;
			break;
		case KeyEvent.VK_A :
			bl = true;
			break;
		case KeyEvent.VK_W :
			bu = true;
			break;
		case KeyEvent.VK_S :
			bd = true;
			break;
		}
		locateDirection();
	}
	private void locateDirection(){
		if(bl && !bu && !br && !bd) dir = Direction.L; //just press the left
		else if(bl &&  bu && !br && !bd) dir = Direction.LU; //press left and up
		else if(!bl && bu && !br && !bd) dir = Direction.U; // just press up
		else if(!bl && bu && br && !bd) dir = Direction.RU; //press right and up
		else if(!bl && !bu && br && !bd) dir = Direction.R;  //just press right
		else if(!bl && !bu && br && bd) dir = Direction.RD; // press right and down
		else if(!bl && !bu && !br && bd) dir = Direction.D; // just press down
		else if(bl && !bu && !br && bd) dir = Direction.LD; //press left and down
		else if (!bl && !bu && !br && !bd) dir = Direction.STOP; // no press
		
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		switch(key){
		case KeyEvent.VK_CONTROL:
			fire();
			break;
		case KeyEvent.VK_W:
			bu = false;
			break;
		case KeyEvent.VK_A :
			bl = false;
			break;
			
		case KeyEvent.VK_D :
			br = false;;
			break;
		case KeyEvent.VK_S :
			bd = false;;
			break;
		case KeyEvent.VK_Q:
			superFire();
			break;
		case KeyEvent.VK_F5:
			tc.initial();
			break;
		}
			
		locateDirection();
	}
	
	public Missile fire(){
		if(isLive == false){
			return null;
		}
		int missile_x = x + Tank.WIDTH/2 - Missile.WIDTH /2;
		int missile_y = y + Tank.HEIGHT/2 - Missile.HEIGHT /2;
		Missile m = new Missile(missile_x,missile_y,this.isMytank,pt_dir,this.tc);
		tc.missiles.add(m);
		return m;
	}
	public Missile fire(Direction dir){
		if(isLive == false){
			return null;
		}
		int missile_x = x + Tank.WIDTH/2 - Missile.WIDTH /2;
		int missile_y = y + Tank.HEIGHT/2 - Missile.HEIGHT /2;
		Missile m = new Missile(missile_x,missile_y,this.isMytank,dir,this.tc);
		tc.missiles.add(m);
		return m;
	}
	
	public void superFire(){
		Direction[] dirs = dirs = Direction.values();
		for(int i = 0 ; i < dirs.length; i++){
			if(dirs[i] != Direction.STOP)
			fire(dirs[i]);
		}
	}
	public Rectangle getRect() {
		
		return new Rectangle(x,y,WIDTH, HEIGHT);
	}
	
	public boolean hitWall(Wall w){
		if(this.isLive && this.getRect().intersects(w.getRect())){
			this.stay();
			return true;
		}
		else 
			return false;

	}
	
	public boolean hitOtherTanks(List<Tank> tanks){
		for(int i = 0 ; i < tanks.size(); i++){
			Tank t = tanks.get(i);
			if(this != t){
				if(this.isLive && t.isLive && this.getRect().intersects(t.getRect())){
					this.stay();
					t.stay();
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean eatBloodMedicine(BloodMedicine bm){
		if(this.isLive && bm.isLive() && this.getRect().intersects(bm.getRect())){
			this.blood = 100;
			bm.setLive(false);
			return true;
		}
		return false;
	}
	private void stay(){
		this.x = this.previous_x;
		this.y = this.previous_y;
	}
	
	private class BloodBar{
		public void draw(Graphics g){
			Color c = g.getColor();
			g.setColor(Color.RED);
			g.drawRect(x, y- 10, WIDTH, 10);
			int w = WIDTH * blood / 100;
			g.fillRect(x, y - 10, w, 10);
			g.setColor(c);
			
		}
	}
}

	
	
