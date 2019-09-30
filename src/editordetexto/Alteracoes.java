/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editordetexto;

/**
 *
 * @author rodrigo
 */
public class Alteracoes {
    private String str;
    private String comando;

    public Alteracoes(String str, String comando) {
        this.str = str;
        this.comando = comando;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String getComando() {
        return comando;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }
    
    
    
}
