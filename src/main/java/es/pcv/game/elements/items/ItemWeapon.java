package es.pcv.game.elements.items;

import java.awt.Graphics;

import es.pcv.core.render.ObjectIcon;
import es.pcv.core.render.Point2D;
import es.pcv.core.render.auxiliar.PolygonHelper;
import es.pcv.core.updater.elements.Collisionable;
import es.pcv.game.Game;
import es.pcv.game.elements.player.Player;
import es.pcv.game.elements.weapons.Weapon;

public class ItemWeapon extends Item {
	ObjectIcon iconGuns= new ObjectIcon("weapons.png", 2, 4);
	int id;
	Point2D position;
	static Point2D size=new Point2D(0.05, 0.05);
	boolean dead=false;
	Weapon gun;
	public ItemWeapon(Point2D p, Weapon g) {
		super(PolygonHelper.createRectangle(p, size).getBounds2D());
		id=g.getId();
		position=p;
		gun=g;
	}

	@Override
	public void takeIt(Player pl) {
		if (!kill()) {
			
			Weapon ng=pl.getWeapon(gun);
			System.out.println(ng);
			if (ng!=null) {
				Game.getGame().addElement(new ItemWeapon(position, ng));
			}
		}
		
	}
	
	public void draw(Graphics g) {
		g.drawImage(iconGuns.getImage(id), getX(),getY(),getSizeX(),getSizeY(),null);
	}

	public void collisionObstacle(Collisionable c) {

	}

}
