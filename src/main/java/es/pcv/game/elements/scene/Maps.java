package es.pcv.game.elements.scene;

import java.awt.Color;

import es.pcv.core.render.Point2D;
import es.pcv.core.render.Render;
import es.pcv.core.render.auxiliar.PolygonHelper;
import es.pcv.core.updater.Updater;
import es.pcv.core.updater.elements.Element;
import es.pcv.game.elements.enemies.EnemyMelee;
import es.pcv.game.elements.objects.Something;
import es.pcv.game.elements.player.Player;

public class Maps {
	public Updater updater;
	public Render render;
	private Map[] maps = new Map[3];
	private int currentMap;
	private Player player;
	
	public Maps(Updater u, Render r,Player p){
		updater=u;
		render=r;
		player = p;
		currentMap=0;
		
		map0();
		maps[0].show();
		player.setPos(maps[0].getPlayerPos());
		
		
		map1();
		
		map2();
		
	}
	
	public void map0(){
		maps[0] = new Map(updater,render,new Point2D(0.5f, 0.9f));
		//maps[0].addElement(new StandarWall(new Point2D(0.6f, 0.3f),new Point2D(0.02f, 0.6f) ));
		
		maps[0].addElement(new StandarWall(new Point2D(0, 0),new Point2D(0.02f, 1)));
		maps[0].addElement(new StandarWall(new Point2D(0, 0),new Point2D(0.4f, 0.02f)));
		maps[0].addElement(new StandarWall(new Point2D(0.6f, 0),new Point2D(0.4f, 0.02f)));
		//maps[0].addElement(new StandarWall(new Point2D(0, 0.98f),new Point2D(1, 0.02f)));
		maps[0].addElement(new StandarWall(new Point2D(0.98f, 0),new Point2D(0.02f, 1)));
		
		maps[0].addElement(new EnemyMelee(new Point2D(0.7f, 0.5f),player));
		
		
		maps[0].addElement(new Something(new Point2D(0.3f, 0.2f),new Point2D(0.1f, 0.2f),"mesa.png",false));
		
		maps[0].addElement(new MapLoader(new Point2D(0.4f, 0),new Point2D(0.2f, 0.02f),this,1));

		
		//player.setPos(new Point2D(0.5f,0.5f).setAbsolutePosition());
		
	}
	
	public void map1(){
		maps[1] = new Map(updater,render,new Point2D(0.5f, 0.9f));
		
		maps[1].addElement(new StandarWall(new Point2D(0, 0),new Point2D(0.02f, 1) ));
		maps[1].addElement(new StandarWall(new Point2D(0, 0),new Point2D(0.4f, 0.02f)));
		maps[1].addElement(new StandarWall(new Point2D(0.6f, 0),new Point2D(0.4f, 0.02f)));
		maps[1].addElement(new StandarWall(new Point2D(0, 0.98f),new Point2D(0.4f, 0.02f)));
		maps[1].addElement(new StandarWall(new Point2D(0.6f, 0.98f),new Point2D(0.4f, 0.02f)));
		maps[1].addElement(new StandarWall(new Point2D(0.98f, 0),new Point2D(0.02f, 1)));
		
		
		maps[1].addElement(new EnemyMelee(new Point2D(0.2f, 0.5f),player));
		maps[1].addElement(new EnemyMelee(new Point2D(0.5f, 0.5f),player));
		maps[1].addElement(new EnemyMelee(new Point2D(0.8f, 0.5f),player));
		
		maps[1].addElement(new MapLoader(new Point2D(0.4f, 0),new Point2D(0.2f, 0.02f),this,2));
		maps[1].addElement(new MapLoader(new Point2D(0.4f, 0.98f),new Point2D(0.2f, 0.02f),this,0));
		
		

	}
	
	public void map2(){
		
		maps[2] = new Map(updater,render,new Point2D(0.5f, 0.9f));
		
		maps[2].addElement(new StandarWall(new Point2D(0.3f, 0),new Point2D(0.02f, 0.3f) ));
		maps[2].addElement(new StandarWall(new Point2D(0.3f, 0),new Point2D(0.4f, 0.02f)));
		
		maps[2].addElement(new StandarWall(new Point2D(0.7f, 0),new Point2D(0.02f, 0.1f) ));
		maps[2].addElement(new StandarWall(new Point2D(0.7f, 0.2f),new Point2D(0.02f, 0.1f) ));
		
		maps[2].addElement(new StandarWall(new Point2D(0.3f, 0.3f),new Point2D(0.1f, 0.02f) ));
		maps[2].addElement(new StandarWall(new Point2D(0.62f, 0.3f),new Point2D(0.1f, 0.02f) ));
		
		
		maps[2].addElement(new StandarWall(new Point2D(0.4f, 0.3f),new Point2D(0.02f, 0.4f) ));
		maps[2].addElement(new StandarWall(new Point2D(0.4f, 0.8f),new Point2D(0.02f, 0.2f) ));
		
		maps[2].addElement(new StandarWall(new Point2D(0.32f, 0.7f),new Point2D(0.1f, 0.02f) ));
		maps[2].addElement(new StandarWall(new Point2D(0.32f, 0.7f),new Point2D(0.02f, 0.1f) ));
		maps[2].addElement(new StandarWall(new Point2D(0.32f, 0.8f),new Point2D(0.1f, 0.02f) ));
		
		maps[2].addElement(new StandarWall(new Point2D(0.6f, 0.3f),new Point2D(0.02f, 0.7f) ));
		
		maps[2].addElement(new MapLoader(new Point2D(0.7f, 0.1f),new Point2D(0.02f, 0.1f),this,1));
		maps[2].addElement(new MapLoader(new Point2D(0.42f, 0.98),new Point2D(0.18f, 0.02f),this,1));

	}
	
	public void changeMap(int id){
		maps[currentMap].hide();
		maps[id].show();
		player.setPos(maps[id].getPlayerPos());
		currentMap = id;
	}
	
	
}
