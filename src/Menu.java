import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private CsvDataReader csvDataReader;

    public Menu(CsvDataReader csvDataReader) {
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
        CsvDataReader csvDataReader = new CsvDataReader();
        Menu menu = new Menu(csvDataReader);
        menu.mostrarMenu();
    }
}
