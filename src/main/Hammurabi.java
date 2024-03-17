package hammurabi.src.main;               // package declaration

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Random;         // imports go here
import java.util.Scanner;

import static jdk.nashorn.tools.ShellFunctions.input;

public class Hammurabi {         // must save in a file named Hammurabi.java
    Random rand = new Random();  // this is an instance variable
    Scanner scanner = new Scanner(System.in);
    private int bushelsOwned = 2800;
    private int peopleOwned = 100;
    private int starvedPeople = 0;
    private int acresOwned = 1000;
    private int tradingPrice = 19;
    private int harvested;
    private int immigrants = 0;

    public static void main(String[] args) { // required in every Java program
        new Hammurabi().playGame();
    }//main

    void playGame() {

        System.out.println("                                 ---Hammurabi---");
        System.out.println("            The classic game of strategy and resource allocation");
        System.out.println("Hammurabi: I beg to report to you,");

        int i = 1;
        while (i < 11) {

            System.out.println(printResult(i));


            //ask how many acres to buy or sell
            while (true) {

                int acresBuyOrSell = getNumber("How many acres do you wish to buy or sell?");
                if (acresBuyOrSell > 0 && acresBuyOrSell * tradingPrice <= bushelsOwned) {
                    askHowManyAcresToBuy(acresBuyOrSell);
                    break;
                } else if (acresBuyOrSell < 0 && acresBuyOrSell * tradingPrice <= bushelsOwned) {
                    askHowManyAcresToSell(acresBuyOrSell);
                    break;
                } else {
                    System.out.println("Hammurabi: Thank again. You have only " + bushelsOwned + " acres of grain.");
                }
            }
            System.out.println(bushelsOwned + " bushels remaining");


            while (true) {
                //ask how many people to feed
                int bush = getNumber("How many bushels do you wish to feed your people?");
                askHowMuchGrainToFeedPeople(bush);
                break;
            }
            System.out.println(bushelsOwned + " bushels remaining");


            //ask how many acres do you wish plant with seed
            int acrePlant = getNumber("How many acres do you wish to plant with seed?");
            askHowManyAcresToPlant(acrePlant, peopleOwned, acresOwned);
            System.out.println(bushelsOwned + " bushels remaining");

            boolean flag = uprising(peopleOwned,starvedPeople);
            if(flag){
                System.out.println("You've been kicked out of office!");
                break;
            }

            System.out.println("------------------------------------------------------------------");
            System.out.println();
            i++;
        }//while loop
        System.out.println(acresOwned);
        System.out.println(bushelsOwned);
        System.out.println(peopleOwned);

    }


    public int askHowManyAcresToBuy(int acres) {
        int tradingPrice = newCostOfLand();
        acresOwned = acresOwned + acres;
        bushelsOwned = bushelsOwned - acres * tradingPrice;
        return acresOwned;
    }


    int askHowManyAcresToSell(int acres) {
        int tradingPrice = newCostOfLand();
        if ((-acres) <= acresOwned) {
            acresOwned = acresOwned + acres;
            bushelsOwned = bushelsOwned + (-acres * tradingPrice);
        } else {
            System.out.println("Hammurabi: Thank again. You have only " + bushelsOwned + " acres of grain.");
        }

        return acresOwned;
    }

    public int askHowMuchGrainToFeedPeople(int bushles) {

        if (bushles <= bushelsOwned) {
            bushelsOwned = bushelsOwned - bushles;
        } else {

            System.out.println("Hammurabi: Thank again. You have only " + bushelsOwned + " bushels of grain.");
        }
        return bushelsOwned;
    }


    int askHowManyAcresToPlant(int acre, int people, int bush) {
        if (acre <= acresOwned && people >= acresOwned && acre <= bush) {
            bushelsOwned = bushelsOwned - acre;
            bushelsOwned = bushelsOwned + acre * harvested;
        }
        return bushelsOwned;
    }

    public int harvest() {
        harvested = rand.nextInt(7);
        return harvested;
    }

    public int newCostOfLand() {
        tradingPrice = rand.nextInt(7) + 17;
        return tradingPrice;
    }

    public int plagueDeaths(int people) {
        return rand.nextInt(100) < 15 ? peopleOwned / 2 : 0;
    }

    public int grainEatenByRats() {
        int ratAte = rand.nextInt(10) + 20;
        int rat = bushelsOwned * ratAte / 100;
        bushelsOwned = bushelsOwned - bushelsOwned * ratAte / 100;
        return rat;
    }

    boolean uprising(int peopleOwned, int starvedPeople) {
        return starvedPeople > (peopleOwned * 0.45);
    }

    public int immigrants(int peopleOwned, int acresOwned, int bushelsOwned) {
        int number = (20 * acresOwned + bushelsOwned) / (100 * peopleOwned) + 1;
        return peopleOwned + number;
    }


    public int starvationDeaths(int population, int bushelsFedToPeople) {
        int number = bushelsFedToPeople / 20;
        starvedPeople = peopleOwned - number;
        peopleOwned = peopleOwned - number;
        return starvedPeople;
    }


    public String printResult(int i) {
        StringBuilder sb = new StringBuilder();

        sb.append("You are in year " + i + " of your ten year rule.").append("\n")
                .append("In the previous year " + starvedPeople + " people starved to death.").append("\n")
                .append(immigrants + " people came to the city.").append("\n")
                .append("Message when people dies").append("\n")
                .append("The city population is now " + peopleOwned).append("\n")
                .append("The city now owns " + acresOwned + " acres.").append("\n")
                .append("You harvested " + harvest() + " bushels per acre.").append("\n")
                .append("Rats ate " + grainEatenByRats() + " bushels.").append("\n")
                .append("You now have " + bushelsOwned + " bushels in store.").append("\n")
                .append("Land is trading at " + tradingPrice + " bushels per acre.");
        return sb.toString();

    }

    /**
     * @param message The request to present to the user.
     * @return The user's numeric response.
     */
    int getNumber(String message) {
        while (true) {
            System.out.print(message + "\t");
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("\"" + scanner.next() + "\" isn't a number!");
            }
        }
    }
}//class