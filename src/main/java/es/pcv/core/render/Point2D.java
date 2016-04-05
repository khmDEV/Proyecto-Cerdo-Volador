package es.pcv.core.render;

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
	
	@Override
	public String toString(){
		return x+","+y;
	}
	
	@Override
	public Point2D clone(){
		return new Point2D(x, y);
	}
	

}
