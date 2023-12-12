import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvDataReader {
    private String rutaFitxerCSV;
    private List<List<String>> dades;

    public CsvDataReader(String rutaFitxerCSV) {
        this.rutaFitxerCSV = rutaFitxerCSV;
        this.dades = new ArrayList<>(); 
    }

    public List<List<String>> getDades() {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaFitxerCSV))) {
            String linia;
            boolean primeraLinia = true;

            while ((linia = br.readLine()) != null) {
                String[] parts = linia.split(",");
                List<String> fila = new ArrayList<>();

                for (String part : parts) {
                    fila.add(part);
                }

                dades.add(fila);

                if (primeraLinia) {
                    primeraLinia = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dades;
    }

    public int obtenerIndiceColumna(String nomColumna) {
        if (dades.isEmpty()) {
            return -1;
        }
    
        List<String> primeraFila = dades.get(0);
    
        for (int i = 0; i < primeraFila.size(); i++) {
            if (primeraFila.get(i).equalsIgnoreCase(nomColumna)) {
                return i;
            }
        }
    
        return -1;
    }
    public void ordenarDatosPorColumna(List<List<String>> datos, String columnaSeleccionada) {
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
                    String valor1 = fila1.get(columnaIdx);
                    String valor2 = fila2.get(columnaIdx);

                    if (columnaIdx == 1 || columnaIdx == 3) {  
                        try {
                            int numValor1 = Integer.parseInt(valor1);
                            int numValor2 = Integer.parseInt(valor2);
                            return Integer.compare(numValor1, numValor2);
                        } catch (NumberFormatException e) {
                            
                            return valor1.compareTo(valor2);
                        }
                    } else {
                        return valor1.compareTo(valor2);
                    }
                });
            }
        }
    }
}

