package comp1721.cwk1;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

public class Guess {
  private final int guessNumber;
  private String chosenWord;



  //constructor for guess
  public Guess (int num){
    if (num <= 0 || num >= 7) {
      throw new GameException("Out of bounds");
    } else {
      this.guessNumber = num;
    }



  }
  //second constructor for guess initializes num and word.
  public Guess(int num, String word){

    //num is out of bonds.
    if (num <= 0 || num >= 7) {
      throw new GameException("Out of bounds");
    } else {
      this.guessNumber = num;
    }

    //word is a letter and is length 5.
    if(word.matches("[a-zA-Z]+") && word.length() == 5){
      this.chosenWord = word.toUpperCase(Locale.ROOT);
    }
    // if not throw error.
    else{
      throw new GameException("chosen word not 5 characters.");
    }


  }

  public int getGuessNumber(){

    return this.guessNumber;
  }



  public String getChosenWord(){


    return this.chosenWord;
  }

  //read input from player used in Game.java
  public void readFromPlayer(){
    Scanner myObj = new Scanner(System.in);
    this.chosenWord = myObj.nextLine().toUpperCase();




  }

  //compares target and chosen word.
  public String compareWith(String target) {
    StringBuilder finalSentence = new StringBuilder();
    // lists to identify green, yellow and white. g, y and left alone for white.
    String[] positionColours = {"-", "-", "-", "-", "-"};
    String[] positionColoursTargetword = {"-", "-", "-", "-", "-"};

    //loops through and finds letter in same places.
    for(int x=0; x<5; x++){
      if(target.charAt(x) == this.chosenWord.charAt(x)){
        positionColours[x] = "g";
        positionColoursTargetword[x] = "g";
      }
    }

    //goes through and finds letter in the word but not in the same place.
    for(int x=0; x<5; x++){
      for (int y= 0; y<5; y++){
        if(Objects.equals(positionColours[x], "g")){
          break;
        }
        else{
          if(this.chosenWord.charAt(x) == target.charAt(y)
                  && !Objects.equals(positionColours[y], "g")
                  && !Objects.equals(positionColoursTargetword[y], "y")){
            positionColours[x] = "y";
            positionColoursTargetword[y] = "y";
          }
        }
      }
    }

    //finally, adding the colours.
    for(int x=0; x<5; x++){
      if(Objects.equals(positionColours[x], "g")){
        positionColours[x] = "\033[30;102m "+ target.charAt(x)+ " \033[0m";
      }
      else if(Objects.equals(positionColours[x], "y")){
        positionColours[x] = "\033[30;103m " + chosenWord.charAt(x) + " \033[0m";
      }else{
        positionColours[x] = "\033[30;107m " + chosenWord.charAt(x) + " \033[0m";
      }
    }

    //adding the colours to one sentence.
    for(int x=0; x<5; x++){
      finalSentence.append(positionColours[x]);
    }

    //return the final result.
    return finalSentence.toString();
  }



  public String comparewithAccessbility(String target) {
    List<String> positions = new ArrayList<>();

    //numbers for putting in sentence.
    positions.add("1st ");
    positions.add("2nd ");
    positions.add("3rd ");
    positions.add("4th ");
    positions.add("5th ");
    positions.add("6th ");

    // boolean values to determine which part of
    // the sentence to print. wrong ones or right ones or both.
    boolean rightPosBool = false;
    boolean wrongPosBool = false;

    // lists to identify green, yellow and white. g, y and left alone for white.
    String[] positionColours = {"-", "-", "-", "-", "-"};
    String[] positionColoursTargetword = {"-", "-", "-", "-", "-"};


    //strrightPosBoolng to add to right and wrong sentences.
    StringBuilder wrongPositions = new StringBuilder();
    StringBuilder rightPositions = new StringBuilder();
    String wrongPlaceSentence = "correct but in wrong place";
    String perfectPlaceSentence = "perfect";

    StringBuilder finalSentence = new StringBuilder();

    //loops through and finds letter in same places.
    for(int x=0; x<5; x++){
      if(target.charAt(x) == this.chosenWord.charAt(x)){

        positionColours[x] = "g";
        positionColoursTargetword[x] = "g";


      }

    }
    //goes through and finds letter in the word but not in the same place.
    for(int x=0; x<5; x++){
      for (int y= 0; y<5; y++){
        if(Objects.equals(positionColours[x], "g")){
          break;
        }
        else{
          if(this.chosenWord.charAt(x) == target.charAt(y)
                  && !Objects.equals(positionColours[y], "g")
                  && !Objects.equals(positionColoursTargetword[y], "y")){
            positionColours[x] = "y";
            positionColoursTargetword[y] = "y";
          }
        }
      }
    }

    // putting the sentences together with the correct positions that are either right or wrong.
    for(int x=0; x<5; x++){
      if(Objects.equals(positionColours[x], "g")){
        rightPositions.append(positions.get(x));
        rightPosBool = true;
      }else if(Objects.equals(positionColours[x], "y")){
        wrongPositions.append(positions.get(x));
        wrongPosBool = true;
      }
    }











    // if there are wrong places letter and right places letter print both.
    if(rightPosBool && wrongPosBool){
      finalSentence.append("\n").append(wrongPositions)
              .append(wrongPlaceSentence).append(", ")
              .append(rightPositions).append(perfectPlaceSentence);
    }
    // if there are wrong places letter and not right places letter then
    // print right places sentence only.
    else if(rightPosBool){
      finalSentence.append("\n").append(rightPositions).append(perfectPlaceSentence);
    }
    // if there are right places letter and not wrong places'
    // letter then print wrong places sentence only.
    else if(wrongPosBool){
      finalSentence.append(wrongPositions).append(wrongPlaceSentence);
    }
    //return the final result
    return finalSentence.toString();
  }

  //checks if two strings are similar.
  public boolean matches(String targetWord) throws AssertionError{
    return targetWord.equalsIgnoreCase(chosenWord);
  }}

