import java.util.ArrayList;

public class FormaNC {
	Arbol izquierda;
	Arbol derecha;
	ArrayList<Arbol> arbolesFNC;

	public FormaNC(ArrayList<Arbol> arboles){
		arbolesFNC = new ArrayList<>();
		main(arboles);
	}

	public void main(ArrayList<Arbol> arboles){
		for(int i = 0; i < arboles.size(); i++) {
			System.out.println("\nDoble implicacion:");
			dobleImplicacion(arboles.get(i));
			arboles.get(i).inorden();

			System.out.println("\nImplicacion:");
			implicacion(arboles.get(i));
			arboles.get(i).inorden();

			System.out.println("\nNegacion:");
			negaciones(arboles.get(i));
			arboles.get(i).inorden();

			System.out.println("\nDistribucion:");
			distribucion(arboles.get(i));
			arboles.get(i).inorden();

			System.out.println("\n");
		}
	}

	public void dobleImplicacion(Arbol arbol){
		if(arbol.izquierda == null && arbol.derecha == null)
			return;

		if(!arbol.nodo.equals("<->")) {
			if(arbol.izquierda != null)
				dobleImplicacion(arbol.izquierda);
			if(arbol.derecha != null)
				dobleImplicacion(arbol.derecha);
		} else{
			izquierda = arbol.izquierda;
			derecha = arbol.derecha;
			
			arbol.setIzquierda(new Arbol("->", izquierda, derecha));
			arbol.setDerecha(new Arbol("->", derecha, izquierda));
			
			arbol.setNodo("&");

			if(arbol.izquierda != null)
				dobleImplicacion(arbol.izquierda);
			if(arbol.derecha != null)
				dobleImplicacion(arbol.derecha);
		}
	}

	public void implicacion(Arbol arbol){
		if(arbol.izquierda == null && arbol.derecha == null)
			return;

		if(!arbol.nodo.equals("->")) {
			if(arbol.izquierda != null)
				implicacion(arbol.izquierda);
			if(arbol.derecha != null)
				implicacion(arbol.derecha);
		} else{
			izquierda = arbol.izquierda;
			derecha = arbol.derecha;
			
			arbol.setIzquierda(new Arbol("!", null, izquierda));
			arbol.setDerecha(derecha);
			
			arbol.setNodo("|");

			if(arbol.izquierda != null)
				dobleImplicacion(arbol.izquierda);
			if(arbol.derecha != null)
				dobleImplicacion(arbol.derecha);
		}
	}

	public void negaciones(Arbol arbol){
		if(arbol.izquierda == null && arbol.derecha == null)
			return;

		if(!arbol.nodo.equals("!")) {
			if(arbol.izquierda != null)
				negaciones(arbol.izquierda);
			if(arbol.derecha != null)
				negaciones(arbol.derecha);
		} else{
			derecha = arbol.derecha;
		
			if(derecha.nodo.equals("&")){
				arbol.setNodo("|");
				arbol.setIzquierda(new Arbol("!", null, derecha.izquierda));
				arbol.setDerecha(new Arbol("!", null, derecha.derecha));

				if(arbol.izquierda != null)
					negaciones(arbol.izquierda);
				if(arbol.derecha != null)
					negaciones(arbol.derecha);

			}
			else if(derecha.nodo.equals("|")){
				arbol.setNodo("&");
				arbol.setIzquierda(new Arbol("!", null, derecha.izquierda));
				arbol.setDerecha(new Arbol("!", null, derecha.derecha));

				if(arbol.izquierda != null)
					negaciones(arbol.izquierda);
				if(arbol.derecha != null)
					negaciones(arbol.derecha);
				
			}
			else if(derecha.nodo.equals("!")){
				arbol.setNodo(derecha.derecha.nodo);
				arbol.setIzquierda(derecha.derecha.izquierda);
				arbol.setDerecha(derecha.derecha.derecha);

				negaciones(arbol);
			} 
			else if(!derecha.nodo.equals("!") && !derecha.nodo.equals("&") && !derecha.nodo.equals("|")){
				if(derecha.nodo.charAt(0) != '_')
					arbol.setNodo("_" + derecha.nodo);
				else
					arbol.setNodo(derecha.nodo.substring(1, derecha.nodo.length()));
				
				arbol.setIzquierda(null);
				arbol.setDerecha(null);

				if(arbol.izquierda != null)
					negaciones(arbol.izquierda);
				if(arbol.derecha != null)
					negaciones(arbol.derecha);
			}
		}
	}

 	public void distribucion(Arbol arbol){
 		arbol.literalOperando();
 		if(arbol.izquierda != null)
            arbol.izquierda.literalOperando();
        if(arbol.derecha != null)   
            arbol.derecha.literalOperando();

        Arbol tempDerecha,tempIzquierda,tempLiteral;
        if(arbol.derecha != null && arbol.izquierda != null && arbol.nodo.equals("|")){
            if(((arbol.derecha.literal && arbol.izquierda.nodo.equals("&")) || (arbol.derecha.nodo.equals("&") && arbol.izquierda.literal))){
                if((arbol.derecha.literal && arbol.izquierda.operando)){
                    tempDerecha = arbol.derecha;
                    arbol.derecha = arbol.izquierda;
                    arbol.izquierda = tempDerecha;
                }
                tempLiteral = arbol.izquierda;
                String operando = arbol.derecha.nodo;
                    
                arbol.izquierda = new Arbol(arbol.nodo, tempLiteral, arbol.derecha.izquierda);
                arbol.derecha = new Arbol(arbol.nodo, tempLiteral, arbol.derecha.derecha);
                arbol.nodo = operando;
            }
            if(arbol.derecha.nodo.equals("&") && arbol.izquierda.nodo.equals("&")){
                Arbol temp1 = new Arbol("|", arbol.izquierda.izquierda, arbol.derecha.izquierda);
                Arbol temp2 = new Arbol("|", arbol.izquierda.izquierda, arbol.derecha.derecha);
                Arbol temp3 = new Arbol("|", arbol.izquierda.derecha, arbol.derecha.izquierda);
                Arbol temp4 = new Arbol("|", arbol.izquierda.derecha, arbol.derecha.derecha);
                
                arbol.derecha = new Arbol("&", temp3, temp4);
                arbol.izquierda = new Arbol("&", temp1, temp2);
                
                arbol.nodo = "&";
            }
        }
        if(arbol.derecha != null)
            distribucion(arbol.derecha);
        
        if(arbol.izquierda != null)
            distribucion(arbol.izquierda);   
  	}
}
