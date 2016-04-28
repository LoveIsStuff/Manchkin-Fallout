package card;

import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import guis.GuiTexture;

public class CardPicker {
	private static List<GuiTexture> guis;
	private static boolean enable = true;
	private static Vector2f clickPosition = new Vector2f(0, 0);
	private static GuiTexture bigVersion;

	public static void loadGuis(List<GuiTexture> guis) {
		CardPicker.guis = guis;
	}

	public static void checkMouseHover(float x, float y) {
		for (int i = guis.size() - 1; i >= 0; i--) {
			if (checkHowering(i, x, y) || guis.get(i).isChosen()) {
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
						guis.get(i).setRotation(new Vector3f(0, 0, 0));
						clickPosition.x = x;
						clickPosition.y = y;
					}
				} else {
					guis.get(i).setChosen(false);
					showBigVersion(i);
				}
				break;
			} else {
				removeBigVersion();
			}
		}
	}

	private static boolean checkHowering(int i, float x, float y) {
		if (x >= guis.get(i).getPosition().x - guis.get(i).getScale().x
				&& x <= guis.get(i).getPosition().x + guis.get(i).getScale().x
				&& y >= guis.get(i).getPosition().y - guis.get(i).getScale().y
				&& y <= guis.get(i).getPosition().y + guis.get(i).getScale().y) {
			return true;
		} else {
			return false;
		}
	}

	private static void showBigVersion(int index) {
		if(!guis.contains(bigVersion)){
			bigVersion = new GuiTexture(guis.get(index).getTexture(), new Vector2f(-0.65f, 0.3f),
				new Vector2f(0.2f, 0.5f), new Vector3f(0, 0, 0));
			guis.add(bigVersion);
		}
	}

	private static void removeBigVersion(){
		if(guis.contains(bigVersion))
			guis.remove(bigVersion);
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
