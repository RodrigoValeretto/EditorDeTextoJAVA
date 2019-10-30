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
    private boolean flag;

    public Server(boolean flag) {
        this.nome = "";
        this.txt = "";
        this.flag = flag;
    }

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

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    
    @Override
    public void run() {
        while(flag)
        {
            try
            {
                while(!Thread.interrupted())
                {
                    Thread.sleep(1);
                }
            }catch(InterruptedException i){}
            
            try {
                FileWriter str = new FileWriter(this.nome);
                str.write(txt);
                str.close();
            }catch (IOException ex) {}
        }
    } 
}
