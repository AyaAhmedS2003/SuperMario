package Mario;

import Texture.TextureReader;
import com.sun.opengl.util.GLUT;
import models.CurGame;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.BitSet;

public class MarioGLEventListener extends MarioListener implements GLEventListener {

    public BitSet keyBits = new BitSet(256);
    
        
    
    JFrame gameJframe;
    CurGame gameState;
    GLUT g = new GLUT();

    /* Start Of CONSTANTS */
    public static int y = 15;
    public static int maxWidth = 100;
    public static int maxHeight = 100;
    public static String textureNames[] = {
        "back-in.jpeg",
        "mario1.png",
        "mario2.png",
        "mario3.png",
        "mario1.png",
         "mario1.png",
    };

    public static String assetsFolderName = "Assets/Mario";

    // Add these variables in your class
    boolean isJumping = false;
    int jumpSpeed = 5;  // Adjust for jump height
    int gravity = 1;    // Adjust for fall speed
    int jumpHeight = 20; // Adjust for max jump height
    int initialY = y;   // To remember the initial Y position before the jump

    /* End Of CONSTANTS */
    TextureReader.Texture[] texture = new TextureReader.Texture[textureNames.length];
    int[] textures = new int[textureNames.length];

    public MarioGLEventListener(JFrame gameJframe, CurGame initialGameState) {
        this.gameJframe = gameJframe;
        this.gameState = initialGameState;
    }

    public void init(GLAutoDrawable gld) {

        GL gl = gld.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);    //This Will Clear The Background Color To Black

        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

        gl.glGenTextures(textureNames.length, textures, 0);
        for (int i = 0; i < textureNames.length; i++)
            try {
            texture[i] = TextureReader.readTexture(assetsFolderName + "//" + textureNames[i], true);
            gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);

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
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();

        DrawBackground(gl);
        DrawSprite(gl, this.gameState.xMario, y, this.gameState.marioIdx, 1f, 0);

        if (!this.gameState.paused) {
            this.gameState.Timer += 1;
            handleKeyPress();
            responeEnemy();
            move(); // Ensure move is called here
        }

        gl.glRasterPos2f(-0.8f, 0.9f);
        g.glutBitmapString(5, "Score: " + this.gameState.getCurrentPlayerScore());
        gl.glRasterPos2f(-0.4f, 0.9f);
        g.glutBitmapString(5, "Timer: " + (this.gameState.Timer / 15));
        gl.glRasterPos2f(0.3f, 0.9f);
        g.glutBitmapString(5, "Lives: " + this.gameState.getCurrentPlayerLives());

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

    public float backgroundOffset = 0.0f;  // Offset for scrolling background
public float scrollSpeed = 0.01f;  // Speed of background scrolling

public void DrawBackground(GL gl) {
    gl.glEnable(GL.GL_BLEND);
    gl.glBindTexture(GL.GL_TEXTURE_2D, textures[0]); // Turn Blending On

    gl.glPushMatrix();
    gl.glBegin(GL.GL_QUADS);

    // Draw the first segment of the background
    gl.glTexCoord2f(backgroundOffset, 0.0f);
    gl.glVertex3f(-1.0f, -1.0f, -1.0f);
    gl.glTexCoord2f(1.0f + backgroundOffset, 0.0f);
    gl.glVertex3f(1.0f, -1.0f, -1.0f);
    gl.glTexCoord2f(1.0f + backgroundOffset, 1.0f);
    gl.glVertex3f(1.0f, 1.0f, -1.0f);
    gl.glTexCoord2f(backgroundOffset, 1.0f);
    gl.glVertex3f(-1.0f, 1.0f, -1.0f);

    // Draw the second segment to create a seamless looping effect
    gl.glTexCoord2f(backgroundOffset - 1.0f, 0.0f);
    gl.glVertex3f(1.0f, -1.0f, -1.0f);
    gl.glTexCoord2f(backgroundOffset, 0.0f);
    gl.glVertex3f(3.0f, -1.0f, -1.0f);
    gl.glTexCoord2f(backgroundOffset, 1.0f);
    gl.glVertex3f(3.0f, 1.0f, -1.0f);
    gl.glTexCoord2f(backgroundOffset - 1.0f, 1.0f);
    gl.glVertex3f(1.0f, 1.0f, -1.0f);

    gl.glEnd();
    gl.glPopMatrix();

    // Update the background offset for the next frame
    backgroundOffset += scrollSpeed;
    if (backgroundOffset > 1.0f) {
        backgroundOffset -= 1.0f;  // Reset the offset for seamless looping
    }

    gl.glDisable(GL.GL_BLEND);
}


    public boolean isKeyPressed(int keyCode) {
        return keyBits.get(keyCode);
    }

    public void handleKeyPress() {
        if (isKeyPressed(KeyEvent.VK_SPACE) && !isJumping) {
            isJumping = true;
            initialY = y; // Store the initial Y position before the jump
        }

        if (isKeyPressed(KeyEvent.VK_RIGHT)) {
            if (!isJumping && this.gameState.xMario < maxWidth - 10) {
                this.gameState.xMario += 1;
                this.gameState.marioIdx++;
                this.gameState.marioIdx %= 5;
                this.gameState.marioIdx++;
            }
        }
        if (isKeyPressed(KeyEvent.VK_LEFT)) {
            if (!isJumping && this.gameState.xMario > 0) {
                this.gameState.xMario -= 1;
                this.gameState.marioIdx++;
                this.gameState.marioIdx %= 5;
                this.gameState.marioIdx++;
            }
        }

    }

    boolean isAscending = false;

    private void move() {
        if (isJumping) {
            if (isAscending) {
                if (y < initialY + jumpHeight) {
                    y += jumpSpeed; // Mario goes up
                } else {
                    isAscending = false; // Start descending
                }
            } else {
                if (y > initialY) {
                    y -= jumpSpeed; // Mario comes down
                } else {
                    y = initialY; // Ensure Mario doesn't go below ground level
                    isJumping = false; // End the jump
                    isAscending = true; // Reset to start ascending next time
                    jumpSpeed = 5; // Reset jump speed
                }
            }
        }
    }

    private void responeEnemy() {

    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int keyCode = ke.getKeyCode();
        keyBits.set(keyCode);
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        int keyCode = ke.getKeyCode();
        keyBits.clear(keyCode);
    }

}
