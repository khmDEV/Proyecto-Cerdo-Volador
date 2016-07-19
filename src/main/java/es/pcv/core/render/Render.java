/**package es.pcv.core.render;

import es.pcv.core.render.figure.Drawable;

public interface Render {
	public abstract void add(Drawable d);

	public abstract void remove(Drawable d);
	public abstract void clear();

	void start();

	void end();


}
*/

package es.pcv.core.render;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.concurrent.Semaphore;

import javax.swing.JPanel;

import es.pcv.core.render.figure.Drawable;
import es.pcv.game.configuration.Config;

@SuppressWarnings("serial")
public abstract class Render extends JPanel {
	
	private boolean seguir = true;
	private Semaphore s = new Semaphore(1);
	
	public Render(){/**
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
		});*/
	}

	public abstract void add(Drawable d);

	public abstract void remove(Drawable d);


	public void render() {
		long start = System.currentTimeMillis();
		while (seguir) {
			s.release();
			if (System.currentTimeMillis() - start > Config.RENDER_TICK) {
				repaint();
				start = System.currentTimeMillis();
			}
			try {
				s.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public abstract void clear();
	
	public void end(){
		try {
			s.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		seguir=false;
		s.release();
	}
	
	
	public void addMouseListener(MouseListener ml){
		super.addMouseListener(ml);
	}
	
	public void addKeyListener(KeyListener kl){
		super.addKeyListener(kl);
		
	}
	
	public abstract boolean is3D();
	
	

}
