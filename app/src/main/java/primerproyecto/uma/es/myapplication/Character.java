package primerproyecto.uma.es.myapplication;

import android.provider.BaseColumns;

/**
 * Created by Marin on 07/11/2017.
 *
 * Class that defines the table for the database,
 * inside are defined the name of the table and the columns
 */
public class Character {

    // To prevent someone from accidentally instantiating the character class,
    // make the constructor private.
    private Character() {}

    /* Inner class that defines the table contents */
    public static class CharacterTable implements BaseColumns {
        public static final String TABLE_NAME = "characters";
        public static final String COLUMN_NAME_CHARACTER_NAME = "name";
        public static final String COLUMN_NAME_UNLOCK = "unlocked";
        public static final String COLUMN_NAME_TIP_LETTERS = "letters";
    }
}
