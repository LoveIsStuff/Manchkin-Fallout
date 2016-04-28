package card;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import models.TexturedModel;

public class Card extends Entity {
	private String name;

	public Card(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, String name) {
		super(model, position, rotX, rotY, rotZ, scale);
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
