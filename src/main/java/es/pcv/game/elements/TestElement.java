package es.pcv.game.elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import es.pcv.core.updater.elements.Collisionable;
import es.pcv.core.updater.elements.Element;
import es.pcv.game.configuration.Config;

public class TestElement implements Element {

	float x = 0.5f;
	float y = 0;
	float vx = 0.005f;
	float vy = 0.005f;
	float w = 0.05f;
	float h = 0.05f;

	Polygon ply;
	Color c=new Color(0, 255, 0);

	public TestElement() {
		ply = new Polygon(
				new int[] { Math.round((x + w) * Config.WEITH), Math.round((x - w) * Config.WEITH),
						Math.round((x - w) * Config.WEITH), Math.round((x + w) * Config.WEITH) },
				new int[] { Math.round((y - h) * Config.WEITH), Math.round((y - h) * Config.WEITH),
						Math.round((y + h) * Config.WEITH), Math.round((y + h) * Config.WEITH) },
				4);
	}

	public boolean isCollision(Collisionable c) {
		return c.getCollisionBox().intersects(ply.getBounds2D());
	}

	public void update() {
		x += vx;
		y += vy;
		if (x > 1 || x < 0) {
			vx = -vx;
		}
		if (y > 1 || y < 0) {
			vy = -vy;
		}
	}

	public void draw(Graphics g) {
		ply = new Polygon(
				new int[] { Math.round((x + w) * Config.WEITH), Math.round((x - w) * Config.WEITH),
						Math.round((x - w) * Config.WEITH), Math.round((x + w) * Config.WEITH) },
				new int[] { Math.round((y - h) * Config.WEITH), Math.round((y - h) * Config.WEITH),
						Math.round((y + h) * Config.WEITH), Math.round((y + h) * Config.WEITH) },
				4);
		g.setColor(c);
		g.drawPolygon(ply);
	}

	public void collision(Collisionable col) {
		c=new Color((int) Math.round(Math.random()*255),(int) Math.round(Math.random()*255),(int) Math.round(Math.random()*255));
	}

	public synchronized boolean isDead() {
		return false;
	}
	
	public synchronized boolean kill() {
		return false;
	}

	public Rectangle2D getCollisionBox() {
		return ply.getBounds2D();
	}

}
