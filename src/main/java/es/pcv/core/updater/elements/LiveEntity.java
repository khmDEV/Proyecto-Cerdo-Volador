package es.pcv.core.updater.elements;

import java.awt.geom.Rectangle2D;
import java.util.concurrent.Semaphore;

import es.pcv.core.render.Point2D;

public abstract class LiveEntity extends Obstacle implements hasLive,Element {

	protected Semaphore liveS = new Semaphore(1);
	protected int live, max_live, damage;
	protected long lastHit = 0;
	
	protected Point2D position;
	protected Point2D lastPosition;
	
	protected Point2D velocity;
	protected Point2D size;
	

	public LiveEntity(Rectangle2D r,int l, int d) {
		super(r);
		live = l;
		max_live = l;
		damage = d;
	}
	
	public LiveEntity(Point2D p,Point2D v,Point2D s,int l, int d) {
		live = l;
		max_live = l;
		damage = d;
		position = p;
		lastPosition = new Point2D(p.getX(),p.getY());
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
		
		//lastHit = System.currentTimeMillis();
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
	
	public void returnLastPosition(){
		System.out.println("--------------------------------------");
		System.out.println(lastPosition.getX() + "_" + lastPosition.getY());
		System.out.println(position.getX() + "_" + position.getY());
		position.setX(lastPosition.getX());
		position.setY(lastPosition.getY());
	}
}
