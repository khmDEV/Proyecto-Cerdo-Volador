package es.pcv.game.elements.enemies;

import java.awt.geom.Rectangle2D;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.LiveEntity;

public abstract class Enemy extends LiveEntity{
	
	public Enemy(Point2D p,Point2D v,Point2D s,int l, int d) {
		super(p, v, s, l, d);
	}
	
	public Enemy(Rectangle2D r, int l, int d) {
		super(r, l, d);
	}

	

}
