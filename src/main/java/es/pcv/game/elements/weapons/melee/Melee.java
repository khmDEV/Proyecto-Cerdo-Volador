package es.pcv.game.elements.weapons.melee;

import java.awt.Graphics;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Collisionable;
import es.pcv.core.updater.elements.LiveEntity;
import es.pcv.core.updater.elements.Walker;
import es.pcv.game.elements.enemies.Enemy;
import es.pcv.game.elements.player.Player;
import es.pcv.game.elements.weapons.Weapon;
import es.pcv.game.elements.weapons.WeaponEntity;

public abstract class Melee extends WeaponEntity {
	protected int durability;
	protected Walker whoAttack;
	protected boolean vertical = true;

	public Melee(Walker w, Point2D size, int dur, int damage) {
		super(w!=null?w.getPos():new Point2D(0, 0), new Point2D(0, 0), size, 1, damage);
		whoAttack = w;
		durability = dur;
	}

	protected void moveMelee() {
		int aux = getSizeX();
		setSizeX(getSizeY());
		setSizeY(aux);
		vertical = !vertical;
	}
	
	public void update() {
		/*	if (System.currentTimeMillis() - init > time) {
			kill();
		}*/
		setPos(whoAttack.getPos());
		if (whoAttack.getDir() == 0) {
			if (!vertical) {
				moveMelee();
			}
			addY(whoAttack.getSizeY());
		} else if (whoAttack.getDir() == 1) {
			if (vertical) {
				moveMelee();
			}
			addX(-getSizeX());
		} else if (whoAttack.getDir() == 2) {
			if (vertical) {
				moveMelee();
			}
			addX(whoAttack.getSizeX());
			addY(whoAttack.getSizeY());
		} else {
			if (!vertical) {
				moveMelee();
			}
			addY(-whoAttack.getSizeY());
			addX(whoAttack.getSizeX());
		}

	}
	
	public void draw(Graphics g) {
		g.drawPolygon(getRectangle());
	}

	public void setWalker(Walker whoAttack) {
		this.whoAttack = whoAttack;
	}

	public void collisionObstacle(Collisionable c) {
	}

	public boolean isCollision(Collisionable col) {
		return whoAttack != null && whoAttack != col && !(col instanceof Weapon) && super.isCollision(col);
	}

	public int doDamage(int d) {
		return 1;
	}
	
	public void collision(Collisionable col) {
		if (whoAttack != col && (col instanceof Player && whoAttack instanceof Enemy)
				|| (col instanceof Enemy && whoAttack instanceof Player)) {
			LiveEntity r = (LiveEntity) col;
			r.doDamage(getDamage());
			durability--;
			if (durability == 0) {
				this.kill();
			}
		}
	}
	
	public int getDurability(){
		return durability;
	}

}
