package es.pcv.core.render;

import java.awt.Frame;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import es.pcv.core.render.figure.Drawable;

public class RenderDefault extends Render{
	List<Drawable> figures=new LinkedList<Drawable>();
	
	public RenderDefault() {
	}

	@Override
	public void add(Drawable d) {
		figures.add(d);
	}
	
	@Override
	public void remove(Drawable d) {
		figures.remove(d);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		List<Drawable> toRemove=new LinkedList<Drawable>();
		for (Drawable drawable : figures) {
			if (drawable.isDead()) {
				toRemove.add(drawable);
			}else{
				drawable.draw(g);
			}
		}
		
		for (Drawable d : toRemove) {
			figures.remove(d);
		}
	}


}
