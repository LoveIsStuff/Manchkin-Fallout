package guis;

import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

public class GuiPicker {
	private static List<GuiTexture> guis;
	private static boolean enable = true;
	private static Vector2f clickPosition = new Vector2f(0, 0);

	public static void loadGuis(List<GuiTexture> guis) {
		GuiPicker.guis = guis;
	}

	public static void checkMouseHover(float x, float y) {
		for (int i = guis.size() - 1; i >= 0; i--) {
			if (x >= guis.get(i).getPosition().x - guis.get(i).getScale().x
					&& x <= (guis.get(i).getPosition().x + guis.get(i).getScale().x)
					&& y >= guis.get(i).getPosition().y - guis.get(i).getScale().y
					&& y <= (guis.get(i).getPosition().y + guis.get(i).getScale().y)) {
				if (checkMouseState()) {
					if (enable || guis.get(i).isChosen()) {
						if (!guis.get(i).isChosen()) {
							guis.get(i).setChosen(true);
							clickPosition.x = x;
							clickPosition.y = y;
						}
						enable = false;
						Vector2f resultVector = new Vector2f(guis.get(i).getPosition().x + x - clickPosition.x,
								guis.get(i).getPosition().y + y - clickPosition.y);
						guis.get(i).setPosition(resultVector);
						clickPosition.x = x;
						clickPosition.y = y;
					}
				} else {
					guis.get(i).setChosen(false);
				}
			}
		}
	}

	private static boolean checkMouseState() {
		if (Mouse.isButtonDown(0)) {
			return true;
		} else {
			enable = true;
			return false;
		}
	}

	public static boolean isEnable() {
		return enable;
	}
}
