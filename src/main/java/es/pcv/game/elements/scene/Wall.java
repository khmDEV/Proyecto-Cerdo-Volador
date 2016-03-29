package es.pcv.game.elements.scene;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import es.pcv.core.updater.elements.Collisionable;
import es.pcv.core.updater.elements.Element;
import es.pcv.core.updater.elements.Obstacle;

public class Wall extends Obstacle implements Element{
	
	public Wall(Rectangle2D r){
		
	}


	public void update() {
		// TODO Auto-generated method stub
		
	}

	public void draw(Graphics g) {
		
	}

	public boolean isDead() {
		return false;
	}

	public boolean kill() {
		return false;
	}


	public void collision(Collisionable c) {
		
	}


}
