package es.pcv.core.render;

import static com.jogamp.opengl.GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT;
import static com.jogamp.opengl.GL2ES3.GL_QUADS;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_AMBIENT;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_DIFFUSE;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_LIGHT1;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_LIGHTING;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_POSITION;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_SMOOTH;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import es.pcv.core.render.auxiliar.Helper3D;
import es.pcv.core.render.figure.Drawable;
import es.pcv.core.updater.elements.PolygonCollision;
import es.pcv.game.Game;
import es.pcv.game.configuration.Config;
import es.pcv.game.elements.enemies.Enemy;
import es.pcv.game.elements.player.Player;
import es.pcv.game.elements.scene.MapLoader;
import es.pcv.game.elements.scene.Wall;
import es.pcv.game.elements.weapons.bulls.Bullet;
import es.pcv.game.gui.EndTitle;
import es.pcv.game.gui.Gui;

public class Render3D extends JFrame implements GLEventListener, KeyListener, MouseListener,ActionListener,Render {
	/**
	 * 
	 */
	private Texture[] textures = new Texture[6];
	private Gui gui;
	List<Drawable> figures = new LinkedList<Drawable>();
	private static final long serialVersionUID = 1L;    
	private Player player;
	private FPSAnimator animator;
	GLCanvas canvas;
	int width, height;
	JButton butt;
	
	public Render3D(int width, int height,Gui gui) {		
		super("Minimal OpenGL");
		this.gui=gui;
		GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(profile);
		this.canvas = new GLCanvas(capabilities);
		canvas.addGLEventListener(this);
		this.width=width;
		this.height=height;
		canvas.setPreferredSize(new Dimension(width-100,height-200));
		butt=new JButton();
		butt.setPreferredSize(new Dimension(width,100));
		butt.addActionListener(this);
		butt.setIcon(new ImageIcon(Config.RESOURCES_PATH+"icons/play_again.png"));
		gui.setPreferredSize(new Dimension(width,100));
		this.setSize(width, height);
		this.getContentPane().setLayout(new BorderLayout());
		this.setName("Minimal OpenGL");
		this.getContentPane().add(canvas, BorderLayout.CENTER);
		this.getContentPane().add(gui,BorderLayout.PAGE_START);	
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		canvas.addKeyListener(this);
		canvas.addMouseListener(this);
		canvas.requestFocusInWindow();
		FPSAnimator animator = new FPSAnimator(canvas, 30);					
		animator.setIgnoreExceptions(true);
		canvas.setVisible(false);
		//animator.start();
		this.animator=animator;// start the animator
		
	}
	private boolean lightingEnabled;    // Lighting ON/OFF

	private float[] lightPosition = {0.0f, 0.0f, 2.0f, 1.0f};
	private float[] lightAmbient = {0.5f, 0.5f, 0.5f, 1.0f};
	private float[] lightDiffuse = {1.0f, 1.0f, 1.0f, 1.0f};                 // Which Filter To Use
	private GLU glu = new GLU();

	private GLUquadric quadric;
	private boolean restart=false;
	public void addEnd(EndTitle tip){
		
		
	}
	public void addRestartButton(){
		//esto
		this.getContentPane().add(butt, BorderLayout.PAGE_END);
		setVisible(true);
	}

	public boolean isRestarted(){
		return restart;
		
	}
	boolean textInit=false;
	private void initTextures(GL2 gl){
		textInit=true;
		for(int i=0;i<6;i++){
			String ruta=Config.RESOURCES_PATH+"/textures/";
			if(i==0){
				ruta=ruta+"floor.bmp";
			}
			else if(i==1){
				ruta=ruta+"wall.bmp";
			}
			else if(i==2){
				ruta=ruta+"door.bmp";
			}
			else if(i==3){
				ruta=ruta+"box.bmp";
			}
			else if(i==4){
				ruta=ruta+"tu.bmp";
			}
			else if(i==5){
				ruta=ruta+"zombie.bmp";
			}
			Texture tex=loadGLTextures(ruta);
		  	tex.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
		  	tex.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
		  	tex.setTexParameteri(gl, GL2.GL_TEXTURE_MAG_FILTER,GL2.GL_NEAREST);
		  	tex.setTexParameteri(gl, GL2.GL_TEXTURE_MIN_FILTER,GL2.GL_LINEAR_MIPMAP_LINEAR);
		  	gl.glGenerateMipmap(GL2.GL_TEXTURE_2D);
		  	textures[i]=tex;
		}
	
	  
	  	
	}

	
	private Texture loadGLTextures(String file) {    // Load image And Convert To Textures
      	try {
			Texture texture = TextureIO.newTexture(new File(file), true);
			return texture;
		} catch (GLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      	return null;
      	 
      	 
          
       }

	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();

		gl.glEnable(GL.GL_TEXTURE_2D);       
		gl.glShadeModel(GL_SMOOTH);                             // Enable Smooth Shading
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);                   // Black Background
		gl.glClearDepth(1.0f);                                     // Depth Buffer Setup
		gl.glEnable(GL.GL_DEPTH_TEST);                             // Enables Depth Testing

		// The Type Of Depth Testing To Do
		gl.glDepthFunc(GL.GL_LEQUAL);                              

		// Really Nice Perspective Calculations
		gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);

		gl.glLightfv(GL_LIGHT1, GL_AMBIENT, lightAmbient, 0);   // Setup The Ambient Light
		gl.glLightfv(GL_LIGHT1, GL_DIFFUSE, lightDiffuse, 0);   // Setup The Diffuse Light
		gl.glLightfv(GL_LIGHT1, GL_POSITION, lightPosition, 0);  // Position The Light
		gl.glEnable(GL_LIGHT1);                                 // Enable Light One

		// Create A Pointer To The Quadric Object (Return 0 If No Memory) (NEW)
		quadric = glu.gluNewQuadric();
		glu.gluQuadricNormals(quadric, GLU.GLU_SMOOTH);  // Create Smooth Normals (NEW)
		glu.gluQuadricTexture(quadric, true);            // Create Texture Coords (NEW)

		//loadGLTextures(gl, glu);
		Helper3D.initTextures(gl);

	}      
	private void drawSphere(GL2 gl,double posX,double posY,Color c,double h,double rad){
		gl.glTranslated(-posX, h, -posY);
		gl.glColor3f(c.getRed(), c.getGreen(), c.getBlue());
		glu.gluSphere(quadric, rad, 32, 32);
		gl.glTranslated(posX, -h, posY);
	}
	private void drawBase(GL2 gl){
		gl.glActiveTexture(0);
  	  textures[0].enable(gl);
  	  textures[0].bind(gl);
		
  	  gl.glTranslatef(0.0f, -.5f, -3.2f);
  	  gl.glRotatef(45, 1, 0, 0);
  	  gl.glBegin(GL_QUADS);
  	  gl.glNormal3f(0.0f, 0.0f, 1.0f);               // Front Face
  	  gl.glTexCoord2f(0.0f, 0.0f);
  	  gl.glVertex3f(-1.0f, 0.7f, 1.0f);
  	  gl.glTexCoord2f(1.0f, 0.0f);
  	  gl.glVertex3f(1.0f, 0.7f, 1.0f);
  	  gl.glTexCoord2f(1.0f, 1.0f);
  	  gl.glVertex3f(1.0f, 1.0f, 1.0f);
  	  gl.glTexCoord2f(0.0f, 1.0f);
  	  gl.glVertex3f(-1.0f, 1.0f, 1.0f);

  	  gl.glNormal3f(0.0f, 0.0f, -1.0f);                // Back Face
  	  gl.glTexCoord2f(1.0f, 0.0f);
  	  gl.glVertex3f(-1.0f, 0.7f, -1.0f);
  	  gl.glTexCoord2f(1.0f, 1.0f);
  	  gl.glVertex3f(-1.0f, 1.0f, -1.0f);
  	  gl.glTexCoord2f(0.0f, 1.0f);
  	  gl.glVertex3f(1.0f, 1.0f, -1.0f);
  	  gl.glTexCoord2f(0.0f, 0.0f);
  	  gl.glVertex3f(1.0f, 0.7f, -1.0f);

  	  gl.glNormal3f(0.0f, 1.0f, 0.0f);                 // Top Face
  	  gl.glTexCoord2f(0.0f, 1.0f);
  	  gl.glVertex3f(-1.0f, 1.0f, -1.0f);
  	  gl.glTexCoord2f(0.0f, 0.0f);
  	  gl.glVertex3f(-1.0f, 1.0f, 1.0f);
  	  gl.glTexCoord2f(1.0f, 0.0f);
  	  gl.glVertex3f(1.0f, 1.0f, 1.0f);
  	  gl.glTexCoord2f(1.0f, 1.0f);
  	  gl.glVertex3f(1.0f, 1.0f, -1.0f);

  	  gl.glNormal3f(0.0f, -1.0f, 0.0f);              // Bottom Face
  	  gl.glTexCoord2f(1.0f, 1.0f);
  	  gl.glVertex3f(-1.0f, 0.7f, -1.0f);
  	  gl.glTexCoord2f(0.0f, 1.0f);
  	  gl.glVertex3f(1.0f, 0.7f, -1.0f);
  	  gl.glTexCoord2f(0.0f, 0.0f);
  	  gl.glVertex3f(1.0f, 0.7f, 1.0f);
  	  gl.glTexCoord2f(1.0f, 0.0f);
  	  gl.glVertex3f(-1.0f, 0.7f, 1.0f);

  	  gl.glNormal3f(1.0f, 0.0f, 0.0f);               // Right Face
  	  gl.glTexCoord2f(1.0f, 0.0f);
  	  gl.glVertex3f(1.0f, 0.7f, -1.0f);
      gl.glTexCoord2f(1.0f, 1.0f);
  	  gl.glVertex3f(1.0f, 1.0f, -1.0f);
  	  gl.glTexCoord2f(0.0f, 1.0f);
  	  gl.glVertex3f(1.0f, 1.0f, 1.0f);
  	  gl.glTexCoord2f(0.0f, 0.0f);
  	  gl.glVertex3f(1.0f, 0.7f, 1.0f);

  	  gl.glNormal3f(-1.0f, 0.0f, 0.0f);               // Left Face
  	  gl.glTexCoord2f(0.0f, 0.0f);
  	  gl.glVertex3f(-1.0f, 0.7f, -1.0f);
 	  gl.glTexCoord2f(1.0f, 0.0f);
  	  gl.glVertex3f(-1.0f, 0.7f, 1.0f);
  	  gl.glTexCoord2f(1.0f, 1.0f);
  	  gl.glVertex3f(-1.0f, 1.0f, 1.0f);
 	  gl.glTexCoord2f(0.0f, 1.0f);
  	  gl.glVertex3f(-1.0f, 1.0f, -1.0f);
  	  gl.glEnd();
  	  
  	  textures[0].disable(gl);
  	
    }
	private void drawRectangle(float x1,float x2,float x3,float x4,float y1,float y2,float y3,float y4,GL2 gl,int texture,float h){
		
		//gl.glTranslatef(0f, -0.1f, 0f);
		gl.glColor3f(1, 1, 1);
		textures[texture].enable(gl);
	  	textures[texture].bind(gl);
		gl.glBegin(GL_QUADS);
		//gl.glColor3f(c.getRed(), c.getGreen(), c.getBlue());
		
	  	gl.glNormal3f(0.0f, 0.0f, 1.0f);
	  	gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(x2, 0.0f, y2);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(x1, 0.0f, y1);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(x1, h, y1);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(x2, h, y2);
		
			
		 gl.glNormal3f(0.0f, 0.0f, -1.0f);  
	  	gl.glTexCoord2f(1.0f, 0.0f);// Back Face
		gl.glVertex3f(x4, 0.0f, y4);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(x4, h, y4);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(x3, h, y3);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(x3, 0.0f, y3);

		
	  	  // Top Face
		gl.glNormal3f(0.0f, 1.0f, 0.0f);
	  	gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(x4, h, y4);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(x2, h, y2);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(x3, h, y3);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(x1, h, y1);

		
		gl.glNormal3f(0.0f, -1.0f, 0.0f);                // Bottom Face
	  	gl.glTexCoord2f(1.0f, 1.0f);
	  	gl.glVertex3f(x4,0.0f, y4);
	  	gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(x3, 0.0f, y3);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(x1, 0.0f, y1);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(x2, 0.0f, y2);
		
		gl.glNormal3f(1.0f, 0.0f, 0.0f);
	  	gl.glTexCoord2f(1.0f, 0.0f);
	  	gl.glVertex3f(x3, 0.0f, y3);
	  	gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(x3, h, y3);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(x1, h, y1);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(x1, 0.0f, y1);

		gl.glNormal3f(-1.0f, 0.0f, 0.0f);               // Left Face
	  	gl.glTexCoord2f(0.0f, 0.0f);
	  	gl.glVertex3f(x4, 0.0f, y4);
	  	gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(x2, 0.0f, y2);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(x2, h, y2);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(x4, h, y4);


		gl.glEnd();
		//gl.glTranslatef(0f,0.1f, 0f);
	
		
		textures[texture].disable(gl);


	}
	private void update() {
		List<Drawable> toRemove = new LinkedList<Drawable>();
		for (Drawable drawable : figures) {
			if (drawable.isDead()) {
				toRemove.add(drawable);
			} 
			if (drawable instanceof Player) {
				player=(Player) drawable;
			}
		}
		
		for (Drawable d : toRemove) {
			remove(d);
		}
		
		if(player!=null&&player.isFire()){
			player.shoot=this.getMousePosition();			
		}
		
		gui.render();
		

		
	}
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2(); 
		//System.out.println(this.getMousePosition());
		update();
		
		if (!lightingEnabled)
			gl.glDisable(GL_LIGHTING);
		else
			gl.glEnable(GL_LIGHTING);

		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity(); // Reset The View    	  
		gl.glColor3f(1, 1, 1);
		
		Helper3D.drawBase(gl);
		gl.glTranslatef(0f, 1.0f, 0f);
		Color play=new Color(1,0,0);
		Color enem=new Color(0,1,0);

		for (Drawable draw : figures) {
			gl.glColor3f(1, 1, 1);
			draw.draw3d(gl, glu, quadric);
		}
	}
	public void drawCilinder(GL2 gl,double posX,double posY,Color c,int texture){ 
		textures[texture].enable(gl);
		textures[texture].bind(gl);	
		gl.glTranslated(-posX, 0, -posY);
		gl.glRotated(-110, 1, 0, 0);
		
		//gl.glColor3f(c.getRed(), c.getGreen(), c.getBlue());
		glu.gluCylinder(quadric, 0.06f, 0.06f, .11f, 32, 32); 
		gl.glRotated(110, 1, 0, 0);
		gl.glTranslated(posX, 0, posY);
		textures[texture].disable(gl);
	}
	public void reshape(GLAutoDrawable drawable,
			int xstart,
			int ystart,
			int width,
			int height) {
		GL2 gl = drawable.getGL().getGL2();

		height = (height == 0) ? 1 : height;

		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL_PROJECTION);
		gl.glLoadIdentity();

		glu.gluPerspective(45, (float) width / height, 1, 1000);
		gl.glMatrixMode(GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	public void displayChanged(GLAutoDrawable drawable,
			boolean modeChanged,
			boolean deviceChanged) {
	}

	public void dispose(GLAutoDrawable arg0) {


	}
	public void start(){
		canvas.setVisible(true);
		animator.start();
	}
	public void end(){
		//animator.stop();
		canvas.setVisible(false);
		Helper3D.end();
	}
	public void add(Drawable d) {
		animator.stop();
		figures.add(d);
		animator.start();
		
	}
	public void remove(Drawable d) {
		animator.stop();
		figures.remove(d);
		animator.start();		
	}
	public void clear(){	
		animator.stop();
		figures.clear();		
		animator.start();	
		
	}
	
	public void mouseClicked(MouseEvent arg0) {
		
		
	}
	public void mouseEntered(MouseEvent arg0) {
		
		
	}
	public void mouseExited(MouseEvent arg0) {
		
		
	}
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == 1) {
			if(player!=null)
				player.finishFire();
		}
	}

	public void mousePressed(MouseEvent e) {
		if (e.getButton() == 1) {
			player.shoot=this.getMousePosition();
			player.startFire();			
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
	public void actionPerformed(ActionEvent arg0) {
		restart=true;
		//esto
		getContentPane().remove(butt);
		gui.clear();
		Game.getGame().startGame();
		setVisible(true);
	}




}
