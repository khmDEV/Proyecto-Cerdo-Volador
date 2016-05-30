package es.pcv.core.updater.elements;

import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.Semaphore;

import es.pcv.core.render.Point2D;
import es.pcv.game.configuration.Config;

public abstract class LiveEntity extends PolygonObstacle implements hasLive, Element {

	protected Semaphore liveS = new Semaphore(1);
	protected int live, max_live, damage;
	protected long lastHit = 0;

	protected Point2D velocity;

	protected boolean obstacle_collision_ux = false;
	protected boolean obstacle_collision_uy = false;
	protected boolean obstacle_collision_dx = false;
	protected boolean obstacle_collision_dy = false;

	/**
	 * 
	 * @param p
	 *            posicion de la esquina superior izquierda
	 * @param v
	 *            velocidad
	 * @param s
	 *            tamaño
	 * @param l
	 *            vidas
	 * @param d
	 *            daño
	 */
	public LiveEntity(Point2D p, Point2D v, Point2D s, int l, int d) {
		super(p, s);
		live = l;
		max_live = l;
		damage = d;
		velocity = v.multiply(Config.scale);
	}

	public int getLive() {
		try {
			liveS.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int l = live;
		liveS.release();
		return l;
	}

	public int getMaxLive() {
		try {
			liveS.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int l = max_live;
		liveS.release();
		return l;
	}

	public int addLive(int nl) {
		try {
			liveS.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int l = live+nl>max_live?max_live:(live += nl);
		liveS.release();
		return l;
	}

	public int doDamage(int d) {
		try {
			liveS.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (isVulnerable()) {
			live -= d;
			lastHit = System.currentTimeMillis();
		}
		int l = live;
		if (live <= 0) {
			kill();
		}
		// lastHit = System.currentTimeMillis();
		liveS.release();
		return l;
	}

	public boolean isVulnerable() {
		return true;
	}

	public int addMaxLive(int nl) {
		try {
			liveS.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int l = (max_live += nl);
		lastHit = System.currentTimeMillis();
		liveS.release();
		return l;
	}

	public int setMaxLive(int nl) {
		try {
			liveS.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int l = (max_live = nl);
		lastHit = System.currentTimeMillis();
		liveS.release();
		return l;
	}

	public int getDamage() {
		return damage;
	}

	public void collisionObstacle(Collisionable c) {
		Rectangle2D r1 = getCollisionBox();
		Rectangle2D r2 = c.getCollisionBox();
		Line2D dX = new Line2D.Double(new java.awt.geom.Point2D.Double(r1.getMinX(), r1.getMinY()),
				new java.awt.geom.Point2D.Double(r1.getMinX(), r1.getMaxY()));

		Line2D uX = new Line2D.Double(new java.awt.geom.Point2D.Double(r1.getMaxX(), r1.getMinY()),
				new java.awt.geom.Point2D.Double(r1.getMaxX(), r1.getMaxY()));

		Line2D dY = new Line2D.Double(new java.awt.geom.Point2D.Double(r1.getMinX(), r1.getMaxY()),
				new java.awt.geom.Point2D.Double(r1.getMaxX(), r1.getMaxY()));
		Line2D uY = new Line2D.Double(new java.awt.geom.Point2D.Double(r1.getMinX(), r1.getMinY()),
				new java.awt.geom.Point2D.Double(r1.getMaxX(), r1.getMinY()));
		if (r2.intersectsLine(uY)&&r2.intersectsLine(dY)) {
			Rectangle2D ruY = r2.createIntersection(new Rectangle2D.Double((uY.getX1() + uY.getX2()) / 2, uY.getY1(),
					Math.abs(uY.getX1() - uY.getX2()), 1));
			Rectangle2D rdY = r2.createIntersection(new Rectangle2D.Double((dY.getX1() + dY.getX2()) / 2, dY.getY1(),
					Math.abs(dY.getX1() - dY.getX2()), 1));
			Rectangle2D cuY = r2.createIntersection(ruY);
			Rectangle2D cdY = r2.createIntersection(rdY);
			if (cuY.getWidth() > cdY.getWidth()) {
				obstacle_collision_uy = true;
			}else if(cuY.getWidth() > cdY.getWidth()){
				obstacle_collision_dy = true;
			}
		}else{
			obstacle_collision_uy = obstacle_collision_uy || r2.intersectsLine(uY);
			obstacle_collision_dy = obstacle_collision_dy || r2.intersectsLine(dY);
		}
		
		if (r2.intersectsLine(uX)&&r2.intersectsLine(dX)) {
			Rectangle2D ruX = r2
					.createIntersection(new Rectangle2D.Double(uX.getX1(), (uX.getY1() + uX.getY2()) / 2, 1, 500));
			Rectangle2D rdX = r2
					.createIntersection(new Rectangle2D.Double(dX.getX1(), (dX.getY1() + dX.getY2()) / 2, 1, 500));
			Rectangle2D cuX = r2.createIntersection(ruX);
			Rectangle2D cdX = r2.createIntersection(rdX);
			if (cuX.getHeight() > cdX.getHeight()) {
				obstacle_collision_ux = true;
			}else if(cuX.getHeight() > cdX.getHeight()){
				obstacle_collision_dx = true;
			}
		}else{
			obstacle_collision_dx = obstacle_collision_dx || r2.intersectsLine(dX);
			obstacle_collision_ux = obstacle_collision_ux || r2.intersectsLine(uX);
		}

	}

}