package com.mobiddiction.fishsmart.Onboarding;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.KeyEvent;

import com.mobiddiction.fishsmart.BaseTabActivity;
import com.mobiddiction.fishsmart.Network.BasicEvent;
import com.mobiddiction.fishsmart.Network.ModelManager;
import com.mobiddiction.fishsmart.Network.NetworkManager;
import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.SignIn.SignInActivity;
import com.mobiddiction.fishsmart.dao.Onboarding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import me.relex.circleindicator.CircleIndicator;

public class OnboardingActivity extends BaseTabActivity {


    ViewPager pager;
    CircleIndicator indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        pager = findViewById(R.id.view_pager);
        indicator = findViewById(R.id.indicator);

        List<Onboarding> onboarding = ModelManager.getInstance().getOnboarding();
        if(onboarding.size() > 0) {
            setViewPager(onboarding);
        } else{
            startActivity(new Intent(OnboardingActivity.this, SignInActivity.class));
        }
    }

    private void setViewPager(List<Onboarding> onboardings) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        if(onboardings != null) {
            List<Onboarding> activeOnboardings = new ArrayList<>();
            for (int x = 0; x < onboardings.size(); x++) {
                if (onboardings.get(x).getEnabled().equals("true")) {
                    activeOnboardings.add(onboardings.get(x));
                }
            }
            for (int x = 0; x < activeOnboardings.size(); x++) {
                if (x == activeOnboardings.size() - 1) {
                    adapter.addFrag(new OnboardingFragment4(activeOnboardings.get(x)), "");
                } else {
                    adapter.addFrag(new OnboardingFragment1(OnboardingActivity.this, activeOnboardings.get(x)), "");
                }
            }
//        adapter.addFrag(new OnboardingFragment1(OnboardingActivity.this),"");
//        adapter.addFrag(new OnboardingFragment2(OnboardingActivity.this),"");
//        adapter.addFrag(new OnboardingFragment3(OnboardingActivity.this),"");
//        adapter.addFrag(new OnboardingFragment4(),"");
        pager.setAdapter(adapter);
        indicator.setViewPager(pager);
        }
    }

    public void moveToNext(int x){
        pager.setCurrentItem(x);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(BasicEvent event) {
        if(event == BasicEvent.GET_ONBOARDING_SUCCESS){
            List<Onboarding> onboarding = ModelManager.getInstance().getOnboarding();
            if(onboarding.size() > 0) {
                setViewPager(onboarding);
            } else{
                startActivity(new Intent(OnboardingActivity.this, SignInActivity.class));
            }
        }

        if(event == BasicEvent.GET_ONBOARDING_FAILED){

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
