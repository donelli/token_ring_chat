package main;

public class Rede implements Runnable {
	
	private boolean rodando;
	public Window window;
	private Token token;
	private ListaDeUsuarios listaDeUsuarios;

	public Rede () {
		this.listaDeUsuarios = new ListaDeUsuarios();
		this.window = new Window(this);
		this.rodando = false;
	}
	
	@Override
	public void run() {
		
		Usuario usuarioAtual;
		token = new Token();

		while (true) {
			
			//window.print("");
			System.out.println("asd");
			
			if (listaDeUsuarios.getNumUsuarios() == 0 || !rodando) continue;
			
			listaDeUsuarios.goToProx();
			usuarioAtual = listaDeUsuarios.getUsuarioAtual();
			
			window.print(usuarioAtual.getNome());
			
			if (token.temMensagem()) {
				if (token.mensEhParaUsuario(usuarioAtual)) {
					Mensagem mensaux = this.token.verMensagem();
					usuarioAtual.receberMens(token.getMensagem());
					window.print("Mensagem '" + mensaux.getTexto() + 
						"' enviada por " + mensaux.getUsuarioDe().getNome() + 
						" recebida por " + mensaux.getUsuarioPara().getNome());
					mensaux.getUsuarioPara().atualizaConversa();
				}
			} else {
				if(usuarioAtual.temMensAEnviar()) {
					token.setMensagem(usuarioAtual.getMensagem());
					Mensagem mensaux = this.token.verMensagem();
					window.print("Mensagem rebebida: '" + mensaux.getTexto() + 
						"' enviada por " + mensaux.getUsuarioDe().getNome() + 
						" para " + mensaux.getUsuarioPara().getNome());
				}
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	public Usuario addUsuario (String nome) {
		Usuario usu = new Usuario(nome);
		this.getListaDeUsuarios().incluirUsuario(usu, this);
		return usu;
	}

	public ListaDeUsuarios getListaDeUsuarios() {
		return this.listaDeUsuarios;
	}

	public void novoUsuario(Usuario usuario){
		window.print("Usuario: " + usuario.getNome() + " - Entrou na Sessao!");
	}

	public void sairUsuario(Usuario usuario){
		window.print("Usuario: " + usuario.getNome() + " - Saiu da Sessao!");
	}

	public void ativar() {
		rodando = true;
		window.print("Token Ativado!");
	}
	
	public void parar() {
		rodando = false;
		window.print("Token Desativado!");
	}
	
	private class Token {

		Mensagem mensagem = null;

		private boolean temMensagem() {
			return mensagem != null;
		}

		private boolean mensEhParaUsuario(Usuario usuario) {
			return this.mensagem.getUsuarioPara().equals(usuario);
		}		

		private Mensagem getMensagem() {

			Mensagem aux = this.mensagem;
			this.mensagem = null;

			return aux;

		}

		private Mensagem verMensagem(){
			return this.mensagem;
		}

		private void setMensagem(Mensagem mensagem) {
			this.mensagem = mensagem;
		}

	}

}
