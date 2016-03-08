package es.pcv.game.configuration;

import es.pcv.core.render.Point2D;

public class Config {
	
	public static long UPDATE_TICK=50;
	public static long RENDER_TICK=50;
	
	public static Point2D scale = new Point2D(10, 10);
	public static Point2D size = new Point2D(Math.round(50*scale.getX()),Math.round(50*scale.getY()));

}
