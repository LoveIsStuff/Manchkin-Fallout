package models;

/** This class represents 3D model, stored in memory. */
public class RawModel {
	/** ID of VAO, where this model stored. */
	private int vaoID;
	/** How many vertices this model has. */
	private int vertexCount;

	/**
	 * Constructor takes VAO ID and vetrex count of 3D model.
	 *
	 * @param vaoID
	 *            ID of VAO.
	 * @param vertexCount
	 *            number of vertices.
	 */
	public RawModel(int vaoID, int vertexCount) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}

	public int getVaoID() {
		return vaoID;
	}

	public int getVertexCount() {
		return vertexCount;
	}
}
