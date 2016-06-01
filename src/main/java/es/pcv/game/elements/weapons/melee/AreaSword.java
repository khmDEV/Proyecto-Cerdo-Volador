package es.pcv.game.elements.weapons.melee;

import java.awt.Graphics;
import java.awt.Polygon;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import es.pcv.core.render.Point2D;
import es.pcv.core.render.Render3D;
import es.pcv.core.render.auxiliar.PolygonHelper;
import es.pcv.core.updater.elements.Walker;

public class AreaSword extends Melee {
	private long time;
	private long init;

	public AreaSword(Walker w, int dur, int damage, long time) {
		super(w, new Point2D(0.01f, 0.05), dur, damage);
		init = 0;
		this.time = time;
	}

	public void update(long ms) {
		init += ms;
		double perc = (double) init / time;
		if (perc >= 1) {
			kill();
		} else {
			super.update(ms);
			pl = PolygonHelper.createRectangle(getPos().getAbsolutePosition(), getSize());
			pl = PolygonHelper.rotatePolygon(pl, whoAttack.getCenterPos(), -perc * (Math.PI / 2));
			rect = pl.getBounds2D();
		}
	}

	Point2D size = new Point2D(0.01f, 0.05);
	Polygon pl = PolygonHelper.createRectangle(getPos().getAbsolutePosition(), getSize());

	public Point2D getSize() {
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

	@Override
	public void draw3d(GL2 gl, GLU glu, GLUquadric quadric) {
		// TODO Auto-generated method stub
		
	}

}
