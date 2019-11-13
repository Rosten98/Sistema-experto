
import java.util.regex.Pattern;

public class Arbol {
    String nodo;
    String texto;

    Arbol derecha = null;
    Arbol izquierda = null;

    boolean operando;
    boolean literal;

    String letraInicial = "[A-Z]"; 
    String variable = "-|<|&|[|]|>";

    public Arbol(String cadena, Arbol izquierda, Arbol derecha){
        this.nodo = cadena;
        this.izquierda = izquierda;
        this.derecha = derecha;

        literalOperando();
        if(izquierda != null)
            izquierda.literalOperando();
        if(derecha != null)   
            derecha.literalOperando();
    }
    
    public Arbol(String cadena){
        texto = cadena;
        // Verificar los parentesis iniciales y finales
        if(!eliminarParentesis()){  
            // System.out.println("Cadena inicial: " + texto);
        }

        // Separar cadena
        if(!separarCadena()){  
            if(texto.charAt(0) == '!' ){
                nodo = "!";
                derecha = new Arbol(texto.substring(1, texto.length()));
            }
            
            // solo puede entrar a este if cuando sea solo una variable sin parentesis
            if( Pattern.matches( letraInicial, String.valueOf(texto.charAt(0))) ){ 
                nodo = texto;
            }   
        }
    }
    
    public void literalOperando(){
        if(this.nodo.equals("|") || this.nodo.equals("&") || this.nodo.equals("!") || this.nodo.equals("->") || this.nodo.equals("<->")){
            operando = true;
            literal = false;
        }else{
            literal = true;
            operando = false;
        }
    }

    public boolean separarCadena(){
        // los cortes determinan donde empieza el operando y donde termina para cortarlo con substring 
        int corteInicial = 0;
        int cortefinal = 0;
        int parentesis = 0;
        int posicion = 0;
        boolean seguir = false;
        
        while(true){
            if(posicion < texto.length()-1){
                if( texto.charAt(posicion) == '(' ) 
                    parentesis++;
                if( texto.charAt(posicion) == ')' ) 
                    parentesis--;
                if( Pattern.matches(variable, String.valueOf(texto.charAt(posicion))) && parentesis == 0){
                    switch(texto.charAt(posicion)){
                        case '&':
                        case '|':
                            corteInicial = posicion;
                            cortefinal = posicion+1;
                            // imprimirNodo( texto.substring(corteInicial,cortefinal), texto.substring(0, corteInicial), texto.substring(cortefinal, texto.length()));
                            nodo = texto.substring(corteInicial,cortefinal); 
                            izquierda = new Arbol( texto.substring(0, corteInicial) );
                            derecha = new Arbol( texto.substring(cortefinal, texto.length() ) );
                            return true;
                        case '-':
                            if(corteInicial == 0){
                                corteInicial = posicion;
                                cortefinal = posicion+2;
                                
                            }
                        case '<':
                            if(corteInicial == 0){
                                corteInicial = posicion;
                                cortefinal = posicion+3;
                            }
                    }
                }
            }else{
                
                break;
            }
            posicion++;
        } 
        
        if(corteInicial != 0){ 
            // imprimirNodo( texto.substring(corteInicial, cortefinal), texto.substring(0, corteInicial), texto.substring(cortefinal, texto.length()));
            nodo = texto.substring(corteInicial, cortefinal);  
            izquierda = new Arbol(texto.substring(0, corteInicial));
            derecha = new Arbol(texto.substring(cortefinal, texto.length()));
            return true;
        }
        
        return false;
    }
    
    public boolean eliminarParentesis(){
        int parentesis = 0;
        int posicion = 0;
        boolean salir = true;
        boolean retorno = false;

        while(salir){
            posicion = 0;
            parentesis = 0;
            if(texto.charAt(0) == '(' && texto.charAt(texto.length()-1) == ')'){ 
                while(true){
                    if(posicion < texto.length()-1){
                        if(texto.charAt(posicion) == '(' ) 
                            parentesis++;
                        if(texto.charAt(posicion) == ')' ) 
                            parentesis--;
                        if(posicion+1 < texto.length()-1 ) {
                            if(parentesis == 0 ){ 
                                salir = false;
                                break;
                            }    
                        }else{
                            texto = texto.substring(1,texto.length()-1);
                            // System.out.println("Texto: " + texto);
                            retorno = true;
                        }
                    }else{
                        break;
                    }
                    posicion++;
                }   
            }else{
                salir = false;
            }  
        }     
        return retorno;
    }
    
    public void setIzquierda(Arbol nodo){
        this.izquierda = nodo;
    }

    public void setDerecha(Arbol nodo){
        this.derecha = nodo;   
    }
    
    public void setNodo(String nodo){
        this.nodo = nodo;
    }

    public void inorden(){
        if(izquierda != null)
            izquierda.inorden();
        System.out.print(nodo + " ");
        if(derecha != null)
            derecha.inorden();
    }

    public void postorden(){    
        if(izquierda != null)
            izquierda.postorden();
        if(derecha != null)
            derecha.postorden();
        System.out.print(nodo + " ");
    }

    public void preorden(){
        System.out.print(nodo + " ");
        if(izquierda != null)
            izquierda.preorden();
        if(derecha != null)
            derecha.preorden();
    }
}