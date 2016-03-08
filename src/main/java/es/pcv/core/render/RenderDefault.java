package es.pcv.core.render;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import es.pcv.core.render.figure.Drawable;

@SuppressWarnings("serial")
public class RenderDefault extends Render {
	List<Drawable> figures = new LinkedList<Drawable>();

	public RenderDefault() {
	}

	@Override
	public synchronized void add(Drawable d) {
		figures.add(d);
	}

	@Override
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
				/*ObjectIcon icon=new ObjectIcon("bad1.png");
				g.drawImage(icon.getImage(), 0, 0,this);*/
				drawable.draw(g);
			}
		}

		for (Drawable d : toRemove) {
			remove(d);
		}
	}

}
