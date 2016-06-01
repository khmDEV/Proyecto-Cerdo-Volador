package es.pcv.game.elements.items;

import java.awt.Color;
import java.awt.Graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import es.pcv.core.render.Point2D;
import es.pcv.core.render.auxiliar.Helper3D;
import es.pcv.core.render.auxiliar.PolygonHelper;
import es.pcv.core.updater.elements.Collisionable;
import es.pcv.game.elements.player.Player;

public class ItemHeal extends Item {
	int heal;
	Point2D position;

	static Point2D size = new Point2D(0.005, 0.005);
	static Point2D max_size = new Point2D(0.025, 0.025);

	public ItemHeal(Point2D p, int heal) {
		super(PolygonHelper.createRectangle(p, new Point2D(size.getX()*heal>max_size.getX()?max_size.getX():size.getX()*heal, 
				size.getY()*heal>max_size.getY()?max_size.getY():size.getY()*heal)).getBounds2D());
		this.heal = heal;
		position = p;
	}

	@Override
	public void takeIt(Player pl) {
		if (!kill()) {
			pl.addLive(heal);
		}
	}

	public void draw(Graphics g) {
		g.drawOval(getX(), getY(), getSizeX(), getSizeY());
	}
	Color c = new Color(1f, 0, 0);

	@Override
	public void draw3d(GL2 gl, GLU glu, GLUquadric quadric) {
		float MAX=0.02f;
		float M=0.003f;
		float vm=M*heal>MAX?MAX:M*heal;
		Helper3D.drawSphere(gl, glu, quadric, getCenterPos(), 0f, vm, c, -1);
	}

	public void collisionObstacle(Collisionable c) {

	}
	
	public ItemHeal cloneItem(){
		return new ItemHeal(position.clone(), heal);
	}

}
