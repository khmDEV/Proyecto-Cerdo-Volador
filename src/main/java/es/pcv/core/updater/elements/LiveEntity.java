package es.pcv.core.updater.elements;

import java.awt.geom.Rectangle2D;
import java.util.concurrent.Semaphore;

import es.pcv.core.render.Point2D;

public abstract class LiveEntity extends PolygonObstacle implements hasLive, Element {

	protected Semaphore liveS = new Semaphore(1);
	protected int live, max_live, damage;
	protected long lastHit = 0;

	protected Point2D position;
	protected Point2D lastPosition;

	protected Point2D velocity;
	protected Point2D size;

	protected boolean obstacle_collision_ux = false;
	protected boolean obstacle_collision_uy = false;
	protected boolean obstacle_collision_dx = false;
	protected boolean obstacle_collision_dy = false;

	public LiveEntity(Rectangle2D r, int l, int d) {
		super(r);
		live = l;
		max_live = l;
		damage = d;
		size = new Point2D((float) r.getWidth(), (float) r.getHeight());
		position = new Point2D((float) r.getX(), (float) r.getY());
	}

	public LiveEntity(Point2D p, Point2D v, Point2D s, int l, int d) {
		live = l;
		max_live = l;
		damage = d;
		position = p;
		lastPosition = new Point2D(p.getX(), p.getY());
		velocity = v;
		size = s;
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

	public synchronized void update() {
		lastPosition.update(position);
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
		// System.out.println("--------------------------------------");
		// System.out.println(lastPosition.getX() + "_" + lastPosition.getY());
		// System.out.println(position.getX() + "_" + position.getY());
		// System.out.println(c.getCollisionBox().getMinY()+"<"+getCollisionBox().getMaxY());
		// System.out.println(c.getCollisionBox().getMaxY()+">"+getCollisionBox().getMinY());
		double uy = (c.getCollisionBox().getMinY() < getCollisionBox().getMaxY()
				&& c.getCollisionBox().getMaxY() > getCollisionBox().getMaxY() ? 0
						: Math.abs(c.getCollisionBox().getMinY() - getCollisionBox().getMaxY())),
				dy = (c.getCollisionBox().getMaxY() > getCollisionBox().getMinY()
						&& c.getCollisionBox().getMinY() < getCollisionBox().getMinY() ? 0
								: Math.abs(c.getCollisionBox().getMaxY() - getCollisionBox().getMinY())),
				ux = (c.getCollisionBox().getMinX() < getCollisionBox().getMaxX()
						&& c.getCollisionBox().getMaxX() > getCollisionBox().getMaxX() ? 0
								: Math.abs(c.getCollisionBox().getMinX() - getCollisionBox().getMaxX())),
				dx = (c.getCollisionBox().getMaxX() > getCollisionBox().getMinX()
						&& c.getCollisionBox().getMinX() < getCollisionBox().getMinX() ? 0
								: Math.abs(c.getCollisionBox().getMaxX() - getCollisionBox().getMinX()));
		//System.out.println(ux+" "+dx+" "+uy+" "+dy);
		double max = Math.max(Math.max(uy, dy), Math.max(ux, dx));
		if (max != 0) {
			obstacle_collision_dy = obstacle_collision_dy || dy == max;
			obstacle_collision_uy = obstacle_collision_uy || uy == max;
			obstacle_collision_dx = obstacle_collision_dx || dx == max;
			obstacle_collision_ux = obstacle_collision_ux || ux == max;
		}
	}

}
