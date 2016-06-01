package es.pcv.core.render.figure;

import java.awt.Graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import es.pcv.core.updater.elements.canDead;

public interface Drawable extends canDead{
	void draw(Graphics g);
	void draw3d(GL2 gl, GLU glu, GLUquadric quadric);
}
