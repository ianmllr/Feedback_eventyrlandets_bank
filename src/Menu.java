import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    public final static Scanner input = new Scanner(System.in);

    static ArrayList<Bankkonto> konti = new ArrayList<>();

    public static void menu() {
        boolean close = false;
        int choice;
        int userInput;
        boolean found = false;

        System.out.println("Velkommen til bankens admin side.");

        while (!close) {
            System.out.println("1. Opret konto");
            System.out.println("2. Vis oprettede konti");
            System.out.println("3. Find konto ud fra kontonummer og rediger indhold");
            System.out.println("4. Indsæt penge på konto");
            System.out.println("5. Hæv penge på konto");
            System.out.println("4. Luk programmet.");

            choice = input.nextInt();

            switch (choice) {
                case 1:
                    try {
                        opretKonto();
                    } catch (InvalidAmountException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 2:
                    for (int i = 0; i < konti.size(); i++) {
                        System.out.println("Konti: ");
                        System.out.println("Konto " + konti.get(i).getAccountNumber() + " har en balance på " + konti.get(i).getBalance() + ".");
                    }
                    break;

                case 3:
                    System.out.println("Indtast nummeret på den konto du ønsker at redigere.");
                    Bankkonto chosenKonto;
                    found = false;
                    while (!found) {
                        userInput = input.nextInt();
                        try {
                            chosenKonto = findKonto(userInput);
                            redigerKonto(chosenKonto);
                            found = true;
                        } catch (AccountNotFoundException | InvalidAmountException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;

                case 4:
                    System.out.println("Indtast kontonummeret på kontoen du vil indsætte penge på.");
                    Bankkonto chosenKonto2;
                    found = false;
                    while (!found) {
                        userInput = input.nextInt();
                        try {
                            chosenKonto2 = findKonto(userInput);
                            indsætPenge(chosenKonto2);
                            found = true;
                        } catch (AccountNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;

                case 5:
                    Bankkonto chosenKonto3;
                    found = false;
                    while (!found) {
                        userInput = input.nextInt();
                        try {
                            chosenKonto3 = findKonto(userInput);
                            hævPenge(chosenKonto3);
                            found = true;
                        } catch (AccountNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;

                case 6:
                    System.out.println("Programmet afsluttes.");
                    close = true;
                default:
                    System.out.println("Ugyldigt input. Prøv igen.");
            }
        }
    }




    // finder konto og returnerer den
    public static Bankkonto findKonto(int kontoNummer) throws AccountNotFoundException {
        for (Bankkonto konto : konti) {
            if (konto.getAccountNumber() == kontoNummer) {
                return konto;
            }
        }
        throw new AccountNotFoundException("Konto ikke fundet.");
    }

    // opret konto
    public static void opretKonto() throws InvalidAmountException {
        int newAccountNumber = 0;
        double newBalance = 0;
        boolean accountMade = false;

        System.out.println("Du har valgt at oprette en konto.");
        while (!accountMade) {
            System.out.println("Indtast det ønskede kontonummer.");
            newAccountNumber = input.nextInt();
            if (newAccountNumber > 0) {
                System.out.println("Indtast kontoens startbalance.");
                newBalance = input.nextDouble();
                if (newBalance > 0 && newBalance < 1000000000) {
                    System.out.println("Konto oprettet.");
                    accountMade = true;
                } else {
                    throw new InvalidAmountException("Ugyldigt beløb. Prøv igen.");
                }
            } else {
                System.out.println("Ugyldigt input. Prøv igen.");
            }
        }
        Bankkonto nyKonto = new Bankkonto(newAccountNumber, newBalance);
        konti.add(nyKonto); // tilføjer kontoen til arraylisten med konti

    }

    // rediger kontos kontonummer eller balance
    public static void redigerKonto(Bankkonto chosenKonto) throws InvalidAmountException {
        boolean closed = false;
        int userInput;
        while (!closed) {
            System.out.println("Du har valgt konto " + chosenKonto.getAccountNumber() + ". Valgmuligheder:");
            System.out.println("1. Rediger kontonummer.");
            System.out.println("2. Rediger balance.");

            switch (input.nextInt()) {
                case 1:
                    System.out.println("Indtast det ønskede nye kontonummer.");
                    userInput = input.nextInt();
                    chosenKonto.setAccountNumber(userInput);
                    break;
                case 2:
                    System.out.println("Indtast den nye ønskede balance.");
                    double newAccountBalance = input.nextDouble();
                    chosenKonto.setBalance(newAccountBalance);
                    break;
                default:
                    System.out.println("Ugyldigt input. Prøv igen.");
            }
            System.out.println("Konto " + chosenKonto.getAccountNumber() + " har nu en balance på " + chosenKonto.getBalance());
            closed = true;
        }
    }

    public static void indsætPenge(Bankkonto konto) {
        double depositedAmount;
        boolean open = true;
        while (open) {
            try {
                System.out.println("Konto " + konto.getAccountNumber() + " har en balance på " + konto.getBalance() + ".");
                System.out.println("Indtast beløbet som du ønsker at indsætte på kontoen.");
                depositedAmount = input.nextDouble();
                if (depositedAmount > 0 && depositedAmount < 1000000000) {
                    konto.deposit(depositedAmount);
                    open = false;
                } else {
                    throw new InvalidAmountException("Ugyldigt beløb. Prøv igen.");
                }
            } catch (InvalidAmountException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void hævPenge(Bankkonto konto) {
        double withdrawnAmount;
        boolean open = true;
        while (open) {
            try {
                System.out.println("Konto " + konto.getAccountNumber() + " har en balance på " + konto.getBalance() + ".");
                System.out.println("Indtast beløbet som du ønsker at hæve fra kontoen.");
                withdrawnAmount = input.nextDouble();
                if (withdrawnAmount > 0 && withdrawnAmount < 1000000000) {
                    konto.withdraw(withdrawnAmount);
                    open = false;
                } else {
                    throw new InvalidAmountException("Ugyldigt beløb. Prøv igen.");
                }
            } catch (InvalidAmountException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
