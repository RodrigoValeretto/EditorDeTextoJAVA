/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editordetexto;

import java.util.LinkedList;

/**
 *
 * @author rodrigo
 */
public class Texto {
    private LinkedList<Character> text;

    public Texto() {
        this.text = new LinkedList();
    }

    public LinkedList<Character> getText() {
        return text;
    }

    public void setText(LinkedList<Character> text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Texto{" + "text=" + text + '}';
    }
}
