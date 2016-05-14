package es.pcv.core.render.auxiliar;

import java.awt.Polygon;

import es.pcv.core.render.Point2D;
import es.pcv.game.configuration.Config;

public class PolygonHelper {
	
	public static Polygon createRectangle(Point2D position,Point2D size){
		
		return new Polygon(new int[] { Math.round((position.getX() + size.getX()) * Config.scale.getX()) + Config.startX,
				Math.round((position.getX()) * Config.scale.getX()) + Config.startX,
				Math.round((position.getX()) * Config.scale.getX()) + Config.startX,
				Math.round((position.getX() + size.getX()) * Config.scale.getX())  + Config.startX},
		new int[] { Math.round((position.getY()) * Config.scale.getY()) + Config.startY,
				Math.round((position.getY()) * Config.scale.getY()) + Config.startY,
				Math.round((position.getY() + size.getY()) * Config.scale.getY()) + Config.startY,
				Math.round((position.getY() + size.getY()) * Config.scale.getY())  + Config.startY},
		4);
	}
	
	public static Polygon getRectangle(Point2D position,Point2D size){
		return new Polygon(new int[] { Math.round(position.getX() + size.getX()),
				Math.round(position.getX()),
				Math.round(position.getX()),
				Math.round(position.getX() + size.getX()) },
		new int[] { Math.round(position.getY()),
				Math.round(position.getY()),
				Math.round((position.getY() + size.getY())),
				Math.round((position.getY() + size.getY())) },
		4);
	}

}
