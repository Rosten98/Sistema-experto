import java.util.ArrayList;

public class Regla{
	public ArrayList<String> antecedentes;
	public String consecuente, regla;

	public Regla(ArrayList<String> antecedentes, String consecuente){
		this.antecedentes = new ArrayList<>();
		this.consecuente = consecuente;
		regla = "";

		for(int i = 0; i < antecedentes.size(); i++){
			String literal = antecedentes.get(i);
			if(!consecuente.equals(literal))
				if(literal.charAt(0) == '_')
					this.antecedentes.add(literal.substring(1, literal.length()));
				else
					this.antecedentes.add("_" + literal);
		}

		createRegla();
	}

	public void createRegla(){
		// System.out.println(antecedentes);
		for(int i = 0; i < antecedentes.size(); i++){
			regla = regla + antecedentes.get(i);
			if(i+1 < antecedentes.size())
				regla += "&";
		}
		regla = regla + "->" + consecuente;
	}
	public String getRegla(){
		
		return regla;
	}
}