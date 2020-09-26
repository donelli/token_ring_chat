package main;

import java.util.ArrayList;

public class Usuario {
	
	private String nome;
	private Fila<Mensagem> mensAEnviar;
	private UserWindow sessao;
	private ListaDeMensagens listaDeMensagens;

	public Usuario (String nome) {
		this.nome = nome;
		mensAEnviar = new Fila<>();
		listaDeMensagens = new ListaDeMensagens();
	}
	
	public void atualizaConversa() {
		this.sessao.atualizarConversa();
	}

	public String getNome() {
		return this.nome;
	}
	
	public UserWindow getSessao() {
		return this.sessao;
	}

	public void setSessao(UserWindow userWindow) {
		this.sessao = userWindow;
	}

	public void enviarMensagem(Mensagem mensagem) {
		mensAEnviar.adicionar(mensagem);
	}

	public void receberMens(Mensagem mensagem) {
		listaDeMensagens.incluirMens(mensagem);
	}
	
	public boolean temMensAEnviar() {
		return mensAEnviar.temDados();
	}

	public Mensagem getMensagem(){
		return mensAEnviar.retirar();
	}

	public ArrayList<Mensagem> getMensagens(String usuario) {
		return this.listaDeMensagens.getListaMens(usuario);
	}
	
	public void incMens(Mensagem mens) {
		this.listaDeMensagens.incluirMens(mens.getUsuarioPara().getNome(), mens);
	}

}
