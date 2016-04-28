package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

/** Class, that creates display that we need in our game. */
public class DisplayManager {
	/** Width of the display. */
	private static final int WIDTH = 1280;
	/** Height of the display. */
	private static final int HEIGHT = 720;
	/** Frame Per Second. */
	private static final int FPS = 120;
	private static long lastFrameTime;
	private static float delta;

	/** Open display at the beggining of the game. */
	public static void createDisplay() {
		ContextAttribs attribs = new ContextAttribs(3, 2) // attribs, that takes
															// version of OGL
				.withForwardCompatible(true) // совместимость (хз что значит)
				.withProfileCore(true); // с сердечником профиля??? вроде это не
										// особо нужные пар-ры

		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT)); // set
																	// display
																	// size
			Display.create(new PixelFormat(), attribs); // create display with
														// some attributes
			Display.setTitle("Manchkin Fallout"); // set title
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		GL11.glViewport(0, 0, WIDTH, HEIGHT); // set WHERE display should be
												// printed (whole space we set)
		lastFrameTime = getCurrentTime();
	}

	/** Update display every single frame. */
	public static void updateDisplay() {
		Display.sync(FPS); // syncronise display with fixed FPS (Frame Per
							// Second)
		Display.update();
		long currentFrameTime = getCurrentTime();
		delta = (float) (currentFrameTime - lastFrameTime) / 1000;
		lastFrameTime = currentFrameTime;
	}

	public static float getFrameTimeSeconds() {
		return delta;
	}

	/** Closing display at the end of the game. */
	public static void closeDisplay() {
		Display.destroy(); // that method close display
	}

	private static long getCurrentTime() {
		return Sys.getTime() * 1000 / Sys.getTimerResolution();
	}
}
