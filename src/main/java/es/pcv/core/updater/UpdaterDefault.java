package es.pcv.core.updater;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import es.pcv.core.updater.elements.Element;

public class UpdaterDefault extends Updater {
	List<Element> elements=new LinkedList<Element>();

	@Override
	public void add(Element e) {
		elements.add(e);
	}

	@Override
	public void remove(Element e) {
		elements.remove(e);
	}

	@Override
	public void update() {
		List<Element> tests=Arrays.asList(elements.toArray(new Element[elements.size()]));
		List<Element> toRemove=new LinkedList<Element>();
		for (Element e : elements) {
			e.update();
			for (Element element : tests) {
				if (element!=e&&e.isCollision(element)) {
					e.collision(element);
					element.collision(e);
				}
			}
			if (e.isDead()) {
				toRemove.add(e);
			}
		}
		for (Element element : toRemove) {
			elements.remove(element);
		}
	}

}
