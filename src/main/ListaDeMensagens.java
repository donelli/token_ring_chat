
package main;

import java.util.ArrayList;

public class ListaDeMensagens {

    private Nodo primUsu;
    private int quantidade;

    public void incluirMens(String usuario, Mensagem mens) {
        
        Nodo aux = this.getNodoUsu(usuario);

        if (aux == null) {

            if (primUsu == null) {
                primUsu = new Nodo(usuario);
                primUsu.listaMens.add(mens);
            } else {

                aux = primUsu;
                for (int i = 0; i < this.quantidade - 1; i++) {
                    aux = aux.prox;
                }
                aux.prox = new Nodo(usuario);
                aux.prox.listaMens.add(mens);
            }
            
            quantidade++;

        } else {
            aux.listaMens.add(mens);
        }
        
    }
    
    public void incluirMens(Mensagem mens) {
        incluirMens(mens.getUsuarioDe().getNome(), mens);
    }

    public ArrayList<Mensagem> getListaMens(String usuario) {
        Nodo aux = this.getNodoUsu(usuario);
        if (aux == null) {
            return null;
        } else {
            return aux.listaMens;
        }
    }

    private Nodo getNodoUsu(String usu) {
       
        if (this.quantidade == 0) {
            return null;
        } else {
            Nodo aux = primUsu;

            for (int i = 0; i < this.quantidade; i++) {
                if (aux.usuario.equals(usu)) {
                    return aux;
                }
                aux = aux.prox;
            }
        }
        return null;
    }

    private class Nodo {
        
        protected String usuario;
        protected ArrayList<Mensagem> listaMens;
        protected Nodo prox;

        private Nodo (String usuario) {
            this.usuario = usuario;
            listaMens = new ArrayList<>();
        }

    }

}
