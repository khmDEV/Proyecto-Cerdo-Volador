package es.pcv.core.updater.elements;

import java.awt.Polygon;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.Semaphore;

import es.pcv.core.render.Point2D;
import es.pcv.core.render.auxiliar.PolygonHelper;

public abstract class PolygonCollision implements Collisionable{
	protected Rectangle2D rect;
	
	protected boolean dead=false;
	Semaphore deadS = new Semaphore(1);
	
	public PolygonCollision(Rectangle2D ply) {
		this.rect = ply;
	}

	public boolean isCollision(Collisionable c) {
		return c.getCollisionBox().intersects(rect);
	}

	public Rectangle2D getCollisionBox() {
		return rect;
	}
	
	public void setCollisionBox(Rectangle2D r){
		rect=r;
	}
	
	public void collision(Collisionable c){
	}
	
	public int getX(){
		return (int)rect.getX();
	}
	
	public void setX(int x){
		rect.setRect(x, getY(), getSizeX() , getSizeY() );
		rect.setFrame(x, getY(), getSizeX() , getSizeY() );
	}
	public void addX(int x){
		setX(getX() + x);
	}
	
	public int getY(){
		return (int)rect.getY();
	}
	
	public void setY(int y){
		rect.setRect(getX() , y, getSizeX(), getSizeY());
		rect.setFrame(getX() , y, getSizeX(), getSizeY());
	}
	
	public void addY(int y){
		setY(getY() + y);
	}
	
	public int getSizeX(){
		return (int)rect.getWidth();
	}
	public void setSizeX(int sizeX){
		rect.setRect(getX() , getY() , sizeX, getSizeY());
		rect.setFrame(getX() , getY() , sizeX, getSizeY());
	}
	public int getSizeY(){
		return (int)rect.getHeight();
	}
	public void setSizeY(int sizeY){
		rect.setRect(getX() , getY() , getSizeX() , sizeY);
		rect.setFrame(getX() , getY() , getSizeX() , sizeY);
	}
	
	public Point2D getPos(){
		return new Point2D(getX(),getY());
	}
	
	public void setPos(int x, int y){
		setX(x);
		setY(y);
	}
	public void setPos(Point2D pos){
		setX((int)pos.getX());
		setY((int)pos.getY());
	}
	
	public void posAdd(Point2D pos){
		addX((int)pos.getX());
		addY((int)pos.getY());
	}
	
	public Point2D getSize(){
		return new Point2D(getSizeX(),getSizeY());
	}
	
	public void setSize(int sizeX, int sizeY){
		setX(sizeX);
		setY(sizeY);
	}
	
	public Polygon getRectangle(){
		return PolygonHelper.getRectangle(getPos(), getSize());
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
