package es.pcv.game.elements.weapons.melee;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import es.pcv.core.render.Point2D;
import es.pcv.core.render.Render3D;
import es.pcv.core.render.auxiliar.Helper3D;
import es.pcv.core.render.auxiliar.PolygonHelper;
import es.pcv.core.updater.elements.Walker;

public class AreaSword extends Melee {
	private long time;
	private long init;
	Color c=new Color(1, 0, 0);

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
		}else{
			super.update(0);
		}
	}

	Point2D size = new Point2D(0.01f, 0.05);
	Polygon pl = PolygonHelper.createRectangle(getPos().getAbsolutePosition(), getSize());

	public Point2D getSize() {
		return size;
	}

	public void draw(Graphics g) {
		double perc = (double) init / time;
		pl = PolygonHelper.createRectangle(getPos().getAbsolutePosition(), getSize());
		pl = PolygonHelper.rotatePolygon(pl, whoAttack.getCenterPos(), -perc * (Math.PI / 2));
		rect = pl.getBounds2D();
		g.drawPolygon(pl);
	}

	public void draw3d(GL2 gl, GLU glu, GLUquadric quadric) {
		double perc = (double) init / time;
		double an=-perc * 90;
		Helper3D.drawRotatedRectangle(gl, getCenterPos(), super.getSize(), 0.01f, 0.02f, c, -1,whoAttack.getCenterPos(),(float) an);	

	}

}
