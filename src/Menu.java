import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.google.gson.Gson;

public class Menu {
    private Scanner scanner;
    private String rutaConfigXML;
    private ConfiguracioXML configuracio;
    private CsvDataReader csvDataReader;
    private String columnaSeleccionada;
    private boolean criteriOrdenacio; 


    public Menu() {
        scanner = new Scanner(System.in);
        rutaConfigXML = "configuracio.xml"; 
        configuracio = new ConfiguracioXML(rutaConfigXML);
        csvDataReader = new CsvDataReader(configuracio.getRutaFitxerCSV());
        columnaSeleccionada = null;
        criteriOrdenacio = true;
    }

    public void iniciar() {
        boolean sortir = false;

        while (!sortir) {
            mostrarMenu();
            if (scanner.hasNextInt()) { 
                int opcio = scanner.nextInt();
                scanner.nextLine(); 

                switch (opcio) {
                    case 1:
                        mostrarDades();
                        break;
                    case 2:
                        definirFiltresPerColumnes();
                        break;
                    case 3:
                        restablirFiltresPerColumnes();
                        break;
                    case 4:
                        definirCriteriOrdenacio();
                        break;
                    case 5:
                        restablirCriteriOrdenacio();
                        break;
                    case 6:
                        desarDadesEnJson();
                        break;
                    case 7:
                        realitzarComparativa();
                        break;
                    case 8:
                        desarComparativaEnTXT("comparativa.txt");
                        break;
                    case 9:
                        sortir = true;
                        scanner.close();
                        break;
                    default:
                        System.out.println("Opció no vàlida. Si us plau, seleccioneu una opció vàlida.");
                }
            } else {
                System.out.println("Entrada no vàlida. Si us plau, introduïu un número.");
                scanner.nextLine(); 
            }
        }
    }

    private void mostrarMenu() {
        System.out.println("Menú Principal:");
        System.out.println("1. Mostrar llista de dades del CSV");
        System.out.println("2. Definir filtres per columnes");
        System.out.println("3. Restablir filtres per columnes");
        System.out.println("4. Definir criteri d'ordenació");
        System.out.println("5. Restablir criteri d'ordenació");
        System.out.println("6. Desar llista filtrada i ordenada en format JSON");
        System.out.println("7. Realitzar comparativa de dades");
        System.out.println("8. Desar comparativa en format TXT");
        System.out.println("9. Sortir");
        System.out.print("Seleccioneu una opció: ");
    }

    private void mostrarDades() {
        int limitRegistres = configuracio.getLimitRegistres();
    
        List<List<String>> dades = csvDataReader.getDades();
    
        csvDataReader.ordenarDatosPorColumna(dades, columnaSeleccionada, criteriOrdenacio);
    
        int totalRegistres = dades.size();
    
        System.out.println("Llista de dades:");
    
        for (int i = 0; i < Math.min(limitRegistres, totalRegistres); i++) {
            List<String> fila = dades.get(i);
            System.out.println(fila);
        }

    }

    private void definirFiltresPerColumnes() {
        System.out.print("Introdueix el nom de la columna (Name, Year, Quarter o Count): ");
        String nomColumna = scanner.nextLine().toLowerCase(); 
    
        if (nomColumna.equals("name") || nomColumna.equals("year") || nomColumna.equals("quarter") || nomColumna.equals("count")) {
            columnaSeleccionada = nomColumna;
            System.out.println("Columna seleccionada per a la ordenació: " + columnaSeleccionada);
        } else {
            System.out.println("Nom de columna no vàlid.");
            columnaSeleccionada = null;
        }
    }

    private void restablirFiltresPerColumnes() {
        columnaSeleccionada = null; 
        System.out.println("Filtres per columnes restablerts.");
    }

    private void definirCriteriOrdenacio() {
        System.out.print("Vols ordenar de menor a major (1) o de major a menor (2)? ");
        int opcio = scanner.nextInt();
    
        if (opcio == 1 || opcio == 2) {
            if (opcio == 1) {
                criteriOrdenacio = true;
            }
            else{
                criteriOrdenacio = false;
            }
            System.out.println("Criteri d'ordenació establert a: " + (opcio == 1 ? "de menor a major" : "de major a menor"));
        } else {
            System.out.println("Opció no vàlida. Si us plau, seleccioneu 1 per a de major a menor o 2 per a de menor a major.");
        }
    }

    private void restablirCriteriOrdenacio() {
       criteriOrdenacio = true;
       System.out.println("Criteri d'ordenacio restablert");
    }

    public void desarDadesEnJson() {
        List<List<String>> dades = csvDataReader.getDades();

        csvDataReader.ordenarDatosPorColumna(dades, columnaSeleccionada, criteriOrdenacio);

        int limitRegistres = configuracio.getLimitRegistres();
        List<List<String>> dadesLimitades = dades.stream().limit(limitRegistres).collect(Collectors.toList());

        Gson gson = new Gson();
        String json = gson.toJson(dadesLimitades);

        String rutaSortida = configuracio.getRutaSortida();
        String rutaArchivo = rutaSortida + "/ordenado.json";

        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void realitzarComparativa() {
        // Obtener los datos del CSV
        List<List<String>> dades = csvDataReader.getDades();

        // Define los lenguajes para la comparativa
        String[] llenguatges = {"Python", "Java", "C#", "JavaScript", "C++"};
        int currentYear = java.time.Year.now().getValue();
        int startYear = currentYear - 5;

        // Inicializar la estructura para almacenar la evolución
        Map<String, Map<Integer, Integer>> evolucio = new HashMap<>();
        for (String llenguatge : llenguatges) {
            evolucio.put(llenguatge, new HashMap<>());
        }

        // Procesar los datos
        for (List<String> fila : dades) {
            String llenguatge = fila.get(0).replace("\"", "");
            try {
                int any = Integer.parseInt(fila.get(1).replace("\"", ""));
                int count = Integer.parseInt(fila.get(3).replace("\"", ""));

                // Verificar si el lenguaje y el año están dentro de los parámetros deseados
                if (any >= startYear && java.util.Arrays.asList(llenguatges).contains(llenguatge)) {
                    Map<Integer, Integer> countsPerYear = evolucio.get(llenguatge);
                    countsPerYear.merge(any, count, Integer::sum);
                }
            } catch (NumberFormatException e) {
                // Manejar posibles errores de formato numérico
                System.err.println("Error en el formato numérico: " + e.getMessage());
            }
        }

        // Mostrar los resultados
        for (String llenguatge : llenguatges) {
            System.out.println("Evolució de " + llenguatge + ":");
            Map<Integer, Integer> countsPerYear = evolucio.get(llenguatge);
            for (int any = startYear; any <= currentYear; any++) {
                System.out.println(any + ": " + countsPerYear.getOrDefault(any, 0));
            }
            System.out.println();
        }
    }

    public void desarComparativaEnTXT(String rutaArchivo) {
        List<List<String>> dades = csvDataReader.getDades();

        // Define los lenguajes para la comparativa
        String[] llenguatges = {"Python", "Java", "C#", "JavaScript", "C++"};
        int currentYear = java.time.Year.now().getValue();
        int startYear = currentYear - 5;

        // Inicializar la estructura para almacenar la evolución
        Map<String, Map<Integer, Integer>> evolucio = new HashMap<>();
        for (String llenguatge : llenguatges) {
            evolucio.put(llenguatge, new TreeMap<>()); // TreeMap mantiene los años ordenados
        }

        // Procesar los datos
        for (List<String> fila : dades) {
            String llenguatge = fila.get(0).replace("\"", "");
            try {
                int any = Integer.parseInt(fila.get(1).replace("\"", ""));
                int count = Integer.parseInt(fila.get(3).replace("\"", ""));

                if (any >= startYear && java.util.Arrays.asList(llenguatges).contains(llenguatge)) {
                    Map<Integer, Integer> countsPerYear = evolucio.get(llenguatge);
                    countsPerYear.merge(any, count, Integer::sum);
                }
            } catch (NumberFormatException e) {
                System.err.println("Error en el formato numérico: " + e.getMessage());
            }
        }

        // Guardar los resultados en un archivo TXT
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (String llenguatge : llenguatges) {
                writer.write("Evolucio de " + llenguatge + ":\n");
                Map<Integer, Integer> countsPerYear = evolucio.get(llenguatge);
                for (Map.Entry<Integer, Integer> entry : countsPerYear.entrySet()) {
                    writer.write(entry.getKey() + ": " + entry.getValue() + "\n");
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.iniciar();
    }
}
