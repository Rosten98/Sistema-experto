import java.util.Hashtable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.StringTokenizer;

public class Diccionario {

    Hashtable<String, String> atomoValor = new Hashtable<String, String>();
    Hashtable<String, String> conclus = new Hashtable<String, String>();

    public void leer() {

        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        String linea = "No se encontró el archivo";
        String descripcion = "", atomo;
        boolean conclu = false;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File ("_diccionario.txt");
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            do {
                linea = br.readLine();

                if (linea != null) {
                    StringTokenizer st = new StringTokenizer(linea, ":");
                    atomo = st.nextToken();
                    if(atomo.equals("---------------------")){
                        conclu = true;
                    }else{
                        while (st.hasMoreTokens())
                            descripcion = descripcion + " " + st.nextToken() + " ";
                        if(conclu){ //se están guardan conclusiones
                            conclus.put(atomo,descripcion);
                        }else{  //se guardan atomos
                            atomoValor.put(atomo,descripcion);
                        }
                        descripcion = "";
                    }
                }
            } while (linea != null);
            br.close();

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            // En el finally cerramos el fichero, para asegurarnos que se
            // cierra tanto si todo va bien como si salta una excepcion.
            try {
                if( null != fr )fr.close();

            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
