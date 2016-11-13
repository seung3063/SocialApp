package mobile.com.prototype_socialapp.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import mobile.com.prototype_socialapp.Fragment.PageFragment;

/**
 * Created by SeungJun on 16. 10. 4..
 */
public class Pager_Adapter extends FragmentStatePagerAdapter {

    int _numOfTabs;

    public Pager_Adapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this._numOfTabs = numOfTabs;
    }


    String[] string={"Need You","Need Team","Study"};
    @Override
    public Fragment getItem(int position) {
        // 해당하는 page의 Fragment를 생성합니다.
        return PageFragment.create(position);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return string[position];
    }
    @Override
    public int getCount() {
        return 2;  // 총 3개의 page를 보여줍니다.
    }
}
