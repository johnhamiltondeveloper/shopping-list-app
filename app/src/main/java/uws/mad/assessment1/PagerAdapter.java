package uws.mad.assessment1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    // load fragment base on what tab you are on
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                shopping tab2 = new shopping();
                return tab2;
            case 1:
                pantry tab1 = new pantry();
                return tab1;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
