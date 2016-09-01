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

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import es.pcv.core.render.ObjectIcon;
import es.pcv.core.render.Point2D;
import es.pcv.core.render.Render;
import es.pcv.core.render.Render3D;
import es.pcv.core.render.auxiliar.Helper3D;
import es.pcv.core.updater.elements.Walker;
import es.pcv.game.Game;
import es.pcv.game.configuration.Config;
import es.pcv.game.elements.weapons.GunDefault;
import es.pcv.game.elements.weapons.Weapon;

public class Player extends Walker{
	protected int TEXTURE = 4;

	private Render render;
	// for shooting
	private Semaphore fireS = new Semaphore(1);
	private Point last_mouse_position;
	private Point2D shoot_dir=new Point2D(0, 0);
	private boolean dim3d;
	private boolean click = false;
	private double z = 0;
	private static boolean jump = false;
	private boolean up = true;

	public static Set<Integer> pressed;
	public Point shoot;

	protected long CD_HIT = 1000;

	private static boolean listeners = true;
	private static MouseListener ml;
	private static KeyListener kl;
	//private KeyListener kl;
	
	
	// Weapons

	// private List weapons;
	private Weapon[] weapons = new Weapon[]{new GunDefault(this),null};

	private int currentWeapon = 0;
	private static final Point2D MAX_VELOCITY = new Point2D(0.0005f, 0.0005f).multiply(Config.scale);

	public Player(Point2D position,Render render,boolean dim3D) {		
		super(position,new Point2D(0, 0),new Point2D(0.05f, 0.05f),200, 10);
		icon= new ObjectIcon(Config.RESOURCES_PATH + "/icons/bad1.png", 4, 3);
		this.dim3d=dim3D;
		changeWeapon(currentWeapon);
		dead = false;
		
		this.render=render;
		
		
		
		if(listeners){
			createListeners();
		}
		
		 
	}
	
	/**
	public Player(Point2D position) {
		super(position, new Point2D(0, 0), new Point2D(0.05f, 0.05f), 200, 10);
		changeWeapon(currentWeapon);
		dead = false;
	}*/
	
	public synchronized void addKey(Integer k){
		pressed.add(k);
	}
	
	public synchronized void update(long ms) {
		if(jump){
			if(up){
				if(z == 30){
					up = false;
				}else{
					z += 2;
				}
			}else{
				if(z == 0){
					up = true;
					jump = false;
				}else{
					z -= 2;
				}
			}
		}
		velocity.multiply(0);
		shoot_dir.multiply(0);
		finishFire();

		if (pressed.size() > 0) {


			if (pressed.contains(KeyEvent.VK_W) && !obstacle_collision_uy) {
				velocity.addY(-MAX_VELOCITY.getY());
			}
			if (pressed.contains(KeyEvent.VK_A) && !obstacle_collision_dx) {
				velocity.addX(-MAX_VELOCITY.getX());
			}
			if (pressed.contains(KeyEvent.VK_S) && !obstacle_collision_dy) {
				velocity.addY(MAX_VELOCITY.getY());
			}
			if (pressed.contains(KeyEvent.VK_D) && !obstacle_collision_ux) {
				velocity.addX(MAX_VELOCITY.getX());
			}
			if (pressed.contains(KeyEvent.VK_UP)
					||pressed.contains(KeyEvent.VK_DOWN)
					||pressed.contains(KeyEvent.VK_LEFT)
					||pressed.contains(KeyEvent.VK_RIGHT)) {
				if (pressed.contains(KeyEvent.VK_UP)) {
					shoot_dir.setY(-1);
				}else if (pressed.contains(KeyEvent.VK_DOWN)) {
					shoot_dir.setY(1);
				}
				
				if (pressed.contains(KeyEvent.VK_LEFT)) {
					shoot_dir.setX(-1);
				}else if (pressed.contains(KeyEvent.VK_RIGHT)) {
					shoot_dir.setX(1);
				}
				startFire();
			}

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


		checkWeapon();
		super.update(ms);

	}

	public void attack() {
		Point last = render.getMousePosition();

		if (last != null) {
			last_mouse_position = last;
		}

		float fx = (float) (last_mouse_position.getX() - getCenterPos().getX())- 220;
		float fy = (float) (last_mouse_position.getY() - getCenterPos().getY()) +50;

		float aux = (float) Math.sqrt(fx * fx + fy * fy);
		fx = (fx / aux);
		fy = (fy / aux);

		//weapons[currentWeapon].attack(this, (getCenterPos()), new Point2D(fx, fy));
		weapons[currentWeapon].attack(this, (getCenterPos()), shoot_dir);

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
		Game.getGame().end(false);
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
	public boolean isJumping(){
		return jump;
	}
	

	Color c = new Color(0, 255, 0);

	
	public void draw3d(GL2 gl, GLU glu, GLUquadric quadric) {
		
		//esto -z/100 .1f
		
		Helper3D.drawCilinder(gl,glu,quadric, getCenterPos(), 0, 0.05f, -z/100, .1f, c,TEXTURE);
	}

	public synchronized void removeKey(int keyCode) {
		pressed.remove(keyCode);
	}

	public void setWeapons(Weapon[] aLL_WEAPONS) {
		weapons=aLL_WEAPONS;
	}
	
	private void createListeners(){
		listeners=false;
		pressed = new HashSet<Integer>();
		kl= new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public synchronized void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE  && !jump) {
					if(dim3d){
						jump = true;
					}
					
				}
				pressed.remove(e.getKeyCode());
			}

			public synchronized void keyPressed(KeyEvent e) {
				pressed.add(e.getKeyCode());
				Render render =Game.getGame().render;
				if(render.is3D()){
					((Render3D) render).keyPressed(e);
				}
				
			}
		};
		// 1 boton inquierdo, 2 central y 3 derecho
		ml = new MouseListener() {

			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == 1) {
					finishFire();
				}
			}

			public void mousePressed(MouseEvent e) {
				if (e.getButton() == 1) {
					//startFire();
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
		
		render.addKeyListener(kl);
		render.addMouseListener(ml);
	}
	/**
	
	
	public void mouseClicked(MouseEvent arg0) {

	}

	public void mouseEntered(MouseEvent arg0) {

	}

	public void mouseExited(MouseEvent arg0) {

	}

	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == 1) {
			finishFire();
		}
	}

	public void mousePressed(MouseEvent e) {
		if (e.getButton() == 1) {
			shoot = getMousePosition();
			startFire();
		}

	}

	public synchronized void keyReleased(KeyEvent e) {
		player.pressed.remove(e.getKeyCode());
	}

	public void keyTyped(KeyEvent arg0) {

	}

	public synchronized void keyPressed(KeyEvent e) {
		player.pressed.add(e.getKeyCode());


	}
	*/
	

}
