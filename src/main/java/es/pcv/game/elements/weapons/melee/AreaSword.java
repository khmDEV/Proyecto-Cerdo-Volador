package es.pcv.game.elements.weapons.melee;

import java.awt.Graphics;
import java.awt.Polygon;

import es.pcv.core.render.Point2D;
import es.pcv.core.render.auxiliar.PolygonHelper;
import es.pcv.core.updater.elements.Walker;

public class AreaSword extends Melee {
	private long time;
	private long init;
	
	public AreaSword(Walker w,int dur,int damage,long time) {
		super(w,new Point2D(0.01f,0.05), dur, damage);
		init=System.currentTimeMillis();
		this.time=time;
	}
	
	public void update() {
		double perc=(double)(System.currentTimeMillis()-init)/time;
		if (perc>=1) {
			kill();
		}else{
			super.update();
			pl = PolygonHelper.createRectangle(getPos().getAbsolutePosition(), getSize());
			pl=PolygonHelper.rotatePolygon(pl, whoAttack.getCenterPos(), -perc*(Math.PI/2));
			rect=pl.getBounds2D();
			
		}
		

	}
	Point2D size=new Point2D(0.01f,0.05);
	Polygon pl =PolygonHelper.createRectangle(getPos().getAbsolutePosition(), getSize());
	public Point2D getSize(){
		return size;
	}
	protected void moveMelee() {
		float aux = size.getX();
		size.setX(size.getY());
		size.setY(aux);
		vertical = !vertical;
	}
	
	public void draw(Graphics g) {
		g.drawPolygon(pl);
	}
	
	
	

}
