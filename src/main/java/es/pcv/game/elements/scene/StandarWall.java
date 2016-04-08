package es.pcv.game.elements.scene;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import es.pcv.core.render.Point2D;
import es.pcv.core.render.auxiliar.PolygonHelper;
import es.pcv.core.updater.elements.Element;

public class StandarWall extends Wall implements Element {
	Color c;
	public StandarWall(Point2D p,Point2D s,Color c) {
		super(p,s);
		this.c=c;
	}

	public void update() {
		
	}

	public void draw(Graphics g) {
		g.setColor(c);
		g.drawPolygon(getRectangle());
	}

	public boolean isDead() {
		return false;
	}

	public boolean kill() {
		return false;
	}


}
