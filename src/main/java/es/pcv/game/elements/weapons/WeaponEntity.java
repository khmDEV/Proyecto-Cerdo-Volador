package es.pcv.game.elements.weapons;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.LiveEntity;

public abstract class WeaponEntity extends LiveEntity{

	public WeaponEntity(Point2D p, Point2D v, Point2D s, int l, int d) {
		super(p, v, s, l, d);
	}

}
