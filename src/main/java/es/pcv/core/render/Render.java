package es.pcv.core.render;

import es.pcv.core.render.figure.Drawable;

public interface Render {
	public abstract void add(Drawable d);

	public abstract void remove(Drawable d);
	public abstract void clear();

	void start();

	void end();


}
