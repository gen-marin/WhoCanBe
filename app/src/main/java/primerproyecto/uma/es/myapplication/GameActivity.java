package primerproyecto.uma.es.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
/**
 * Created by Marin on 07/11/2017.
 *
 * Class that contains all the game relateds,
 * in here the character is selected randomly, the tips showed
 * and verified if the character name is correctly inserted.
 */
public class GameActivity extends AppCompatActivity{
    int backgroundID;
    String characterDbId;
    String nameToGuess;
    String actualInGameName;
    ArrayList<String> tipLetters = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        generateGame();
    }

    /**
     * Function that calls other functions at the beginning of each game,
     * this functions helps to generate the game properly.
     */
    public void generateGame() {
        clearGame();
        initializeNewValues(GameHelper.getRandomLockedCharacterValues(getApplicationContext()));
        setGameCharacterBackground();
        setTipLetterCounter();
        generateUnsolvedName();
    }

    /**
     * Initialize the values for each game
     *
     * @param characterValues has the values that are going to be in the game
     */
    public void initializeNewValues(ArrayList<String> characterValues) {
        // Empty means: all the characters unlocked
        if(!characterValues.isEmpty()){
            characterDbId = characterValues.get(0);
            nameToGuess = characterValues.get(1);
            backgroundID = getResources().getIdentifier(parseBackgroundIdFromDb(characterValues.get(1)),
                    "drawable", getPackageName());
            tipLetters = GameHelper.setTipLetters(characterValues.get(2), tipLetters);
        }else{
            emptyValues();
        }
    }

    /**
     *
     *
     * @param
     */
    public String parseBackgroundIdFromDb(String charactersName) {
        // QUITA LOS ESPACIOS EN BLANCO DE LOS NOMBRES
        StringBuilder unsolvedName = new StringBuilder();

        //String data = "1,Diego Maradona,Footballer,Argentina";
        String[] items = charactersName.split(" ");
        for (String item : items)
        {
            unsolvedName.append(item);
        }

        return unsolvedName.toString();
    }


    /**
     * Initialize the values to 0.
     */
    public void emptyValues() {
        characterDbId = "";
        nameToGuess = "";
        backgroundID = 0;
        tipLetters.clear();
    }

    /**
     * Initialize the values and the textViews to 0.
     */
    public void clearGame(){
        emptyValues();
        EditText editTextWord = (EditText)findViewById(R.id.editTextWord);
        editTextWord.setHint("");
        TextView textViewTips = (TextView)findViewById(R.id.textViewAvailableLetters);
        textViewTips.setText("");
    }

    /**
     * Called each time a new game starts.
     * This function sets the background for the new character
     */
    public void setGameCharacterBackground() {
        ImageView imageViewCharacter = (ImageView)findViewById(R.id.imageViewCharacter);
        // Value '0' means: no more locked characters
        if (backgroundID != 0){
            imageViewCharacter.setBackgroundResource(backgroundID);
        }else{
            imageViewCharacter.setBackgroundResource(R.drawable.default_character_image);
            Toast.makeText(this, "!!! ALL CHARACTERS UNLOCKED !!!",Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Helper function that display the tip letters in the tips counter.
     */
    public void setTipLetterCounter() {
        StringBuilder availableLetters = new StringBuilder();
        for(int i = 0; i < tipLetters.size() ;i++) {
            availableLetters.append(tipLetters.get(i) + " ");
        }
        TextView textViewAvailableLetters = (TextView)findViewById(R.id.textViewAvailableLetters);
        textViewAvailableLetters.setText(availableLetters.toString());
    }

    /**
     * Function that leads the creation of the 'actualInGameName'.
     *
     */
    public void generateUnsolvedName() {
        generateEmptyName();
        if(!tipLetters.isEmpty()){
            generateTipsLetters();
        }
    }

    /**
     * Function that generates a name with the right lenght which is
     * going to be showed in the 'actualInGameName'.
     * Creates a String like '___'
     */
    public void generateEmptyName() {
        int nameLenght = nameToGuess.length();
        StringBuilder unsolvedName = new StringBuilder();
        for(int i = 0; i < nameLenght ;i++){
            unsolvedName.append("_");
        }
        actualInGameName = unsolvedName.toString();
    }

    /**     MODIFIED
     *
     * this function helps to show the tips letters in the actualInGameName.
     * Iterates along the tip letters array checking if any of them exists
     * in the word. Results like '_e_'
     */
    public void generateTipsLetters() {
        // iterates for each letter in the tip letters
        for(int i = 0; i < tipLetters.size() ;i++){
            if(nameToGuess.contains(tipLetters.get(i))){
                actualInGameName = GameHelper.modifyString(i, nameToGuess, tipLetters, actualInGameName);
            }
        }
        if(nameToGuess.contains(" ")){
            actualInGameName = addSpaces();
        }

        setActualInGameName();
    }


    public static String replaceCharAt(String s, int pos, String c) {
        return s.substring(0, pos) + " " + s.substring(pos + 1);
    }

    /**
     *
     *
     *
     */
    public String addSpaces() {
        String unsolvedName;
        for(int i = 0; i < nameToGuess.length() ;i++){
            if(nameToGuess.charAt(i) == ' '){
                actualInGameName = replaceCharAt(actualInGameName, i, " ");
            }
        }

        /*
        String myString = "Send this String Out/Leave this one behind";
String toSendOut = "";
for(int i = 0; i < myString.length(); i++){
    if(myString.charAt(i) != '/'){
        toSendOut = toSendOut + myString.charAt(i);
    } else {
        break;
    }
}
         */

        return actualInGameName;
    }


    /**
     * Helper function that display the tip letters in the 'actualInGameName'.
     * The 'actualInGameName' isn´t showed raw, the functions adds spaces
     * between the letters.
     */
    public void setActualInGameName(){
        StringBuilder displayedLetters = new StringBuilder();
        for(int i = 0; i < nameToGuess.length() ;i++) {
            char letterForTheCounter = actualInGameName.charAt(i);
            displayedLetters.append(letterForTheCounter + " ");
        }
        EditText editTextWord = (EditText)findViewById(R.id.editTextWord);
        editTextWord.setHint(displayedLetters.toString());
    }

    /**
     * Called when the user clicks the Play button in the activity_game.
     * Compares if the inserted name is equal to the stored name.
     *
     * @param view the view whose is executing.
     */
    public void guessWho(View view) {
        EditText editTextPlayersChoice = (EditText)findViewById(R.id.editTextWord);
        // Comprueba si coincide
        if (editTextPlayersChoice.getText().toString().toLowerCase().equals(nameToGuess)){
            Toast.makeText(this, "CORRECT!: ",Toast.LENGTH_LONG).show();
            turnCharacterUnlocked();
            generateGame();
        }else{
            Toast.makeText(this, "TRY AGAIN", Toast.LENGTH_LONG).show();
        }
        // Clear the input312
        editTextPlayersChoice.setText("");
    }

    /**
     * Helper function that modify in the DB the 'Unlock' column for
     * the actual in game character.
     */
    public void turnCharacterUnlocked(){
        CharacterDbHelper mDbHelper = new CharacterDbHelper(getApplicationContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Cursor cursor =  db.rawQuery("select * from " + Character.CharacterTable.TABLE_NAME +
                " where " + Character.CharacterTable._ID + "='" + characterDbId + "'" , null);
        if (cursor.moveToFirst() ) {
            String[] columnNames = cursor.getColumnNames();

            // AQUI HAY QUE MODIFICAR EL ESTADO DEL PERSONAJE A DESBLOQUEADO

        }
    }

    /**
     * Called when the user clicks the Back button.
     *
     * @param view the view whose is executing.
     */
    public void toMenu(View view) {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }
}
