package card;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import guis.GuiTexture;

public class CardGui extends GuiTexture {
	private Card card;

	public CardGui(int texture, Vector2f position, Vector2f scale, Vector3f rotation) {
		super(texture, position, scale, rotation);
	}

	public void setRotation(Vector3f rotation) {
		super.setRotation(rotation);
	}

	public void setPosition(Vector2f position) {
		super.setPosition(position);
	}

	public void setCard(Card card){
		this.card = card;
	}

	public Card getCard(){
		return card;
	}
}
