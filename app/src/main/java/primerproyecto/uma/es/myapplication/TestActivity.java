package primerproyecto.uma.es.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    /**
     * Function that calls "addsCharacterToDatabase" function from "CharacterDbHelper" class,
     * before call this function it checks the values to add through the method
     * "checkValidityOfValuesForAdd" and shows an error message if any value is not valid.
     *
     * @param view the view whose is running
     */
    public void addsDB(View view){
        //
        TextView textViewName = (TextView)findViewById(R.id.editTextAddName);
        TextView textViewUnlock = (TextView)findViewById(R.id.editTextAddUnlock);
        TextView textViewTipLetters = (TextView)findViewById(R.id.editTextAddTipLetters);
        //
        String newName = textViewName.getText().toString();
        String newUnlock = textViewUnlock.getText().toString();
        String newTipLetters = textViewTipLetters.getText().toString();

        if(checkValidityOfValuesForAdd(newName, newUnlock, newTipLetters)){
            CharacterDbHelper.addsCharacterToDatabase(getApplicationContext(), newName, newUnlock, newTipLetters);
        }else{
            Toast.makeText(this, "Incorrect Value",
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Function that validates all the necessary values to add a new
     * "Character" to the database.
     *
     * @param newCharacterName the new name value
     * @param newCharacterUnlock the new unlock value
     * @param newTipLettersUnlock the new tip letters value
     *
     * @return true if the values are valid, false if not
     */
    public boolean checkValidityOfValuesForAdd(String newCharacterName, String newCharacterUnlock, String newTipLettersUnlock){
        boolean areValid = true;

        if(!newCharacterName.matches("[a-z ]*") || newCharacterName.isEmpty()){
            areValid = false;
        }
        if(newCharacterUnlock.equals("1") || newCharacterUnlock.equals("0") ){
        }else{
            areValid = false;
        }
        if(!newTipLettersUnlock.matches("[a-z]*")){
            areValid = false;
        }
        return areValid;
    }

    /**
     * Function that calls "getTableAsString" function from "CharacterDbHelper" class,
     * and shows in the "EditText" the table.
     *
     * @param view the view whose is running
     */
    public void showDb(View view){
        CharacterDbHelper mDbHelper = new CharacterDbHelper(getApplicationContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        EditText printer = (EditText)findViewById(R.id.editTextTest);
        printer.setText(CharacterDbHelper.getTableAsString(db, Character.CharacterTable.TABLE_NAME));
        db.close();
    }

    public void deleteDb(View view){
        CharacterDbHelper mDbHelper = new CharacterDbHelper(getApplicationContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        TextView textDelete = (TextView)findViewById(R.id.editTextDelete);

        String deleteId = textDelete.getText().toString();

        // Define 'where' part of query.
        String selection = Character.CharacterTable._ID + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { deleteId };
        // Issue SQL statement.
        db.delete(Character.CharacterTable.TABLE_NAME, selection, selectionArgs);

        db.close();
    }

    public void modifyDb(View view){
        CharacterDbHelper mDbHelper = new CharacterDbHelper(getApplicationContext());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        TextView textModifyId = (TextView)findViewById(R.id.editTextModifyId);
        TextView textModifyName = (TextView)findViewById(R.id.editTextModifyName);
        TextView textModifyUnlock = (TextView)findViewById(R.id.editTextModifyUnlock);
        TextView textModifyTipLetters = (TextView)findViewById(R.id.editTextModifyTipLetters);


        String characterId = textModifyId.getText().toString();
        String newName = textModifyName.getText().toString();
        String newUnlock = textModifyUnlock.getText().toString();
        String newTipLetters = textModifyTipLetters.getText().toString();


        ContentValues values = new ContentValues();

        if(!newName.equals("")){
            // New value for one column
            values.put(Character.CharacterTable.COLUMN_NAME_CHARACTER_NAME, newName);
        }else if (!newUnlock.equals("")){
            // New value for one column
            values.put(Character.CharacterTable.COLUMN_NAME_UNLOCK, newUnlock);
        }else if (!newTipLetters.equals("")){
            // New value for one column
            values.put(Character.CharacterTable.COLUMN_NAME_TIP_LETTERS, newTipLetters);
        }
        // Which row to update, based on the name
        String selection = Character.CharacterTable._ID + " LIKE ?";
        String[] selectionArgs = { characterId };

        int count = db.update(
                Character.CharacterTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        db.close();
    }
}
