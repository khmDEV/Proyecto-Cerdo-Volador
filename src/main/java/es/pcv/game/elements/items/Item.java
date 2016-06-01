package es.pcv.game.elements.items;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import es.pcv.core.render.Point2D;
import es.pcv.core.render.Render3D;
import es.pcv.core.render.auxiliar.Helper3D;
import es.pcv.core.updater.elements.Collisionable;
import es.pcv.core.updater.elements.Element;
import es.pcv.core.updater.elements.PolygonCollision;
import es.pcv.game.elements.player.Player;

public abstract class Item extends PolygonCollision implements Element {
	protected int TEXTURE = 5;

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

	public abstract void takeIt(Player pl);

	public abstract Item cloneItem();
	
	
}
