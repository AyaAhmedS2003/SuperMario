/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author asus
 */
public class GameObj {
    public int x;
    public String type;
    public boolean remove;

    void GameObj(int x, String type) {
        this.x = x;
        this.type = type;
    }
}
