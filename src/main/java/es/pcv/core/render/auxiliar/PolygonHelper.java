package es.pcv.core.render.auxiliar;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;

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
	
	public static Polygon rotatePolygon(Polygon pl,Point2D center,double ang){
		return rotatePolygon(pl, new Point(Math.round(center.getX()), Math.round(center.getY())), ang);
	}

	public static Polygon rotatePolygon(Polygon pl,Point center,double ang){
		Polygon rec=new Polygon(pl.xpoints, pl.ypoints, pl.npoints);
		AffineTransform rotateTransform = new AffineTransform();
		rotateTransform.rotate(ang,center.getX(),center.getY());
		for(int i=0; i<rec.npoints; i++){
			  java.awt.geom.Point2D p = new java.awt.geom.Point2D.Double(rec.xpoints[i], rec.ypoints[i]);
			  rotateTransform.transform(p,p);
			  rec.xpoints[i]=(int) Math.round(p.getX());
			  rec.ypoints[i]=(int) Math.round(p.getY());
		}
		return rec;
	}
}
