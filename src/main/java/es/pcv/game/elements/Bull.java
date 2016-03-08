package es.pcv.game.elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.Semaphore;

import javax.swing.JPanel;

import es.pcv.core.updater.elements.Collisionable;
import es.pcv.core.updater.elements.Element;
import es.pcv.game.configuration.Config;

public class Bull implements Element {
	float x = 0.5f;
	float y = 0;
	float vx = 0.005f;
	float vy = -0.005f;
	float w = 0.01f;
	float h = 0.01f;
	Color c = new Color(255, 255, 0);
	Semaphore deadS = new Semaphore(1);


	public Bull(float x,float y,float vx,float vy) {
		ply = new Polygon(
				new int[] { Math.round((x + w) * Config.WEITH), Math.round((x - w) * Config.WEITH),
						Math.round((x - w) * Config.WEITH), Math.round((x + w) * Config.WEITH) },
				new int[] { Math.round((y - h) * Config.WEITH), Math.round((y - h) * Config.WEITH),
						Math.round((y + h) * Config.WEITH), Math.round((y + h) * Config.WEITH) },
				4);
		this.x=x;
		this.y=y;
		this.vx=vx;
		this.vy=vy;
		
		
	}
	boolean dead=false;
	public void update() {
		x += vx;
		y += vy;
		if (x > 1 || x < 0) {
			kill();
		}
		if (y > 1 || y < 0) {
			kill();
		}
	}

	Polygon ply;

	public void draw(Graphics g) {
		ply = new Polygon(
				new int[] { Math.round((x + w) * Config.WEITH), Math.round((x - w) * Config.WEITH),
						Math.round((x - w) * Config.WEITH), Math.round((x + w) * Config.WEITH) },
				new int[] { Math.round((y - h) * Config.WEITH), Math.round((y - h) * Config.WEITH),
						Math.round((y + h) * Config.WEITH), Math.round((y + h) * Config.WEITH) },
				4);
		g.setColor(c);
		//g.setColor(c);
		g.drawOval(Math.round(x*Config.WEITH), Math.round(y*Config.HEIGTH), Math.round(w*Config.WEITH), Math.round(h*Config.HEIGTH));
		//g.drawPolygon(ply);
	}

	public boolean isDead() {
		try {
			deadS.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean r = dead;
		deadS.release();
		return r;
	}
	
	public boolean kill() {
		try {
			deadS.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean r = (dead=true);
		deadS.release();
		return r;
	}

	public boolean isCollision(Collisionable c) {
		return c.getCollisionBox().intersects(ply.getBounds2D());
	}

	public void collision(Collisionable col) {
		c = new Color((int) Math.round(Math.random() * 255), (int) Math.round(Math.random() * 255),
				(int) Math.round(Math.random() * 255));
	}

	public Rectangle2D getCollisionBox() {
		return ply.getBounds2D();
	}
}
