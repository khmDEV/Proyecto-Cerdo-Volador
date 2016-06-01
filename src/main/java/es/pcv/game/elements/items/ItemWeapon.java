package es.pcv.game.elements.items;

import java.awt.Color;
import java.awt.Graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import es.pcv.core.render.Point2D;
import es.pcv.core.render.auxiliar.Helper3D;
import es.pcv.core.render.auxiliar.PolygonHelper;
import es.pcv.core.updater.elements.Collisionable;
import es.pcv.game.elements.player.Player;
import es.pcv.game.elements.weapons.Weapon;

public class ItemWeapon extends Item {
	int id;
	Point2D position;
	static Point2D size = new Point2D(0.05, 0.05);
	boolean dead = false;
	Weapon gun;

	public ItemWeapon(Point2D p, Weapon g) {
		super(PolygonHelper.createRectangle(p, size).getBounds2D());
		id = g.getId();
		position = p.clone();
		gun = g;
	}

	private int hit = 0;

	public void update(long ms) {
		hit = hit <= 1 ? hit + 1 : hit;
	}

	public void collision(Collisionable c) {
		if (c instanceof Player) {
			if (hit > 1) {
				takeIt((Player) c);
			}
			hit = 0;
		}
	};

	@Override
	public synchronized void takeIt(Player pl) {
		if (!isDead()) {
			Weapon ng = pl.getWeapon(gun);
			if (ng == null) {
				kill();
			} else {
				gun = ng;
				id = gun.getId();
			}
		}

	}

	public void draw(Graphics g) {
		g.drawImage(Weapon.ICONS.getImage(id), getX(), getY(), getSizeX(), getSizeY(), null);
	}

	public void collisionObstacle(Collisionable c) {

	}

	public ItemWeapon cloneItem() {
		return new ItemWeapon(position.clone(), gun);
	}

	public void draw3d(GL2 gl, GLU glu, GLUquadric quadric) {
		Helper3D.drawRectangle(gl, getCenterPos(), getSize(), 0, 0.1f, null, 3);
	}

}
