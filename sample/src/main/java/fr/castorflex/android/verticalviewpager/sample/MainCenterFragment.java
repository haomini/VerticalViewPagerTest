package fr.castorflex.android.verticalviewpager.sample;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;
import fr.castorflex.android.verticalviewpager.sample.verticaltablayout.QTabView;
import fr.castorflex.android.verticalviewpager.sample.verticaltablayout.TabAdapter;
import fr.castorflex.android.verticalviewpager.sample.verticaltablayout.TabView;
import fr.castorflex.android.verticalviewpager.sample.verticaltablayout.VerticalTabLayout;

/**
 * @author zhouhao
 * @since 2019/03/08
 */
public class MainCenterFragment extends Fragment {
    private static final float MIN_SCALE = 0.75f;
    private static final float MIN_ALPHA = 0.75f;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final VerticalViewPager verticalViewPager = view.findViewById(R.id.verticalviewpager);
        final VerticalTabLayout tablayout = view.findViewById(R.id.tablayout);
        tablayout.setTabAdapter(new MyTabAdapter());
        tablayout.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tab, int position) {
                verticalViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselected(TabView tab, int position) {

            }
        });
        verticalViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tablayout.setTabSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        verticalViewPager.setAdapter(new DummyAdapter(getChildFragmentManager()));
        verticalViewPager.setPageMargin(getResources().
                getDimensionPixelSize(R.dimen.pagemargin));
        verticalViewPager.setPageMarginDrawable(new ColorDrawable(
                getResources().getColor(android.R.color.holo_green_dark)));

        verticalViewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View view, float position) {
                int pageWidth = view.getWidth();
                int pageHeight = view.getHeight();

                if (position < -1) { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    view.setAlpha(0);

                } else if (position <= 1) { // [-1,1]
                    // Modify the default slide transition to shrink the page as well
                    float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                    float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                    float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                    if (position < 0) {
                        view.setTranslationY(vertMargin - horzMargin / 2);
                    } else {
                        view.setTranslationY(-vertMargin + horzMargin / 2);
                    }

                    // Scale the page down (between MIN_SCALE and 1)
                    view.setScaleX(scaleFactor);
                    view.setScaleY(scaleFactor);

                    // Fade the page relative to its size.
                    view.setAlpha(MIN_ALPHA +
                            (scaleFactor - MIN_SCALE) /
                                    (1 - MIN_SCALE) * (1 - MIN_ALPHA));

                } else { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    view.setAlpha(0);
                }
            }
        });
    }

    public class DummyAdapter extends FragmentPagerAdapter {
        List<MainFragment> fragments = new ArrayList<>();

        public DummyAdapter(FragmentManager fm) {
            super(fm);

            for (int i = 0; i < 5; i++) {
                fragments.add(new MainFragment());
            }
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "PAGE 0";
                case 1:
                    return "PAGE 1";
                case 2:
                    return "PAGE 2";
                case 3:
                    return "PAGE 3";
                case 4:
                    return "PAGE 4";
            }
            return null;
        }
    }

    class MyTabAdapter implements TabAdapter {

        List<String> titles;

        {
            titles = new ArrayList<>();
            Collections.addAll(titles, "Android", "IOS", "Web", "JAVA", "C++"
            );
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public int getBadge(int position) {
            if (position == 5) {
                return position;
            }
            return 0;
        }

        @Override
        public QTabView.TabIcon getIcon(int position) {
            return null;
        }

        @Override
        public QTabView.TabTitle getTitle(int position) {
            return new QTabView.TabTitle.Builder(getContext())
                    .setContent(titles.get(position))
                    .setTextColor(Color.BLUE, Color.BLACK)
                    .build();
        }

        @Override
        public int getBackground(int position) {
            return 0;
        }
    }
}
