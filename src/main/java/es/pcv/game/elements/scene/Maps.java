package es.pcv.game.elements.scene;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import es.pcv.core.render.Point2D;
import es.pcv.core.render.Render;
import es.pcv.core.updater.Updater;
import es.pcv.game.elements.enemies.EnemyMelee;
import es.pcv.game.elements.objects.Something;
import es.pcv.game.elements.player.Player;

public class Maps {
	public Updater updater;
	public Render render;
	private List<Map> maps;
	private int currentMap;
	private Player player;
	
	public Maps(Updater u, Render r,Player p){
		updater=u;
		render=r;
		player = p;
		currentMap=0;
		maps=new ArrayList<Map>();
		
	
		File f;
		Scanner s;
		
		
		
		Integer[] nextPos;
		
		Integer[] nextMap;
		
		int map;
		int rot;
		try{
			for(int i=0;i<24;i++){
				nextPos = new Integer[3];
				nextMap = new Integer[3];
				if(i<10){
					f=new File("maps/map0"+i+".txt");
				}else{
					f=new File("maps/map"+i+".txt");
				}
				
				s=new Scanner(f);
				
				s.nextLine();
				map=Integer.parseInt(s.nextLine());
				rot=Integer.parseInt(s.nextLine());
				
				
				s.nextLine();
				String res=s.nextLine();
				int j=0;
				while(!res.equals("Enemigos")){
					nextMap[j]=Integer.parseInt(res.split(" ")[0]);
					nextPos[j]=Integer.parseInt(res.split(" ")[1]);
					res=s.nextLine();
					j++;
				}
				
				
				Map m = null;
				switch(map){
					case 0:
							m = map0(nextMap,nextPos);
						break;
					case 1:
							m = map1(nextMap,nextPos);
						break;
					case 2:
							m = map2(nextMap,nextPos);
						break;
					case 3:
							m = map3(nextMap,nextPos);
						break;
					case 4:
							m = map4(nextMap,nextPos);
							break;
					case 5:
							m = map5(nextMap,nextPos);
							break;
					default:
						break;
				}
				if(i==0){
					m.show();
					player.setPos(new Point2D(0.5f,0.9f).setAbsolutePosition());
				}
				maps.add(m);
			}
		}catch(FileNotFoundException e){
			System.out.println(e.getMessage());
		}
		
	}
	
	public Map map0(Integer[] nextMaps,Integer nextPos[]){
		
		Point2D[] playerPos = new Point2D[3];
		
		//playerPos[0]=new Point2D(0.5f,0.9f).setAbsolutePosition();
		playerPos[0]=new Point2D(0.5f,0.05f).setAbsolutePosition();
		
		Map m = new Map(updater,render,playerPos,nextMaps);
		//maps[0].addElement(new StandarWall(new Point2D(0.6f, 0.3f),new Point2D(0.02f, 0.6f) ));
		
		m.addElement(new StandarWall(new Point2D(0, 0),new Point2D(0.02f, 1)));
		m.addElement(new StandarWall(new Point2D(0, 0),new Point2D(0.4f, 0.02f)));
		m.addElement(new StandarWall(new Point2D(0.6f, 0),new Point2D(0.4f, 0.02f)));
		m.addElement(new StandarWall(new Point2D(0, 0.98f),new Point2D(1, 0.02f)));
		m.addElement(new StandarWall(new Point2D(0.98f, 0),new Point2D(0.02f, 1)));
		
		
		m.addElement(new EnemyMelee(new Point2D(0.7f, 0.5f),player));
		
		
		m.addElement(new Something(new Point2D(0.3f, 0.2f),new Point2D(0.1f, 0.2f),"mesa.png",false));
		
		m.addElement(new MapLoader(new Point2D(0.4f, 0),new Point2D(0.2f, 0.02f),this,nextMaps[0],nextPos[0]));

		
		//player.setPos(new Point2D(0.5f,0.5f).setAbsolutePosition());
		return m;
	}
	
	public Map map1(Integer[] nextMaps,Integer[] nextPos){
		
		Point2D[] playerPos = new Point2D[3];
		
		playerPos[0]=new Point2D(0.5f,0.9f).setAbsolutePosition();
		playerPos[1]=new Point2D(0.5f,0.05f).setAbsolutePosition();
		
		Map m = new Map(updater,render,playerPos,nextMaps);
		
		m.addElement(new StandarWall(new Point2D(0, 0),new Point2D(0.02f, 1) ));
		m.addElement(new StandarWall(new Point2D(0, 0),new Point2D(0.4f, 0.02f)));
		m.addElement(new StandarWall(new Point2D(0.6f, 0),new Point2D(0.4f, 0.02f)));
		m.addElement(new StandarWall(new Point2D(0, 0.98f),new Point2D(0.4f, 0.02f)));
		m.addElement(new StandarWall(new Point2D(0.6f, 0.98f),new Point2D(0.4f, 0.02f)));
		m.addElement(new StandarWall(new Point2D(0.98f, 0),new Point2D(0.02f, 1)));
		
		
		//m.addElement(new EnemyMelee(new Point2D(0.2f, 0.5f),player));
		//m.addElement(new EnemyMelee(new Point2D(0.5f, 0.5f),player));
		//m.addElement(new EnemyMelee(new Point2D(0.8f, 0.5f),player));
		
		m.addElement(new MapLoader(new Point2D(0.4f, 0.98f),new Point2D(0.2f, 0.02f),this,nextMaps[0],nextPos[0]));
		m.addElement(new MapLoader(new Point2D(0.4f, 0),new Point2D(0.2f, 0.02f),this,nextMaps[1],nextPos[1]));
		
		return m;
	}
	
	public Map map2(Integer[] nextMaps,Integer[] nextPos){
		
		Point2D[] playerPos = new Point2D[3];
		
		playerPos[0]=new Point2D(0.5f,0.9f).setAbsolutePosition();
		playerPos[1]=new Point2D(0.6f,0.15f).setAbsolutePosition();
		
		Map m = new Map(updater,render,playerPos,nextMaps);
		
		m.addElement(new StandarWall(new Point2D(0.3f, 0),new Point2D(0.02f, 0.3f) ));
		m.addElement(new StandarWall(new Point2D(0.3f, 0),new Point2D(0.4f, 0.02f)));
		
		m.addElement(new StandarWall(new Point2D(0.7f, 0),new Point2D(0.02f, 0.1f) ));
		m.addElement(new StandarWall(new Point2D(0.7f, 0.2f),new Point2D(0.02f, 0.1f) ));
		
		m.addElement(new StandarWall(new Point2D(0.3f, 0.3f),new Point2D(0.1f, 0.02f) ));
		m.addElement(new StandarWall(new Point2D(0.62f, 0.3f),new Point2D(0.1f, 0.02f) ));
		
		
		m.addElement(new StandarWall(new Point2D(0.4f, 0.3f),new Point2D(0.02f, 0.4f) ));
		m.addElement(new StandarWall(new Point2D(0.4f, 0.8f),new Point2D(0.02f, 0.2f) ));
		
		m.addElement(new StandarWall(new Point2D(0.32f, 0.7f),new Point2D(0.1f, 0.02f) ));
		m.addElement(new StandarWall(new Point2D(0.32f, 0.7f),new Point2D(0.02f, 0.1f) ));
		m.addElement(new StandarWall(new Point2D(0.32f, 0.8f),new Point2D(0.1f, 0.02f) ));
		
		m.addElement(new StandarWall(new Point2D(0.6f, 0.3f),new Point2D(0.02f, 0.7f) ));
		
		
		m.addElement(new MapLoader(new Point2D(0.42f, 0.98),new Point2D(0.18f, 0.02f),this,nextMaps[0],nextPos[0]));
		m.addElement(new MapLoader(new Point2D(0.7f, 0.1f),new Point2D(0.02f, 0.1f),this,nextMaps[1],nextPos[1]));

		return m;
	}
	
	public Map map3(Integer[] nextMaps,Integer[] nextPos){
		
		Point2D[] playerPos = new Point2D[3];
		
		playerPos[0]=new Point2D(0.5f,0.9f).setAbsolutePosition();
		playerPos[1]=new Point2D(0.8f,0.15f).setAbsolutePosition();
		playerPos[2]=new Point2D(0.2f,0.15f).setAbsolutePosition();
		Map m = new Map(updater,render,playerPos,nextMaps);
		
		//m.addElement(new StandarWall(new Point2D(0, 0),new Point2D(0.02f, 0.03f)));
		//m.addElement(new StandarWall(new Point2D(1, 0),new Point2D(0.02f, 0.03f)));
		//m.addElement(new StandarWall(new Point2D(0, 1),new Point2D(0.02f, 0.03f)));
		//m.addElement(new StandarWall(new Point2D(1, 1),new Point2D(0.02f, 0.03f)));
		
		
		m.addElement(new StandarWall(new Point2D(0.25f,0.98),new Point2D(0.15f, 0.02f)));
		m.addElement(new StandarWall(new Point2D(0.60f,0.98),new Point2D(0.15f, 0.02f)));
		
		m.addElement(new StandarWall(new Point2D(0.23f, 0.5f),new Point2D(0.02f, 0.5f)));
		m.addElement(new StandarWall(new Point2D(0.75f, 0.5f),new Point2D(0.02f, 0.5f)));
		
		
		m.addElement(new StandarWall(new Point2D(0, 0.48f),new Point2D(0.25f, 0.02f)));
		m.addElement(new StandarWall(new Point2D(0.75, 0.48f),new Point2D(0.25f, 0.02f)));
		
		m.addElement(new StandarWall(new Point2D(0, 0.35f),new Point2D(0.02f, 0.15f)));
		m.addElement(new StandarWall(new Point2D(0.98, 0.35f),new Point2D(0.02f, 0.15f)));
		
		m.addElement(new StandarWall(new Point2D(0, 0),new Point2D(0.02f, 0.15f)));
		m.addElement(new StandarWall(new Point2D(0.98, 0),new Point2D(0.02f, 0.15f)));
		
		
		m.addElement(new StandarWall(new Point2D(0,0),new Point2D(1, 0.02f)));
		
		
		//m.addElement(new StandarWall(new Point2D(0.6f,0),new Point2D(0.4f, 0.02f)));
		//m.addElement(new StandarWall(new Point2D(0.75f, 0.5f),new Point2D(0.02f, 0.5f)));
		
		m.addElement(new MapLoader(new Point2D(0.4f,0.98),new Point2D(0.2f, 0.02f),this,nextMaps[0],nextPos[0]));
		
		m.addElement(new MapLoader(new Point2D(0.98,0.15),new Point2D(0.02f, 0.2f),this,nextMaps[1],nextPos[1]));
		m.addElement(new MapLoader(new Point2D(0,0.15),new Point2D(0.02f, 0.2f),this,nextMaps[2],nextPos[2]));
		return m;
	}
	
	public Map map4(Integer[] nextMaps,Integer nextPos[]){
		
		Point2D[] playerPos = new Point2D[3];
		
		//playerPos[0]=new Point2D(0.5f,0.9f).setAbsolutePosition();
		playerPos[0]=new Point2D(0.7f,0.9f).setAbsolutePosition();
		playerPos[1]=new Point2D(0.3f,0.9f).setAbsolutePosition();
		
		Map m = new Map(updater,render,playerPos,nextMaps);
		//maps[0].addElement(new StandarWall(new Point2D(0.6f, 0.3f),new Point2D(0.02f, 0.6f) ));
		
		m.addElement(new StandarWall(new Point2D(0, 0.5f),new Point2D(1, 0.02f)));
		
		m.addElement(new StandarWall(new Point2D(0, 0.5f),new Point2D(0.02f, 0.5f)));
		m.addElement(new StandarWall(new Point2D(0.98f, 0.5f),new Point2D(0.02f, 0.5f)));
		
		m.addElement(new StandarWall(new Point2D(0, 0.98f),new Point2D(0.2f, 0.02f)));
		m.addElement(new StandarWall(new Point2D(0.4f, 0.98f),new Point2D(0.2f, 0.02f)));
		m.addElement(new StandarWall(new Point2D(0.8f, 0.98f),new Point2D(0.2f, 0.02f)));
		
		
		
		//m.addElement(new EnemyMelee(new Point2D(0.7f, 0.5f),player));
		
		
		//m.addElement(new Something(new Point2D(0.3f, 0.2f),new Point2D(0.1f, 0.2f),"mesa.png",false));
		
		m.addElement(new MapLoader(new Point2D(0.6f, 0.98f),new Point2D(0.2f, 0.02f),this,nextMaps[0],nextPos[0]));
		m.addElement(new MapLoader(new Point2D(0.2f, 0.98f),new Point2D(0.2f, 0.02f),this,nextMaps[1],nextPos[1]));
		
		//player.setPos(new Point2D(0.5f,0.5f).setAbsolutePosition());
		return m;
	}
	
	public Map map5(Integer[] nextMaps,Integer[] nextPos){
		
		Point2D[] playerPos = new Point2D[3];
		
		playerPos[0]=new Point2D(0.5f,0.9f).setAbsolutePosition();
		playerPos[1]=new Point2D(0.5f,0.05f).setAbsolutePosition();
		
		Map m = new Map(updater,render,playerPos,nextMaps);
		
		m.addElement(new StandarWall(new Point2D(0.4f, 0),new Point2D(0.02f,1)));
		
		//m.addElement(new StandarWall(new Point2D(0, 0),new Point2D(0.4f, 0.02f)));
		//m.addElement(new StandarWall(new Point2D(0.6f, 0),new Point2D(0.4f, 0.02f)));
		//m.addElement(new StandarWall(new Point2D(0, 0.98f),new Point2D(0.4f, 0.02f)));
		//m.addElement(new StandarWall(new Point2D(0.6f, 0.98f),new Point2D(0.4f, 0.02f)));
		m.addElement(new StandarWall(new Point2D(0.58f, 0),new Point2D(0.02f, 1)));
		
		
		//m.addElement(new EnemyMelee(new Point2D(0.2f, 0.5f),player));
		//m.addElement(new EnemyMelee(new Point2D(0.5f, 0.5f),player));
		//m.addElement(new EnemyMelee(new Point2D(0.8f, 0.5f),player));
		
		m.addElement(new MapLoader(new Point2D(0.4f, 0.98f),new Point2D(0.2f, 0.02f),this,nextMaps[0],nextPos[0]));
		m.addElement(new MapLoader(new Point2D(0.4f, 0),new Point2D(0.2f, 0.02f),this,nextMaps[1],nextPos[1]));
		
		return m;
	}
	
	
	
	public void changeMap(int id,int n){
		maps.get(currentMap).hide();
		Map m = maps.get(id);
		m.show();
		player.setPos(m.getPlayerPos(n));
		currentMap = id;
	}
	
	
}
