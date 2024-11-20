import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    public final static Scanner input = new Scanner(System.in);

    static ArrayList<Bankkonto> konti = new ArrayList<>();

    public static void menu() {
        System.out.println("Velkommen til bankens admin side.");
        boolean close = false;
        int choice = 0;
        while (!close) {
            System.out.println("1. Opret konto");
            System.out.println("2. Vis oprettede konti");
            System.out.println("3. Find konto ud fra kontonummer og rediger indhold");
            System.out.println("4. Luk programmet.");

            choice = input.nextInt();

            switch (choice) {
                case 1:
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
                                System.out.println("Ugyldigt input. Prøv igen.");
                            }
                        } else {
                            System.out.println("Ugyldigt input. Prøv igen.");
                        }
                    }
                    Bankkonto nyKonto = new Bankkonto(newAccountNumber, newBalance);
                    konti.add(nyKonto);
                    break;

                case 2:
                    for (int i = 0; i < konti.size(); i++) {
                        System.out.println("Konti: ");
                        System.out.println("Konto " + konti.get(i).getAccountNumber() + " har en balance på " + konti.get(i).getBalance() + ".");
                    }
                    break;

                case 3:
                    System.out.println("Indtast nummeret på den konto du ønsker at redigere.");
                    Bankkonto chosenKonto = new Bankkonto();

                    int userInput = input.nextInt();
                    boolean closed = false;
                    while (!closed) {
                        try {
                            chosenKonto = findKonto(userInput);
                            closed = true;
                        } catch (AccountNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                    }

                    while (!closed) {
                        System.out.println("Du har valgt konto " + chosenKonto.getAccountNumber() + ". Valgmuligheder:");
                        System.out.println("1. Rediger kontonummer.");
                        System.out.println("2. Rediger balance.");

                        switch (input.nextInt()) {
                            case 1:
                                System.out.println("Indtast det ønskede kontonummer.");
                                userInput = input.nextInt();
                                chosenKonto.setAccountNumber(userInput);
                                break;
                            case 2:
                                System.out.println("Indtast det ønskede balance.");
                                double newAccountBalance = input.nextDouble();
                                chosenKonto.setBalance(newAccountBalance);
                                break;
                            default:
                                System.out.println("Ugyldigt input. Prøv igen.");
                        }



                    }

                    while (!closed) {
                        System.out.println("Indtast nyt kontonummer.");
                        userInput = input.nextInt();
                        chosenKonto.setAccountNumber(userInput);
                        closed = true;
                    }


                    break;

                default:
                    System.out.println("Ugyldigt input. Prøv igen.");
            }
        }
    }

    public static Bankkonto findKonto(int kontoNummer) throws AccountNotFoundException {
        for (Bankkonto konto : konti) {
            if (konto.getAccountNumber() == kontoNummer) {
                return konto;
            }
        }
        throw new AccountNotFoundException("Konto ikke fundet.");
    }

}
