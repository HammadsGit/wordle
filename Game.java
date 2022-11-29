package comp1721.cwk1;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Game {
  private final int gameNumber;
  private final String target;
  // List for the save method.
  private final List<String> guessObjectList = new ArrayList<>();

  public Game(String filename) throws IOException {
      WordList words = new WordList(filename);

      // finding the no of days between first day and today.
      LocalDate initialDate  = LocalDate.of(2021, 6, 19);
      LocalDate today = LocalDate.now();
      long days = ChronoUnit.DAYS.between(initialDate, today);

      // initialises game number by assigning no of days to it.
      this.gameNumber = (int) days;
      //      this.gameNumber = 236;
      this.target = words.getWord(this.gameNumber);


    }

    // second constructor, game-number and grabs target-word.
    public  Game(int number, String filename) throws IOException {
      WordList words = new WordList(filename);
      this.gameNumber = number;
      //      this.gameNumber = 236;
      this.target = words.getWord(gameNumber);


    }
    // constructor to initialise game number and target
    public Game(String accessibility,int number, String filename) throws IOException{
      WordList words = new WordList(filename);
      this.gameNumber = number;
      this.target = words.getWord(gameNumber);
    }

    // is like the main function runs the main functions of the game.
    public void play() throws IOException, InterruptedException {
      System.out.printf("WORDLE %d\n\n", this.gameNumber);


      int guessesMade = 0;
      boolean gameWon = false;


      // creates guess object for each guess
      for(int x=1;x<=6; x++){
        //creating guess object
        Guess guessObject = new Guess(x, target);
        System.out.printf("\nEnter guess (%d/6): ", x);
        //reading from player and incrementing 1 for each guess
        guessObject.readFromPlayer();
        guessesMade += 1;
        //printing out the results of the guess if correct or not.
        System.out.print("\n" + guessObject.compareWith(target));
        //adds the results to a list so the save function can user later to
        //save in file.
        guessObjectList.add(guessObject.compareWith(target));
        //if statements for each condition of game winning or not
        if(guessObject.matches(target)){
          gameWon = true;
          if(x==1 && guessObject.matches(target)){
            System.out.print("\nSuperb - Got it in one!");

          }
          else if (x >= 2 && x <= 5 && guessObject.matches(target)){
            System.out.print("\nWell done!");
          }
          else if(x==6 && guessObject.matches(target)){
            System.out.print("\nThat was a close call!");
          }

          break;


        }
      }
      //printing this when game is not won
      if(!gameWon){
        System.out.print("\nNope - Better luck next time!");

      }
      // functions running for advanced features.
      writeHistory(guessesMade, gameWon);
      createStatistics();


    }

    //function for advanced features.
    public void playAccessbility() throws IOException, InterruptedException {
      int guessesMade = 0;
      boolean gameWon = false;

      System.out.printf("WORDLE %d\n\n", this.gameNumber);
      for(int x=1;x<=6; x++){
        //creating guess object.
        Guess guessObject = new Guess(x, target);
        //asking user for inout
        System.out.printf("\nEnter guess (%d/6): ", x);
        guessObject.readFromPlayer();
        //incrementing 1
        guessesMade += 1;
        if(guessObject.matches(target)){
          gameWon = true;
          System.out.print("\nYou won!");
          break;
        }
        else{
          System.out.print(guessObject.comparewithAccessbility(target));
        }

      }
      //printing this when game is not won
      if(!gameWon){
        System.out.print("\nNope - Better luck next time!");

      }

      // functions running for advanced features.
      writeHistory(guessesMade, gameWon);
      createStatistics();

    }

  // this functions writes to a text file the current game.
  public void  writeHistory(int guess_made, boolean game_won) throws IOException {
    //first writing everything to a string builder before even opening a file.

    StringBuilder contentToAppend = new StringBuilder();
    //first writes the game number
    contentToAppend.append("\nWORDLE %d\n".formatted(this.gameNumber));

    //then if game is won or not.
    if(game_won){
      contentToAppend.append("Guessed Successfully: %s\n".formatted("Yes"));
    }
    else{
      contentToAppend.append("Guessed Successfully: %s\n".formatted("No"));
    }

    //then how many guesses made.
    contentToAppend.append("Guesses Made: %d\n".formatted(guess_made));

    // then each guess made.
    for(String e : guessObjectList){
      contentToAppend.append(e);
      contentToAppend.append("\n");
    }

    // checking if file exists.
    File tempFile = new File("history.txt");
    boolean exists = tempFile.exists();



    //if file exists append to the file.
    if(exists){

      Files.write(Paths.get("history.txt"),
              contentToAppend.toString().getBytes(), StandardOpenOption.APPEND);
    }
    else{
      // if not exists then create a new file and write to it for the first time.
      FileWriter writer = new FileWriter("./history.txt");
      writer.write(contentToAppend.toString());
      writer.close();

    }
    }
  public void createStatistics() throws IOException, InterruptedException {
    //give time for the file to be opened and closed.
    TimeUnit.SECONDS.sleep(1);
    //open scanner to read file

    Scanner readFile = new Scanner(new File("history.txt"));

    // variables that will be written inside the terminal
    double gamesPlayed = 0;
    double currentWinStreak = 0;
    double longestWinStreak= 0;
    double countAllWins  = 0;


    int[] histData = {0, 0, 0, 0, 0, 0};

    //loop to read file till the end
    while(readFile.hasNextLine()){
      //grab each line
      String currentLine = readFile.nextLine();
      //split each line by spaces.
      String[] elements = currentLine.split(" ");

      //reading the amount of games there are.
      if(Objects.equals(elements[0], "WORDLE")){
        gamesPlayed += 1;
      }
      //counting the wins
      if(Objects.equals(elements[0], "Guessed") && Objects.equals(elements[1], "Successfully:")){
        if(Objects.equals(elements[2], "Yes")){
          countAllWins += 1;
          //counting win streaks
          currentWinStreak += 1;
          //longest win streak if current win streak is greater.
          if (currentWinStreak > longestWinStreak){
            longestWinStreak = currentWinStreak;
          }

        }
        //if game loss current win streak goes to 0.
        else{
          currentWinStreak = 0;
        }}
      //counting how many times, each type of guesses were made. and making
      // histogram data.
      if(Objects.equals(elements[0], "Guesses") && Objects.equals(elements[1], "Made:")){
        if(Objects.equals(elements[2], "1")){
          histData[0] += 1;
        }
        else if(Objects.equals(elements[2], "2")){
          histData[1] += 1;
        }
        else if(Objects.equals(elements[2], "3")){
          histData[2] += 1;
        }
        else if(Objects.equals(elements[2], "4")){
          histData[3] += 1;
        }
        else if(Objects.equals(elements[2], "5")){
          histData[4] += 1;
        }
        else if(Objects.equals(elements[2], "6")){
          histData[5] += 1;
        }

      }
    }


    System.out.print("\n\n");

    //printing out the data.
    System.out.printf("Number of games played:  %.0f\n", gamesPlayed);
    System.out.printf("Percentage of games that were wins: %.2f\n", (countAllWins/gamesPlayed)*100);
    System.out.printf("Length of the current winning streak:  %.0f\n", currentWinStreak);
    System.out.printf("Longest winning streak:  %.0f\n", longestWinStreak);

    //printing histogram data.
    System.out.print("\nGuesses | No of Games\n\n");

    for(int x=0; x<=5; x++){
      StringBuilder tooprint = new StringBuilder(String.format("   %d    |", x + 1));
      int number = histData[x];
      while(number >0){
        tooprint.append("▉");
        number --;


      }
      tooprint.append("(%d)".formatted(histData[x]));
      System.out.println(tooprint);
    }




  }

  //save function, writing all guesses of compare-with in this file.
  public void save(String filename) throws FileNotFoundException {
      PrintWriter writer = new PrintWriter(filename);


      for(String e : guessObjectList){
        writer.println(e);


      }
      writer.close();


    }


}
