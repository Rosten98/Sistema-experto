public class Parser{
	public int contadorDeParentesis;
	public int contador, tamano;
	public String nombreLiteral;

	public void main(String linea_leida, int in){
		linea_leida = linea_leida.replace(" ", "");
		// System.out.println(linea_leida);
		
		nombreLiteral = "";
	
		revisarCadenas(linea_leida);
		
	}

	public void revisarCadenas(String cadena){
		System.out.println(cadena);
		
		tamano = cadena.length()-1;
		if(cadena.charAt(0) == '(' && cadena.charAt(tamano) == ')')
			cadena = cadena.substring(1, tamano);
		contador = 0;
		contadorDeParentesis = 0;

		for(int i = 0; i < cadena.length(); i++){
			System.out.println(i);
			if(cadena.charAt(i) == '('){
				contadorDeParentesis++;
			}else if(cadena.charAt(i) == ')'){
				contadorDeParentesis--;
			}else if(cadena.charAt(i) == '!'){
				cadena = cadena.substring(1, cadena.length());
				if(contadorDeParentesis == 0)
					revisarCadenas(cadena);
			}else if(cadena.charAt(i) == '&' || cadena.charAt(i) == '|' || cadena.charAt(i) == '<' || cadena.charAt(i) == '-'){
				// Por la derecha
				revisarCadenas(cadena.substring(i+1, cadena.length()));
				// Por la izquierda
				revisarCadenas(cadena.substring(0, i+1));
			}

			// else if(cadena.charAt(i) >= 'A' && cadena.charAt(0) >= 'Z' || cadena.charAt(i) >= 'a' && cadena.charAt(0) >= 'z' || cadena.charAt(i) >= '0' && cadena.charAt(0) >= '9'){
				
			// }
		}
	}
}	
