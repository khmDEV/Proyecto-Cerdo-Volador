package es.pcv.game.elements.items.drops;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import es.pcv.core.render.Point2D;
import es.pcv.game.elements.items.Item;
import es.pcv.game.elements.items.ItemAmmo;
import es.pcv.game.elements.items.ItemHeal;

public class DropEnemy extends Drops {
	public static final int MIN_N_ANOTHER_DROPS=1;
	public static final int MAX_N_ANOTHER_DROPS=3;

	@SuppressWarnings("serial")
	public static final HashMap<Item, Double>ANOTHER_DROPS=new HashMap<Item, Double>(){{
	    put(new ItemHeal(new Point2D(0, 0), 5),0.1);
	    put(new ItemHeal(new Point2D(0, 0), 10),0.05);
	    put(new ItemHeal(new Point2D(0, 0), 15),0.025);
	    put(new ItemHeal(new Point2D(0, 0), 25),0.0125);

	    put(new ItemAmmo(new Point2D(0, 0), 5),0.1);
	    put(new ItemAmmo(new Point2D(0, 0), 10),0.05);
	    put(new ItemAmmo(new Point2D(0, 0), 20),0.025);
	    put(new ItemAmmo(new Point2D(0, 0), 25),0.0125);
	}};

	public List<Item> getDrops(Point2D p) {
		List<Item> r=new LinkedList<Item>();
		r.addAll(getRandom(p,ANOTHER_DROPS, MAX_N_ANOTHER_DROPS, MIN_N_ANOTHER_DROPS));
		return r;
	}

}
