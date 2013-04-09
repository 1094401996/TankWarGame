package com.gengchen.www;
import java.awt.*;


public class BloodMedicine {
	int x,y , w, h;
	TankClient tc;
	private int step = 0;
	private boolean isLive = true;
	
	
	public boolean isLive() {
		return isLive;
	}
	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}

	private  int[][] points = {
			{350,300},{360,300},{375,275},{400,200},
			{360,270},{365,290},{340,280} 	
	};
	public BloodMedicine(){
		x = points[0][0];
		y = points[0][1];
		w = h = 15;
	}
	public void draw(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.MAGENTA);
		g.fillRect(x,y,w,h);
		g.setColor(c);
		move();
	}
	private void move() {
		step ++;
		if(step == points.length){
			step = 0;
		}
		x = points[step][0];
		y = points[step][0];
	}
	
	public Rectangle getRect(){
		return new Rectangle(x,y,w,h);
	}
	
}
