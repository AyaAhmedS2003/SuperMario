package Mario;

import CONST.Consts;
import Texture.TextureReader;
import com.sun.opengl.util.GLUT;
import models.GameObj;
import models.CurGame;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import static CONST.Consts.*;

public class MarioGLEventListener extends Key implements GLEventListener {
    JFrame gameJframe;
    CurGame gameState;
    GLUT g = new GLUT();
    //String textureName = "back-in.jpeg";
    TextureReader.Texture[] texture = new TextureReader.Texture[textureNames.length];
    int[] textures = new int[textureNames.length];
    public MarioGLEventListener(JFrame gameJframe, CurGame initialGameState) {
        this.gameJframe = gameJframe;
        this.gameState = initialGameState;
    }
    /*
     5 means gun in array pos
     x and y coordinate for gun 
     */
    public void init(GLAutoDrawable gld) {

        GL gl = gld.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);    //This Will Clear The Background Color To Black

        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

        //number of textures,array to hold the indeces

        gl.glGenTextures(textureNames.length, textures, 0);
        for (int i = 0; i < textureNames.length; i++)
            try {
            texture[i] = TextureReader.readTexture(assetsFolderName + "//" + textureNames[i], true);
            gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);

//                mipmapsFromPNG(gl, new GLU(), texture[i]);
            new GLU().gluBuild2DMipmaps(
                    GL.GL_TEXTURE_2D,
                    GL.GL_RGBA, // Internal Texel Format,
                    texture[i].getWidth(), texture[i].getHeight(),
                    GL.GL_RGBA, // External format from image,
                    GL.GL_UNSIGNED_BYTE,
                    texture[i].getPixels() // Imagedata
            );
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public void display(GLAutoDrawable gld) {

        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);       //Clear The Screen And The Depth Buffer
        gl.glLoadIdentity();

        DrawBackground(gl);


    DrawSprite(gl, this.gameState.xMario, 15, this.gameState.marioIdx, 1f, 0);

        if (!this.gameState.paused) {
        this.gameState.Timer += 1;
        handleKeyPress();
        responeEnemy();
        move();
//            Collisions.destroy(this);
//            Collisions.remove(this);
    }
 gl.glRasterPos2f(-.8f, .9f);
        g.glutBitmapString(5, "Score ");
        g.glutBitmapString
                (5, Integer.toString(this.gameState.getCurrentPlayerScore()));

        gl.glRasterPos2f(-.8f, .8f);
        g.glutBitmapString(5, "Timer  ");
        g.glutBitmapString(5, Long.toString(this.gameState.Timer / 15));

        gl.glRasterPos2f(-.8f, .7f);
        g.glutBitmapString(5, "lives  ");
        g.glutBitmapString(5, Integer.toString(this.gameState.getCurrentPlayerLives()));

        gl.glEnd();


}

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
    public void DrawSprite(GL gl, int x, int y, int index, float scale, float angle) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]); // Turn Blending On

        gl.glPushMatrix();
        gl.glTranslated(x / (maxWidth / 2.0) - 0.9, y / (maxHeight / 2.0) - 0.9, 0);
        gl.glRotatef(angle, 0, 1, 0);

        gl.glScaled(0.1 * scale, 0.1 * scale, 1);
        // System.out.println(x + " " + y);
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }


    public void DrawBackground(GL gl) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[0]); // Turn Blending On

        gl.glPushMatrix();
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }

    public void handleKeyPress() {
        if (isKeyPressed(KeyEvent.VK_SPACE)) {// shooooot.

        }
//        System.out.println(isKeyPressed(KeyEvent.VK_RIGHT));
        if (isKeyPressed(KeyEvent.VK_RIGHT)) {
            if (this.gameState.xMario <= 95) {
                this.gameState.xMario += 1;
                this.gameState.marioIdx++;
                this.gameState.marioIdx %= 5;
                this.gameState.marioIdx++;
            }
        }

    }

    private void move() {

    }

    private void responeEnemy() {

    }
    @Override
    public void keyTyped(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
