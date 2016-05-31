package es.pcv.game.gui;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import es.pcv.core.render.figure.Drawable;

@SuppressWarnings("serial")
public class GuiDefault extends Gui {
	List<Drawable> figures = new LinkedList<Drawable>();

	public GuiDefault() {
	}

	
	public synchronized void add(Drawable d) {
		figures.add(d);
	}

	
	public synchronized void remove(Drawable d) {
		figures.remove(d);
	}

	@Override
	public synchronized void paint(Graphics g) {
		super.paint(g);
		List<Drawable> toRemove = new LinkedList<Drawable>();
		for (Drawable drawable : figures) {
			if (drawable.isDead()) {
				toRemove.add(drawable);
			} else {
				drawable.draw(g);
			}
		}

		for (Drawable d : toRemove) {
			remove(d);
		}
	}
	
	public synchronized void clear(){
		figures.clear();
	}


}