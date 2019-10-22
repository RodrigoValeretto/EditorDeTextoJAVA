/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editordetexto;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 *
 * @author rodrigo
 */
public class JInserir extends JFrame{
    private JButton ok = new JButton("OK");
    private JTextArea area = new JTextArea();
    private Texto t;
    
    public JInserir(Texto tn)
    {
        super("Insira o texto desejado aqui!");
        
        this.t = tn;
        
        this.setSize(1024,768);
        this.setLayout(new BorderLayout());
        this.add(area, BorderLayout.NORTH);
        this.add(ok, BorderLayout.SOUTH);

        //area.setPreferredSize(new Dimension(768,1024));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ok.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = area.getText();
                for(int i = 0; i < str.length(); i++)
                    t.getText().add(str.charAt(i));   
            }
        });
    }

    public JButton getOk() {
        return ok;
    }

    public void setOk(JButton ok) {
        this.ok = ok;
    }

    public JTextArea getArea() {
        return area;
    }

    public void setArea(JTextArea area) {
        this.area = area;
    }

    public Texto getT() {
        return t;
    }

    public void setT(Texto t) {
        this.t = t;
    }
    
    
}
