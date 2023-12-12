import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvDataReader {
    private List<String[]> dades;

    public CsvDataReader() {
        dades = new ArrayList<>();
    }

    public void llegirFitxerCSV(String rutaFitxerCSV) {
        String linia = "";
        String separador = ","; // Assumeixem que les columnes estan separades per comes.

        try (BufferedReader br = new BufferedReader(new FileReader(rutaFitxerCSV))) {
            // Llegim la capçalera (la primera línia) si hi és i la descartem.
            br.readLine();

            while ((linia = br.readLine()) != null) {
                String[] fila = linia.split(separador);
                dades.add(fila);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String[]> obtenirDades() {
        return dades;
    }
}
