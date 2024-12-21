/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mario;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.BitSet;


public class Key implements KeyListener {

    public BitSet keyBits = new BitSet(256);

    @Override
    public void keyPressed(final KeyEvent event) {
        int keyCode = event.getKeyCode();
        keyBits.set(keyCode);
    }

    public boolean isKeyPressed(int keyCode) {
        return keyBits.get(keyCode);
    }

    @Override
    public void keyReleased(final KeyEvent event) {
        int keyCode = event.getKeyCode();
        keyBits.clear(keyCode);
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

}

