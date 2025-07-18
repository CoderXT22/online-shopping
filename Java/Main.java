package All;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserManager userManager = new UserManager();    
        userManager.registerDefaultAdmin();
        Authentication authService = new Authentication(userManager); 
        
        ProductStore store = new ProductStore();
        store.loadFromFile();       
        store.loadTrashFromFile();
        
        MenuManager.showMainMenu(scanner, userManager, authService);

        scanner.close();
    }

    public static int readIntInput(Scanner scanner, String prompt, int min, int max) {
        int choice;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); 
                if (choice >= min && choice <= max) {
                    return choice;
                }
            } else {
                scanner.nextLine(); 
            }
            System.out.println("Invalid input. Please try again");
        }
    }
}
