package es.pcv.game.elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
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
import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Collisionable;
import es.pcv.core.updater.elements.Element;
import es.pcv.core.updater.elements.Obstacle;
import es.pcv.game.Game;
import es.pcv.game.configuration.Config;

public class Player implements Obstacle {
	Semaphore fireS = new Semaphore(1);
	Point2D position = new Point2D(0.5f, 0);
	Point2D velocity = new Point2D(0.005f, -0.005f);
	Point2D size = new Point2D(0.05f, 0.05f);
	float vbull = 0.05f;
	Color c = new Color(255, 255, 0);
	Point last_mouse_position;
	ObjectIcon icon = new ObjectIcon("bad1.png",4,3);
	int movYImg = 0;
	int movXImg = 0;
	int mov=0;
	int imgFija=1;
	boolean fire = false;
	JFrame jp;
	final long RELOAD_CD = 50;
	long reload = 0;

	public Player(JFrame jp) {
		this.jp = jp;
		ply = new Polygon(
				new int[] { Math.round((position.getX() + size.getX()) * Config.size.getX()),
						Math.round((position.getX() - size.getX()) * Config.size.getX()),
						Math.round((position.getX() - size.getX()) * Config.size.getX()),
						Math.round((position.getX() + size.getX()) * Config.size.getX()) },
				new int[] { Math.round((position.getY() - size.getY()) * Config.size.getY()),
						Math.round((position.getY() - size.getY()) * Config.size.getY()),
						Math.round((position.getY() + size.getY()) * Config.size.getY()),
						Math.round((position.getY() + size.getY()) * Config.size.getY()) },
				4);

		KeyListener kl = new KeyListener() {
			private final Set<Integer> pressed = new HashSet<Integer>();
			public void keyTyped(KeyEvent e) {}

			public synchronized void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_W){
					movYImg=0;
					imgFija=10;
				}
				if(e.getKeyCode()==KeyEvent.VK_A){
					movXImg=0;
					imgFija=4;
				}
				if(e.getKeyCode()==KeyEvent.VK_S){
					movYImg=0;
					imgFija=1;
				}
				if(e.getKeyCode()==KeyEvent.VK_D){
					movXImg=0;
					imgFija=7;
				}
				pressed.remove(e.getKeyCode());
			}

			

			public synchronized void keyPressed(KeyEvent e) {
				pressed.add(e.getKeyCode());
				if (pressed.size() > 0) {
					if (pressed.contains(KeyEvent.VK_W)) {
						movYImg=1;
						position.addY(velocity.getY());
					}
					if (pressed.contains(KeyEvent.VK_A)) {
						movXImg=-1;
						position.addX(-velocity.getX());
					}
					if (pressed.contains(KeyEvent.VK_S)) {
						movYImg=-1;
						position.addY(-velocity.getY());
					}
					if (pressed.contains(KeyEvent.VK_D)) {
						movXImg=1;
						position.addX(velocity.getX());
					}
					if (position.getX() > 1) {
						position.setX(1);
					}
					if (position.getY() > 1) {
						position.setY(1);
					}
					if (position.getX() < 0) {
						position.setX(0);
					}
					if (position.getY() < 0) {
						position.setY(0);
					}
				}
			}
		};
		// 1 boton inquierdo, 2 central y 3 derecho
		MouseListener ml = new MouseListener() {

			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == 1) {
					finishFire();
				}
			}

			public void mousePressed(MouseEvent e) {
				if (e.getButton() == 1) {
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
			Point last = jp.getMousePosition();
			if (last != null) {
				last_mouse_position = last;
			}

			// Calculate fire direction
			float fx = (float) last_mouse_position.getX() / Config.size.getX() - position.getX();
			float fy = (float) last_mouse_position.getY() / Config.size.getY() - position.getY();
			float aux = Math.abs(fy) + Math.abs(fx);
			fx = (fx / aux);
			fy = (fy / aux);

			// Calculate offset
			float ox = size.getX() * fx;
			float oy = size.getY() * fy;
			System.out.println(fx + "_" + fy);

			Bull b = new Bull(position.getX() + ox, position.getY() + oy, vbull * fx, vbull * fy,this);
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
		boolean r = fire;
		fireS.release();
		return r;
	}

	Polygon ply;

	public void draw(Graphics g) {
		ply = new Polygon(
				new int[] { Math.round((position.getX() + size.getX()) * Config.size.getX()),
						Math.round((position.getX() - size.getX()) * Config.size.getX()),
						Math.round((position.getX() - size.getX()) * Config.size.getX()),
						Math.round((position.getX() + size.getX()) * Config.size.getX()) },
				new int[] { Math.round((position.getY() - size.getY()) * Config.size.getY()),
						Math.round((position.getY() - size.getY()) * Config.size.getY()),
						Math.round((position.getY() + size.getY()) * Config.size.getY()),
						Math.round((position.getY() + size.getY()) * Config.size.getY()) },
				4);
		g.setColor(c);
		int img=0;
		if(movXImg==0&&movYImg==0){
			img=imgFija;			
		}
		else if(movXImg==-1){
			img=3+mov;
			mov++;
			if(mov==3){
				mov=0;
			}
		}
		else if(movXImg==1){
			img=6+mov;
			mov++;
			if(mov==3){
				mov=0;
			}
		}
		else if(movYImg==-1){
			img=0+mov;
			mov++;
			if(mov==3){
				mov=0;
			}
		}
		else if(movYImg==1){
			img=9+mov;
			mov++;
			if(mov==3){
				mov=0;
			}
		}
		g.drawImage(icon.getImage(img), Math.round((position.getX() - size.getX()) * Config.size.getX()),
				Math.round((position.getY() - size.getY()) * Config.size.getY()),Math.round(size.getX()*Config.size.getX())*2,
				Math.round(size.getY()*Config.size.getY())*2, null);
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
		if(col instanceof Bull){
			Bull b = (Bull) col;
			if(!b.playerShoot()){
				//perder vida
			}
		}
		c = new Color((int) Math.round(Math.random() * 255), (int) Math.round(Math.random() * 255),
				(int) Math.round(Math.random() * 255));
	}

	public Rectangle2D getCollisionBox() {
		return ply.getBounds2D();
	}
}
