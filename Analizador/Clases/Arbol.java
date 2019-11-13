public class Arbol {

    /* Atributos */
    private Nodo raiz;

    /* Contructories */    
    public Arbol( String valor ) {
        this.raiz = new Nodo( valor );
    }

    /* Setters y Getters */
    public Nodo getRaiz() {
        return raiz;
    }

    public void setRaiz(Nodo raiz) {
        this.raiz = raiz;
    }

}