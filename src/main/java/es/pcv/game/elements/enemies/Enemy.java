package es.pcv.game.elements.enemies;

import java.awt.geom.Rectangle2D;

import es.pcv.core.updater.elements.LiveEntity;

public abstract class Enemy extends LiveEntity{
	
	public Enemy(int l, int d) {
		super(l, d);
	}
	
	public Enemy(Rectangle2D r, int l, int d) {
		super(r, l, d);
	}

	

}
