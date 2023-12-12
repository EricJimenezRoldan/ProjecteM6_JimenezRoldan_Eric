import java.util.List;
import java.util.Scanner;

public class Menu {
    private Scanner scanner;
    private String rutaConfigXML;
    private ConfiguracioXML configuracio;
    private CsvDataReader csvDataReader;
    private String columnaSeleccionada;

    public Menu() {
        scanner = new Scanner(System.in);
        rutaConfigXML = "configuracio.xml"; 
        configuracio = new ConfiguracioXML(rutaConfigXML);
        csvDataReader = new CsvDataReader(configuracio.getRutaFitxerCSV());
        columnaSeleccionada = null;
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
    
        
        List<List<String>> dades = csvDataReader.getDades();
    
        
        csvDataReader.ordenarDatosPorColumna(dades, columnaSeleccionada);
    
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

    }

    private void restablirCriteriOrdenacio() {
       
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
