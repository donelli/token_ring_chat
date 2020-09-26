
package main;

public class Mensagem {

    private String texto;
    private Usuario de;
    private Usuario para;
    private boolean lida;

    public Mensagem(String texto, Usuario de, Usuario para) {
        this.texto = texto;
        this.de = de;
        this.para = para;
        this.lida = false;
    }

    public String getTexto(){
        return this.texto;
    }

    public Usuario getUsuarioDe() {
        return this.de;
    }

    public Usuario getUsuarioPara() {
        return this.para;
    }

    public boolean jaLida(){
        return this.lida;
    }

    public void setLida(boolean lida){
        this.lida = lida;
    }

}
