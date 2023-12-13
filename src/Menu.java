import java.util.List;
import java.util.Scanner;

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
                        desarDadesEnJSON();
                        break;
                    case 7:
                        realitzarComparativa();
                        break;
                    case 8:
                        desarComparativaEnTXT();
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
    
        // Obtener los datos de CsvDataReader
        List<List<String>> dades = csvDataReader.getDades();
    
        // Llamar a ordenarDatosPorColumna con la columna seleccionada y el criterio de ordenación
        csvDataReader.ordenarDatosPorColumna(dades, columnaSeleccionada, criteriOrdenacio);
    
        int totalRegistres = dades.size();
    
        System.out.println("Llista de dades:");
    
        for (int i = 0; i < Math.min(limitRegistres, totalRegistres); i++) {
            List<String> fila = dades.get(i);
            System.out.println(fila);
        }
    
        if (totalRegistres > limitRegistres) {
            System.out.println("Hi ha més registres disponibles. Utilitza filtres per veure'n menys.");
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

    private void desarDadesEnJSON() {
        
    }

    private void realitzarComparativa() {
       
    }

    private void desarComparativaEnTXT() {
        
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.iniciar();
    }
}
