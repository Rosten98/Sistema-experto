import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ForwardChaining{
	public String hechosInput;
	public Integer cardinalidad;
	public ArrayList<String> hechos;
	public ArrayList<Regla> conocimientoRelevante;
	public StringTokenizer hechosTokens;

	public ForwardChaining(ArrayList<Regla> reglas){
		hechos = new ArrayList<>();
		conocimientoRelevante = reglas;

		// System.out.println("ForwardChaining: ");
		// for(int i = 0; i < conocimientoRelevante.size(); i++){
		// 	System.out.println(i + " " + conocimientoRelevante.get(i).getRegla());
		// }
		main();
	}

	public void main(){
		System.out.println("\n--------------------------------------------------------------\n");
		System.out.println("FORWARD CHAINING\n");

		// Verificar lo que se ingresa, que no pueda ingresar cualquier cosa como hecho, solo atomos y que estos atomos no se repitan y que no este vacio
		Scanner input  = new Scanner(System.in);
		System.out.println("Ingresa los hechos separados por una coma");
		hechosInput = input.nextLine();

		guardarHechos();
		inferir();

		System.out.println(hechos);
	}

	public void guardarHechos(){
		hechosTokens = new StringTokenizer(hechosInput, ",");

		while(hechosTokens.hasMoreTokens()){
			hechos.add(hechosTokens.nextToken());
		}
		cardinalidad = hechos.size();
	}

	public void inferir(){
		Integer cardinalidadAnterior = 0;
		String inferencia;
		boolean flag;

		while(cardinalidadAnterior != cardinalidad && !conocimientoRelevante.isEmpty()){
			cardinalidadAnterior = cardinalidad;
			// System.out.println(conocimientoRelevante);

			for(int i = 0; i < conocimientoRelevante.size(); i++){
				inferencia = reglaParser(conocimientoRelevante.get(i).getRegla());
				flag = false;

				if(!inferencia.equals("nulo")){
					for(int j = 0; j < hechos.size(); j++){
						if(hechos.get(j).equals(inferencia)){
							flag = true;
							break;
						}
					}
					if(!flag){
						hechos.add(inferencia);
						cardinalidad = hechos.size();
						conocimientoRelevante.remove(i);
						System.out.println(hechos);
						System.out.println("Se ha inferido: " + inferencia + "\n¿Desea continuar? (Y/N)");

						Scanner input  = new Scanner(System.in);
						if(input.nextLine().equals("N"))
							return;
					}
				}
			}
		}
	}

	public String reglaParser(String regla){
		ArrayList<String> antecedentes = new ArrayList<>();
		StringTokenizer antecedenteConsecuente = new StringTokenizer(regla, "->");
		String consecuente = "";
		Integer tokens, count, noAntecedentes = 0, noHechosEncontrados = 0;

		tokens = antecedenteConsecuente.countTokens();
		while(antecedenteConsecuente.hasMoreTokens()){
			if(tokens > 1){
				StringTokenizer literalesAntecedente = new StringTokenizer(antecedenteConsecuente.nextToken(), "&");

				while(literalesAntecedente.hasMoreTokens()){
					antecedentes.add(literalesAntecedente.nextToken());
				}
			}else{
				consecuente = antecedenteConsecuente.nextToken();
			}
			tokens--;
		}

		noAntecedentes = antecedentes.size();

		for(int i = 0 ; i < antecedentes.size(); i++){
			for(int j = 0; j < hechos.size(); j++){
				if(antecedentes.get(i).equals( hechos.get(j) )){
					noHechosEncontrados++;
					break;
				}
			}
		}

		if(noHechosEncontrados == noAntecedentes){
			return consecuente;
		}else{
			return "nulo";
		}
	}
}
