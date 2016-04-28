package guis;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class GuiTexture {
	private int texture;
	private Vector2f position;
	private Vector2f scale;
	private Vector3f rotation;
	private boolean chosen = false;

	public GuiTexture(int texture, Vector2f position, Vector2f scale, Vector3f rotation) {
		this.texture = texture;
		this.position = position;
		this.scale = scale;
		this.rotation = rotation;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public void setChosen(boolean chosen) {
		this.chosen = chosen;
	}

	public boolean isChosen() {
		return chosen;
	}

	public int getTexture() {
		return texture;
	}

	public Vector2f getPosition() {
		return position;
	}

	public Vector2f getScale() {
		return scale;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}
}
