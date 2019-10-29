/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editordetexto;

import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author rodrigo
 */
public class Server implements Runnable{
    private String nome;
    private String txt;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
    
    @Override
    public void run() {
        try {
            FileWriter str = new FileWriter(this.nome);
            str.write(txt);
            str.close();
            System.out.println("Acabou a thread!!!");
        } catch (IOException ex) {}
    } 
}
