package es.pcv.game.elements.scene;

import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.Semaphore;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import es.pcv.core.render.ObjectIcon;
import es.pcv.core.render.Point2D;
import es.pcv.core.render.auxiliar.Helper3D;
import es.pcv.core.render.auxiliar.PolygonHelper;
import es.pcv.core.updater.elements.Collisionable;
import es.pcv.core.updater.elements.Element;
import es.pcv.core.updater.elements.LiveEntity;
import es.pcv.core.updater.elements.PolygonObstacle;
import es.pcv.game.configuration.Config;
import es.pcv.game.elements.player.Player;

public class MapLoader extends PolygonObstacle implements Element{
	protected int TEXTURE_ACT = 2;
	protected int TEXTURE_DEC = 1;

	private static Semaphore s = new Semaphore(1);
	private static boolean activate = false;
	Maps map;
	int idMap;
	int pos;
	protected ObjectIcon icon_hide= new ObjectIcon(Config.RESOURCES_PATH + "/textures/wall.bmp", 1, 1),
			icon_show= new ObjectIcon(Config.RESOURCES_PATH + "/textures/door.bmp", 1, 1);;
	
	public MapLoader(Point2D p, Point2D s, Maps m,int idMap,int pos) {
		super(p, s);
		map=m;
		this.idMap=idMap;
		this.pos=pos;
		// TODO Auto-generated constructor stub
		
	}
	public MapLoader(Point2D p, Point2D s, Point2D center, double ang, Maps m,int idMap,int pos) {
		super(PolygonHelper.rotatePolygon(PolygonHelper.createRectangle(p,s),center,ang).getBounds2D());
		map=m;
		this.idMap=idMap;
		this.pos=pos;
		// TODO Auto-generated constructor stub
		
	}

	public static void activate(){
		try {
			s.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		activate = true;
		s.release();
	}
	public static void desactivate(){
		try {
			s.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		activate = false;
		s.release();
	}

	public void collisionObstacle(Collisionable c) {
		boolean res = isActivate();
		if(res){
			if((c instanceof Player)){
				map.changeMap(idMap,pos);
			}
		}else{
			if(c instanceof LiveEntity){
				c.collisionObstacle(this);
			}
		}
		
	}
	public boolean isActivate(){
		try {
			s.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean res = activate;
		s.release();
		return res;
	}
	public void update(long ms) {
		// TODO Auto-generated method stub
		
	}
	Color act=new Color(0, 255, 0),des=new Color(30, 30, 30);

	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		if(isActivate()){
			g.drawImage(icon_show.getImage(0), getX(), getY(), getSizeX(), getSizeY(), null);
		}else{
			g.drawImage(icon_hide.getImage(0), getX(), getY(), getSizeX(), getSizeY(), null);
		}

	}
	
	public void draw3d(GL2 gl, GLU glu, GLUquadric quadric) {
		if(isActivate()){
			Helper3D.drawRectangle(gl, getCenterPos(), getSize(), 0, 0.1f, null,TEXTURE_ACT);
		}else{
			Helper3D.drawRectangle(gl, getCenterPos(), getSize(), 0, 0.1f, null,TEXTURE_DEC);
		}
	}
	
	
}
