import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    public final static Scanner input = new Scanner(System.in);
    static ArrayList<Bankkonto> konti = new ArrayList<>();

    public static void menu() {
        boolean close = false;
        int choice;
        int userInput;
        boolean found;

        System.out.println("Velkommen til bankens admin side");

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
                    if (!konti.isEmpty()) {
                        System.out.println("Konti: ");
                        for (Bankkonto bankkonto : konti) {
                            System.out.println("Konto " + bankkonto.getAccountNumber() + " har en balance på " + bankkonto.getBalance() + ".");
                        }
                    } else {
                        System.out.println("Ingen konti oprettet.");
                    }
                    break;

                case 3:
                    if (!konti.isEmpty()) {
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
                    } else {
                        System.out.println("Ingen konti oprettet.");
                    }
                    break;

                case 4:
                    if (!konti.isEmpty()) {
                        Bankkonto chosenKonto2;
                        found = false;
                        System.out.println("Indtast kontonummeret på kontoen du vil indsætte penge på.");
                        while (!found) {
                            userInput = input.nextInt();
                            try {
                                chosenKonto2 = findKonto(userInput);
                                indsaetPenge(chosenKonto2);
                                found = true;
                            } catch (AccountNotFoundException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    } else {
                        System.out.println("Ingen konti oprettet.");
                    }
                    break;

                case 5:
                    if (!konti.isEmpty()) {
                        Bankkonto chosenKonto3;
                        found = false;
                        System.out.println("Indtast kontonummeret på kontoen du vil hæve penge fra.");
                        while (!found) {
                            userInput = input.nextInt();
                            try {
                                chosenKonto3 = findKonto(userInput);
                                haevPenge(chosenKonto3);
                                found = true;
                            } catch (AccountNotFoundException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    } else {
                        System.out.println("Ingen konti oprettet.");
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
                    accountMade = true;
                } else {
                    throw new InvalidAmountException("Ugyldigt beløb. Prøv igen.");
                }
            } else {
                System.out.println("Ugyldigt input. Prøv igen.");
            }
        }
        Bankkonto nyKonto = new Bankkonto(newAccountNumber, newBalance);
        System.out.println("Konto " + nyKonto.getAccountNumber() + " oprettet med en balance på " + nyKonto.getBalance() + ".");
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
            System.out.println("Konto opdateret.");
            closed = true;
        }
    }

    // indsæt penge på konto
    public static void indsaetPenge(Bankkonto konto) {
        double depositedAmount;
        boolean open = true;
        while (open) {
            try {
                System.out.println("Konto " + konto.getAccountNumber() + " har en balance på " + konto.getBalance() + ".");
                System.out.println("Indtast beløbet som du ønsker at indsætte på kontoen.");
                depositedAmount = input.nextDouble();
                if (depositedAmount > 0 && depositedAmount < 1000000000) {
                    konto.deposit(depositedAmount);
                    System.out.println("Konto " + konto.getAccountNumber() + " har nu en balance på " + konto.getBalance() + ".");
                    open = false;
                } else {
                    throw new InvalidAmountException("Ugyldigt beløb. Prøv igen.");
                }
            } catch (InvalidAmountException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // hæv penge fra konto
    private static void haevPenge(Bankkonto konto) {
        double withdrawnAmount;
        boolean open = true;
        System.out.println("Konto " + konto.getAccountNumber() + " har en balance på " + konto.getBalance() + ".");
        while (open) {
            try {
                System.out.println("Indtast beløbet som du ønsker at hæve fra kontoen.");
                withdrawnAmount = input.nextDouble();
                if (withdrawnAmount > konto.getBalance()) {
                    throw new InsufficientFundsException("Ikke nok penge på kontoen.");
                } else if (withdrawnAmount > 0 && withdrawnAmount < 1000000000) {
                    konto.withdraw(withdrawnAmount);
                    System.out.println("Konto " + konto.getAccountNumber() + " har nu en balance på " + konto.getBalance() + ".");
                    open = false;
                } else {
                    throw new InvalidAmountException("Ugyldigt beløb.");
                }
            } catch (InsufficientFundsException | InvalidAmountException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
