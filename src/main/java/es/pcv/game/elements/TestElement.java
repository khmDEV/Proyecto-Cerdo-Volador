package es.pcv.game.elements;

import java.awt.Color;
import java.awt.Graphics;

import es.pcv.core.updater.elements.Collisionable;
import es.pcv.core.updater.elements.Element;
import es.pcv.game.configuration.Config;


public class TestElement implements Element{
	
	float x=0.5f;
	float y=0;
	float vx=0.005f;
	float vy=0.005f;
	
	public boolean isCollision(Collisionable c) {
		// TODO Auto-generated method stub
		return false;
	}

	public void update() {
		x+=vx;y+=vy;
		if (x>1||x<0) {
			vx=-vx;
		}
		if (y>1||y<0) {
			vy=-vy;
		}
	}

	public void draw(Graphics g) {
		g.setColor(new Color(0, 255, 0));
		int xx=Math.round(x*Config.WEITH);
		int yy=Math.round(y*Config.HEIGTH);
		g.draw3DRect(xx, yy, 10, 10, false);
	}

	public void collision(Collisionable c) {
		// TODO Auto-generated method stub
		
	}

	public boolean isDead() {
		// TODO Auto-generated method stub
		return false;
	}

}
