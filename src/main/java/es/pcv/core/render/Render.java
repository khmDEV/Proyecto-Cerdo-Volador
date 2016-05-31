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
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
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

import es.pcv.core.render.figure.Drawable;
import es.pcv.core.updater.elements.PolygonCollision;
import es.pcv.game.configuration.Config;
import es.pcv.game.elements.enemies.Enemy;
import es.pcv.game.elements.player.Player;
import es.pcv.game.elements.scene.MapLoader;
import es.pcv.game.elements.scene.Wall;
import es.pcv.game.elements.weapons.bulls.Bullet;
import es.pcv.game.gui.ButtonRestart;
import es.pcv.game.gui.EndTitle;
import es.pcv.game.gui.Gui;

public class Render extends JFrame implements GLEventListener, KeyListener, MouseListener,ActionListener {
	/**
	 * 
	 */
	private Gui gui;
	List<Drawable> figures = new LinkedList<Drawable>();
	private static final long serialVersionUID = 1L;    
	private Player player;
	private FPSAnimator animator;
	GLCanvas canvas;
	int width, height;
	public Render(int width, int height,Gui gui) {
		
		super("Minimal OpenGL");
		this.gui=gui;
		GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(profile);
		this.canvas = new GLCanvas(capabilities);
		canvas.addGLEventListener(this);
		this.width=width;
		this.height=height;
		canvas.setPreferredSize(new Dimension(width-100,height-200));
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
		JButton butt=new JButton("Restart");
		butt.setPreferredSize(new Dimension(width,100));
		butt.addActionListener(this);
		this.getContentPane().add(butt, BorderLayout.PAGE_END);
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
		JButton butt=new JButton("Restart");
		butt.setPreferredSize(new Dimension(width,100));
		butt.addActionListener(this);
		this.getContentPane().add(butt, BorderLayout.PAGE_END);
		
	}
	public boolean isRestarted(){
		return restart;
		
	}
	


	
	/*private void loadGLTextures(GL gl, GLU glu) {    // Load image And Convert To Textures
      	 Texture texture = TextureIO.newTexture(new File("D:\text.bmp"), true);
           gl.glGenTextures(3, textures, 0);  // Create Three Textures
           // Create Nearest Filtered Texture
           gl.glBindTexture(GL.GL_TEXTURE_2D, textures[0]);
           gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
           gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
           gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, 3, texture.getWidth(), 
                   texture.getHeight(), 0, GL.GL_RGB, GL.GL_UNSIGNED_BYTE, texture.getPixels());

           // Create Linear Filtered Texture
           gl.glBindTexture(GL.GL_TEXTURE_2D, textures[1]);
           gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
           gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
           gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, 3, texture.getWidth(), 
                   texture.getHeight(), 0, GL.GL_RGB, GL.GL_UNSIGNED_BYTE, texture.getPixels());

           // Create MipMapped Texture
           gl.glBindTexture(GL.GL_TEXTURE_2D, textures[2]);
           gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
           gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, 
                   GL.GL_LINEAR_MIPMAP_NEAREST);

           glu.gluBuild2DMipmaps(GL.GL_TEXTURE_2D, 3, 
                   texture.getWidth(), texture.getHeight(), GL.GL_RGB, 
                   GL.GL_UNSIGNED_BYTE, texture.getPixels());
       }*/

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
	}      
	private void drawSphere(GL2 gl,double posX,double posY,Color c,double h,double rad){
		gl.glTranslated(-posX, h, -posY);
		gl.glColor3f(c.getRed(), c.getGreen(), c.getBlue());
		glu.gluSphere(quadric, rad, 32, 32);
		gl.glTranslated(posX, -h, posY);
	}
	private void drawBase(GL2 gl){
		gl.glTranslatef(0.0f, -.5f, -3.2f);
		gl.glRotatef(45, 1, 0, 0);
		gl.glBegin(GL_QUADS);
		gl.glNormal3f(0.0f, 0.0f, 1.0f);               // Front Face
		//gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-1.0f, 0.7f, 1.0f);
		//gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(1.0f, 0.7f, 1.0f);
		//gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, 1.0f);
		//gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, 1.0f);

		gl.glNormal3f(0.0f, 0.0f, -1.0f);                // Back Face
		//gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(-1.0f, 0.7f, -1.0f);
		//gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, -1.0f);
		//gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, -1.0f);
		//gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(1.0f, 0.75f, -1.0f);

		gl.glNormal3f(0.0f, 1.0f, 0.0f);                 // Top Face
		//gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, -1.0f);
		//gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-1.0f, 1.0f, 1.0f);
		//gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(1.0f, 1.0f, 1.0f);
		//gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, -1.0f);

		gl.glNormal3f(0.0f, -1.0f, 0.0f);              // Bottom Face
		//gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(-1.0f, 0.7f, -1.0f);
		//gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(1.0f, 0.7f, -1.0f);
		//gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(1.0f, 0.7f, 1.0f);
		//gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(-1.0f, 0.7f, 1.0f);

		gl.glNormal3f(1.0f, 0.0f, 0.0f);               // Right Face
		// gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(1.0f, 0.7f, -1.0f);
		// gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, -1.0f);
		// gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, 1.0f);
		// gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(1.0f, 0.7f, 1.0f);

		gl.glNormal3f(-1.0f, 0.0f, 0.0f);               // Left Face
		//  gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-1.0f, 0.7f, -1.0f);
		//  gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(-1.0f, 0.7f, 1.0f);
		//  gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, 1.0f);
		//  gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, -1.0f);
		gl.glEnd();
	}
	private void drawRectangle(float x1,float x2,float x3,float x4,float y1,float y2,float y3,float y4,GL2 gl,Color c,float h){
		gl.glTranslatef(0f, -0.1f, 0f);
		gl.glBegin(GL_QUADS);
		gl.glColor3f(c.getRed(), c.getGreen(), c.getBlue());
		//gl.glNormal3f(0.0f, 0.0f, 1.0f);               // Front Face

		gl.glVertex3f(x2, 0.0f, y2);
		gl.glVertex3f(x1, 0.0f, y1);
		gl.glVertex3f(x1, h, y1);
		gl.glVertex3f(x2, h, y2);


		//gl.glNormal3f(0.0f, 0.0f, -1.0f);                // Back Face
		gl.glVertex3f(x3, 0.0f, y3);
		gl.glVertex3f(x3, h, y3);
		gl.glVertex3f(x4, h, y4);
		gl.glVertex3f(x4, 0.0f, y4);

		// gl.glNormal3f(0.0f, 1.0f, 0.0f);                 // Top Face
		gl.glVertex3f(x1,h, y1);
		gl.glVertex3f(x2, h, y2);
		gl.glVertex3f(x3, h, y3);
		gl.glVertex3f(x4, h, y4);

		//gl.glNormal3f(0.0f, -1.0f, 0.0f);              // Bottom Face
		gl.glVertex3f(x1,0.0f, y1);
		gl.glVertex3f(x2, 0.0f, y2);
		gl.glVertex3f(x3, 0.0f, y3);
		gl.glVertex3f(x4, 0.0f, y4);

		//gl.glNormal3f(1.0f, 0.0f, 0.0f);               // Right Face
		gl.glVertex3f(x2, 0.0f, y2);
		gl.glVertex3f(x4, 0.0f, y4);
		gl.glVertex3f(x4, h, y4);
		gl.glVertex3f(x2, h, y2);

		// gl.glNormal3f(-1.0f, 0.0f, 0.0f);               // Left Face
		gl.glVertex3f(x1, 0.0f, y1);
		gl.glVertex3f(x3, 0.0f, y3);
		gl.glVertex3f(x3, h, y3);
		gl.glVertex3f(x1, h, y1);

		gl.glEnd();
		gl.glTranslatef(0f,0.1f, 0f);


	}
	private void update() {
		
		if(player!=null&&player.isFire()){
			player.shoot=this.getMousePosition();			
		}
		
		gui.render();
		List<Drawable> toRemove = new LinkedList<Drawable>();
		for (Drawable drawable : figures) {
			if (drawable.isDead()) {
				toRemove.add(drawable);
			} 
		}

		for (Drawable d : toRemove) {
			remove(d);
		}
	}
	public void display(GLAutoDrawable drawable) {
		//System.out.println(this.getMousePosition());
		update();
		GL2 gl = drawable.getGL().getGL2(); 
		if (!lightingEnabled)
			gl.glDisable(GL_LIGHTING);
		else
			gl.glEnable(GL_LIGHTING);

		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity(); // Reset The View    	  
		gl.glColor3f(1, 1, 1);
		drawBase(gl);
		gl.glTranslatef(0f, 1.1f, 0f);
		Color play=new Color(1,0,0);
		Color enem=new Color(0,1,0);
		for (Drawable draw : figures) {
			if (draw instanceof Player) {				
				Player pl = (Player) draw;
				this.player=pl;
				Point2D p=pl.getCenterPos();
				drawCilinder(gl,p.adaptar().getX(),p.adaptar().getY(),play);				
			}
			else if(draw instanceof Wall){
				Wall pl = (Wall) draw;
				Point2D abs=pl.getCenterPos().adaptar();
				abs.setX(-abs.getX());
				abs.setY(-abs.getY());
				System.out.println(abs);
				float alt=pl.getSizeX()/Config.scale.getX();
				float anch=pl.getSizeY()/Config.scale.getY();
				Color b=new Color(0,0,1);
				drawRectangle(abs.getX()+alt,abs.getX()-alt,abs.getX()+alt,abs.getX()-alt,
						abs.getY()+anch,abs.getY()+anch,abs.getY()-anch,abs.getY()-anch,gl,b,0.1f);
				
			}
			else if(draw instanceof Enemy){
				Enemy pl = (Enemy) draw;
				Point2D p=pl.getCenterPos();
				drawCilinder(gl,p.adaptar().getX(),p.adaptar().getY(),enem);		
			}
			else if(draw instanceof Bullet){
				Color col=new Color(1,1,0);
				Bullet pl = (Bullet) draw;
				Point2D p=pl.getCenterPos();
				drawSphere(gl, p.adaptar().getX(), p.adaptar().getY(), col, 0, 0.01);
			}
			else if(draw instanceof Bullet){
				Color col=new Color(1,1,0);
				Bullet pl = (Bullet) draw;
				Point2D p=pl.getCenterPos();
				drawSphere(gl, p.adaptar().getX(), p.adaptar().getY(), col, 0, 0.01);
			}
			else if(draw instanceof MapLoader){
				
				Color col=new Color(1,0,1);
				MapLoader pl = (MapLoader) draw;
				if(pl.isActivate()){
					
				}
				else{
					Point2D abs=pl.getCenterPos().adaptar();	
					abs.setX(-abs.getX());
					abs.setY(-abs.getY());
					float alt=pl.getSizeX()/Config.scale.getX();
					float anch=pl.getSizeY()/Config.scale.getY();
					
					drawRectangle(abs.getX()+alt,abs.getX()-alt,abs.getX()+alt,abs.getX()-alt,
							abs.getY()+anch,abs.getY()+anch,abs.getY()-anch,abs.getY()-anch,gl,col,0.1f);
				}
				
				
			}
			else if(draw instanceof PolygonCollision){
				PolygonCollision pl = (PolygonCollision) draw;
				Color col=new Color(0,1,1);
				Point2D abs=pl.getCenterPos().adaptar();	
				abs.setX(-abs.getX());
				abs.setY(-abs.getY());
				float alt=pl.getSizeX()/Config.scale.getX();
				float anch=pl.getSizeY()/Config.scale.getY();
				
				drawRectangle(abs.getX()+alt,abs.getX()-alt,abs.getX()+alt,abs.getX()-alt,
						abs.getY()+anch,abs.getY()+anch,abs.getY()-anch,abs.getY()-anch,gl,col,0.01f);
				
				
				
			}
		}
		//Color c=new Color(1,0,0);
		
		//drawRectangle(1,0.9f,1,0.9f,1,1,-1,-1,gl,c,0.1f);

		// System.out.println("cil")
		/*Color f=new Color(0,1,0);
		drawCilinder(gl,1,1,f);
		drawCilinder(gl,-1,1,c);
		drawCilinder(gl,1,-1,f);
		drawCilinder(gl,-1,-1,c);
		drawSphere(gl, 0.5, 0.2, c, 0, 0.01);*/



	}
	public void drawCilinder(GL2 gl,double posX,double posY,Color c){ 
		gl.glTranslated(-posX, 0, -posY);
		gl.glRotated(90, 1, 0, 0);
		gl.glColor3f(c.getRed(), c.getGreen(), c.getBlue());
		glu.gluCylinder(quadric, 0.00f, 0.05f, .1f, 32, 32); 
		gl.glRotated(-90, 1, 0, 0);
		gl.glTranslated(posX, 0, posY);
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
		canvas.setVisible(false);
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
		
	}



}
