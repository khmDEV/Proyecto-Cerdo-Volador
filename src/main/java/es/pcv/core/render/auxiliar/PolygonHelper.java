package es.pcv.core.render.auxiliar;

import java.awt.Polygon;

import es.pcv.core.render.Point2D;
import es.pcv.game.configuration.Config;

public class PolygonHelper {
	
	public static Polygon createRectangle(Point2D position,Point2D size){
		return new Polygon(new int[] { Math.round((position.getX() + size.getX()) * Config.size.getX()),
				Math.round((position.getX() - size.getX()) * Config.size.getX()),
				Math.round((position.getX() - size.getX()) * Config.size.getX()),
				Math.round((position.getX() + size.getX()) * Config.size.getX()) },
		new int[] { Math.round((position.getY() - size.getY()) * Config.size.getY()),
				Math.round((position.getY() - size.getY()) * Config.size.getY()),
				Math.round((position.getY() + size.getY()) * Config.size.getY()),
				Math.round((position.getY() + size.getY()) * Config.size.getY()) },
		4);
	}

}
