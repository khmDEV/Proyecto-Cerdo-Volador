package es.pcv.game.elements.items;

import java.awt.Color;
import java.awt.Graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import es.pcv.core.render.ObjectIcon;
import es.pcv.core.render.Point2D;
import es.pcv.core.render.auxiliar.Helper3D;
import es.pcv.core.render.auxiliar.PolygonHelper;
import es.pcv.core.updater.elements.Collisionable;
import es.pcv.game.configuration.Config;
import es.pcv.game.elements.player.Player;

public class ItemAmmo extends Item {
	int ammo;
	Point2D position;

	static Point2D size = new Point2D(0.005, 0.005);
	static Point2D max_size = new Point2D(0.025, 0.025);

	public ItemAmmo(Point2D p, int ammo) {
		super(PolygonHelper.createRectangle(p, new Point2D(size.getX()*ammo>max_size.getX()?max_size.getX():size.getX()*ammo, 
				size.getY()*ammo>max_size.getY()?max_size.getY():size.getY()*ammo)).getBounds2D());
		icon= new ObjectIcon(Config.RESOURCES_PATH + "/icons/particleYellow_4.png", 1, 1);
		this.ammo = ammo;
		position = p;
	}

	
	public void takeIt(Player pl) {
		if (!kill()) {
			pl.addAmmo(ammo);
		}

	}

	public void collisionObstacle(Collisionable c) {

	}
	
	public ItemAmmo cloneItem(){
		return new ItemAmmo(position.clone(), ammo);
	}
	Color c = new Color(0.2f, 0.2f, 0.2f);

	
	public void draw3d(GL2 gl, GLU glu, GLUquadric quadric) {
		Helper3D.drawRectangle(gl, getCenterPos(), getSize(), 0, 0.01f, c, -1);
	}


}
