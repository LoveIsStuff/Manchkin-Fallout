package mainApp;

import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import card.Card;
import card.CardGuiManager;
import card.CardPicker;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import guis.GuiRenderer;
import guis.GuiTexture;
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import toolbox.MousePicker;

/** This class uses to create start point of our game. */
public class MainGameLoop {
	/**
	 * Main function.
	 *
	 * @param args
	 *            command line.
	 */
	public static void main(String[] args) {

		DisplayManager.createDisplay(); // open display

		Loader loader = new Loader(); // obj, that load all stuff

		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy2"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));

		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));

		Terrain terrain = new Terrain(0, 0, loader, texturePack, blendMap, "heightmap");
		// Terrain terrain1 = new Terrain(0, 1, loader, texturePack, blendMap,
		// "heightmap");
		// Terrain terrain2 = new Terrain(1, 1, loader, texturePack, blendMap,
		// "heightmap");
		// Terrain terrain3 = new Terrain(1, 0, loader, texturePack, blendMap,
		// "heightmap");

		Light light = new Light(new Vector3f(800, 1000, 20), new Vector3f(1, 1, 1));

		MasterRenderer renderer = new MasterRenderer();

		ModelData bunnyData = OBJFileLoader.loadOBJ("person");
		RawModel bunnyModel = loader.loadToVAO(bunnyData.getVertices(), bunnyData.getTextureCoords(),
				bunnyData.getNormals(), bunnyData.getIndices());
		TexturedModel texturedBunny = new TexturedModel(bunnyModel,
				new ModelTexture(loader.loadTexture("playerTexture")));
		texturedBunny.getTexture().setReflectivity(1);
		texturedBunny.getTexture().setShineDamper(10);
		Player player = new Player(texturedBunny, new Vector3f(200, 100, 200), 0, 0, 0, 1);
		Camera camera = new Camera(player);
		camera.setPitch(60);

		ModelData cardPackData = OBJFileLoader.loadOBJ("CardPack1");
		RawModel cardPackModel = loader.loadToVAO(cardPackData.getVertices(), cardPackData.getTextureCoords(),
				cardPackData.getNormals(), cardPackData.getIndices());
		TexturedModel texturedPackCard1 = new TexturedModel(cardPackModel,
				new ModelTexture(loader.loadTexture("PackCardTexture")));
		texturedPackCard1.getTexture().setReflectivity(1);
		texturedPackCard1.getTexture().setShineDamper(10);
		Entity cardPack1 = new Entity(texturedPackCard1, new Vector3f(130,0,250), 0, 0, 0, 10);

		ModelData cardData = OBJFileLoader.loadOBJ("LoneWanderer");
		RawModel cardModel = loader.loadToVAO(cardData.getVertices(), cardData.getTextureCoords(),
				cardData.getNormals(), cardData.getIndices());
		TexturedModel texturedCard = new TexturedModel(cardModel,
				new ModelTexture(loader.loadTexture("LoneWandererTex1")));
		texturedCard.getTexture().setReflectivity(1);
		texturedCard.getTexture().setShineDamper(10);
		Card card = new Card(texturedCard, new Vector3f(0, 0, 0), 0, 0, 0, 1, "LoneWandererTex1Gui");

		TexturedModel texturedCard1 = new TexturedModel(cardModel,
				new ModelTexture(loader.loadTexture("LoneWandererTex2")));
		texturedCard1.getTexture().setReflectivity(1);
		texturedCard1.getTexture().setShineDamper(10);
		Card card1 = new Card(texturedCard, new Vector3f(0, 0, 0), 0, 0, 0, 1, "LoneWandererTex2Gui");

		TexturedModel texturedCard2 = new TexturedModel(cardModel,
				new ModelTexture(loader.loadTexture("LoneWandererTex3")));
		texturedCard2.getTexture().setReflectivity(1);
		texturedCard2.getTexture().setShineDamper(10);
		Card card2 = new Card(texturedCard, new Vector3f(0, 0, 0), 0, 0, 0, 1, "LoneWandererTex3Gui");

		CardGuiManager manager = new CardGuiManager(loader);
		manager.addCard(card);
		manager.addCard(card);
		manager.addCard(card1);
		manager.addCard(card2);
		manager.addCard(card2);
		List<GuiTexture> cards = manager.getCards();
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		camera.move();
		boolean flag = true;

		while (!Display.isCloseRequested()) // main game loop, that every frame
											// do smth
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
				if (flag) {
					Random rand = new Random();
					int cardI = rand.nextInt(3);
					switch (cardI) {
					case 0:
						manager.addCard(card);
						break;
					case 1:
						manager.addCard(card1);
						break;
					case 2:
						manager.addCard(card2);
						break;
					}
					flag = false;
				}
			} else {
				flag = true;
			}
			if (CardPicker.isEnable())
				manager.sortCards();
			CardPicker.loadGuis(cards);
			renderer.processTerrain(terrain);
			renderer.processEntity(cardPack1);
			renderer.render(light, camera);
			guiRenderer.render(cards);
			float x = Mouse.getX();
			float y = Mouse.getY();
			Vector2f normalizedCoords = MousePicker.getNormalisedDeviceCoordinates(x, y);
			CardPicker.checkMouseHover(normalizedCoords.x, normalizedCoords.y);

			DisplayManager.updateDisplay(); // every frame update display
		}

		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp(); // delete all VAO and other
		DisplayManager.closeDisplay();
	}

}
