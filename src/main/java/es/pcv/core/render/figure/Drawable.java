package es.pcv.core.render.figure;

import java.awt.Graphics;

import es.pcv.core.updater.elements.canDead;

public interface Drawable extends canDead{
	void draw(Graphics g);
}
