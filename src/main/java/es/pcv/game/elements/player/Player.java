package es.pcv.game.elements.player;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

import javax.swing.JFrame;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Walker;
import es.pcv.game.Game;
import es.pcv.game.elements.weapons.GunDefault;
import es.pcv.game.elements.weapons.Gun;
import es.pcv.game.elements.weapons.Scabbard;
import es.pcv.game.elements.weapons.Shotgun;
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

		weapons[0]=new GunDefault(this);
		weapons[1]=new Scabbard(this);
		weapons[2]=new Shotgun(this);
		changeWeapon(currentWeapon);
		
		
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
			
			for(int i=KeyEvent.VK_1;i<=KeyEvent.VK_3;i++){
				if (pressed.contains(i)){
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

		((Gun) weapons[currentWeapon]).attack(this, new Point2D(ox, oy).add(getPos()), new Point2D(fx, fy));
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
		if (isFire() && weapons[currentWeapon]!=null &&weapons[currentWeapon].canAttack()) {
			shoot();
		}
	}
	public void changeWeapon(int n){
		if(weapons[n]!=null){
			weapons[currentWeapon].unequip();
			currentWeapon=n;
			weapons[currentWeapon].equip(this);
		}
	}

	public Weapon getWeapon(Weapon gun) {
		for (int i = 0; i < weapons.length; i++) {
			if (weapons[i]==null) {
				weapons[i]=gun;
				if (currentWeapon==i) {
					weapons[i].equip(this);
				}
				return null;	
			}
		}
		Weapon aux=weapons[currentWeapon];
		weapons[currentWeapon]=gun;
		aux.unequip();
		weapons[currentWeapon].equip(this);
		return aux;
	}

	public int getCurrentWeapon(){
		return currentWeapon;
	}
	

}
