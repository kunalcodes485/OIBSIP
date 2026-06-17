import java.util.*;

public class NumberGuessingGame {
    static Scanner sc = new Scanner(System.in);
    static int totalScore = 0;
    static int roundsPlayed = 0;

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("       NUMBER GUESSING GAME");
        System.out.println("       Developed by: OIBSIP");
        System.out.println("========================================");
        System.out.println("Guess the number between 1 and 100!");
        while (true) {
            playRound();
            System.out.print("\nPlay another round? (yes/no): ");
            String choice = sc.nextLine().trim();
            if (!choice.equalsIgnoreCase("yes")) break;
        }
        System.out.println("\n========================================");
        System.out.println("              GAME OVER!");
        System.out.println("Total Rounds Played : " + roundsPlayed);
        System.out.println("Total Score         : " + totalScore);
        System.out.println("========================================");
    }

    static void playRound() {
        Random rand = new Random();
        int target = rand.nextInt(100) + 1;
        int maxAttempts = 7;
        int attempts = 0;
        roundsPlayed++;
        System.out.println("\n--- Round " + roundsPlayed + " ---");
        System.out.println("You have " + maxAttempts + " attempts. Good luck!");
        while (attempts < maxAttempts) {
            System.out.print("Enter your guess: ");
            int guess;
            try {
                guess = Integer.parseInt(sc.nextLine().trim());
                if (guess < 1 || guess > 100) {
                    System.out.println("Enter a number between 1 and 100!");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Enter a number.");
                continue;
            }
            attempts++;
            if (guess == target) {
                int points = (maxAttempts - attempts + 1) * 10;
                totalScore += points;
                System.out.println("CORRECT! You guessed it in " + attempts + " attempt(s)!");
                System.out.println("Points earned: " + points + " | Total Score: " + totalScore);
                return;
            } else if (guess < target) {
                System.out.println("Too LOW!  Attempts left: " + (maxAttempts - attempts));
            } else {
                System.out.println("Too HIGH! Attempts left: " + (maxAttempts - attempts));
            }
        }
        System.out.println("Out of attempts! The number was: " + target);
    }
}
