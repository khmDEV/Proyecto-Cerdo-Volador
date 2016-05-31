package es.pcv.core.render;

import es.pcv.game.configuration.Config;

public class Point2D {
	
	private float x;
	private float y;
	
	public Point2D(float x,float y){
		this.x=x;
		this.y=y;
	}
	public Point2D(double x2, double y2) {
		update((float)x2,(float)y2);
	}

	
	public synchronized Point2D update(Point2D p){
		update(p.getX(),p.getY());
		return this;
	}

	public synchronized void update(float x, float y){
		this.x=x;
		this.y=y;
	}
	
	public synchronized float getX(){
		return x;
	}
	
	public synchronized float getY(){
		return y;
	}
	
	public synchronized Point2D setX(float x){
		this.x=x;
		return this;
	}
	
	public synchronized Point2D setY(float y){
		this.y=y;
		return this;
	}

	public Point2D addX(float x) {
		this.x+=x;
		return this;
	}
	
	public Point2D addY(float y) {
		this.y+=y;
		return this;
	}
	
	public Point2D add(float v) {
		this.x+=v;
		this.y+=v;
		return this;
	}
	
	public Point2D add(Point2D v) {
		this.x+=v.getX();
		this.y+=v.getY();
		return this;
	}
	
	public Point2D multiply(Point2D v) {
		this.x*=v.getX();
		this.y*=v.getY();
		return this;
	}
	public Point2D multiply(float v) {
		this.x*=v;
		this.y*=v;
		return this;
	}
	public Point2D adaptar(){
		Point2D p=this.getAbsolutePosition();

		float x=(-p.getX()*2)+1;
		float y=(-p.getY()*2)+1;
		return new Point2D(x,y);
  	  
  	  
  	  
    }
	@Override
	public String toString(){
		return x+","+y;
	}
	
	@Override
	public Point2D clone(){
		return new Point2D(x, y);
	}
	
	public Point2D getAbsolutePosition(){
		return new Point2D(((x - Config.startX)/Config.scale.getX())
				,(y - Config.startY)/ Config.scale.getY());
	}

	public Point2D setAbsolutePosition(){
		x = Math.round(x * Config.scale.getX()) + Config.startX;
		y = Math.round(y * Config.scale.getX()) + Config.startY;
		return this;
	}
	public Point2D divide(Point2D v) {
		this.x/=v.getX();
		this.y/=v.getY();
		return this;
	}
	public Point2D divide(float v) {
		this.x/=v;
		this.y/=v;
		return this;
	}
	public double value() {
		return Math.sqrt(x*x+y*y);
	}
}
