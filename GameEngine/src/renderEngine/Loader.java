package renderEngine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import models.RawModel;

/** This class is used to load 3D models to VAO. */
public class Loader {
	/** List of all VAO. */
	private List<Integer> vaos = new ArrayList<Integer>();
	/** List of all VBO. */
	private List<Integer> vbos = new ArrayList<Integer>();
	/** List of all textures. */
	private List<Integer> textures = new ArrayList<Integer>();

	/**
	 * Method, that takes information and returns created model.
	 *
	 * @param positions
	 *            positions of vertices.
	 * @param textureCoords
	 *            texture positions.
	 * @param normals
	 *            normal vectors.
	 * @param indices
	 *            indices of vertices.
	 * @return get prepared model.
	 */
	public RawModel loadToVAO(float[] positions, float[] textureCoords, float[] normals, int[] indices) {
		int vaoID = createVAO(); // creating empty VAO
		bindIndicesBuffer(indices);
		storeDataInAttributeList(0, 3, positions); // positions go to 0
													// attr.list
		storeDataInAttributeList(1, 2, textureCoords); // texture coordinates -
														// in 1st
		storeDataInAttributeList(2, 3, normals); // normal vectors - in 2nd
		unbindVAO(); // unbind VAO.
		return new RawModel(vaoID, indices.length); // count of indicies = count
													// of verticies
	}

	public RawModel loadToVAO(float[] positions) {
		int vaoID = createVAO();
		storeDataInAttributeList(0, 2, positions);
		unbindVAO();
		return new RawModel(vaoID, positions.length / 2);
	}

	public int loadTexture(String fileName) {
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", new FileInputStream("res/" + fileName + ".png"));
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -0.6f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		int textureID = texture.getTextureID();
		textures.add(textureID);
		return textureID;
	}

	/** Delete all VAO, VBO and textures. */
	public void cleanUp() {
		for (int vao : vaos) {
			GL30.glDeleteVertexArrays(vao);
		}
		for (int vbo : vbos) {
			GL15.glDeleteBuffers(vbo);
		}
		for (int texture : textures) {
			GL11.glDeleteTextures(texture);
		}
	}

	/**
	 * Create empty VAO.
	 *
	 * @return ID of VAO.
	 */
	private int createVAO() {
		int vaoID = GL30.glGenVertexArrays(); // create empty VAO
		vaos.add(vaoID); // add to VAO list
		GL30.glBindVertexArray(vaoID); // activate this new VAO by binding it
		return vaoID;
	}

	/**
	 * Method, that store data in some attribute list of VAO.
	 *
	 * @param attributeNumber
	 *            number of attribute, where data will be stored.
	 * @param coordinateSize
	 *            ???
	 * @param data
	 *            data, that we store.
	 */
	private void storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data) {
		int vboID = GL15.glGenBuffers(); // create empty VBO
		vbos.add(vboID); // add to VBO list
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID); // set new VBO active
		FloatBuffer buffer = storeDataInFloatBuffer(data); // converting data in
															// float buffer
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW); // set
																				// data
																				// in
																				// VBO
		// bind attribute list to this VBO
		GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); // unbind VBO
	}

	/** Undinding VAO. */
	private void unbindVAO() {
		GL30.glBindVertexArray(0); // stop using our new VAO.
	}

	private void bindIndicesBuffer(int[] indices) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDataInIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}

	private IntBuffer storeDataInIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	/**
	 * Convert data in float buffer.
	 *
	 * @param data
	 *            some data, that we want to convert.
	 * @return returns created float buffer.
	 */
	private FloatBuffer storeDataInFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length); // create
																			// empty
																			// float
																			// buffer
		buffer.put(data); // put data in buffer
		buffer.flip(); // prepare buffer to be read from (now its only takes
						// info, not give it)
		return buffer;
	}
}
