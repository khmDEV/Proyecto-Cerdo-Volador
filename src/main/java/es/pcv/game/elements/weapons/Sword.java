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
		super(w,p, new Point2D(0,0), new Point2D(0.01f,0.05), l, d);
		durability = dur;
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
		setPos(whoAttack.getPos().clone());
		if(whoAttack.getDir() == 0){
			if(!vertical){
				moveSword();
			}
			addY(whoAttack.getSizeY());
		}else if(whoAttack.getDir() == 1){
			if(vertical){
				moveSword();
			}
			addX(-getSizeX());
		}else if(whoAttack.getDir() == 2){
			if(vertical){
				moveSword();
			}
			addX(whoAttack.getSizeX());
			addY(whoAttack.getSizeY());
		}else{
			if(!vertical){
				moveSword();
			}
			addY(-getSizeY());
			addX(whoAttack.getSizeX());
		}
		
	}
	@Override
	public void draw(Graphics g) {
		g.drawPolygon(getRectangle());
	}

	private void moveSword(){
		int aux = getSizeX();
		setSizeX(getSizeY());
		setSizeY(aux);
		vertical=!vertical;
	}

	
}
