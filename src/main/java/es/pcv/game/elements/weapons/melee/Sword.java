package es.pcv.game.elements.weapons.melee;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Walker;

public class Sword extends Melee {

	public Sword(Walker w) {
		super(w,new Point2D(0.01f,0.05), 100, 3);
	}

}