package primerproyecto.uma.es.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

//import static android.support.v4.media.session.MediaControllerCompatApi21.getPackageName;

/**
 * Created by Marin on 10/12/2017.
 */

/**
 * This class determines all the views inside the 'Gallery'
 */
public class GalleryPageFragment extends Fragment {
    private static final String PAGE_NUM = "page_num";
    // Actual page position
    private int page_num;

    public GalleryPageFragment() {

    }
    public static GalleryPageFragment newInstance(int pagenumber) {
        GalleryPageFragment fragment = new GalleryPageFragment();
        Bundle args = new Bundle();
        args.putInt(PAGE_NUM, pagenumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            page_num = getArguments().getInt(PAGE_NUM);
        }
    }

    /**
     * Its called each time a page is slided.
     * Inside you can define each page individually.
     *
     *
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.gallery_fragment_page, container, false);
        ImageButton characterButton1 = (ImageButton)rootview.findViewById(R.id.imageButtonCharacter1);
        ImageButton characterButton2 = (ImageButton)rootview.findViewById(R.id.imageButtonCharacter2);
        ImageButton characterButton3 = (ImageButton)rootview.findViewById(R.id.imageButtonCharacter3);
        EditText text = rootview.findViewById(R.id.editText);

        ArrayList<Boolean> characterIsUnlock;
        if (page_num == 1){
            characterIsUnlock = getActualCharactersState(1, 3);
            if(characterIsUnlock.get(0) == true){
                characterButton1.setBackgroundResource(R.drawable.luffy);
            }
            if(characterIsUnlock.get(1) == true){
                characterButton2.setBackgroundResource(R.drawable.homersimpson);
            }
            if(characterIsUnlock.get(2) == true){
                characterButton3.setBackgroundResource(R.drawable.luffy);
            }
            text.setText(characterIsUnlock.toString());
        }else if (page_num == 2){
            characterIsUnlock = getActualCharactersState(4, 6);
            if(characterIsUnlock.get(0) == true){
                characterButton1.setBackgroundResource(R.drawable.homersimpson);
            }
            if(characterIsUnlock.get(1) == true){
                characterButton2.setBackgroundResource(R.drawable.luffy);
            }
            if(characterIsUnlock.get(2) == true){
                characterButton3.setBackgroundResource(R.drawable.homersimpson);
            }
            text.setText(characterIsUnlock.toString());
        }else if (page_num == 3){
            characterIsUnlock = getActualCharactersState(7, 7);
            if(characterIsUnlock.get(0) == true){
                characterButton1.setBackgroundResource(R.drawable.luffy);
            }
            characterButton2.setVisibility(View.INVISIBLE);
            characterButton3.setVisibility(View.INVISIBLE);
            text.setText(characterIsUnlock.toString());
        }
        return rootview;

    }

    /**
     *
     *
     * @param first
     * @param last
     * @return
     */
    public ArrayList<Boolean> getActualCharactersState(int first, int last){
        SQLiteDatabase db = CharacterDbHelper.openDB(getContext());
        Cursor cursor  = CharacterDbHelper.queryForSomeCharacters(db, first, last);

        ArrayList<Boolean> charactersUnlockValue = new ArrayList<Boolean>();
        if (cursor.moveToFirst()) {
            charactersUnlockValue = parseCharactersState(cursor);
        }
        cursor.close();
        db.close();
        return charactersUnlockValue;
    }

    /**
     *
     *
     * @param cursor
     * @return
     */
    public ArrayList<Boolean> parseCharactersState(Cursor cursor){
        String[] columnNames = cursor.getColumnNames();
        ArrayList<Boolean> charactersState = new ArrayList<Boolean>();
        do {
            // its unlocked?
            String unlockedValue = cursor.getString(cursor.getColumnIndex(columnNames[2]));
            if (unlockedValue.equals("1")) {
                charactersState.add(true);
            }else{
                charactersState.add(false);
            }
        } while (cursor.moveToNext());
        return charactersState;
    }






    /**
     * CHANGE BACKGROUND PROGRAMATICALLY
     *

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ArrayList<String> getBackgroundValuesFromDb(int start, int end) {
        CharacterDbHelper mDbHelper = new CharacterDbHelper(getContext());
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Character.CharacterTable.TABLE_NAME +
                        " WHERE " + Character.CharacterTable._ID + " BETWEEN '" + start + "' AND '" + end + "'",
                null, null);

        ArrayList<String> charactersBackground = new ArrayList<String>();
        if (cursor.moveToFirst()) {
            String[] columnNames = cursor.getColumnNames();
            do {
                // its unlocked?
                String unlockedCharacter = cursor.getString(cursor.getColumnIndex(columnNames[2]));
                if (unlockedCharacter.equals("1")) {
                    // Building the button name
                    StringBuilder characterButtonName = new StringBuilder();
                    characterButtonName.append("imageButtonCharacter");
                    characterButtonName.append(cursor.getString(cursor.getColumnIndex(columnNames[0])));
                    // Searching the button
                    int buttonID = getResources().getIdentifier(characterButtonName.toString(), "id", getPackageName(BuildConfig.APPLICATION_ID
                    ));
                    ImageButton characterButton = (ImageButton) findViewById(buttonID);
                    // setting a new background for the button
                    int backgroundID = getResources().getIdentifier(cursor.getString(cursor.getColumnIndex(columnNames[1])), "drawable", getPackageName());
                    characterButton.setBackgroundResource(backgroundID);
                    //characterButton.setBackgroundResource(R.drawable.luffy);


                    charactersBackground.add(cursor.getString(cursor.getColumnIndex(columnNames[0])));
                }
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return charactersBackground;
    }
     */


    /** Initialice all the "imageButton" with their respective image*/
    /*
    public void drawCharacters(){
        CharacterDbHelper mDbHelper = new CharacterDbHelper(getApplicationContext());
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Cursor cursor  = db.rawQuery("SELECT * FROM " + Character.CharacterTable.TABLE_NAME, null);

        if (cursor.moveToFirst() ){
            String[] columnNames = cursor.getColumnNames();
            do {
                // its unlocked?
                String unlockedCharacter = cursor.getString(cursor.getColumnIndex(columnNames[2]));
                if(unlockedCharacter.equals("1")){
                    // Building the button name
                    StringBuilder characterButtonName = new StringBuilder();
                    characterButtonName.append("imageButtonCharacter");
                    characterButtonName.append(cursor.getString(cursor.getColumnIndex(columnNames[0])));
                    // Searching the button
                    int buttonID = getResources().getIdentifier(characterButtonName.toString(), "id", getPackageName());
                    ImageButton characterButton = (ImageButton)findViewById(buttonID);
                    // setting a new background for the button
                    int backgroundID = getResources().getIdentifier(cursor.getString(cursor.getColumnIndex(columnNames[1])), "drawable", getPackageName());
                    characterButton.setBackgroundResource(backgroundID);
                    //characterButton.setBackgroundResource(R.drawable.luffy);
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
    }*/


}
