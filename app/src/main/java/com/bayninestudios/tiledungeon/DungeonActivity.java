package com.bayninestudios.tiledungeon;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class DungeonActivity extends Activity implements GLSurfaceView.Renderer {

    private GLSurfaceView mGLView;
    private GameTile gameTile;
    private TileMap tileMap;
    private int[] mTexture = new int[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGLView = new GLSurfaceView(this);
        mGLView.setRenderer(this);
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            mGLView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
//            mGLView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        gameTile = new GameTile();
        tileMap = new TileMap();
//        tileMap.initCheckers();
        setContentView(mGLView);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, 60.0f, (mGLView.getWidth() * 1f) / mGLView.getHeight(), 1, 100);
        GLU.gluLookAt(gl, 0f, 0f, 30f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//        gl.glEnable(GL10.GL_DEPTH_TEST);

        gl.glEnable(GL10.GL_TEXTURE_2D);

        loadTextures(gl);
    }

    private void loadTextures(GL10 gl) {
        gl.glGenTextures(2, mTexture, 0);
        Bitmap bitmap;
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        gl.glBindTexture(GL10.GL_TEXTURE_2D, mTexture[0]);
        bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.wall, options);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();
        gl.glBindTexture(GL10.GL_TEXTURE_2D, mTexture[1]);
        bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.floor, options);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int w, int h) {
        gl.glViewport(0, 0, w, h);
    }

    public void setActiveTexture(GL10 gl, int textureNum) {
        gl.glBindTexture(GL10.GL_TEXTURE_2D, mTexture[textureNum]);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);
    }

    private void drawTile(GL10 gl, float x, float y) {
        gl.glPushMatrix();
        gl.glTranslatef(x,y,0f);
        gameTile.draw(gl);
        gl.glPopMatrix();
    }

    private void drawTile(GL10 gl, float x, float y, int textureNum) {
        setActiveTexture(gl, textureNum);
        drawTile(gl,x,y);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClearColor(0f, 0f, .7f, 1.0f);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        long start = System.currentTimeMillis();
        for (int i = 0; i < tileMap.SIZE; i++) {
            for (int j = 0; j < tileMap.SIZE; j++) {
                drawTile(gl,i-(tileMap.SIZE/2),j-(tileMap.SIZE/2),tileMap.tiles[i][j]);
            }
        }
        long end = System.currentTimeMillis();
        Log.d("Perf",">>> "+(end-start));
    }
}
