package es.pcv.game.elements.scene;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import es.pcv.core.render.Point2D;
import es.pcv.core.render.Render;
import es.pcv.core.render.auxiliar.PolygonHelper;
import es.pcv.core.updater.Updater;
import es.pcv.core.updater.elements.Element;
import es.pcv.core.updater.elements.PolygonCollision;
import es.pcv.game.configuration.Config;
import es.pcv.game.elements.items.drops.DropMap;
import es.pcv.game.elements.weapons.WeaponEntity;

public class Map {

	List<Element> elements;
	
	Updater updater;
	Render render;
	
	Integer nextMaps[];
	Point2D playerPos[];
	
	protected DropMap drop=new DropMap();
	
	public Map(List<Element> l,Updater u,Render r,Point2D[] pos,Integer[] nextMaps){
		elements = l;
		updater = u;
		render = r;
		playerPos = pos;
		this.nextMaps=nextMaps;
	}
	
	public Map(Updater u,Render r,Point2D[] pos,Integer[] nextMaps){
		elements = new ArrayList<Element>();
		updater = u;
		render = r;
		playerPos = pos;
		this.nextMaps=nextMaps;
	}
	
	
	public void addElement(Element e){
		elements.add(e);
	}
	
	public void showElement(Element e){
		updater.add(e);
		render.add(e);
	}
	
	public void removeElement(Element e){
		updater.remove(e);
		render.remove(e);
	}
	public void show(){
		Iterator<Element> i = elements.iterator();
		while(i.hasNext()){
			showElement(i.next());
		}
	}
	public void hide(){
		Iterator<Element> i = elements.iterator();
		List<Element> to_remove = new LinkedList<Element>();
		while(i.hasNext()){
			Element e=i.next();
			removeElement(e);
			if (e instanceof WeaponEntity) { // ANOTHER SOLUTION?
				e.kill();
			}
			if (e.isDead()) {
				to_remove.add(e);
			}
		}
		for (Element element : to_remove) {
			elements.remove(element);
		}
	}
	
	public Point2D getPlayerPos(int n){
		return playerPos[n];
	}
	public void list(){
		Iterator<Element> i = elements.iterator();
		System.out.println("------------------------------------------");
		while(i.hasNext()){
			System.out.println(i.next());
		}
	}
	public void rotate(double rot) {
		
		for (int i=0; i<playerPos.length;i++){
			if(playerPos[i]!=null){
				Polygon pl =PolygonHelper.getRectangle(playerPos[i],new Point2D(0,0));
				Polygon pl2=PolygonHelper.rotatePolygon(pl,new Point2D(Config.startX + 
						Config.scale.getX() / 2, Config.startY + Config.scale.getY() / 2),rot);
				playerPos[i]= new Point2D (pl2.getBounds2D().getX(),pl2.getBounds2D().getY());
			}
		}
		for (Element element : elements) {
			if (element instanceof PolygonCollision) {
				((PolygonCollision) element).rotate(rot,
						new Point2D(Config.startX + Config.scale.getX() / 2, Config.startY + Config.scale.getY() / 2));
			}
		}
	}
	
	public void invert() {
		int x = (int) (Config.startX + Config.scale.getX() / 2);
		for (int i=0; i<playerPos.length;i++){
			if(playerPos[i]!=null){
				if(x>playerPos[i].getX()){
					playerPos[i].setX(x+(x-playerPos[i].getX()));
				}else{
					playerPos[i].setX(x-(playerPos[i].getX()-x));
				}
			}
		}
		for (Element element : elements) {
			if (element instanceof PolygonCollision) {
				((PolygonCollision) element).invert(x);
			}
		}
	}

	public void clearRoom() {
		System.out.println("Spawneando");
		drop.spawnDrops(new Point2D(Config.startX + Config.scale.getX() / 2, Config.startY + Config.scale.getY() / 2));
	}
}
