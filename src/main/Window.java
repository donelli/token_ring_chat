package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Calendar;

public class Window {

    private int usuariosConectados;
    private JFrame frame;
    private JPanel panel;
    private TextArea textArea;
    private JScrollPane scrollPane;
    private JPanel southPanel;
    private JPanel panelNovoUsuario;
    private JLabel labelUsuarioConectados;
    private JButton buttonNovoUsuario;
    private JTextField fieldNomeUsuario;
    private Rede rede;

    public Window(Rede rede) {

        this.usuariosConectados = 0;
        this.rede = rede;
        
        this.setComponents();
        
    }

    private void setComponents() {
        
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Token Ring");

        panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(5, 5, 0, 5));
        textArea = new TextArea(30, 100);
        scrollPane = new JScrollPane(textArea);
        textArea.setEditable(false);
        panel.add(scrollPane);
        
        labelUsuarioConectados = new JLabel("Usuarios Conectados: " + usuariosConectados);
        
        fieldNomeUsuario = new JTextField(10);

        buttonNovoUsuario = new JButton("Novo Usuario");
        buttonNovoUsuario.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserWindow(rede, fieldNomeUsuario.getText());
                atualizarUsuariosConectados(+1);
                fieldNomeUsuario.setText("");
            }
        });
        
        fieldNomeUsuario.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fieldNomeUsuario.getText().length() == 0) {
                    return;
                }
                new UserWindow(rede, fieldNomeUsuario.getText());
                atualizarUsuariosConectados(+1);
                fieldNomeUsuario.setText("");
            }
        });

        panelNovoUsuario = new JPanel(new FlowLayout());
        panelNovoUsuario.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panelNovoUsuario.add(fieldNomeUsuario);
        panelNovoUsuario.add(buttonNovoUsuario);

        southPanel = new JPanel(new BorderLayout());
        southPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        southPanel.add(labelUsuarioConectados, BorderLayout.WEST);
        southPanel.add(panelNovoUsuario, BorderLayout.EAST);
        frame.add(panel, BorderLayout.CENTER);
        frame.add(southPanel, BorderLayout.SOUTH);

        this.print("Serviço Iniciado!");

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public void print(String msg) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        textArea.append(dateFormat.format(cal.getTime()) + " : " + msg + "\n");
    }
    
    public int getUsuarioConectados(){
        return this.usuariosConectados;
    }
    
    public void atualizarUsuariosConectados(int valor) {
        this.usuariosConectados = this.usuariosConectados + valor;
        labelUsuarioConectados.setText("Usuarios Conectados: " + this.usuariosConectados);
    }
    
}
