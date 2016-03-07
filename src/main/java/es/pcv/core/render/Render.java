package es.pcv.core.render;

import javax.swing.JPanel;

import es.pcv.core.render.figure.Drawable;
import es.pcv.game.configuration.Config;

@SuppressWarnings("serial")
public abstract class Render extends JPanel {

	public abstract void add(Drawable d);

	public abstract void remove(Drawable d);


	public void render() {
		long start = System.currentTimeMillis();
		while (true) {
			if (System.currentTimeMillis() - start > Config.RENDER_TICK) {
				repaint();
				start = System.currentTimeMillis();
			}
		}
	}

}
