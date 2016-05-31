package es.pcv.core.render;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JPanel;

import es.pcv.core.render.figure.Drawable;
import es.pcv.game.configuration.Config;

@SuppressWarnings("serial")
public abstract class CopyOfRender extends JPanel {
	
	public CopyOfRender(){
		addComponentListener( new ComponentListener() {
			
			public void componentShown(ComponentEvent arg0) {
			}
			
			public void componentResized(ComponentEvent e) {
				//Config.updateSize(e.getComponent().getWidth(), e.getComponent().getHeight());
			}
			
			public void componentMoved(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			public void componentHidden(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

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
	
	public abstract void clear();
	

}
