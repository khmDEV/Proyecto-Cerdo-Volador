package es.pcv.game.elements.scene;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import es.pcv.core.render.Point2D;
import es.pcv.core.render.Render;
import es.pcv.core.updater.Updater;
import es.pcv.core.updater.elements.Element;

public class Map {

	private List<Element> elements;
	
	Updater updater;
	Render render;
	
	Integer nextMaps[];
	Point2D playerPos[];
	

	
	
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
		while(i.hasNext()){
			removeElement(i.next());
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
}
