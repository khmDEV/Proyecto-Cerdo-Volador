package es.pcv.game.elements.enemies;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.LiveEntity;
import es.pcv.core.updater.elements.Walker;

public abstract class Enemy extends Walker{
	
	public Enemy(Point2D p,Point2D v,Point2D s,int l, int d) {
		super(p, v, s, l, d);
	}
	
}
