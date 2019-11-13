import java.util.ArrayList;
public class Automata {
	public ArrayList<Integer> parentesis;
	public ArrayList<String> atomos;
	public ArrayList<String> arbol_general;

	public boolean esperandoAtomo;
	public boolean esperandoConector;
	public boolean esperandoCierreParentesis;
	public boolean esperandoCierreImplicacion;
	public boolean esperandoGuion;
	public int i, indice;
	public String linea;
	public String atomo;
	
	public void main(String linea_leida, int in){
		linea_leida = linea_leida.replace(" ", "");
		// System.out.println(linea_leida);

		parentesis = new ArrayList<>();
		atomos = new ArrayList<>();

		atomo = "";
		indice = in;
		linea = linea_leida;
		i = 0;

		esperandoAtomo = true;
		esperandoConector = false;
		esperandoCierreParentesis = false;
		esperandoCierreImplicacion = false;
		esperandoGuion = false;

		estado1(linea.charAt(i));
	}

	// Estado 1 revisa el inicio de la Formula bien formada
	public void estado1(char elemento){
		if(elemento == '!' && !esperandoConector){
			if(i+1 < linea.length()){
				i++;
				estado1(linea.charAt(i));
			}else{
				revisarErrores();
			}
		}
		else if(elemento >= 'A' && elemento <= 'Z'){
			estado2(elemento);
		}
		else if(elemento == '(' && !esperandoConector){
			parentesis.add(1);
			if(i+1 < linea.length()){
				i++;
				estado1(linea.charAt(i));
			}else{
				revisarErrores();
			}
		}
		else if(elemento == ')' && !esperandoAtomo){
			if(parentesis.size() > 0){
				parentesis.remove(parentesis.size()-1);
				if(i+1 < linea.length()){
					i++;
					estado1(linea.charAt(i));
				}else{
					esperandoConector = false;
					revisarErrores();
				}
			}else{
				System.out.println("Error: Los parentesis no estan balanceados");
				revisarErrores();	
			}

		}
		else{
			estado3(elemento);
		}
	}

	// Estado 2 revisa los atomos y los agrega a un array que guarda
	// el nombre del átomo
	public void estado2(char elemento){
		if((elemento >= 'A' && elemento <= 'Z' || elemento >= 'a' && elemento <= 'z' || elemento >= '0' && elemento <= '9') && esperandoAtomo){
			atomo += elemento;
			if(i+1 < linea.length()){
				i++;
				estado2(linea.charAt(i));
			}else{
				esperandoAtomo = false;
				esperandoConector = false;
				// atomos.add(atomo);
				agregarAtomoAlArray(atomo);
				atomo = "";
				revisarErrores();
			}
		}
		else{
			esperandoAtomo = false;
			// atomos.add(atomo);
			agregarAtomoAlArray(atomo);
			atomo = "";
			esperandoConector = true;
			estado1(linea.charAt(i));	
		}
	}

	// Estado 3 revisa los conectores que puede haber 
	public void estado3(char elemento){
		if(esperandoConector){
			if(elemento == '&' || elemento == '|'){
				esperandoAtomo = true;
				esperandoConector = false;
				if(i+1 < linea.length()){
					i++;
					estado1(linea.charAt(i));
				}else{
					revisarErrores();
				}
			}else{
				esperandoAtomo = true;
				if(elemento == '<'){
					esperandoGuion = true;
					if(i+1 < linea.length()){
						i++;
						estado3(linea.charAt(i));
					}else{
						revisarErrores();
					}
				}
				else if(elemento == '-'){
					esperandoGuion = false;
					esperandoCierreImplicacion = true;
					if(i+1 < linea.length()){
						i++;
						estado3(linea.charAt(i));
					}else{
						revisarErrores();
					}
				}
				else if(elemento == '>' && esperandoCierreImplicacion && !esperandoGuion){
					esperandoCierreImplicacion = false;
					esperandoConector = false;
					if(i+1 < linea.length()){
						i++;
						estado1(linea.charAt(i));
					}else{
						revisarErrores();
					}
				}
				else{
					revisarErrores();
				}
			}
		}
		else{
			revisarErrores();
		}
	}

	public void revisarErrores(){
		if(esperandoAtomo)
			System.out.println("Error: Esperaba atomo en linea: " + indice);
		if(esperandoConector)
			System.out.println("Error: Esperando conector en linea: " + indice);
		if(esperandoCierreImplicacion || esperandoGuion)
			System.out.println("Error: Esperando cierre implicación: " + indice);
		if(!parentesis.isEmpty()){
			System.out.println("Error: Los parentesis no estan balanceados");
			System.out.println("Cuenta de parentesis: " + parentesis);
		}

		if(!esperandoAtomo && !esperandoConector && !esperandoCierreImplicacion && !esperandoGuion && parentesis.isEmpty()){
			// System.out.println("Linea " + indice + " Correcta");
		}
		else{
			System.exit(0);
		}
	}


	// Agrega atomos a un array que regresará a la clase lector para guardar todos los atomos en un arraylist
	public void agregarAtomoAlArray(String atomo){
		boolean encontrado = false;

		for(int i = 0; i < atomos.size(); i++){
			if(atomos.get(i).equals(atomo)){
				encontrado = true;
				atomos.set(i, atomo);
			}
		}

		if(!encontrado)
			atomos.add(atomo);
	}

	public ArrayList<String> getAtomos(){
		return atomos;
	}

}