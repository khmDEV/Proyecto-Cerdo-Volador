package es.pcv.game.elements.enemies;

import java.awt.Color;
import java.awt.Graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import es.pcv.core.render.Point2D;
import es.pcv.core.render.auxiliar.Helper3D;
import es.pcv.core.updater.elements.Collisionable;
import es.pcv.core.updater.elements.Walker;
import es.pcv.game.elements.player.Player;

public class EnemyTest extends Walker {

	Player pl;
	protected Color c = new Color(1, 0, 0);
	protected int TEXTURE = 5;

	public EnemyTest(Point2D p) {		
		super(p,new Point2D(0, 0), new Point2D(0.05f, 0.05f), 1, 10);
	}
	

	public void draw(Graphics g) {
		g.setColor(c);
		g.fillRect(getX(), getY(), getSizeX() , getSizeY());
	}

	public void draw3d(GL2 gl, GLU glu, GLUquadric quadric) {
		Helper3D.drawCilinder(gl, glu, quadric, getPos(), 0.06f, 0.06f, 0, .11f, null,TEXTURE);
	}
	
	public void collision(Collisionable col) {
		super.collision(col);
		if (col instanceof Player) {
			Player pl = (Player) col;
			pl.doDamage(getDamage());
		}
	}
	

}
