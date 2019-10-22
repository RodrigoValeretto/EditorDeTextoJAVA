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
import java.util.LinkedList;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Classe principal do programa, contém uma variável do tipo Texto e duas LinkedLists.
 * @author Rodrigo Valeretto e Leonardo Cerce
 */
public class EditorDeTexto extends JFrame{
    private Texto t;
    private LinkedList<Alteracoes> desfaz;
    private LinkedList<Alteracoes> refaz;
    private JPanel painel = new JPanel();
    private JButton redo = new JButton("Refazer");
    private JButton undo = new JButton("Desfazer");
    private JButton insert = new JButton("Inserir Texto");
    private JTextArea visor = new JTextArea();
    private JScrollPane scroll = new JScrollPane(visor);

    /**
     * Imprime na tela do usuário o texto completo por ele editado.
     * @author Rodrigo Valeretto e Leonardo Cerce
     */
    public void exibetexto()
    {
        for(char l : this.t.getText())
        {
            System.out.print(l);
        }
        
        System.out.print("\n");
    }
    
    /**
     * Função que desfaz a última alteração feita pelo usuário, salvando-a na lista de possíveis ações a serem refeitas.
     * @throws NullPointerException 
     */
    public void desfazer() throws NullPointerException
    {
        if(this.desfaz.isEmpty())
        {
            throw new NullPointerException("\nNão há operações para serem desfeitas!\n");
        }
        
        Alteracoes e;
        String aux = this.desfaz.getLast().getComando();
        if(aux.equals("1"))
        {
            e = this.desfaz.removeLast();
            try 
            {
                this.removetexto(e.getStr().length());
            }catch(InvalidPropertiesFormatException ex)
            {
                System.out.println(ex.getMessage());
            }
            e.setComando("2");
            this.refaz.add(e);
        }
        
        if(aux.equals("2"))
        {
            e = this.desfaz.removeLast();
            this.inseretexto(e.getStr());
            e.setComando("1");
            this.refaz.add(e);
            
        }
            
    }
    
    /**
     * Função que refaz a última modificação que foi desfeita pelo usuário.
     * @throws NullPointerException 
     */
        public void refazer() throws NullPointerException
    {
        if(this.refaz.isEmpty())
        {
            throw new NullPointerException("\nNão há operações para serem refeitas!\n");
        }
        
        Alteracoes e;
        String aux = this.refaz.getLast().getComando();
        if(aux.equals("1"))
        {
            e = this.refaz.removeLast();
            
            try 
            {
                this.removetexto(e.getStr().length());
            }catch(InvalidPropertiesFormatException ex) 
            {
                System.out.println(ex.getMessage());
            }
            
            e.setComando("2");
            this.desfaz.add(e);
        }
        
        if(aux.equals("2"))
        {
            e = this.refaz.removeLast();
            this.inseretexto(e.getStr());
            e.setComando("1");
            this.desfaz.add(e);
            
        }
            
    }

    /**
     * Função que remove uma determinada quantidade de caracteres, especificada pelo usuário.
     * @param num
     * @return String
     * @throws InvalidPropertiesFormatException 
     */
    public String removetexto(int num) throws InvalidPropertiesFormatException
    {
        if(num > this.t.getText().size())
        {
            throw new InvalidPropertiesFormatException("\nNumero maior do que o tamanho total do texto!\n");
        }
        
        StringBuffer x = new StringBuffer();
        String aux;
        
        for(int i = 0; i < num; i++)
        {
          x = x.append(this.t.getText().removeLast());
        }
        
        aux = x.reverse().toString();
        
        return aux;
    }
    
    /**
     * Função que insere o texto digitado pelo usuário até que ele pressione "Enter".
     * @param str
     * @throws NullPointerException 
     */
    public void inseretexto(String str) throws NullPointerException
    {
        if(str.isEmpty())
        {
            throw new NullPointerException("\nNenhum texto digitado!\n");
        }
        
        int i;          
        for(i = 0; i < str.length(); i++)
        {
            this.t.getText().add(str.charAt(i));
        }
    }

    /**
     * Função que insere um elemento da classe Alterações em uma lista encadeada de alterações passada na função
     * @param a
     * @param str
     * @param n 
     */
    public void inserealteracao(LinkedList<Alteracoes> a, String str, String n)
    {
        Alteracoes e = new Alteracoes(str, n);
        a.add(e);
    }
    
    /**
     * Função que remove o último elemento da classe Alterações de uma lista encadeada de alterações passada na função
     * @param a
     * @return Retorna o elemento removido da lista
     */
    public Alteracoes removealteracao(LinkedList<Alteracoes> a)
    {
        Alteracoes e = a.removeLast();
        return e;
    }
    
    /**
     * Construtor da classe EditorDeTexto
     * @author Rodrigo Valeretto e Leonardo Cerce
     */
    public EditorDeTexto() {
        super("Editor de Texto");
        this.t = new Texto();
        this.desfaz = new LinkedList();
        this.refaz = new LinkedList();
        
        painel.setLayout(new GridLayout(1,8));
        
        this.setSize(1280,720);
        this.setLayout(new BorderLayout());

        painel.add(undo);
        painel.add(redo);
        painel.add(insert);
        
        visor.setEditable(false);
        visor.setLineWrap(true);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setPreferredSize(new Dimension(768,1024));
        
        this.add(painel, BorderLayout.SOUTH);
        this.add(scroll, BorderLayout.NORTH);
        
        undo.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent a) {
                String str = "";
                try
                {
                    desfazer();
                    for(char i : t.getText())
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
                    refazer();
                    for(char i : t.getText())
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
                JInserir ins = new JInserir(t);
                String str = "";
                int x;
                x = t.getText().size();
                ins.setVisible(true);

                do{
                    t.setText(ins.getT().getText());
                  }while(ins.getArea().getText() != ins.getT().getText().toString());
                
                for(char i : ins.getT().getText())
                    str = str.concat(String.valueOf(i));
                
                inseretexto(str);
                visor.setText(str);
                
            }
        });
    }
    
    /**
     * Programa principal do editor de texto
     * @param args the command line arguments
     */    
    public static void main(String[] args) {
        EditorDeTexto e = new EditorDeTexto();
        Scanner scan = new Scanner(System.in);
        String aux;
        int num;
        String n;
        String op;
        
        e.setVisible(true);
        e.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        while(true)
        {
            System.out.println("---------------------------------------------");
            System.out.println("Selecione a opção desejada:");
            System.out.println("0 - Sair do programa");
            System.out.println("1 - Insere Texto");
            System.out.println("2 - Exclui texto");
            System.out.println("3 - Exibe Texto");
            System.out.println("4 - Pula Linha");
            System.out.println("5 - Desfazer");
            System.out.println("6 - Refazer");
            System.out.println("---------------------------------------------");
            
            switch(op = scan.nextLine())
            {
                case "0":
                    return;
                
                case "1":
                    aux = scan.nextLine();
                    try
                    {
                        e.inseretexto(aux);
                        e.inserealteracao(e.desfaz,aux, op);
                        e.refaz.clear();
                    }catch(NullPointerException f)
                    {
                        System.out.println(f.getMessage());
                    }
                    break;
                
                case "2":
                    System.out.println("Quantos caracteres deseja remover:");
                    num = scan.nextInt();
                    scan.nextLine();
                {
                    try 
                    {
                        aux = e.removetexto(num);
                        e.inserealteracao(e.desfaz,aux, op);
                        e.refaz.clear();
                    }catch(InvalidPropertiesFormatException f)
                    {
                        System.out.println(f.getMessage());
                    }
                }
                    break;

                    
                case "3":
                    e.exibetexto();
                    break;
                    
                case "4":
                    e.inseretexto("\n");
                    e.inserealteracao(e.desfaz,"\n", "1");
                    e.refaz.clear();
                    break;
                    
                case "5":
                    try
                    {
                        e.desfazer();
                    }catch(NullPointerException f)
                    {
                        System.out.println(f.getMessage());
                    }
                    break;
                    
                case "6":
                    try
                    {
                        e.refazer();
                    }catch(NullPointerException f)
                    {
                        System.out.println(f.getMessage());
                    }
                    break;
            }
        }
        
        
    }
    
}
