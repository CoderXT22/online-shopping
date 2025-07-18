package All;

import java.util.List;
import java.util.Scanner;
import java.time.YearMonth;

public class CreditCard extends Payment {

    public CreditCard(String paymentMethod, String name, List<CartItem> purchasedItems,
            String phone, String email, String address, User user, String orderID) {
        super(paymentMethod, name, purchasedItems, phone, email, address, user, orderID);
    }

    @Override
    protected boolean doPaymentProcess(Scanner sc, double totalPrice) {
        String cardNumber = "";
        String expirationDate = "";
        String cvv = "";

        System.out.println("\n\t-------- CREDIT CARD PAYMENT --------");

        // Credit card number validation
        while (true) {
            System.out.print("Enter Credit Card Number (16 digits): ");
            cardNumber = sc.nextLine();
            if (cardNumber.matches("\\d{16}")) {
                break;
            } else {
                System.out.println("Invalid credit card number. It must be 16 digits. Please try again.");
            }
        }

        // Expiration Date validation
        while (true) {
            System.out.print("Enter Expiration Date (MM/YY): ");
            expirationDate = sc.nextLine();
            if (expirationDate.matches("(0[1-9]|1[0-2])\\/\\d{2}")) {
                String[] parts = expirationDate.split("/");
                int expMonth = Integer.parseInt(parts[0]);
                int expYear = 2000 + Integer.parseInt(parts[1]); // Convert YY to YYYY

                YearMonth expiry = YearMonth.of(expYear, expMonth);
                YearMonth now = YearMonth.now();
                if (expiry.isBefore(now)) {
                    System.out.println("Card is expired. Please use a valid card.");
                } else {
                    break;
                }
            } else {
                System.out.println("Invalid expiration date format. Please use MM/YY.");
            }
        }

        // CVV validation
        while (true) {
            System.out.print("Enter CVV (3 digits): ");
            cvv = sc.nextLine();
            if (cvv.matches("\\d{3}")) {
                break;
            } else {
                System.out.println("Invalid CVV. It must be 3 digits. Please try again.");
            }
        }

        // OTP validation (max of 3 attempts)
        String actualOTP = generateOTP(6, 6);
        int attempts = 0;
        while (attempts < 3) {
            System.out.print("Enter OTP : ");
            String enteredOtp  = sc.nextLine();
            
            if (enteredOtp.equals(actualOTP)) {
                isPaid = true;
                paymentRef = generateTransactionID();
                System.out.printf("\nPayment of RM%.2f successful via Credit Card.\n", totalPrice);
                System.out.println("Payment Reference: " + paymentRef);
                return true;
            } else {
                attempts++;
                System.out.println("Invalid OTP. Attempts left: " + (3 - attempts));
            }
        }

        System.out.println("\nMaximum OTP attempts reached. Payment failed.");
        OrderHistoryHandler.saveOrderHistory(user, purchasedItems, totalPrice, "N/A",
                orderID, name, address, phone, email, "Failed - OTP", this.paymentMethod);
        return false;
    }

}
