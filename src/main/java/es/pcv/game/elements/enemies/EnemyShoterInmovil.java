package es.pcv.game.elements.enemies;

import es.pcv.core.render.ObjectIcon;
import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Collisionable;
import es.pcv.game.configuration.Config;
import es.pcv.game.elements.player.Player;
import es.pcv.game.elements.weapons.LaserGun;
import es.pcv.game.elements.weapons.Weapon;

public class EnemyShoterInmovil extends EnemyShoter{
	
	public EnemyShoterInmovil(Point2D position,Player pl,  Weapon wp) {		
		super(position, pl,new Point2D(0, 0),wp);
		icon= new ObjectIcon(Config.RESOURCES_PATH + "/icons/magicpot.png", 4, 4);
	}
	public EnemyShoterInmovil(Point2D position,Player pl) {		
		super(position, pl,new Point2D(0, 0));
		icon= new ObjectIcon(Config.RESOURCES_PATH + "/icons/magicpot.png", 4, 4);
	}
	
}