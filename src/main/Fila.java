
package main;

public class Fila<T> {

    private Nodo<T> primeira;
    private Nodo<T> ultima;
    private int quantidade;

    public Fila() {
        primeira = null;
        ultima = null;
        quantidade = 0;
    }

    public void adicionar(T dado) {
        
        Nodo<T> novo = new Nodo<>(dado);

        if (ultima == null) {
            this.primeira = novo;
            this.ultima = novo;
        } else {
            this.ultima.prox = novo;
            this.ultima = novo;
        }

        quantidade++;

    }

    public T retirar() {

        System.out.println(this.quantidade);
        
        if (this.quantidade > 0) {
            Nodo<T> aux = primeira;
            this.primeira = aux.prox;
            quantidade--;
            if (this.quantidade == 0) this.ultima = null;
            return aux.dado;
        }
        
        return null;
        
    }

    public boolean temDados(){
        return quantidade > 0;
    }
    
    private class Nodo<T> {
        
        protected T dado;
        protected Nodo<T> prox;

        protected Nodo (T dado) {
            this.dado = dado;
            this.prox = null;
        }

    }
    
}  
