package es.pcv.core.render;

public class Point2D {
	
	private float x;
	private float y;
	
	public Point2D(float x,float y){
		this.x=x;
		this.y=y;
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
	
	public synchronized void setX(float x){
		this.x=x;
	}
	
	public synchronized void setY(float y){
		this.y=y;
	}

	public void addX(float x) {
		this.x+=x;
	}
	
	public void addY(float y) {
		this.y+=y;
	}

	public void add(Point2D velocity) {
		this.x+=velocity.getX();
		this.y+=velocity.getY();
	}

}
