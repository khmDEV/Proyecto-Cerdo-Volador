package es.pcv.game.elements.enemies;

import java.awt.Color;
import java.awt.Graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import es.pcv.core.render.ObjectIcon;
import es.pcv.core.render.Point2D;
import es.pcv.core.render.auxiliar.Helper3D;
import es.pcv.core.updater.elements.Walker;
import es.pcv.game.configuration.Config;
import es.pcv.game.elements.items.drops.DropEnemy;
import es.pcv.game.elements.player.Player;

public abstract class Enemy extends Walker{
	Player pl;
	public static final DropEnemy drop=new DropEnemy();
	protected Color c = new Color(1, 0, 0);
	protected int TEXTURE = 5;
	protected ObjectIcon iconBad = new ObjectIcon(Config.RESOURCES_PATH + "/icons/zombie.png", 1, 1);

	public Enemy(Point2D p,Point2D v,Point2D s,int l, int d, Player pl) {		
		super(p, v, s, l, d);
		this.pl=pl;
	}
	
	public boolean kill(){
		boolean r=super.kill();
		if (!r) {
			drop.spawnDrops(getPos());
		}
		return r;
	}
	

	public void draw(Graphics g) {
		g.drawImage(iconBad.getImage(0), getX(), getY(), getSizeX(), getSizeY(), null);
	}

	public void draw3d(GL2 gl, GLU glu, GLUquadric quadric) {
		Helper3D.drawCilinder(gl, glu, quadric, getPos(), 0.06f, 0.06f, 0, .11f, null,TEXTURE);
	}
	
}
