import java.util.ArrayList;

public class CreadorReglas{
	public ArrayList<Regla> reglas;
	public ArrayList<String> literales;

	public CreadorReglas(ArrayList<Arbol> arboles){
		// literales = new ArrayList<>();
		reglas = new ArrayList<>();
		main(arboles);
	}

	public void main(ArrayList<Arbol> arboles){
		for(int i = 0; i < arboles.size(); i++) {
			literales = new ArrayList<>();
			tomarLiterales(arboles.get(i));
			crearReglas();
		}
		
		imprimirReglas();
	}

	public void tomarLiterales(Arbol arbol){
		if(arbol.nodo.equals("|")){
			if(arbol.izquierda.literal)
				literales.add(arbol.izquierda.nodo);
			else
				tomarLiterales(arbol.izquierda);

			if(arbol.derecha.literal)
				literales.add(arbol.derecha.nodo);
			else
				tomarLiterales(arbol.derecha);
		}
		else{
			if(arbol.izquierda != null)
				tomarLiterales(arbol.izquierda);
			if(arbol.derecha != null)
				tomarLiterales(arbol.derecha);
		}
	}

	public void crearReglas(){
		for(int i = 0; i < literales.size(); i++){
			reglas.add( new Regla(literales, literales.get(i)) );
		}
	}

	public void imprimirReglas(){
		System.out.println("Reglas:");
		for(int i = 0; i < reglas.size(); i++)
			System.out.println(reglas.get(i).getRegla());
	}

	public ArrayList<Regla> obtenerReglas(){
		return reglas;
	}
}