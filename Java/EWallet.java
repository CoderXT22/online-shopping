package All;

import java.util.List;
import java.util.Scanner;

public class EWallet extends Payment {
//PIN:1234

    public EWallet(String paymentMethod, String name, List<CartItem> purchasedItems,
                  String phone, String email, String address, User user, String orderID) {
        super(paymentMethod, name, purchasedItems, phone, email, address, user, orderID);
    }

    @Override
    protected boolean doPaymentProcess(Scanner sc, double totalPrice) {
        System.out.println("\n\t-------- E-WALLET PAYMENT --------");
        
        // Select provider
        String provider = selectProvider(sc);
        if (provider == null) return false;
        
        System.out.println("\n\t--------" + provider.toUpperCase().replace("", "") + " PAYMENT --------");

        // Verify phone number
        String registeredPhone = verifyPhoneNumber(sc);
        if (registeredPhone == null) return false;

        // Verify PIN
        if (!verifyPin(sc)) {
            OrderHistoryHandler.saveOrderHistory(user, purchasedItems, totalPrice, "N/A",
                    orderID, name, address, phone, email, "Failed - PIN", this.paymentMethod);
            return false;
        }

        // Verify OTP
        if (!verifyOTP(sc)) {
            OrderHistoryHandler.saveOrderHistory(user, purchasedItems, totalPrice, "N/A",
                    orderID, name, address, phone, email, "Failed - OTP", this.paymentMethod);
            return false;
        }
        isPaid = true;
                paymentRef = generateTransactionID();
                System.out.printf("\nPayment of RM%.2f successful via %s E-Wallet.\n", totalPrice, provider);
                System.out.println("Payment Reference: " + paymentRef);
        return true;
    }

    private String selectProvider(Scanner sc) {
            System.out.println("Choose provider:");
            System.out.println("1. Touch 'n Go");
            System.out.println("2. GrabPay");
            System.out.println("3. ShopeePay");
            System.out.println("0. Cancel");
            
        while (true) {
            System.out.print("Choice: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1": return "Touch 'n Go";
                case "2": return "GrabPay";
                case "3": return "ShopeePay";
                case "0": return null;
                default: System.out.println("\nInvalid choice '" + choice + "'. Please enter 0-3.");
            }
        }
    }

    private String verifyPhoneNumber(Scanner sc) {
        while (true) {
            System.out.print("Enter registered mobile number (10-11 digits): ");
            String phone = sc.nextLine();
            if (phone.matches("\\d{10,11}")) {
                return phone;
            }
            System.out.println("Invalid phone number. Please enter a 10 or 11-digit number.");
        }
    }

    private boolean verifyPin(Scanner sc) {
        int pinAttempts = 0;
        while (pinAttempts < 3) {
            System.out.print("Enter 6-digit wallet PIN: ");
            String pin = sc.nextLine();
            if (pin.equals("123456")) {
                return true;
            }
            pinAttempts++;
            System.out.println("Invalid PIN. Attempts left: " + (3 - pinAttempts));
        }
        return false;
    }

    private boolean verifyOTP(Scanner sc) {
        String actualOTP = generateOTP(4, 6);
        int otpAttempts = 0;
        
        while (otpAttempts < 3) {
            System.out.print("Enter OTP: ");
            String enteredOtp = sc.nextLine();
            if (enteredOtp.equals(actualOTP)) {
                return true;
            }
            otpAttempts++;
            System.out.println("Invalid OTP. Attempts left: " + (3 - otpAttempts));
        }
        return false;
    }
}
