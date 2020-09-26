package main;

public class ListaDeUsuarios {
	
	private Nodo atual;
	private int quantidade;
	
	public ListaDeUsuarios() {
		atual = null;
		quantidade = 0;
	}
	
	public void goToProx() {
		atual = atual.prox;
	}
	
	public Usuario getUsuarioAtual() {
		return atual.usuario;
	}
	
	public int getNumUsuarios() {
		return this.quantidade;
	}
	
	public void incluirUsuario(Usuario usu, Rede rede) {
		
		Nodo novo = new Nodo(usu);
		
		if (atual == null) {
			atual = novo;
			atual.prox = atual;
		} else {
			novo.prox = atual.prox;
			atual.prox = novo;
		}
		
		rede.novoUsuario(novo.usuario);
		
		quantidade++;

	}

	public void incluirUsuario(String usuario, Rede rede) {
		
		Nodo novo = new Nodo(usuario);
		
		if (atual == null) {
			atual = novo;
			atual.prox = atual;
		} else {
			novo.prox = atual.prox;
			atual.prox = novo;
		}
		
		rede.novoUsuario(novo.usuario);

		quantidade++;

	}
	
	public void removerUsuario(String usuario, Rede rede) {
		
		if (this.quantidade == 1) {
			if (atual.usuario.getNome().equals(usuario)) {
				rede.sairUsuario(atual.usuario);
				atual = null;
				this.quantidade--;
			}
		} else if (this.quantidade > 0) {
			
			Nodo aux = this.atual;
			
			boolean achou = false;
			
			for (int i = 0; i < this.quantidade; i++) {
				if(aux.usuario.getNome().equals(usuario)) {
					achou = true;
					break;
				}
				aux = aux.prox;
			}
			
			if (achou) {
				
				Nodo ant = aux;
				
				for (int i = 0; i < this.quantidade - 1; i++) {
					ant = ant.prox;
				}
					
				rede.sairUsuario(this.atual.usuario);

				ant.prox = aux.prox;
				this.atual = ant;
				--this.quantidade;
				
			}
			
		}
		
	}
	
	public String[] getUsuarios() {
		
		String[] lista = new String[this.quantidade];
		
		if (this.quantidade == 1) {
			
			lista[0] = atual.usuario.getNome();
			
		} else if (this.quantidade > 0) {
			
			Nodo aux = atual;
			
			for (int i = 0; i < this.quantidade; i++) {
				lista[i] = aux.usuario.getNome();
				aux = aux.prox;
			}
			
		}
		
		return (this.quantidade == 0) ? null : lista;
		
	}
	
	public boolean posicionaUsuario(String nome_usuario) {

		if (this.quantidade == 0) {
			return false;
		}

		for (int i = 0; i < this.quantidade; i++) {
			if (atual.usuario.getNome().equals(nome_usuario)) {
				return true;
			}
			this.goToProx();
		}
		return false;
	}

	public Usuario getUsuario(String nome_usuario) {

		if (this.posicionaUsuario(nome_usuario)) {
			return this.atual.usuario;
		}

		return null;

	}

	public Usuario getUsuario(){
		return this.quantidade == 0 ? null : this.atual.usuario;
	}

	public class Nodo {
		
		protected Usuario usuario;
		protected Nodo prox;
		
		public Nodo(String usuario) {
			Usuario usu = new Usuario(usuario);
			this.usuario = usu;
			this.prox = null;
		}
		
		public Nodo(Usuario usu) {
			this.usuario = usu;
			this.prox = null;
		}

	}
	
}
