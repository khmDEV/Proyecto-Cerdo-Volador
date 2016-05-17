package es.pcv.game.elements.scene;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import es.pcv.core.render.Point2D;

public class StandarWall extends Wall {
	Color c;
	public StandarWall(Point2D p,Point2D s) {
		super(p,s);
		this.c=new Color(0, 0, 0);
	}
	

	public void draw(Graphics g) {
		g.setColor(c);
		g.fillRect(getX(), getY(), getSizeX() , getSizeY());
		//g.drawPolygon(getRectangle());
	}

	public boolean isDead() {
		return false;
	}

	public boolean kill() {
		return false;
	}


}
