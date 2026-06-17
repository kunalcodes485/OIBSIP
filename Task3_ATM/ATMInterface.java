import java.util.*;

public class ATMInterface {
    static Scanner sc = new Scanner(System.in);
    static Map<String, double[]> accounts = new HashMap<>();
    static List<String> transactionHistory = new ArrayList<>();
    static String currentUser = null;

    public static void main(String[] args) {
        accounts.put("123456", new double[]{1111, 50000.00});
        accounts.put("789012", new double[]{2222, 25000.00});
        System.out.println("==========================================");
        System.out.println("         WELCOME TO OIBSIP ATM");
        System.out.println("==========================================");
        if (login()) { showMenu(); }
        else { System.out.println("Card blocked. Contact your bank."); }
        System.out.println("Thank you for using OIBSIP ATM. Bye!");
    }

    static boolean login() {
        int attempts = 0;
        while (attempts < 3) {
            System.out.print("Enter User ID: ");
            String uid = sc.nextLine().trim();
            System.out.print("Enter PIN: ");
            String pin = sc.nextLine().trim();
            if (accounts.containsKey(uid)) {
                try {
                    if (accounts.get(uid)[0] == Double.parseDouble(pin)) {
                        currentUser = uid;
                        System.out.println("Login Successful! Welcome User " + uid);
                        return true;
                    }
                } catch (NumberFormatException ignored) {}
            }
            attempts++;
            System.out.println("Invalid! Attempts left: " + (3 - attempts));
        }
        return false;
    }

    static void showMenu() {
        while (true) {
            System.out.println("\n--- ATM MENU ---");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Check Balance");
            System.out.println("6. Quit");
            System.out.print("Choose: ");
            int ch;
            try { ch = Integer.parseInt(sc.nextLine().trim()); }
            catch (Exception e) { System.out.println("Invalid!"); continue; }
            switch (ch) {
                case 1: showHistory(); break;
                case 2: withdraw(); break;
                case 3: deposit(); break;
                case 4: transfer(); break;
                case 5: checkBalance(); break;
                case 6: return;
                default: System.out.println("Enter 1-6.");
            }
        }
    }

    static void checkBalance() {
        System.out.printf("Current Balance: Rs.%.2f%n", accounts.get(currentUser)[1]);
    }

    static void withdraw() {
        System.out.print("Amount to withdraw: Rs.");
        try {
            double amount = Double.parseDouble(sc.nextLine().trim());
            double balance = accounts.get(currentUser)[1];
            if (amount <= 0) { System.out.println("Invalid amount!"); return; }
            if (amount > balance) { System.out.println("Insufficient balance!"); return; }
            accounts.get(currentUser)[1] -= amount;
            double newBal = accounts.get(currentUser)[1];
            transactionHistory.add(String.format("WITHDRAWAL: Rs.%.2f | Balance: Rs.%.2f", amount, newBal));
            System.out.printf("Withdrawn Rs.%.2f | Remaining: Rs.%.2f%n", amount, newBal);
        } catch (Exception e) { System.out.println("Invalid input!"); }
    }

    static void deposit() {
        System.out.print("Amount to deposit: Rs.");
        try {
            double amount = Double.parseDouble(sc.nextLine().trim());
            if (amount <= 0) { System.out.println("Invalid amount!"); return; }
            accounts.get(currentUser)[1] += amount;
            double newBal = accounts.get(currentUser)[1];
            transactionHistory.add(String.format("DEPOSIT: Rs.%.2f | Balance: Rs.%.2f", amount, newBal));
            System.out.printf("Deposited Rs.%.2f | New Balance: Rs.%.2f%n", amount, newBal);
        } catch (Exception e) { System.out.println("Invalid input!"); }
    }

    static void transfer() {
        System.out.print("Recipient User ID: ");
        String rid = sc.nextLine().trim();
        if (!accounts.containsKey(rid)) { System.out.println("User not found!"); return; }
        if (rid.equals(currentUser)) { System.out.println("Cannot transfer to yourself!"); return; }
        System.out.print("Amount to transfer: Rs.");
        try {
            double amount = Double.parseDouble(sc.nextLine().trim());
            double balance = accounts.get(currentUser)[1];
            if (amount <= 0) { System.out.println("Invalid amount!"); return; }
            if (amount > balance) { System.out.println("Insufficient balance!"); return; }
            accounts.get(currentUser)[1] -= amount;
            accounts.get(rid)[1] += amount;
            double newBal = accounts.get(currentUser)[1];
            transactionHistory.add(String.format("TRANSFER: Rs.%.2f to %s | Balance: Rs.%.2f", amount, rid, newBal));
            System.out.printf("Transferred Rs.%.2f to %s | Remaining: Rs.%.2f%n", amount, rid, newBal);
        } catch (Exception e) { System.out.println("Invalid input!"); }
    }

    static void showHistory() {
        System.out.println("\n--- TRANSACTION HISTORY ---");
        if (transactionHistory.isEmpty()) { System.out.println("No transactions yet."); return; }
        for (int i = 0; i < transactionHistory.size(); i++)
            System.out.println((i+1) + ". " + transactionHistory.get(i));
    }
}
