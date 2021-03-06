package es.pcv.core.render;

import static com.jogamp.opengl.GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_AMBIENT;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_DIFFUSE;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_LIGHT1;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_LIGHTING;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_POSITION;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_SMOOTH;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.FPSAnimator;

import es.pcv.core.render.auxiliar.Helper3D;
import es.pcv.core.render.figure.Drawable;
import es.pcv.game.Game;
import es.pcv.game.configuration.Config;
import es.pcv.game.elements.player.Player;
import es.pcv.game.gui.EndTitle;
import es.pcv.game.gui.Gui;

public class Render3D extends Render implements GLEventListener,MouseMotionListener,MouseListener,MouseWheelListener {
	/**
	 * 
	 */
	private Render gui;
	List<Drawable> figures = new LinkedList<Drawable>();
	private static final long serialVersionUID = 1L;
	//private Player player;
	private FPSAnimator animator;
	GLCanvas canvas;
	int width, height;
	//JButton butt;
	public int rotationx=0;
	public int rotationy=0;
	public int rotationz=0;
	public float zoom=0;
	public float zoom2=0;
	public float zoom3=0;
	public boolean player_center = false;
	private boolean rotationMode=false;
	
	public Render3D(int width, int height, Render gui) {
		//System.out.println("creando render");
		//super("Minimal OpenGL");
		super();
		this.gui = gui;
		GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(profile);
		this.canvas = new GLCanvas(capabilities);
		
		
		canvas.addGLEventListener(this);
		this.width = width;
		this.height = height;
		canvas.setPreferredSize(new Dimension(width - 100, height - 200));
		
		
		//boton de restart
		//butt = new JButton();
		//butt.setPreferredSize(new Dimension(width, 100));
		//butt.addActionListener(this);
		//butt.setIcon(new ImageIcon(Config.RESOURCES_PATH + "icons/play_again.png"));
		
		//gui superior
		gui.setPreferredSize(new Dimension(width, 100));
		this.setSize(width, height);
		
		this.setLayout(new BorderLayout());
		//this.setName("Minimal OpenGL");
		//this.getContentPane().add(canvas, BorderLayout.CENTER);
		//this.getContentPane().add(gui, BorderLayout.PAGE_START);
		//this.setLocationRelativeTo(null);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setVisible(true);
		//this.setResizable(false);
		
		
		this.add(canvas, BorderLayout.CENTER);
		this.add(gui, BorderLayout.PAGE_START);
		//this.add(butt, BorderLayout.PAGE_END);
		//validate();
		//setVisible(true);
		//this.setLocationRelativeTo(null);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		//this.setResizable(false);
		
		//canvas.addKeyListener(this);
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		canvas.addMouseWheelListener(this);
		canvas.requestFocusInWindow();
		
		FPSAnimator animator = new FPSAnimator(canvas, 30);
		animator.setIgnoreExceptions(true);
		canvas.setVisible(false);
		// animator.start();
		this.animator = animator;// start the animator
		//esto
		start();

	}

	private float[] lightPosition = { 0f, 1.5f, 0f, 1.0f };
	private float[] lightAmbient = { 1.2f, 1.2f, 1.2f, 2.0f };
	private float[] lightDiffuse = { 1.2f, 1.2f, 1.2f, 2.0f }; // Which Filter
																// To Use
	private GLU glu = new GLU();

	private GLUquadric quadric;
	private boolean restart = false;
	private boolean perspective=false;

	/**
	public void addEnd(EndTitle tip) {

	}*/

	/**
	public void addRestartButton() {
		// esto
		//this.getContentPane().add(butt, BorderLayout.PAGE_END);
		this.add(butt, BorderLayout.PAGE_END);
		validate();
		setVisible(true);
	}*/

	/**
	public boolean isRestarted() {
		return restart;
	}*/

	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();

		gl.glEnable(GL.GL_TEXTURE_2D);
		gl.glShadeModel(GL_SMOOTH); // Enable Smooth Shading
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f); // Black Background
		gl.glClearDepth(1.0f); // Depth Buffer Setup
		gl.glEnable(GL.GL_DEPTH_TEST); // Enables Depth Testing

		// The Type Of Depth Testing To Do
		gl.glDepthFunc(GL.GL_LEQUAL);

		// Really Nice Perspective Calculations
		gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);

		gl.glLightfv(GL_LIGHT1, GL_AMBIENT, lightAmbient, 0); // Setup The
																// Ambient Light
		gl.glLightfv(GL_LIGHT1, GL_DIFFUSE, lightDiffuse, 0); // Setup The
																// Diffuse Light
		gl.glLightfv(GL_LIGHT1, GL_POSITION, lightPosition, 0); // Position The
																// Light
		gl.glEnable(GL_LIGHT1); // Enable Light One

		// Create A Pointer To The Quadric Object (Return 0 If No Memory) (NEW)
		quadric = glu.gluNewQuadric();
		glu.gluQuadricNormals(quadric, GLU.GLU_SMOOTH); // Create Smooth Normals
														// (NEW)
		glu.gluQuadricTexture(quadric, true); // Create Texture Coords (NEW)

		// loadGLTextures(gl, glu);
		Helper3D.initTextures(gl);
	}

	/**
	private void update() {
		List<Drawable> toRemove = new LinkedList<Drawable>();
		for (Drawable drawable : figures) {
			if (drawable.isDead()) {
				toRemove.add(drawable);
			}
			if (drawable instanceof Player) {
				player = (Player) drawable;
			}
		}

		for (Drawable d : toRemove) {
			remove(d);
		}

		if (player != null && player.isFire()) {
			player.shoot = this.getMousePosition();
		}

		gui.render();

	}*/
	
	public void updateLight(GL2 gl){
		gl.glLightfv(GL_LIGHT1, GL_AMBIENT, lightAmbient, 0); // Setup The
		// Ambient Light
		gl.glLightfv(GL_LIGHT1, GL_DIFFUSE, lightDiffuse, 0); // Setup The
		// Diffuse Light
		gl.glLightfv(GL_LIGHT1, GL_POSITION, lightPosition, 0); // Position The
		// Light
		gl.glEnable(GL_LIGHT1); // Enable Light One
	}

	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		// System.out.println(this.getMousePosition());
		//update();
		List<Drawable> toRemove = new LinkedList<Drawable>();
		for (Drawable dr : figures) {
			if (dr.isDead()) {
				toRemove.add(dr);
			}
			/**
			if (dr instanceof Player) {
				player = (Player) dr;
			}*/
		}

		for (Drawable d : toRemove) {
			remove(d);
		}
		
		gui.repaint();
		//gl.glDisable(GL_LIGHTING);
		gl.glEnable(GL_LIGHTING);

		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity(); // Reset The View
		gl.glColor3f(1, 1, 1);
		
		if (perspective) {
			glu.gluPerspective(45, (float) width / height, 1, 1000);
		}else{
			gl.glOrtho(-1+1*zoom, 1f-1*zoom, -1f+1*zoom, 1f-1*zoom, -3f, 20.0f);
		}

		Helper3D.startRenderFrame(gl, player_center, rotationx, rotationy,rotationz, zoom, rotationMode,zoom2,zoom3);
		updateLight(gl);
		Helper3D.drawBase(gl);
		gl.glTranslatef(0f, 1.0f, 0f);

		for (Drawable draw : figures) {
			gl.glColor3f(1, 1, 1);
			draw.draw3d(gl, glu, quadric);
		}
	}

	public void reshape(GLAutoDrawable drawable, int xstart, int ystart, int width, int height) {
		GL2 gl = drawable.getGL().getGL2();

		height = (height == 0) ? 1 : height;

		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL_PROJECTION);
		gl.glLoadIdentity();
		
		gl.glMatrixMode(GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
	}

	public void dispose(GLAutoDrawable arg0) {

	}

	public void start() {
		canvas.setVisible(true);
		animator.start();
	}

	public void end() {
		// animator.stop();
		canvas.setVisible(false);
		Helper3D.end();
	}

	public void add(Drawable d) {
		// animator.stop();
		figures.add(d);
		// animator.start();

	}

	public void remove(Drawable d) {
		// animator.stop();
		figures.remove(d);
		// animator.start();
	}

	public void clear() {
		// animator.stop();
		gui.clear();
		figures.clear();
		// animator.start();

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
			if (player != null)
				player.finishFire();
		}
	}

	public void mousePressed(MouseEvent e) {
		if (e.getButton() == 1) {
			player.shoot = this.getMousePosition();
			player.startFire();
		}

	}

	public synchronized void keyReleased(KeyEvent e) {
		player.pressed.remove(e.getKeyCode());
	}

	public void keyTyped(KeyEvent arg0) {

	}

*/
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_M) {
			perspective=!perspective;
			rotationx=0;
			rotationy=0;
			rotationz=0;
			zoom=0;
			zoom2=0;
			zoom3=0;
		}
		if (e.getKeyCode()==KeyEvent.VK_L) {
			rotationMode=!rotationMode;
		}
		if(e.getKeyCode()==KeyEvent.VK_PLUS){
			zoom+=0.1;
		}
		if(e.getKeyCode()==KeyEvent.VK_MINUS){
			zoom-=0.1;
		}
		if(e.getKeyCode()==KeyEvent.VK_G){
			zoom2-=0.1;
		}
		if(e.getKeyCode()==KeyEvent.VK_T){

			zoom2+=0.1;
		}
		if(e.getKeyCode()==KeyEvent.VK_F){
			zoom3-=0.1;
		}
		if(e.getKeyCode()==KeyEvent.VK_H){
			zoom3+=0.1;
			
		}
		if(e.getKeyCode()==KeyEvent.VK_R){
			rotationx=0;
			rotationy=0;
			rotationz=0;
			zoom=0;
			zoom2=0;
			zoom3=0;
		}
		if(e.getKeyCode()==KeyEvent.VK_Q){
			rotationz-=1;
		}
		if(e.getKeyCode()==KeyEvent.VK_E){
			rotationz+=1;
		}
		if (e.getKeyCode()==KeyEvent.VK_C) {
			player_center=!player_center;
		}

	}
	
	public void addMouseListener(MouseListener ml){
		canvas.addMouseListener(ml);
	}
	
	public void addKeyListener(KeyListener kl){
		canvas.addKeyListener(kl);
		
	}
	public boolean is3D(){
		return true;
	}

	//@Override
	public void mouseClicked(MouseEvent e) {
		last_x=e.getX();
		last_y=e.getY();
	}

	//@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	//@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	private int last_x=0,last_y=0;
	//@Override
	public void mousePressed(MouseEvent e) {
		last_x=e.getX();
		last_y=e.getY();
	}

	//@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	//@Override
	public void mouseDragged(MouseEvent e) {
		rotationy=rotationy	+(last_x-e.getX());
		rotationx=rotationx+(last_y-e.getY());
		last_x=e.getX();
		last_y=e.getY();
	}

	//@Override
	public void mouseMoved(MouseEvent e) {
		
	}

//	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		zoom+=e.getWheelRotation()*0.1;
	}
	
/**
	public void actionPerformed(ActionEvent arg0) {
		//restart = true;
		// esto
		//remove(butt);
		//gui.clear();
		Game.getGame().restart();
		setVisible(true);
	}*/

}
