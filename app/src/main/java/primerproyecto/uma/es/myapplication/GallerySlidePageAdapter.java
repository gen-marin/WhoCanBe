package primerproyecto.uma.es.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Marin on 10/12/2017.
 */

/**
 * This is a helper class with the values and functions for the 'ScreenSlide'.
 */
public class GallerySlidePageAdapter  extends FragmentStatePagerAdapter {
    // Max pages that we want
    static final int NUM_PAGES = 3;
    public GallerySlidePageAdapter(FragmentManager fm) {
        super(fm);
    }

    // Returns the 'position' of the page that it is showing
    @Override
    public Fragment getItem(int position) {
        return GalleryPageFragment.newInstance(position+1);
    }

    // Returns the total amount of pages that we determine
    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
