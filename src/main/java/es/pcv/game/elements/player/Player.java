package es.pcv.game.elements.player;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

import javax.swing.JFrame;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import es.pcv.core.render.Point2D;
import es.pcv.core.render.auxiliar.Helper3D;
import es.pcv.core.updater.elements.Walker;
import es.pcv.game.Game;
import es.pcv.game.configuration.Config;
import es.pcv.game.elements.weapons.Weapon;

public class Player extends Walker {
	protected int TEXTURE = 4;

	// for shooting
	private Semaphore fireS = new Semaphore(1);
	private Point last_mouse_position;

	private boolean click = false;

	public final Set<Integer> pressed = new HashSet<Integer>();
	public Point shoot;

	protected long CD_HIT = 1000;

	// Weapons

	// private List weapons;
	private Weapon[] weapons = Weapon.ALL_WEAPONS;// new Weapon[]{new
													// LaserGun(this),null};

	private int currentWeapon = 0;
	private static final Point2D MAX_VELOCITY = new Point2D(0.0005f, 0.0005f).multiply(Config.scale);

	public Player(Point2D position,JFrame jp) {
		super(position,new Point2D(0, 0),new Point2D(0.05f, 0.05f),200, 10);

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
	
	public Player(Point2D position) {
		super(position, new Point2D(0, 0), new Point2D(0.05f, 0.05f), 200, 10);
		changeWeapon(currentWeapon);
		dead = false;
	}
	
	public synchronized void addKey(Integer k){
		pressed.add(k);
	}
	
	public synchronized void update(long ms) {
		velocity.multiply(0);
		if (pressed.size() > 0) {
			if (pressed.contains(KeyEvent.VK_W) && !obstacle_collision_uy) {
				movYImg = 1;

				velocity.addY(-MAX_VELOCITY.getY());
			}
			if (pressed.contains(KeyEvent.VK_A) && !obstacle_collision_dx) {
				movXImg = -1;
				velocity.addX(-MAX_VELOCITY.getX());
			}
			if (pressed.contains(KeyEvent.VK_S) && !obstacle_collision_dy) {
				movYImg = -1;
				velocity.addY(MAX_VELOCITY.getY());
			}
			if (pressed.contains(KeyEvent.VK_D) && !obstacle_collision_ux) {
				movXImg = 1;
				velocity.addX(MAX_VELOCITY.getX());
			}

			for (int i = KeyEvent.VK_1; i <= KeyEvent.VK_9; i++) {
				if (pressed.contains(i)) {
					changeWeapon(i - KeyEvent.VK_1);
				}
			}

			obstacle_collision_uy = false;
			obstacle_collision_dy = false;
			obstacle_collision_ux = false;
			obstacle_collision_dx = false;

		}
		checkWeapon();
		super.update(ms);

	}

	public void attack() {
		Point last = shoot;

		if (last != null) {
			last_mouse_position = last;
		}

		float fx = (float) (last_mouse_position.getX() - getCenterPos().getX())- 200;
		float fy = (float) (last_mouse_position.getY() - getCenterPos().getY()) +50;

		float aux = (float) Math.sqrt(fx * fx + fy * fy);
		fx = (fx / aux);
		fy = (fy / aux);

		weapons[currentWeapon].attack(this, (getCenterPos()), new Point2D(fx, fy));
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
		return dead;
	}

	public boolean kill() {
		dead = true;
		Game.getGame().end();
		return false;
	}

	public void checkWeapon() {
		if (isFire() && weapons[currentWeapon] != null && weapons[currentWeapon].canAttack()) {
			attack();
		}
	}

	public void changeWeapon(int n) {
		if (weapons.length > n && weapons[n] != null) {
			weapons[currentWeapon].unequip();
			currentWeapon = n;
			weapons[currentWeapon].equip(this);
		}
	}

	public Weapon getWeapon(Weapon gun) {
		for (int i = 0; i < weapons.length; i++) {
			if (weapons[i] == null) {
				weapons[i] = gun;
				if (currentWeapon == i) {
					weapons[i].equip(this);
				}
				return null;
			}
		}
		Weapon aux = weapons[currentWeapon];
		weapons[currentWeapon] = gun;
		aux.unequip();
		weapons[currentWeapon].equip(this);
		return aux;
	}

	public int getCurrentWeapon() {
		return currentWeapon;
	}

	public int getWeaponCapacity() {
		return weapons.length;
	}

	public Weapon getWeapon(int i) {
		return weapons[i];
	}

	public Weapon getWeapon() {
		return weapons[currentWeapon];
	}

	public void addAmmo(int ammo) {
		if (weapons[currentWeapon] != null) {
			weapons[currentWeapon].addDurability(ammo);
		}
	}

	Color c = new Color(0, 255, 0);

	public void draw3d(GL2 gl, GLU glu, GLUquadric quadric) {
		Helper3D.drawCilinder(gl,glu,quadric, getCenterPos(), 0.0f, 0.05f, 0, .1f, c,TEXTURE);
	}

	public synchronized void removeKey(int keyCode) {
		pressed.remove(keyCode);
	}

}
