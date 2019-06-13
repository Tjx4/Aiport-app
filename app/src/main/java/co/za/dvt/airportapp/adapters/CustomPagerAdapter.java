package co.za.dvt.airportapp.adapters;

import android.app.Activity;
import android.os.Parcelable;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import java.util.List;
import co.za.dvt.airportapp.R;
import co.za.dvt.airportapp.customViews.CustomLinearLayout;
import co.za.dvt.airportapp.fragments.AirportFragment;

public class CustomPagerAdapter extends FragmentStatePagerAdapter implements ViewPager.PageTransformer {
    public final static float BIG_SCALE = 1.0f;
    public final static float SMALL_SCALE = 0.7f;
    public final static float DIFF_SCALE = BIG_SCALE - SMALL_SCALE;

    private Activity mContext;
    private List<AirportFragment> fragments;
    private FragmentManager mFragmentManager;
    private float mScale;

    public CustomPagerAdapter(Activity context, FragmentManager fragmentManager, List<AirportFragment> fragments) {
        super(fragmentManager);
        this.mFragmentManager = fragmentManager;
        this.mContext = context;
        this.fragments = fragments;
    }

    public void addFragment(AirportFragment fragment) {
        fragments.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void transformPage(View page, float position) {
        CustomLinearLayout myLinearLayout = (CustomLinearLayout) page.findViewById(R.id.item_root);

        float scale = BIG_SCALE;

        if (position > 0) {
            scale = scale - position * DIFF_SCALE;
        } else {
            scale = scale + position * DIFF_SCALE;
        }

        if (scale < 0)
            scale = 0;

        myLinearLayout.setScaleBoth(scale);
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}