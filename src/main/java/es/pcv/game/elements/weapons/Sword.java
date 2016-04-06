package es.pcv.game.elements.weapons;

import java.awt.Graphics;
import java.awt.Polygon;

import es.pcv.core.render.Point2D;
import es.pcv.core.render.auxiliar.PolygonHelper;
import es.pcv.core.updater.elements.Walker;

public class Sword extends Weapon{

	protected int durability;
	boolean vertical = true;
	
	public Sword(Walker w,Point2D p, int l, int d,int dur) {
		super(w,p, new Point2D(0,0), new Point2D(0.01f,-0.1), l, d);
		durability = dur;
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		//Point2D vel
		position = whoAttack.getPos().clone();
		float distance = whoAttack.getSize().getX();
		if(whoAttack.getDir() == 0){
			if(!vertical){
				moveSword();
			}
			position.setX(position.getX() - distance);
		}else if(whoAttack.getDir() == 1){
			if(vertical){
				moveSword();
			}
			position.setY(position.getY() - distance);
		}else if(whoAttack.getDir() == 2){
			if(vertical){
				moveSword();
			}
			position.setY(position.getY() + distance);
		}else{
			if(!vertical){
				moveSword();
			}
			position.setX(position.getX() + distance);
		}
		whoAttack.setCollisionBox(position, size);
		
	}
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		//ply = PolygonHelper.createRectangle(position, size);
		setCollisionBox(position,size);
		//g.setColor(c);
		g.drawPolygon(PolygonHelper.createRectangle(position,size));
	}

	private void moveSword(){
		float aux = size.getX();
		size.setX(size.getY());
		size.setY(aux);
		vertical=!vertical;
	}

	
}
