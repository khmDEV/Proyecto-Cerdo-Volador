package es.pcv.game.elements.enemies;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Walker;
import es.pcv.game.elements.items.drops.DropEnemy;
import es.pcv.game.elements.player.Player;

public abstract class Enemy extends Walker{
	Player pl;
	public static final DropEnemy drop=new DropEnemy();
	public Enemy(Point2D p,Point2D v,Point2D s,int l, int d, Player pl) {		
		super(p, v, s, l, d);
		this.pl=pl;
	}
	
	public boolean kill(){
		boolean r=super.kill();
		if (!r) {
			drop.spawnDrops(getPos());
		}
		return r;
	}
	
}
