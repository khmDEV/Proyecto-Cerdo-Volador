package es.pcv.game.gui;

import javax.swing.JPanel;

import es.pcv.core.render.figure.Drawable;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
@SuppressWarnings("serial")
public abstract class Gui extends JPanel {
	
	public Gui(){
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
		repaint();	
	}
	
	public abstract void clear();
	

}