package es.pcv.game.elements.items;

import java.awt.Graphics;

import es.pcv.core.render.Point2D;
import es.pcv.core.render.auxiliar.PolygonHelper;
import es.pcv.core.updater.elements.Collisionable;
import es.pcv.game.elements.player.Player;

public class ItemAmmo extends Item {
	int ammo;
	Point2D position;

	static Point2D size = new Point2D(0.012, 0.004);
	static Point2D max_size = new Point2D(0.015, 0.005);

	public ItemAmmo(Point2D p, int ammo) {
		super(PolygonHelper.createRectangle(p, new Point2D(size.getX()*ammo>max_size.getX()?max_size.getX():size.getX()*ammo, 
				size.getY()*ammo>max_size.getY()?max_size.getY():size.getY()*ammo)).getBounds2D());
		this.ammo = ammo;
		position = p;
	}

	@Override
	public void takeIt(Player pl) {
		if (!kill()) {
			pl.addAmmo(ammo);
		}

	}

	public void draw(Graphics g) {
		g.drawRect(getX(), getY(), getSizeX(), getSizeY());
	}

	public void collisionObstacle(Collisionable c) {

	}
	
	public ItemAmmo cloneItem(){
		return new ItemAmmo(position.clone(), ammo);
	}

}
