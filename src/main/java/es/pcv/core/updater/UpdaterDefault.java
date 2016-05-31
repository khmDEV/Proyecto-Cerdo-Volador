package es.pcv.core.updater;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import es.pcv.core.updater.elements.Element;
import es.pcv.game.Game;
import es.pcv.game.elements.enemies.Enemy;
import es.pcv.game.elements.scene.MapLoader;

public class UpdaterDefault extends Updater {
	List<Element> elements = new LinkedList<Element>();

	@Override
	public synchronized void add(Element e) {
		elements.add(e);
	}

	@Override
	public synchronized void remove(Element e) {
		elements.remove(e);
	}

	public int last_enemies = 0;

	private boolean restart = false;
	@Override
	public synchronized void update() {
		List<Element> use = Arrays.asList(elements.toArray(new Element[elements.size()]));
		List<Element> tests = Arrays.asList(elements.toArray(new Element[elements.size()]));
		List<Element> toRemove = new LinkedList<Element>();
		int enemies = 0;
		for (Element e : use) {
			e.update();
			for (Element element : tests) {
				if (element != e && !element.isDead() && !e.isDead() && e.isCollision(element)) {
					e.collision(element);
				}
			}
			if (e.isDead()) {
				toRemove.add(e);
			} else {
				if (e instanceof Enemy) {
					enemies++;
				}
			}
		}
		for (Element element : toRemove) {
			remove(element);
		}
		if (enemies == 0) {
			if(restart){
				restart=false;
			}else{
				MapLoader.activate();
				if (last_enemies != 0) {
					Game.getGame().clearRoom();
				}
			}

		} else {
			MapLoader.desactivate();
		}
		System.out.println(enemies);
		System.out.println(last_enemies);
		System.out.println("-------");
		last_enemies = enemies;
	}

	public synchronized void clear() {
		elements.clear();
		restart = true;
	}

}
