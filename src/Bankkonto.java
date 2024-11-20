public class Bankkonto {
    private int accountNumber;
    private double balance;

    public Bankkonto(int accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public Bankkonto() {}

    public void deposit(double amount) {
        this.balance = balance + amount;
    }

    public void withdraw(double amount) throws InsufficientFundsException, InvalidAmountException {

        if (amount < 0) {
            throw new InvalidAmountException("Ugyldigt beløb");
        } else if (amount > this.balance) {
            throw new InsufficientFundsException("Ikke nok penge på kontoen. Du har " + balance + " kr.");
        } else if (amount > 0) {
            this.balance = balance - amount;
            System.out.println(amount + " trukket fra din konto. Du har " + balance + " kr tilbage.");
        } else {
            System.out.println("Ugyldigt beløb.");
        }
    }

    public double getBalance() {
        return balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }



}
