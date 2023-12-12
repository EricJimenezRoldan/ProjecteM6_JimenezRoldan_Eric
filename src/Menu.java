import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private CsvDataReader csvDataReader;
    private ConfiguracioXML configuracion;

    public Menu(String rutaConfiguracion, CsvDataReader csvDataReader) {
        this.configuracion = new ConfiguracioXML(rutaConfiguracion);
        this.csvDataReader = csvDataReader;
    }

    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcio;

        do {
            System.out.println("----- Menú -----");
            System.out.println("1. Mostrar la lista de lenguajes");
            System.out.println("2. Definir filtros por columnas");
            System.out.println("3. Resetear los filtros por columnas");
            System.out.println("4. Definir criterio de ordenación por columnas");
            System.out.println("5. Resetear el criterio de ordenación por columnas");
            System.out.println("6. Guardar en formato JSON");
            System.out.println("7. Comparativa de lenguajes en los últimos 5 años");
            System.out.println("8. Salir");
            System.out.print("Selecciona una opción: ");
            
            try {
                opcio = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Error: Ingresa un número válido.");
                scanner.nextLine(); 
                opcio = -1; 
            }
            
            switch (opcio) {
                case 1:
                    mostrarDades();
                    break;
                case 2:
                    definirFiltres();
                    break;
                case 3:
                    resetejarFiltres();
                    break;
                case 4:
                    definirCriteriOrdenacio();
                    break;
                case 5:
                    resetejarCriteriOrdenacio();
                    break;
                case 6:
                    desarDadesEnJSON();
                    break;
                case 7:
                    comparativaLlenguatges();
                    break;
                case 8:
                    System.out.println("Adéu!");
                    break;
                default:
                    System.out.println("Opció no vàlida. Torna a intentar.");
                    break;
            }
            
        } while (opcio != 8);
    }

    public void mostrarDades() {
        csvDataReader.llegirFitxerCSV(configuracion.getRutaFitxerCSV());
        List<String[]> dadesSenseOrdenar = csvDataReader.obtenirDades(); 
    
        if (dadesSenseOrdenar.isEmpty()) {
            System.out.println("No se encontraron datos que cumplan con los filtros.");
            return;
        }
    
        int limiteRegistros = configuracion.getLimitRegistres();
        boolean hayFiltrosColumnas = false; //S'implementara mes endavant
        boolean hayFiltrosFilas = false; //S'implementara mes endavant
    
        int registrosMostrados = 0;
    
        for (String[] fila : dadesSenseOrdenar) {
    
            if ((limiteRegistros > 0 && registrosMostrados < limiteRegistros)) {
                String name = fila[0];
                String year = fila[1];
                String quarter = fila[2];
                String count = fila[3];
    
                System.out.println("Nombre: " + name);
                System.out.println("Año: " + year);
                System.out.println("Trimestre: " + quarter);
                System.out.println("Cantidad de personas: " + count);
                System.out.println();
    
                registrosMostrados++;
            }
        }
    }
    
    
    
    public void definirFiltres() {
        
    }

    public void resetejarFiltres() {
        
    }

    public void definirCriteriOrdenacio() {
        
    }

    public void resetejarCriteriOrdenacio() {
        
    }

    public void desarDadesEnJSON() {
        
    }

    public void comparativaLlenguatges() {
        
    }

    public static void main(String[] args) {
        // Aquí proporciona la ruta del archivo XML que has creado
        String rutaConfiguracionXML = "configuracio.xml";
        
        CsvDataReader csvDataReader = new CsvDataReader();
        Menu menu = new Menu(rutaConfiguracionXML, csvDataReader);
        menu.mostrarMenu();
    }
}
