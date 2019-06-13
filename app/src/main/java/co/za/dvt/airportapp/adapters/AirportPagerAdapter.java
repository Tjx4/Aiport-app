package co.za.dvt.airportapp.adapters;

import android.app.Activity;
import android.os.Parcelable;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import java.util.List;
import co.za.dvt.airportapp.fragments.AirportFragment;

public class AirportPagerAdapter extends FragmentStatePagerAdapter implements ViewPager.PageTransformer {
    public final static float BIG_SCALE = 1.0f;

    private Activity mContext;
    private List<AirportFragment> fragments;
    private FragmentManager mFragmentManager;

    public AirportPagerAdapter(Activity context, FragmentManager fragmentManager, List<AirportFragment> fragments) {
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
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}