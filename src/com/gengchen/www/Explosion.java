package com.gengchen.www;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;


public class Explosion {
	private int x;
	private int y;
	private TankClient tc;
	private boolean isLive = true;
	int step;
	private static Toolkit  tk = Toolkit.getDefaultToolkit();
	private static Image[] images = {
		tk.getImage(Explosion.class.getClassLoader().getResource("images/0.gif")),
		tk.getImage(Explosion.class.getClassLoader().getResource("images/1.gif")),
		tk.getImage(Explosion.class.getClassLoader().getResource("images/2.gif")),
		tk.getImage(Explosion.class.getClassLoader().getResource("images/3.gif")),
		tk.getImage(Explosion.class.getClassLoader().getResource("images/4.gif")),
		tk.getImage(Explosion.class.getClassLoader().getResource("images/5.gif")),
		tk.getImage(Explosion.class.getClassLoader().getResource("images/6.gif")),
		tk.getImage(Explosion.class.getClassLoader().getResource("images/7.gif")),
		tk.getImage(Explosion.class.getClassLoader().getResource("images/8.gif")),
		tk.getImage(Explosion.class.getClassLoader().getResource("images/9.gif")),
		tk.getImage(Explosion.class.getClassLoader().getResource("images/10.gif"))
	};
	private static boolean initial;

	
	public Explosion(int x, int y , TankClient tc){
		this.x = x;
		this.y = y;
		this.tc = tc;
	}
	public void draw(Graphics g){
		if(initial == false){
			for(int i = 0 ; i < images.length ; i++){
				g.drawImage(images[i],-100,-100,null);
			}
			initial = true;
		}
		if(!isLive){
			tc.explosions.remove(this);
			return ;
		}
		if(step == images.length){
			isLive = false;
			step = 0;
			return ;
		}
		g.drawImage(images[step],x,y,null);
		step++;
	}
}
