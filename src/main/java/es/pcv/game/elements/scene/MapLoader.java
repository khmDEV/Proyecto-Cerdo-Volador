package es.pcv.game.elements.scene;

import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.Semaphore;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Collisionable;
import es.pcv.core.updater.elements.Element;
import es.pcv.core.updater.elements.LiveEntity;
import es.pcv.core.updater.elements.PolygonObstacle;
import es.pcv.game.elements.player.Player;

public class MapLoader extends PolygonObstacle implements Element{

	private static Semaphore s = new Semaphore(1);
	private static boolean activate = false;
	Maps map;
	int idMap;
	int pos;
	public MapLoader(Point2D p, Point2D s, Maps m,int idMap,int pos) {
		super(p, s);
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

	@Override
	public void collisionObstacle(Collisionable c) {
		// TODO Auto-generated method stub
		try {
			s.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean res = activate;
		s.release();
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

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		if(activate){
			g.setColor(new Color(0, 255, 0));
		}else{
			g.setColor(new Color(0, 0, 0));
		}
		g.fillRect(getX(), getY(), getSizeX() , getSizeY());
		
	}
	
	
}
