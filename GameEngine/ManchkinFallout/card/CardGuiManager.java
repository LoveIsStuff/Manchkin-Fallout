package card;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import guis.GuiTexture;
import renderEngine.Loader;

public class CardGuiManager {
	private List<GuiTexture> cardsInHand = new ArrayList<GuiTexture>();
	private Loader loader;
	private int countOfCards;

	public CardGuiManager(Loader loader) {
		this.loader = loader;
		countOfCards = 0;
	}

	public void addCard(Card card) {
		if (!incCount()) {
			System.out.println("Can't have more");
			return;
		}
		CardGui cardToHand = new CardGui(loader.loadTexture(card.getName()), new Vector2f(0, 0),
				new Vector2f(0.10f, 0.26f), new Vector3f(0, 0, 0));
		cardsInHand.add(cardToHand);
		sortCards();
	}

	public void deleteCard(GuiTexture card) {
		if (!decCount()) {
			System.out.println("Can't remove");
			return;
		}
		cardsInHand.remove(card);
		// sortCards();
	}

	public void sortCards() {
		for (int i = 0; i < countOfCards; i++) {
			if(cardsInHand.get(i).getPosition().y >= 0.05f){
				deleteCard(cardsInHand.get(i));
			}
		}

		switch (countOfCards) {
		case 0:
			return;
		case 1:
			cardsInHand.get(0).setPosition(new Vector2f(0.5f, -0.5f));
			cardsInHand.get(0).setRotation(new Vector3f(0, 0, 0));
			break;
		case 2:
			cardsInHand.get(0).setPosition(new Vector2f(0.45f, -0.52f));
			cardsInHand.get(0).setRotation(new Vector3f(0, 0, 1));
			cardsInHand.get(1).setPosition(new Vector2f(0.55f, -0.52f));
			cardsInHand.get(1).setRotation(new Vector3f(0, 0, -1));
			break;
		case 3:
			cardsInHand.get(0).setPosition(new Vector2f(0.40f, -0.52f));
			cardsInHand.get(0).setRotation(new Vector3f(0, 0, 3));
			cardsInHand.get(1).setPosition(new Vector2f(0.50f, -0.5f));
			cardsInHand.get(1).setRotation(new Vector3f(0, 0, 0));
			cardsInHand.get(2).setPosition(new Vector2f(0.60f, -0.52f));
			cardsInHand.get(2).setRotation(new Vector3f(0, 0, -3));
			break;
		case 4:
			cardsInHand.get(0).setPosition(new Vector2f(0.35f, -0.52f));
			cardsInHand.get(0).setRotation(new Vector3f(0, 0, 3));
			cardsInHand.get(1).setPosition(new Vector2f(0.45f, -0.5f));
			cardsInHand.get(1).setRotation(new Vector3f(0, 0, 1));
			cardsInHand.get(2).setPosition(new Vector2f(0.55f, -0.5f));
			cardsInHand.get(2).setRotation(new Vector3f(0, 0, -1));
			cardsInHand.get(3).setPosition(new Vector2f(0.65f, -0.52f));
			cardsInHand.get(3).setRotation(new Vector3f(0, 0, -3));
			break;
		case 5:
			cardsInHand.get(0).setPosition(new Vector2f(0.30f, -0.54f));
			cardsInHand.get(0).setRotation(new Vector3f(0, 0, 3));
			cardsInHand.get(1).setPosition(new Vector2f(0.40f, -0.52f));
			cardsInHand.get(1).setRotation(new Vector3f(0, 0, 1));
			cardsInHand.get(2).setPosition(new Vector2f(0.50f, -0.5f));
			cardsInHand.get(2).setRotation(new Vector3f(0, 0, 0));
			cardsInHand.get(3).setPosition(new Vector2f(0.60f, -0.52f));
			cardsInHand.get(3).setRotation(new Vector3f(0, 0, -1));
			cardsInHand.get(4).setPosition(new Vector2f(0.70f, -0.54f));
			cardsInHand.get(4).setRotation(new Vector3f(0, 0, -3));
			break;
		}
	}

	private boolean incCount() {
		if (countOfCards == 5) {
			return false;
		} else {
			countOfCards++;
			return true;
		}
	}

	private boolean decCount() {
		if (countOfCards == 0) {
			return false;
		} else {
			countOfCards--;
			return true;
		}
	}

	public List<GuiTexture> getCards() {
		return cardsInHand;
	}
}
