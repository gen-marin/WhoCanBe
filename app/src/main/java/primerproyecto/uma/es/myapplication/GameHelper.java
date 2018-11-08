package primerproyecto.uma.es.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Marin on 10/12/2017.
 */

public class GameHelper {

    /**
     * Go throught the DB checking for characters with the value '0' in
     * the 'unlock' column, and return all the values of a random
     * character.
     *
     * @return all the values of a single character as a ArrayList<String>
     */
    public static ArrayList<String> getRandomLockedCharacterValues(Context context) {
        ArrayList<String> values = new ArrayList<String>();
        SQLiteDatabase db = CharacterDbHelper.openDB(context);
        Cursor cursorWithTheMatchs = CharacterDbHelper.queryForLockedsCharacters(db);
        // Check if its not empty
        if (cursorWithTheMatchs.moveToFirst()){
            int chosenOne = GameHelper.getRandom(0, cursorWithTheMatchs.getCount());
            values = obtainValues(cursorWithTheMatchs, chosenOne);
        }
        cursorWithTheMatchs.close();
        db.close();
        return values;
    }

    /**
     * Puts all character´s values in a list.
     *
     * @param cursorWithTheMatchs query with all the locked characters
     * @return all the values of a random character as a ArrayList<String>
     */
    public static ArrayList<String> obtainValues(Cursor cursorWithTheMatchs, int chosenOne){
        ArrayList<String> values = new ArrayList<String>();
        // gets a rndm number between '0' and 'cursorWithTheMatchs.getCount()'
        cursorWithTheMatchs.moveToPosition(chosenOne);
        String[] columnNames = cursorWithTheMatchs.getColumnNames();
        values.add(cursorWithTheMatchs.getString(cursorWithTheMatchs.getColumnIndex(columnNames[0])));
        values.add(cursorWithTheMatchs.getString(cursorWithTheMatchs.getColumnIndex(columnNames[1])));
        values.add(cursorWithTheMatchs.getString(cursorWithTheMatchs.getColumnIndex(columnNames[3])));
        return values;
    }

    /**
     * Generate a random number between the preasigned limits.
     *
     * @param min begining of the rndm number pool
     * @param max ending of the rndm number pool
     * @return a random number
     */
    static public int getRandom(int min, int max){
        Random rnd = new Random();
        // Number between min and max exclusive, and adds min
        int chosenOne = rnd.nextInt(max - min) + min;
        return  chosenOne;
    }

    /**
     * This function initialize the 'tipLetters' list.
     * The letters comes together in a string, so the function divide and
     * add them to the 'tipLetters' list.
     *
     * @param tipLettersString all the tip letters as a string
     */
    public static ArrayList<String> setTipLetters(String tipLettersString, ArrayList<String> tipLetters) {
        // if the string isn't empty
        if(!tipLettersString.equals("")){
            for(int i=0; i < tipLettersString.length() ;i++) {
                char letterFromTipLetters = tipLettersString.charAt(i);
                tipLetters.add(letterFromTipLetters+"");
            }
        }
        return tipLetters;
    }

    /**
     * Function that compares in every letter of the word if it matches
     * with the requested tip letter.
     *
     * @param pos position of the letter in the 'actualInGameName'
     */
    public static String modifyString(int pos, String nameToGuess, ArrayList<String> tipLetters, String actualInGameName) {
        StringBuilder unsolvedName = new StringBuilder();
        for(int i = 0; i < nameToGuess.length() ;i++){
            char letterFromName = nameToGuess.charAt(i);
            char letterFromTips = tipLetters.get(pos).charAt(0);
            if(letterFromName == letterFromTips){
                unsolvedName.append(tipLetters.get(pos));
            }else{
                char a_char = actualInGameName.charAt(i);
                unsolvedName.append(a_char);
            }
        }
        actualInGameName = unsolvedName.toString();
        return actualInGameName;
    }
}
