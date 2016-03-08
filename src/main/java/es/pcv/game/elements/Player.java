package es.pcv.game.elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

import javax.swing.JFrame;

import es.pcv.core.render.ObjectIcon;
import es.pcv.core.updater.elements.Collisionable;
import es.pcv.core.updater.elements.Element;
import es.pcv.game.Game;
import es.pcv.game.configuration.Config;

public class Player implements Element {
	Semaphore fireS = new Semaphore(1);
	float x = 0.5f;
	float y = 0;
	float vx = 0.005f;
	float vy = -0.005f;
	float w = 0.05f;
	float h = 0.05f;
	float vbull = 0.05f;
	Color c = new Color(255, 255, 0);
	boolean fire = false;
	JFrame jp;
	final long RELOAD_CD = 50;
	long reload = 0;
	ObjectIcon icon=new ObjectIcon("bad1.png");
	int movYImg=0;
	int movXImg=0;
	public Player(JFrame jp) {
		this.jp = jp;
		ply = new Polygon(
				new int[] { Math.round((x + w) * Config.WEITH), Math.round((x - w) * Config.WEITH),
						Math.round((x - w) * Config.WEITH), Math.round((x + w) * Config.WEITH) },
				new int[] { Math.round((y - h) * Config.WEITH), Math.round((y - h) * Config.WEITH),
						Math.round((y + h) * Config.WEITH), Math.round((y + h) * Config.WEITH) },
				4);

		KeyListener kl = new KeyListener() {

			public void keyTyped(KeyEvent e) {

			}

			public synchronized void keyReleased(KeyEvent e) {
				pressed.remove(e.getKeyCode());
			}

			private final Set<Integer> pressed = new HashSet<Integer>();

			public synchronized void keyPressed(KeyEvent e) {
				pressed.add(e.getKeyCode());
				if (pressed.size() > 0) {
					if (pressed.contains(KeyEvent.VK_W)) {
						y += vy;
					}
					if (pressed.contains(KeyEvent.VK_A)) {
						x -= vx;
					}
					if (pressed.contains(KeyEvent.VK_S)) {
						y -= vy;
					}
					if (pressed.contains(KeyEvent.VK_D)) {
						x += vx;
					}
					if (x > 1) {
						x = 1;
					}
					if (y > 1) {
						y = 1;
					}
					if (x < 0) {
						x = 0;
					}
					if (y < 0) {
						y = 0;
					}
				}
			}
		};
		//1 boton inquierdo, 2 central y 3 derecho
		MouseListener ml = new MouseListener() {

			public void mouseReleased(MouseEvent e) {
				if(e.getButton()==1){
					finishFire();
				}
			}

			public void mousePressed(MouseEvent e) {
				if(e.getButton()==1){
					startFire();
				}
				
			}

			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		};
		jp.addMouseListener(ml);
		jp.addKeyListener(kl);
	}

	public synchronized void update() {

		if (isFire() && (System.currentTimeMillis() - reload) > RELOAD_CD) {
			float fx = (float) jp.getMousePosition().getX() / Config.WEITH - x;
			float fy = (float) jp.getMousePosition().getY() / Config.HEIGTH - y;
			float aux = Math.abs(fy) + Math.abs(fx);
			fx = vbull * (fx / aux);
			fy = vbull * (fy / aux);
			Bull b = new Bull(fx * w + x, fy * h + y, fx, fy);
			Game.getGame().render.add(b);
			Game.getGame().updater.add(b);
			reload = System.currentTimeMillis();
		}

	}

	public void startFire() {
		try {
			fireS.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fire = true;
		fireS.release();
	}

	public void finishFire() {
		try {
			fireS.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fire = false;
		fireS.release();
	}

	public boolean isFire() {
		try {
			fireS.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean r=fire;
		fireS.release();
		return r;
	}

	Polygon ply;

	public void draw(Graphics g) {
	
		ply = new Polygon(
				new int[] { Math.round((x + w) * Config.WEITH), Math.round((x - w) * Config.WEITH),
						Math.round((x - w) * Config.WEITH), Math.round((x + w) * Config.WEITH) },
				new int[] { Math.round((y - h) * Config.WEITH), Math.round((y - h) * Config.WEITH),
						Math.round((y + h) * Config.WEITH), Math.round((y + h) * Config.WEITH) },
				4);
		if(vx!=0){
			
		}
		else if(vy!=0){
			
		}
		else{
			
		}
		g.drawImage(icon.getImage(), Math.round((x-w)*Config.WEITH),  Math.round((y-h)*Config.HEIGTH),null);
	}

	public boolean isDead() {
		return false;
	}

	public boolean kill() {
		return false;
	}

	public boolean isCollision(Collisionable c) {
		return c.getCollisionBox().intersects(ply.getBounds2D());
	}

	public void collision(Collisionable col) {
		c = new Color((int) Math.round(Math.random() * 255), (int) Math.round(Math.random() * 255),
				(int) Math.round(Math.random() * 255));
	}

	public Rectangle2D getCollisionBox() {
		return ply.getBounds2D();
	}
}
