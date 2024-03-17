package hammurabi.src.main;               // package declaration
import java.util.InputMismatchException;
import java.util.Random;         // imports go here
import java.util.Scanner;

public class Hammurabi {         // must save in a file named Hammurabi.java
    Random rand = new Random();  // this is an instance variable
    Scanner scanner = new Scanner(System.in);
    private int bushelsOwned = 2800;
   private int peopleOwned = 100;
   private int starvedPeople = 0;
   private int acresOwned = 1000;
   private int tradingPrice = 19;
   private   int harvested = 0;
   private int immigrants = 0;

    public static void main(String[] args) { // required in every Java program
        new Hammurabi().playGame();
    }//main

    void playGame() {
        // declare local variables here: grain, population, etc.


        //gameName

        System.out.println("                                 ---Hammurabi---");
        System.out.println("            The classic game of strategy and resource allocation");
        System.out.println("Hammurabi: I beg to report to you,");


        int i = 1;
        while(i<11){

            printResult(i);

            newCostOfLand();
            System.out.println(acresOwned);
            System.out.println(bushelsOwned);
            System.out.println(peopleOwned);
            System.out.println(tradingPrice);

            //ask how many acres to buy or sell
           int acresBuyOrSell = getNumber("How many acres do you wish to buy or sell?");
           if(acresBuyOrSell>0){
               askHowManyAcresToBuy(acresBuyOrSell);
           }
           else {
               askHowManyAcresToSell(acresBuyOrSell);
           }


           //ask how many people to feed
            int bush = getNumber("How many bushels do you wish to feed your people?");
            askHowMuchGrainToFeedPeople(bush);

            //ask how many acres do you wish plat with seed
            int acrePlant = getNumber("How many acres do you wish to plant with seed?");
            askHowManyAcresToPlant(acrePlant,peopleOwned,acresOwned);
            break;
           //i++;
        }//while loop
        System.out.println(acresOwned);
        System.out.println(bushelsOwned);
        System.out.println(peopleOwned);







        // statements go after the declations
    }




    public int askHowManyAcresToBuy(int acres){
            if (acres * newCostOfLand() <= bushelsOwned) {
                acresOwned = acresOwned + acres;
                bushelsOwned =bushelsOwned -  acres * newCostOfLand();
            } else {
                System.out.println("Hammurabi: Thank again. You have only " + bushelsOwned + " bushels of grain. ");
            }

            return acresOwned;
        }




    int askHowManyAcresToSell(int acres){
        if((-acres)<=acresOwned){
            acresOwned=acresOwned + acres;
            bushelsOwned = bushelsOwned + (-acres * newCostOfLand());
        }
        else {
            System.out.println("Hammurabi: Thank again. You have only "+ acresOwned +" acres of land.");
        }

        return acresOwned;
    }

   public int askHowMuchGrainToFeedPeople(int bushles){

        int feedGrainPeople = 0;
        if(bushles<=bushelsOwned){
            bushelsOwned = bushelsOwned - bushles;
        }
        else {

            System.out.println("Hammurabi: Thank again. You have only " + bushelsOwned + " bushels of grain.");
        }
        return bushelsOwned;
    }

    int askHowManyAcresToPlant(int acre, int people, int bush){
         harvested = rand.nextInt(7);
        if(acre<=acresOwned && people>=acresOwned && acre<=bush) {
        bushelsOwned = bushelsOwned - acre;
        bushelsOwned = bushelsOwned + acre * harvested;
        }
        return bushelsOwned;
    }


    public int newCostOfLand(){
        tradingPrice = rand.nextInt(7)+17;
        return tradingPrice;
    }

    int plagueDeaths(int population){
        starvedPeople = (int) (peopleOwned - peopleOwned * 0.15);
        return starvedPeople;
    }

    public void printResult(int i){
        System.out.println("You are in year "+i+" of your ten year rule.");
        System.out.println("In the previous year " + starvedPeople + " people starved to death.");
        System.out.println(immigrants + " people came to the city.");
        System.out.println("Message when people dies");
        System.out.println("The city population is now "+ peopleOwned);
        System.out.println("The city now owns "+acresOwned +" acres.");
        System.out.println("You harvested "+harvested+" bushels per acre.");
        System.out.println("Rats ate "+0+ " bushels.");
        System.out.println("You now have "+bushelsOwned+ " bushels in store.");
        System.out.println("Land is trading at "+tradingPrice+" bushels per acre.");

    }

    /**
     * @param message The request to present to the user.
     * @return The user's numeric response.
     */
    int getNumber(String message) {
        while (true) {
            System.out.print(message+"\t");
            try {
                return scanner.nextInt();
            }
            catch (InputMismatchException e) {
                System.out.println("\"" + scanner.next() + "\" isn't a number!");
            }
        }
    }
}//class