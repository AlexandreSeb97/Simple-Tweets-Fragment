package com.codepath.apps.mysimpletweets;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.mysimpletweets.fragments.HomeTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.MentionsTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.TweetsListFragment;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimelineActivity extends FragmentActivity {

    private TweetsListFragment fragmentTweetsList;
    private TwitterClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        // Get the viewpaper
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
        // Set the viewpaper adapter for the pager
        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));
        // Find the sliding tabstrip
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the tabstrip to the viewpager
        tabStrip.setViewPager(vpPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_tweet) {
            Intent i = new Intent(this, TweetActivity.class);
            String postAPI = "statuses/update.json";
            String TweetAPI = "https://api.twitter.com/1.1/" + postAPI;
            i.putExtra("TweetAPI", TweetAPI);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void onProfileView(MenuItem mi){
        // Launch the profile view
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    // Return the order of the fragmnts in the view pager
    public class TweetsPagerAdapter extends FragmentPagerAdapter {
        private String tabTitles[] = {"Home", "Mentions"};

        public TweetsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        // The order and creation of fragments within the pager
        @Override
        public Fragment getItem(int position) {
            if (position == 0){
                return new HomeTimelineFragment();
            } else if (position == 1){
                return new MentionsTimelineFragment();
            } else{
                return null;
            }
        }
        // Return the tab title
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
        // How many fragments there are
        @Override
        public int getCount() {
            return tabTitles.length;
        }


    }
}
