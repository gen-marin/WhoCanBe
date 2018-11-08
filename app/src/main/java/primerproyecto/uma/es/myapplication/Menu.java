package primerproyecto.uma.es.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    /** Called when the user clicks the Gallery button */
    public void toGallery(View view) {
        Intent intent = new Intent(this, GallerySlideActivity.class);
        startActivity(intent);
    }

    /** Called when the user clicks the Game button */
    public void toGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    /** Called when the user clicks the Options button */
    public void toOptions(View view) {
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }

    /** Called when the user clicks the button */
    public void toInProgress(View view) {
        Intent intent = new Intent(this, GallerySlideActivity.class);
        startActivity(intent);
    }
}
