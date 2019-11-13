public class Nodo{
	private TipoNodo tipo;
	private String valor;

	// private Nodo nodoPadre;
	private Nodo nodoLeft;
	private Nodo nodoRight;

	public Nodo(String valor){
		this.valor = valor;

		if(valor.equals("&") || valor.equals("|") || valor.equals("<->") || valor.equals("->")){
			this.tipo = TipoNodo.CONECTOR;
		}else if(valor.charAt(0) >= 'A' && valor.charAt(0) >= 'Z'){
			this.tipo = TipoNodo.LITERAL;
		}
	}

	public void setTipoNodo(TipoNodo tipo){
		this.tipo = tipo;
	}

	public void setLeft(Nodo nodoLeft){
		this.nodoLeft = nodoLeft;
	}

	public void setRight(Nodo nodoLeft){
		this.nodoRight = nodoRight;
	}
}