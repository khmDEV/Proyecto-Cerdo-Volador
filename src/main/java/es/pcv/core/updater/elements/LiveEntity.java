package es.pcv.core.updater.elements;

import java.awt.geom.Rectangle2D;
import java.util.concurrent.Semaphore;

public abstract class LiveEntity extends Obstacle implements hasLive,Element {

	protected Semaphore liveS = new Semaphore(1);
	protected int live, max_live, damage;
	protected long lastHit = 0;

	public LiveEntity(Rectangle2D r,int l, int d) {
		super(r);
		live = l;
		max_live = l;
		damage = d;
	}
	
	public LiveEntity(int l, int d) {
		live = l;
		max_live = l;
		damage = d;
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
		}
		int l = live;
		if (live <= 0) {
			kill();
		}
		lastHit = System.currentTimeMillis();
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
}
