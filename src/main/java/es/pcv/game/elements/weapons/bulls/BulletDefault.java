package es.pcv.game.elements.weapons.bulls;

import java.awt.Color;
import java.awt.Graphics;

import es.pcv.core.render.Point2D;
import es.pcv.core.render.auxiliar.PolygonHelper;
import es.pcv.core.updater.elements.LiveEntity;
import es.pcv.core.updater.elements.Walker;
import es.pcv.game.configuration.Config;

public class BulletDefault extends Bullet {
	
	public final static Point2D size = new Point2D(0.01f, 0.01f);
	Color c = new Color(255, 0, 255);
	
	public BulletDefault(Walker whoAttack, Point2D position, Point2D vel) {
		super(whoAttack, position, vel,size,1,50);
	}
	
	public BulletDefault(Walker whoAttack, Point2D position, Point2D vel,int hits,int damage) {
		super(whoAttack, position, vel,size,hits,damage);
	}

	public void update() {
		posAdd(velocity);
		if (getX() > Config.scale.getX() || getX() < 0) {
			kill();
		}
		if (getY() > Config.scale.getY() || getY() < 0) {
			kill();
		}
	}



	public void draw(Graphics g) {
		g.setColor(c);
		g.drawOval(getX(),getY(),getSizeX(),getSizeY());
		/**g.drawOval((int)Math.round(getX() * Config.scale.getX()), (int)Math.round(getY() * Config.scale.getY()),
				(int)Math.round(getSizeX() * Config.scale.getX()), (int)Math.round(getSizeY() * Config.scale.getY()));
				*/
	}




}
