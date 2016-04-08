package es.pcv.game.elements.scene;

import java.awt.Color;

import es.pcv.core.render.Point2D;
import es.pcv.core.render.Render;
import es.pcv.core.render.auxiliar.PolygonHelper;
import es.pcv.core.updater.Updater;
import es.pcv.core.updater.elements.Element;

public class Maps {
	public Updater updater;
	public Render render;
	
	public Maps(Updater u, Render r){
		updater=u;
		render=r;
	}
	public void addElement(Element e){
		updater.add(e);
		render.add(e);
	}
	
	public void createWall(float posX,float posY,float sizeX,float sizeY,Color c){
		addElement(new StandarWall(new Point2D(posX, posY),new Point2D(sizeX, sizeY), c));
	}
	

	public void map1(){
		//izq
		createWall(0,0,0.02f, 1, new Color(255, 0, 0));
		//superior
		createWall(0,0,0.4f, 0.02f, new Color(255, 0, 0));
		createWall(0.6f,0,0.4f, 0.02f, new Color(255, 0, 0));
		//inf
		createWall(0,0.98f,0.4f,0.02f, new Color(255, 0, 0));
		createWall(0.6f,0.98f,0.4f,0.02f, new Color(255, 0, 0));
		//der
		createWall(0.98f,0,0.02f, 1, new Color(255, 0, 0));
	}
	
	
}
