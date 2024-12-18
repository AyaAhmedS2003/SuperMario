package Mario;

import com.sun.opengl.util.*;
import java.awt.*;
import javax.media.opengl.*;
import javax.swing.*;

public class Mario extends JFrame {

    private Animator animator;

    public Mario() {

        GLCanvas glcanvas;

        MarioGLEventListener listener = new MarioGLEventListener();
        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(listener);
        glcanvas.addKeyListener(listener);
        getContentPane().add(glcanvas, BorderLayout.CENTER);

        animator = new FPSAnimator(glcanvas, 15);
        animator.start();

        setTitle("Super Mario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        setFocusable(true);
        glcanvas.requestFocus();
    }
}
