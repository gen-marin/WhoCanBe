package primerproyecto.uma.es.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupWindow;

/**
 * Created by Marin on 10/12/2017.
 */

/**
 * This class is the base of the 'ScreenSlide' for the 'Gallery'
 */
public class GallerySlideActivity extends FragmentActivity {
    private ViewPager vPager;
    private PagerAdapter vPagerAdapter;
    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_slide_page);
        vPager = (ViewPager) findViewById(R.id.pager);
        vPagerAdapter = new GallerySlidePageAdapter(getSupportFragmentManager());
        vPager.setAdapter(vPagerAdapter);
    }

    /** Called when the user clicks the Back button */
    public void toMenu(View view) {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }

    /** Called when the user clicks the Back button */
    public void toPop(View view) {
        //Intent intent = new Intent(this, PopUp.class);
        //startActivity(intent);
    }
}