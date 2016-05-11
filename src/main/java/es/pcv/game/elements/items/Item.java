package es.pcv.game.elements.items;

import java.awt.geom.Rectangle2D;

import es.pcv.core.updater.elements.Collisionable;
import es.pcv.core.updater.elements.Element;
import es.pcv.core.updater.elements.PolygonCollision;
import es.pcv.game.elements.player.Player;

public abstract class Item extends PolygonCollision implements Element {
	
	public Item(Rectangle2D ply) {
		super(ply);
	}
	int hit=-1;
	
	public void update(){
		hit++;
	}
	
	public void collision(Collisionable c){
		if (c instanceof Player) {
			if (hit>1) {
				takeIt((Player) c);
			}else{
				hit=0;
			}
		}
	};

	public abstract void takeIt(Player pl);
	
}
