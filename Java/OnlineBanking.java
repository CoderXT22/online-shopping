package All;

import java.util.List;
import java.util.Scanner;

public class OnlineBanking extends Payment {
// userID:1234,password:1234

    public OnlineBanking(String paymentMethod, String name, List<CartItem> purchasedItems,
                       String phone, String email, String address, User user, String orderID) {
        super(paymentMethod, name, purchasedItems, phone, email, address, user, orderID);
    }

    @Override
    protected boolean doPaymentProcess(Scanner sc, double totalPrice) {
        String selectedBank = selectBank(sc);
        if (selectedBank == null) return false;

        System.out.println("\nYou selected: " + selectedBank);
        System.out.println("\n\t-------- " + selectedBank.toUpperCase() + " LOGIN --------");

        if (!login(sc)) {
            OrderHistoryHandler.saveOrderHistory(user, purchasedItems, totalPrice, "N/A",
                    orderID, name, address, phone, email, "Login to Bank Failed", this.paymentMethod);
            return false;
        }

        System.out.printf("\nLogin successful. Total to process: RM%.2f\n", totalPrice);
        return verifyOTP(sc, totalPrice);
    }

    private String selectBank(Scanner sc) {
            System.out.println("\n\t-------- ONLINE BANK --------");
            System.out.println("\nSelect your bank:");
            System.out.println("1. Maybank");
            System.out.println("2. CIMB");
            System.out.println("3. RHB");
            System.out.println("4. Public Bank");
            System.out.println("0. Cancel");
        while (true) {
            System.out.print("Choice: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1": return "Maybank";
                case "2": return "CIMB";
                case "3": return "RHB";
                case "4": return "Public Bank";
                case "0": return null;
                default: System.out.println("\nInvalid choice '" + choice + "'. Please enter 0-4.");
            }
        }
    }

    private boolean login(Scanner sc) {
        int loginAttempts = 0;
        while (loginAttempts < 3) {
            System.out.print("User ID: ");
            String userID = sc.nextLine().trim();
            System.out.print("Password: ");
            String password = sc.nextLine().trim();

            if (userID.equals("1234") && password.equals("1234")) {
                return true;
            }
            loginAttempts++;
            System.out.println("Incorrect User ID or Password. Attempts left: " + (3 - loginAttempts));
        }
        return false;
    }

    private boolean verifyOTP(Scanner sc, double totalPrice) {
        String actualOTP = generateOTP(4, 6); 
        int otpAttempts = 0;
        while (otpAttempts < 3) {
            System.out.print("Enter OTP: ");
            String enteredOtp = sc.nextLine().trim();
            
            if (enteredOtp.equals(actualOTP)) {
                isPaid = true;
                paymentRef = generateTransactionID();
                System.out.printf("\nPayment of RM%.2f successful via Online Banking.\n", totalPrice);
                System.out.println("Payment Reference: " + paymentRef);
                return true;
            }
            otpAttempts++;
            System.out.println("Invalid OTP. Attempts left: " + (3 - otpAttempts));
        }
        
        System.out.println("\nMaximum OTP attempts reached. Payment failed.");
        OrderHistoryHandler.saveOrderHistory(user, purchasedItems, totalPrice, "N/A",
                orderID, name, address, phone, email, "Failed - OTP", this.paymentMethod);
        return false;
    }
}
