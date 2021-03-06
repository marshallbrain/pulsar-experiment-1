package ui.map;

import input.Keyboard;
import input.Mouse;
import ui.engine.UiElement;
import ui.engine.VectorGraphics;
import universe.Galaxy;

public class GalaxyUi implements UiElement {
	
	private Galaxy galaxy;

	public GalaxyUi(Galaxy g) {
		galaxy = g;
	}
	
	@Override
	public void render(VectorGraphics g) {
	}

	@Override
	public boolean action(Mouse m, Keyboard k) {
		return false;
	}

}
