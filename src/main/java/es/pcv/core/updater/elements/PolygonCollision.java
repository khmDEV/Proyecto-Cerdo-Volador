package es.pcv.core.updater.elements;

import java.awt.geom.Rectangle2D;
import java.util.concurrent.Semaphore;

public abstract class PolygonCollision extends PolygonObj implements Collisionable{
	protected boolean dead=false;
	Semaphore deadS = new Semaphore(1);
	
	public PolygonCollision(Rectangle2D ply) {
		super(ply);
	}

	public boolean isCollision(Collisionable c) {
		return c.getCollisionBox()!=null?c.getCollisionBox().intersects(rect):false;
	}

	public Rectangle2D getCollisionBox() {
		return rect;
	}
	
	public void setCollisionBox(Rectangle2D r){
		rect=r;
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
		boolean r = dead;
		dead = true;
		deadS.release();
		return r;
	}
	
}
