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
import java.util.List;
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
import es.pcv.game.elements.weapons.Weapon;

public class Player extends Walker {
	
	private JFrame jp;
	
	//for shooting
	private Semaphore fireS = new Semaphore(1);
	private Point last_mouse_position;
	
	
	private boolean click = false;

	private final Set<Integer> pressed = new HashSet<Integer>();
	
	
	protected long CD_HIT = 1000;
	
	//Weapons
	
	//private List weapons;
	private Weapon[] weapons=new Weapon[3];
	
	
	private int currentWeapon= 1;
	
	
	public Player(Point2D position,JFrame jp) {
		super(position,new Point2D(0.005f, -0.005f),new Point2D(0.05f, 0.05f),10, 10);
		this.jp = jp;

		weapons[0]=new DefaultGun(this,getPos().clone(), 1, 0);
		weapons[1]=new Sword(this,getPos().clone(), 1, 70, 2);
		weapons[2]=new Shotgun(this,getPos().clone(), 1, 0);
		
		
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
			if (pressed.contains(KeyEvent.VK_1) |
				pressed.contains(KeyEvent.VK_2) |
				pressed.contains(KeyEvent.VK_3)){
				changeWeapon(0);
			}
			for(int i=KeyEvent.VK_1;i<=KeyEvent.VK_3;i++){
				if (pressed.contains(i)){
					System.out.println(i);
					changeWeapon(i-KeyEvent.VK_1);
				}
			}
			
			
			
			obstacle_collision_uy=false;
			obstacle_collision_dy=false;
			obstacle_collision_ux=false;
			obstacle_collision_dx=false;
			
		}

		checkWeapon();
		
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

		((Gun) weapons[currentWeapon]).shoot(this, new Point2D(ox, oy).add(getPos()), new Point2D(fx, fy));
	}
	
	
	public void startFire() {
		try {
			fireS.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		click = true;
		fireS.release();
	}

	public void finishFire() {
		try {
			fireS.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		click = false;
		fireS.release();
	}

	public boolean isFire() {
		try {
			fireS.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean r = click;
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
	
	public void checkWeapon(){
		if(currentWeapon == 0 | currentWeapon == 2){
			if (isFire() && ((Gun) weapons[currentWeapon]).canShoot()) {
				shoot();
			}
		}else if(currentWeapon == 1){
			
		}
	}
	public void changeWeapon(int n){
		if(weapons[n]!=null){
			if(currentWeapon == 0 |currentWeapon == 2){
				Game.getGame().render.remove(weapons[currentWeapon]);
			}else{
				Game.getGame().render.remove(weapons[currentWeapon]);
				Game.getGame().updater.remove(weapons[currentWeapon]);
			}
			currentWeapon=n;
			
			if(currentWeapon == 0 |currentWeapon == 2){
				Game.getGame().render.add(weapons[currentWeapon]);
			}else{
				Game.getGame().render.add(weapons[currentWeapon]);
				Game.getGame().updater.add(weapons[currentWeapon]);
			}
			//addElement();
		}
	}
	

}
