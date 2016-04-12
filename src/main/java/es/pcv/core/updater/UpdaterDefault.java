package es.pcv.core.updater;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Element;
import es.pcv.game.elements.enemies.Enemy;
import es.pcv.game.elements.player.Player;
import es.pcv.game.elements.scene.MapLoader;
import es.pcv.game.elements.scene.Maps;

public class UpdaterDefault extends Updater {
	List<Element> elements=new LinkedList<Element>();

	@Override
	public synchronized void add(Element e) {
		elements.add(e);
	}

	@Override
	public synchronized void remove(Element e) {
		elements.remove(e);
	}

	@Override
	public synchronized void update() {
		List<Element> use=Arrays.asList(elements.toArray(new Element[elements.size()]));
		List<Element> tests=Arrays.asList(elements.toArray(new Element[elements.size()]));
		List<Element> toRemove=new LinkedList<Element>();
		int enemies = 0;
		for (Element e : use) {
			e.update();
			for (Element element : tests) {
				if (element!=e&&e.isCollision(element)) {
					e.collision(element);
				}
			}
			if (e.isDead()) {
				toRemove.add(e);
			}else{
				if(e instanceof Enemy){
					enemies++;
				}
			}
		}
		for (Element element : toRemove) {
			remove(element);
		}
		if(enemies == 0 ){
			MapLoader.activate();
		}else{
			MapLoader.desactivate();
		}
	}
	
	public synchronized void clear(){
		elements.clear();
	}

}
