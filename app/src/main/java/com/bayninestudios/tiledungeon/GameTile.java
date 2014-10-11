package com.bayninestudios.tiledungeon;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by lee on 07/10/14.
 */
public class GameTile {

    private final int NUM_FACE_VERTICES = 3;
    private final int NUM_VERTEX_COORDS = 3;
    private final int NUM_TEX_COORDS = 2;
    private final FloatBuffer mVertexBuffer;
    private final ShortBuffer mIndexBuffer;
    private final FloatBuffer mTexBuffer;

    public GameTile() {
        // create the final buffers
        float[] vertexes = {0f, 0f, 0f,
                            1f, 0f, 0f,
                            1f, 1f, 0f,
                            0f, 1f, 0f};
        float[] textures = {0f, 1f,
                            1f, 1f,
                            1f, 0f,
                            0f, 0f};
        short[] indexes = {0, 1, 2, 3};

        mVertexBuffer = Util.makeFloatBuffer(vertexes);
        mIndexBuffer = Util.makeShortBuffer(indexes);
        mTexBuffer = Util.makeFloatBuffer(textures);
    }

    public void draw(GL10 gl) {
        gl.glFrontFace(GL10.GL_CCW);
        gl.glVertexPointer(NUM_VERTEX_COORDS, GL10.GL_FLOAT, 0, mVertexBuffer);
        gl.glTexCoordPointer(NUM_TEX_COORDS, GL10.GL_FLOAT, 0, mTexBuffer);
        gl.glDrawElements(GL10.GL_TRIANGLE_FAN, mIndexBuffer.remaining(), GL10.GL_UNSIGNED_SHORT, mIndexBuffer);
    }
}
