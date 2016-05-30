package es.pcv.game.elements.items.drops;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import es.pcv.core.render.Point2D;
import es.pcv.game.Game;
import es.pcv.game.configuration.Config;
import es.pcv.game.elements.items.Item;

public abstract class Drops {
	
	public abstract List<Item> getDrops(Point2D p);
	public static final double R=0.1*Config.scale.getX();
	
	public static List<Item> getRandom(Point2D p,HashMap<Item, Double>drops,int max,int min){
		List<Item> r=new LinkedList<Item>();
		long normal=Math.round(Math.random()*(max-min))+min;
		Item i;
		for (int j = 0; j < normal; j++) {
			i=getRandom(p,drops);
			if (i!=null) {
				Point2D np=p.clone();
				np.addX((float) (Math.random()*(R*2)-R));np.addY((float) (Math.random()*(R*2)-R));
				i.setPos(np);
				r.add(i);
			}
		}
		return r;
	}
	
	public static Item getRandom(Point2D p, HashMap<Item, Double>drops){
		double max=0;
		for (double d:drops.values()) {
			max+=d;
		}
		double r=Math.random()*max;
		int c=0;
		for (Entry<Item, Double> e:drops.entrySet()) {
			if (c+e.getValue()>=r  && c<r) {
				Item i=e.getKey();
				return i.cloneItem();
			}else{
				c+=e.getValue();
			}
		}
		return drops.size()!=0?drops.keySet().iterator().next().cloneItem():null;
	}
	
	public void spawnDrops(Point2D p) {
		List<Item>its=getDrops(p);
		for (Item item : its) {
			Game.getGame().addElement(item);
		}		
	}
}
