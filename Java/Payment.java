package All;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public abstract class Payment {
    protected String paymentMethod;
    protected boolean isPaid;
    protected String paymentRef;
    protected double totalPrice;
    protected String name;
    protected List<CartItem> purchasedItems;
    protected String phone;
    protected String email;
    protected String address;
    protected String orderID; 
    protected User user;

    public Payment(String paymentMethod, String name, List<CartItem> purchasedItems, 
                   String phone, String email, String address, User user, String orderID) {
        this.paymentMethod = paymentMethod;
        this.name = name;
        this.isPaid = false;
        this.purchasedItems = purchasedItems;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.user = user;
        this.orderID = orderID;
        this.paymentRef = "";
        this.totalPrice = 0.0;
    }

    public boolean process(double totalPrice, User user) {
        this.totalPrice = totalPrice;
        this.isPaid = doPaymentProcess(new Scanner(System.in), totalPrice);


        // If payment is successful, save order history
        if (isPaid) {
            OrderHistoryHandler.saveOrderHistory(user, purchasedItems, totalPrice, paymentRef, orderID, name, address, phone, email, "Paid",this.paymentMethod);
        }
        return isPaid;
    }


    protected abstract boolean doPaymentProcess(Scanner sc, double totalPrice);
     
    public String generateTransactionID() {
        return "TXN-" + UUID.randomUUID().toString().substring(0, 8);
    }
    
     protected String generateOTP(int minLength, int maxLength) {
        int otpLength = minLength + (int)(Math.random() * (maxLength - minLength + 1));
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < otpLength; i++) {
            otp.append((int)(Math.random() * 10));
        }
         System.out.println("\n[SMS] Your OTP is: " + otp);
        return otp.toString();
    }
}


