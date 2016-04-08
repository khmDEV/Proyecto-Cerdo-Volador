package es.pcv.core.updater.elements;

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
	
	
	
	protected boolean dead=false;
	
	Semaphore deadS = new Semaphore(1);

	
	/**
	 * 
	 * @param p posicion de la esquina superior izquierda
	 * @param v velocidad
	 * @param s tamaño
	 * @param l vidas
	 * @param d daño
	 */
	public LiveEntity(Point2D p, Point2D v, Point2D s, int l, int d) {
		super(p,s);
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
		int l = (live += nl);
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

		double uy = (c.getCollisionBox().getMinY() < getCollisionBox().getMaxY()
				&& c.getCollisionBox().getMaxY() > getCollisionBox().getMaxY() ? 0
						: Math.abs(c.getCollisionBox().getMinY() - getCollisionBox().getMaxY()));
		
		double dy = (c.getCollisionBox().getMaxY() > getCollisionBox().getMinY()
						&& c.getCollisionBox().getMinY() < getCollisionBox().getMinY() ? 0
								: Math.abs(c.getCollisionBox().getMaxY() - getCollisionBox().getMinY()));
		
		double ux = (c.getCollisionBox().getMinX() < getCollisionBox().getMaxX()
						&& c.getCollisionBox().getMaxX() > getCollisionBox().getMaxX() ? 0
								: Math.abs(c.getCollisionBox().getMinX() - getCollisionBox().getMaxX()));
		
		double dx = (c.getCollisionBox().getMaxX() > getCollisionBox().getMinX()
						&& c.getCollisionBox().getMinX() < getCollisionBox().getMinX() ? 0
								: Math.abs(c.getCollisionBox().getMaxX() - getCollisionBox().getMinX()));

		double max = Math.max(Math.max(uy, dy), Math.max(ux, dx));
		if (max != 0) {
			obstacle_collision_dy = obstacle_collision_dy || dy == max;
			obstacle_collision_uy = obstacle_collision_uy || uy == max;
			obstacle_collision_dx = obstacle_collision_dx || dx == max;
			obstacle_collision_ux = obstacle_collision_ux || ux == max;
		}
	}
	
	public synchronized boolean isDead() {
		try {
			deadS.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean r=dead;
		deadS.release();
		return r;
	}

	public synchronized boolean kill() {
		try {
			deadS.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean r = (dead = true);
		deadS.release();
		return r;
	}

}