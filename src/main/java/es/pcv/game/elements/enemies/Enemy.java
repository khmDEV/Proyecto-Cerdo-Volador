package es.pcv.game.elements.enemies;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Walker;
import es.pcv.game.elements.player.Player;

public abstract class Enemy extends Walker{
	Player pl;
	public Enemy(Point2D p,Point2D v,Point2D s,int l, int d, Player pl) {		
		super(p, v, s, l, d);
		this.pl=pl;
	}
	
}
