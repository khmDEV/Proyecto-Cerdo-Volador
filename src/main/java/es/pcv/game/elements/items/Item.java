package es.pcv.game.elements.items;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import es.pcv.core.render.ObjectIcon;
import es.pcv.core.updater.elements.Collisionable;
import es.pcv.core.updater.elements.Element;
import es.pcv.core.updater.elements.PolygonCollision;
import es.pcv.game.elements.player.Player;

public abstract class Item extends PolygonCollision implements Element {
	protected int TEXTURE = 5;
	protected ObjectIcon icon;

	public Item(Rectangle2D ply) {
		super(ply);
	}
	
	public void update(long ms){
	}
	
	public void collision(Collisionable c){
		if (c instanceof Player) {
				takeIt((Player) c);
		}
	};
	
	public void draw(Graphics g) {
		g.drawImage(icon.getImage(0), getX(), getY(), getSizeX(), getSizeY(), null);
	}

	public abstract void takeIt(Player pl);

	public abstract Item cloneItem();
	
	
}
