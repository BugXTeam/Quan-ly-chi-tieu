package com.example.bugx.quanlychitieu;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    FloatingActionButton mAddButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAddButton = findViewById(R.id.add_button);
        mAddButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(MainActivity.this, AddActivity.class));
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_PAGE = "arg_page";

        public PlaceholderFragment() {
        }

        // Method static dạng singleton, cho phép tạo fragment mới, lấy tham số đầu vào để cài đặt text.
        public static PlaceholderFragment newInstance(int arg) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_PAGE, arg);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            android.support.constraint.ConstraintLayout relativeLayout = (android.support.constraint.ConstraintLayout) rootView.findViewById(R.id.cl_fragment);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            /**
             * Số 1: Màu xanh.
             * Số 2: Màu đỏ.
             * Số 3: Màu vàng.
             */
            switch (getArguments().getInt(ARG_PAGE)) {
                case 0:
                    textView.setText("Page 1");
                    break;
                case 1:
                    textView.setText("Page 2");
                    break;
                case 2:
                    textView.setText("Page 3");
                    break;
                case 3:
                    textView.setText("Page 4");
                    break;
                case 4:
                    textView.setText("Page 5");
                    break;
                case 5:
                    textView.setText("Page 6");
                    break;
                case 6:
                    textView.setText("Page 7");
                    break;
                case 7:
                    textView.setText("Page 8");
                    break;
                case 8:
                    textView.setText("Page 9");
                    break;
                case 9:
                    textView.setText("Page 10");
                    break;
                case 10:
                    textView.setText("Page 11");
                    break;
                case 11:
                    textView.setText("Page 12");
                    break;
                default:
                    textView.setText("Page 1");
                    break;
            }

            return rootView;
        }
    }


    public static class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 12;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "1/2018";
                case 1:
                    return "2/2018";
                case 2:
                    return "3/2018";
                case 3:
                    return "4/2018";
                case 4:
                    return "5/2018";
                case 5:
                    return "6/2018";
                case 6:
                    return "7/2018";
                case 7:
                    return "8/2018";
                case 8:
                    return "9/2018";
                case 9:
                    return "10/2018";
                case 10:
                    return "11/2018";
                case 11:
                    return "12/2018";
            }
            return null;
        }
    }
}
