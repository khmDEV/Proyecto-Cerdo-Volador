package es.pcv.game.elements.enemies;

import java.awt.Polygon;

import es.pcv.core.render.ObjectIcon;
import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Collisionable;
import es.pcv.game.configuration.Config;
import es.pcv.game.elements.player.Player;

public class EnemyMeleeFast extends EnemyMelee {
	
	public EnemyMeleeFast(Point2D position,Player pl) {
		super(position,pl,new Point2D(0.2f, 0.2f),50);
		icon= new ObjectIcon(Config.RESOURCES_PATH + "/icons/enemy.png", 4, 4);
	}
	
	public EnemyMeleeFast(Point2D position,Player pl,int live) {
		super(position,pl,new Point2D(0.2f, 0.2f),live);
		icon= new ObjectIcon(Config.RESOURCES_PATH + "/icons/enemy.png", 4, 4);
	}


}
