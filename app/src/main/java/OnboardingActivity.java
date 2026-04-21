package com.example.project;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class OnboardingActivity extends AppCompatActivity {

    // Declare the variables for viewPager and tabLayout
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private OnboardingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        // Initialize the viewPager and tabLayout
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        // Create fragment list for onboarding pages
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new OnboardingFragment1()); // First Slide
        fragments.add(new OnboardingFragment2()); // Second Slide
        fragments.add(new OnboardingFragment3()); // Third Slide
        fragments.add(new OnboardingFragment4()); // Fourth Slide

        // Set up the adapter
        adapter = new OnboardingAdapter(this, fragments);
        viewPager.setAdapter(adapter);

        // Set up TabLayout with ViewPager2 and handle tab selection
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            // No specific configuration needed here
        }).attach();
    }
}




