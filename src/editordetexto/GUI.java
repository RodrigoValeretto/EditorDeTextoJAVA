/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editordetexto;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.InvalidPropertiesFormatException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author rodrigo
 */
public class GUI extends JFrame{
    private EditorDeTexto ed;
    private String copiado;
    private JPanel painel = new JPanel();
    private JButton redo = new JButton("Refazer");
    private JButton undo = new JButton("Desfazer");
    private JButton insert = new JButton("Inserir Texto");
    private JButton remove = new JButton("Remover Texto");
    private JButton copy = new JButton("Copiar");
    private JButton cut = new JButton("Recortar");
    private JButton paste = new JButton("Colar");
    private JTextArea visor = new JTextArea();
    private JTextArea com = new JTextArea();
    private JScrollPane scroll = new JScrollPane(visor);
    private JScrollPane scroll2 = new JScrollPane(com);
    
    
    public GUI(EditorDeTexto n)
    {
        this.ed = n;
        this.copiado = "";
        
        painel.setLayout(new GridLayout(1,8));
        
        this.setSize(800,800);
        this.setLayout(new BorderLayout());

        painel.add(undo);
        painel.add(redo);
        painel.add(insert);
        painel.add(remove);
        painel.add(copy);
        painel.add(cut);
        painel.add(paste);
        
        visor.setEditable(false);
        visor.setLineWrap(true);
        scroll.setBorder(new TitledBorder(new EtchedBorder(), "Texto"));
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setPreferredSize(new Dimension(400,400));
        
        
        com.setEditable(true);
        com.setLineWrap(true);
        scroll2.setBorder(new TitledBorder(new EtchedBorder(), "Comandos"));
        scroll2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll2.setPreferredSize(new Dimension(400,400));
     
        this.add(painel, BorderLayout.SOUTH);
        this.add(scroll, BorderLayout.CENTER);
        this.add(scroll2, BorderLayout.NORTH);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        undo.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent a) {
                String str = "";
                try
                {
                    ed.desfazer();
                    for(char i : ed.getT().getText())
                        str = str.concat(String.valueOf(i));
                    visor.setText(str);
                }catch(NullPointerException f)
                {
                    System.out.println(f.getMessage());
                }

            }
        });
        
        redo.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent a) {
                String str = "";
                try
                {
                    ed.refazer();
                    for(char i : ed.getT().getText())
                        str = str.concat(String.valueOf(i));
                    visor.setText(str);
                }catch(NullPointerException f)
                {
                    System.out.println(f.getMessage());
                }

            }
        });
        
        insert.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = "";
                try
                {
                    ed.inseretexto(com.getText());
                    ed.inserealteracao(ed.getDesfaz(),com.getText(), "1");
                    ed.getRefaz().clear();
                }catch(NullPointerException f)
                {
                    System.out.println(f.getMessage());
                }

                for(char i : ed.getT().getText())
                    str = str.concat(String.valueOf(i));
                visor.setText(str);
                com.setText("");
            }
        });
       
        remove.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = "";
                try 
                {
                    String aux = ed.removetexto(Integer.parseInt(com.getText()));
                    ed.inserealteracao(ed.getDesfaz(),aux, "2");
                    ed.getRefaz().clear();
                }catch(InvalidPropertiesFormatException f)
                {
                    System.out.println(f.getMessage());
                }
                
                for(char i : ed.getT().getText())
                    str = str.concat(String.valueOf(i));
                visor.setText(str);
                com.setText("");
            }
        });

        
        copy.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    copiar();
                }catch(NullPointerException ex)
                {
                    System.out.println(ex.getMessage());
                }

            }
        });
        
        cut.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    recortar();
                    ed.getRefaz().clear();
                    ed.getDesfaz().clear();
                }catch(NullPointerException ex)
                {
                    System.out.println(ex.getMessage());
                }
            }
        });

        paste.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                colar();
                ed.getRefaz().clear();
                ed.getDesfaz().clear();
            }
        });

    }
    
    public void copiar(){
        if(visor.getSelectedText() == null)
            throw new NullPointerException("Nenhum Texto Selecionado");

        this.copiado = visor.getSelectedText();   
        visor.select(0, 0);
    }
    
    public void recortar(){
        if(visor.getSelectedText() == null)
            throw new NullPointerException("Nenhum Texto Selecionado");
        
        String str = "";
        this.copiado = visor.getSelectedText();
        int ini = visor.getSelectionStart();
        int fim = visor.getSelectionEnd();
        
        for(int i = ini; i < fim; i++)
            this.ed.getT().getText().remove(ini);
        
        for(char i : this.ed.getT().getText())
            str = str.concat(String.valueOf(i));
            
        visor.setText("");
        visor.setText(str);
        visor.select(0, 0);
    }
    
    public void colar(){
        String str = this.visor.getText();
        
        str = str.concat(this.copiado);
        
        ed.inseretexto(this.copiado);
        
        visor.setText("");
        visor.setText(str);
    }
}