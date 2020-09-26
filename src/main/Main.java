package main;

public class Main {
	
	public static Rede rede;
	
	public static void main(String[] args) {
		rede = new Rede();
		rede.ativar();
		Thread thread = new Thread(rede);
		thread.start();
	}
	
}
