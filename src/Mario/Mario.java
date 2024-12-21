package Mario;

import com.sun.opengl.util.*;
import java.awt.*;
import java.awt.event.KeyListener;
import javax.media.opengl.*;
import javax.swing.*;
import models.CurGame;

public class Mario extends JFrame {

    private Animator animator;
    private final MarioGLEventListener listener;
    private final GLCanvas glcanvas;


    public Mario(String userName, String secondPlayerName) {
        CurGame gameState = new CurGame();
        gameState.newGame();
        gameState.firstPlayerName = userName;
        if (secondPlayerName != null) {
            gameState.secondPlayerName = secondPlayerName;
            gameState.isMultipalyer = true;
        }
        listener = new MarioGLEventListener(this, gameState);
        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(listener);
        setupFrame();
    }

    public Mario(String userName) {
        this(userName, null);
    }

    private void setupFrame() {
        getContentPane().add(glcanvas, BorderLayout.CENTER);
        animator = new FPSAnimator(15);
        animator.add(glcanvas);
        animator.start();
        setTitle("Mario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        setFocusable(true);
        glcanvas.requestFocus();
    }
}
