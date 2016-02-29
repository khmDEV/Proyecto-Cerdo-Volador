package es.pcv.game.elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;

import es.pcv.core.updater.elements.Collisionable;
import es.pcv.core.updater.elements.Element;
import es.pcv.game.configuration.Config;

public class Player implements Element {
	float x = 0.5f;
	float y = 0;
	float vx = 0.005f;
	float vy = -0.005f;

	

	public Player(JFrame jp) {
		KeyListener kl = new KeyListener() {
			private final Set<Integer> pressed = new HashSet<Integer>();

		    public synchronized void keyPressed(KeyEvent e) {
		        pressed.add(e.getKeyCode());
		        if (pressed.size() > 0) {
		        	if (pressed.contains(KeyEvent.VK_W)) {
						y+=vy;
					}
					if (pressed.contains(KeyEvent.VK_A)) {
						x-=vx;
					}
					if (pressed.contains( KeyEvent.VK_S)) {
						y-=vy;
					}
					if (pressed.contains(KeyEvent.VK_D)) {
						x+=vx;
					}
					if (x>1) {
						x=1;
					}
					if (y>1) {
						y=1;
					}
					if (x<0) {
						x=0;
					}
					if (y<0) {
						y=0;
					}
		        }
		    }

		    
		    public synchronized void keyReleased(KeyEvent e) {
		        pressed.remove(e.getKeyCode());
		    }

		    public void keyTyped(KeyEvent e) {/* Not used */ }
			/*public void keyTyped(KeyEvent e) {
				
			}

			public void keyReleased(KeyEvent e) {

			}

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_W) {
					y+=vy;
				}
				if (e.getKeyCode() == KeyEvent.VK_A) {
					x-=vx;
				}
				if (e.getKeyCode() == KeyEvent.VK_S) {
					y-=vy;
				}
				if (e.getKeyCode() == KeyEvent.VK_D) {
					x+=vx;
				}
				if (x>1) {
					x=1;
				}
				if (y>1) {
					y=1;
				}
				if (x<0) {
					x=0;
				}
				if (y<0) {
					y=0;
				}
			}*/
		};
		jp.addKeyListener(kl);
	}

	public void update() {

	}

	public void draw(Graphics g) {
		g.setColor(new Color(255, 255, 0));
		int xx = Math.round(x * Config.WEITH);
		int yy = Math.round(y * Config.HEIGTH);
		g.draw3DRect(xx, yy, 10, 10, false);
	}


	public boolean isDead() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isCollision(Collisionable c) {
		// TODO Auto-generated method stub
		return false;
	}

	public void collision(Collisionable c) {
		// TODO Auto-generated method stub
		
	}
}
