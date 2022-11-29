package comp1721.cwk1;


import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Scanner;

public class WordList {

    private final LinkedList<String> words = new LinkedList<>();
    public WordList(String filename) throws IOException {
        Scanner input = new Scanner(Paths.get(filename));

        while(input.hasNextLine()){
            String line = input.nextLine();
            words.add(line);
        }


    }

    public int size(){
        return words.size();

    }

    public  String getWord(int number){
        String element;


        if (number < 0 || number >= words.size()) {
            throw new GameException("Out of bounds");
        } else {
            element = words.get(number);
        }
        return element;
    }
  }

