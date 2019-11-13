import java.util.*;
import java.util.Map.Entry;

public class BackwardChaining {
  public String hechosInput;
  public String conclu;
	public ArrayList<String> hechos;
  public ArrayList<Regla> reglas;
	public StringTokenizer hechosTokens;
  public Hashtable<String, String> atomoValor = new Hashtable<String, String>();
  public Hashtable<String, String> conclus = new Hashtable<String, String>();

	public BackwardChaining(ArrayList<Regla> reglas_){
		hechos = new ArrayList<>();
		this.reglas = reglas_;
		// System.out.println("BackwardChaining: ");
		// for(int i = 0; i < reglas.size(); i++){
		// 	System.out.println(i + " " + reglas.get(i).getRegla());
		// }
    Diccionario dic = new Diccionario();
		dic.leer();
		atomoValor = dic.atomoValor;
		conclus = dic.conclus;
		main();
	}
  public void main(){
		System.out.println("\n--------------------------------------------------------------\n");
    System.out.println("BACKWARD CHAINING\n");

		// Verificar lo que se ingresa, que no pueda ingresar cualquier cosa como hecho, solo atomos y que estos atomos no se repitan y que no este vacio
		Scanner input  = new Scanner(System.in);
		System.out.println("Ingresa tu conclusion");
    imprimir(conclus);
		conclu = input.nextLine();
    // System.out.println(conclu);

    input  = new Scanner(System.in);
		System.out.println("Ingresa los hechos separados por una coma");
    imprimir(atomoValor);
		hechosInput = input.nextLine();
		guardarHechos();
		// System.out.println(hechos);

    if(back(conclu)){
			// System.out.println("La conclusion es VERDADERA");
      System.out.println("\nConclusion: ");
      imprimirConclu(conclus, conclu);
		}else{
      System.out.println("\nNo se llego a la conclusion");
		}
	}

  public boolean back(String conclu){
		boolean encontro = true;
		for(int i = 0; i < reglas.size(); i++){ //Se busca la conclusión en los consecuentes
			if(reglas.get(i).consecuente.equals(conclu)){
				for(String ante : reglas.get(i).antecedentes){ //Recorre los antecedentes cuyo cons es la
					if(hechos.contains(ante)){  //El antecedente es un hecho
						encontro = encontro & true;
					}else{ 				//El antencedente NO es un hecho
						encontro = encontro & back(ante);
					}
				}
				//ya revisó todos los antecedentes
				return encontro;
			}
		}
		return false; //si no se encontró en consecuentes
	}

  public void imprimirConclu(Hashtable<String, String> imp, String conclu){
    Enumeration e = imp.keys();
		String key, valor;
    for (Entry<String, String> entry : imp.entrySet()) {
        if(entry.getKey().equals(conclu)){
          System.out.print(entry.getValue());
          break;
        }
    }
  }

  public void imprimir(Hashtable<String, String> imp){
		Enumeration e = imp.keys();
		String key, valor;

		for (Entry<String, String> entry : imp.entrySet()) {
  			System.out.println(entry.getKey() + " = " + entry.getValue());
		}
	}

	public void guardarHechos(){
		hechosTokens = new StringTokenizer(hechosInput, ",");
		while(hechosTokens.hasMoreTokens()){
			hechos.add(hechosTokens.nextToken());
		}
	}
}
