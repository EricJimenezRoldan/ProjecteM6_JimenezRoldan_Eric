import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvDataReader {
    private String rutaFitxerCSV; // Emmagatzema la ruta del fitxer CSV.
    private List<List<String>> dades; // Emmagatzema les dades del fitxer CSV com a una llista de llistes de cadenes.

    public CsvDataReader(String rutaFitxerCSV) { // Constructor de la classe que rep la ruta del fitxer CSV.
        this.rutaFitxerCSV = rutaFitxerCSV; // Assigna la ruta del fitxer CSV.
        this.dades = new ArrayList<>(); // Inicialitza la llista de dades.
    }

    public List<List<String>> getDades() { // Mètode per obtenir les dades del fitxer CSV.
        dades.clear(); // Netega les dades existents a la llista.

        try (BufferedReader br = new BufferedReader(new FileReader(rutaFitxerCSV))) { // Obre el fitxer CSV per a la lectura.
            String linia;
            boolean primeraLinia = true;

            while ((linia = br.readLine()) != null) { // Llegeix cada línia del fitxer CSV.
                if (primeraLinia) { // Ignora la primera línia (capçalera).
                    primeraLinia = false;
                    continue;
                }

                String[] parts = linia.split(","); // Divideix la línia en parts utilitzant ',' com a separador.
                List<String> fila = new ArrayList<>(); // Crea una llista per emmagatzemar les parts.

                for (String part : parts) { // Recorre les parts i les afegeix a la llista.
                    fila.add(part);
                }

                dades.add(fila); // Afegeix la llista de parts a la llista de dades.
            }
        } catch (IOException e) {
            e.printStackTrace(); // Maneig d'errors en cas de problemes en llegir el fitxer.
        }

        return dades; // Retorna la llista de dades.
    }

    public int obtenirIndexColumna(String nomColumna) { // Mètode per obtenir l'índex d'una columna pel seu nom.
        if (dades.isEmpty()) { // Si no hi ha dades, retorna -1.
            return -1;
        }

        List<String> primeraFila = dades.get(0); // Obté la primera fila que conté els noms de les columnes.

        for (int i = 0; i < primeraFila.size(); i++) { // Recorre els noms de les columnes.
            if (primeraFila.get(i).equalsIgnoreCase(nomColumna)) { // Compara el nom de la columna (ignorant majúscules i minúscules).
                return i; // Retorna l'índex de la columna trobada.
            }
        }

        return -1; // Si no es troba la columna, retorna -1.
    }

    // Mètode per ordenar les dades per una columna específica.
    // La lògica d'ordenació es basa en el tipus de columna (nom, any, quarter o count) i la direcció de l'ordre (ascendent o descendent).
    public void ordenarDatosPorColumna(List<List<String>> datos, String columnaSeleccionada, boolean ordenMenorAMayor) {
        if (columnaSeleccionada != null) {
            final int columnaIdx;
    
            switch (columnaSeleccionada.toLowerCase()) {
                case "name":
                    columnaIdx = 0;
                    break;
                case "year":
                    columnaIdx = 1;
                    break;
                case "quarter":
                    columnaIdx = 2;
                    break;
                case "count":
                    columnaIdx = 3;
                    break;
                default:
                    columnaIdx = -1;
            }
    
            if (columnaIdx != -1) {
                datos.sort((fila1, fila2) -> {
                    String valor1 = fila1.get(columnaIdx).replace("\"", "");
                    String valor2 = fila2.get(columnaIdx).replace("\"", "");
    
                    if (columnaIdx == 1 || columnaIdx == 2 || columnaIdx == 3) { 
                        try {
                            int numValor1 = Integer.parseInt(valor1);
                            int numValor2 = Integer.parseInt(valor2);
                            return ordenMenorAMayor ? Integer.compare(numValor1, numValor2) 
                                                     : Integer.compare(numValor2, numValor1);
                        } catch (NumberFormatException e) {
                            return ordenMenorAMayor ? valor1.compareTo(valor2)
                                                     : valor2.compareTo(valor1);
                        }
                    } else {
                        return ordenMenorAMayor ? valor1.compareTo(valor2)
                                                 : valor2.compareTo(valor1);
                    }
                });
            }
        }
    }
    
}


