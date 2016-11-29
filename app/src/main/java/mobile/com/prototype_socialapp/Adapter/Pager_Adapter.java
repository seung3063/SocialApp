package mobile.com.prototype_socialapp.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import mobile.com.prototype_socialapp.Fragment.PageFragment;

/**
 * Created by SeungJun on 16. 10. 4..
 */
public class Pager_Adapter extends FragmentStatePagerAdapter {

    int main_category_num;
    int _numOfTabs;

    public Pager_Adapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this._numOfTabs = numOfTabs;
    }


    String[] string={"Need You","Need Team"};
    @Override
    public Fragment getItem(int position) {

        return PageFragment.create(position);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return string[position];
    }
    @Override
    public int getCount() {
        return 2;  // 총 2개의 page를 보여줍니다.
    }

    public int getMain_category_num() {
        return main_category_num;
    }

    public void setMain_category_num(int main_category_num) {
        this.main_category_num = main_category_num;
    }
}
