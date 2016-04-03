package es.pcv.game.elements.scene;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import es.pcv.core.updater.elements.Element;

public class StandarWall extends Wall implements Element {
	Color c;
	Polygon ply;
	public StandarWall(Polygon ply,Color c) {
		super(ply.getBounds2D());
		this.ply=ply;
		this.c=c;
	}

	public void update() {
		
	}

	public void draw(Graphics g) {
		g.setColor(c);
		g.drawPolygon(ply);
	}

	public boolean isDead() {
		return false;
	}

	public boolean kill() {
		return false;
	}


}
