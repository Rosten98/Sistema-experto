public class Literal{

	public String nombre;
	// 1 = verdad | 0 = falso | 2 = Sin saber
	public int valorVerdad;
	public Literal literalHermano;

	public Literal(String nombre){
		this.nombre = nombre;

		if(this.nombre.charAt(0) != '_') {
			this.literalHermano = new Literal('_' + this.nombre);
		} else {
			this.literalHermano = null;
		}
	}

	public String getNombre(){
		return this.nombre;
	}

	public int getValorVerdad(){
		return this.valorVerdad;
	}

	public Literal getLiteralHermano(){
		return this.literalHermano;
	}

	public void setValorVerdad(int valorVerdad){
		this.valorVerdad = valorVerdad;
		
		if(this.nombre.charAt(0) == '_'){
			return;
		}
		this.literalHermano.setValorVerdad(0);
	}

	public void setLiteralHermano(Literal hermano){
		this.literalHermano = hermano;
	}
}
