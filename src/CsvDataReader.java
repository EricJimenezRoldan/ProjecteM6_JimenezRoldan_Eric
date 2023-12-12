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
        String separador = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(rutaFitxerCSV))) {
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
