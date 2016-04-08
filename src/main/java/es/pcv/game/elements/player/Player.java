package es.pcv.game.elements.player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

import javax.swing.JFrame;

import es.pcv.core.render.ObjectIcon;
import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.LiveEntity;
import es.pcv.core.updater.elements.Walker;
import es.pcv.game.Game;
import es.pcv.game.configuration.Config;
import es.pcv.game.elements.weapons.DefaultGun;
import es.pcv.game.elements.weapons.Gun;
import es.pcv.game.elements.weapons.Shotgun;
import es.pcv.game.elements.weapons.Sword;

public class Player extends Walker {
	
	private JFrame jp;
	
	//for shooting
	private Semaphore fireS = new Semaphore(1);
	private Point last_mouse_position;
	private boolean fire = false;


	private final Set<Integer> pressed = new HashSet<Integer>();
	
	
	protected long CD_HIT = 1000;
	
	//Weapons
	private Gun gun=new DefaultGun();
	//private Sword sword=new Sword(this,position,new Point2D(0,0), new Point2D(0.002f,-0.004), 1, 70, 5);

	public Player(Point2D position,JFrame jp) {
		super(position,new Point2D(0.005f, -0.005f),new Point2D(0.05f, 0.05f),10, 10);
		this.jp = jp;
		

		KeyListener kl = new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public synchronized void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_W) {
					movYImg = 0;
					imgFija = 10;
				}
				if (e.getKeyCode() == KeyEvent.VK_A) {
					movXImg = 0;
					imgFija = 4;
				}
				if (e.getKeyCode() == KeyEvent.VK_S) {
					movYImg = 0;
					imgFija = 1;
				}
				if (e.getKeyCode() == KeyEvent.VK_D) {
					movXImg = 0;
					imgFija = 7;
				}
				pressed.remove(e.getKeyCode());
			}

			public synchronized void keyPressed(KeyEvent e) {
				pressed.add(e.getKeyCode());

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

		if (pressed.size() > 0) {
			if (pressed.contains(KeyEvent.VK_W)
					&& !obstacle_collision_uy) {
				movYImg = 1;
				addY((int)velocity.getY());
			}
			if (pressed.contains(KeyEvent.VK_A)
					&& !obstacle_collision_ux) {
				movXImg = -1;
				addX((int)-velocity.getX());
			}
			if (pressed.contains(KeyEvent.VK_S)
					&& !obstacle_collision_dy) {
				movYImg = -1;
				addY((int)-velocity.getY());
			}
			if (pressed.contains(KeyEvent.VK_D)
					&& !obstacle_collision_dx) {
				movXImg = 1;
				addX((int)velocity.getX());
			}
			
			
			obstacle_collision_uy=false;
			obstacle_collision_dy=false;
			obstacle_collision_ux=false;
			obstacle_collision_dx=false;
			
		}

		if (isFire() && gun.canShoot()) {
			shoot();
		}
		
	}

	public void shoot(){
		Point last = jp.getMousePosition();
		
		if (last != null) {
			last_mouse_position = last;
		}
		
		float fx = (float) (last_mouse_position.getX() - getX());
		float fy = (float) (last_mouse_position.getY() - getY());
		
		float aux = Math.abs(fy) + Math.abs(fx);
		fx = (fx / aux);
		fy = (fy / aux);

		// Calculate offset
		float ox = (float) (getSizeX() * fx);
		float oy = (float) (getSizeY() * fy);

		gun.shoot(this, new Point2D(ox, oy).add(getPos()), new Point2D(fx, fy));
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

	public boolean isVulnerable() {
		return (System.currentTimeMillis() - lastHit) > CD_HIT;
	}


	public boolean isDead() {
		return false;
	}

	public boolean kill() {
		Game.getGame().end();
		return false;
	}
	

}
