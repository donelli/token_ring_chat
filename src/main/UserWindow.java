
package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class UserWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private Rede rede;

    private JPanel mainpanel;
    private JPanel panelUsuarios;
    private JPanel panelMensagens;
    private JButton buttonAtualizarUsuarios;
    private JList<String> listaUsuarios;
    private DefaultListModel<String> listaUsu;
    private Usuario usuario;
    private JButton buttonEnviar;
    private JTextArea areaMens;
    private JTextField textMensagem;
    private JPanel panelEnviarMens;
    
    public UserWindow(Rede rede, String nomeUsuario) {
        
        super();
        
        this.rede = Main.rede;

        this.listaUsu = new DefaultListModel<>();
        
        Usuario usu = this.rede.addUsuario(nomeUsuario);
        this.usuario = usu;
        this.usuario.setSessao(this);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                rede.getListaDeUsuarios().removerUsuario(nomeUsuario, rede);
                rede.window.atualizarUsuariosConectados(-1);
                dispose();
            }
        });
        
        initComponets();

        this.setTitle("Chats do usuario " + nomeUsuario);
        this.setSize(500, 400);
        this.setResizable(false);
        this.setVisible(true);

    }

    private void initComponets() {
        
        mainpanel = new JPanel(new BorderLayout());
        
        panelUsuarios = new JPanel(new BorderLayout());
        panelUsuarios.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        buttonAtualizarUsuarios = new JButton("Atualizar Usuarios");
        atualizarUsuarios();
        
        buttonAtualizarUsuarios.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarUsuarios();
            }
        });

        listaUsuarios = new JList<>(listaUsu);
        listaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaUsuarios.addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                atualizarConversa();
            }
        });

        panelUsuarios.add(buttonAtualizarUsuarios, BorderLayout.NORTH);
        panelUsuarios.add(listaUsuarios, BorderLayout.CENTER);

        panelMensagens = new JPanel(new BorderLayout());
        panelMensagens.setBorder(new EmptyBorder(5, 5, 5, 5));

        areaMens = new JTextArea();
        areaMens.setEditable(false);
        
        panelEnviarMens = new JPanel(new BorderLayout());

        buttonEnviar = new JButton("Enviar");
        textMensagem = new JTextField();

        buttonEnviar.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e) {
                enviaMensagem();
            }
        });

        panelEnviarMens.add(textMensagem, BorderLayout.CENTER);
        panelEnviarMens.add(buttonEnviar, BorderLayout.EAST);

        panelMensagens.add(areaMens, BorderLayout.CENTER);
        panelMensagens.add(panelEnviarMens, BorderLayout.SOUTH);

        mainpanel.add(panelUsuarios, BorderLayout.WEST);
        mainpanel.add(panelMensagens, BorderLayout.CENTER);

        this.add(mainpanel);
    }

    private void atualizarUsuarios() {

        this.listaUsu.removeAllElements();

        String[] aux = rede.getListaDeUsuarios().getUsuarios();
        
        for (int i = 0; i < aux.length; i++) {
            if (aux[i].equals(this.usuario.getNome())) continue;
            this.listaUsu.addElement(aux[i]);
        }

    }

    private Usuario getUsuAtual() {
        String usuario = this.listaUsuarios.getSelectedValue();
        return this.rede.getListaDeUsuarios().getUsuario(usuario);
    }

    public void atualizarConversa() {

        String usuario = this.listaUsuarios.getSelectedValue();

        this.areaMens.setText("");

        if (usuario == null || usuario.length() == 0) return;

        ArrayList<Mensagem> mens = this.usuario.getMensagens(usuario);
        Mensagem atual;
        
        if (mens == null) return;
        
        for (int i = mens.size() - 1; i >= 0; i--) {
            
            atual = mens.get(i);
            
            if (atual.getUsuarioDe() == this.usuario) {
                //this.areaMens.setText(this.areaMens.getText() + "Voce: " + atual.getTexto() + "\n");
                this.areaMens.append("Voce: " + atual.getTexto() + "\n");
            } else {
                //this.areaMens.setText(this.areaMens.getText() + atual.getUsuarioDe().getNome() + ": " + atual.getTexto() + "\n");
                this.areaMens.append(atual.getUsuarioDe().getNome() + ": " + atual.getTexto() + "\n");
            }
            
        }
        
    }
    
    private void enviaMensagem() {
        String mens = this.textMensagem.getText();
        if (mens.length() > 0) {
            Mensagem mensagem = new Mensagem(mens, this.usuario, this.getUsuAtual());
            usuario.enviarMensagem(mensagem);
            usuario.incMens(mensagem);
            atualizarConversa();
            this.textMensagem.setText("");
        }
    }

}
