package com.ynov.h.blizzardreader;

import android.app.Dialog;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;
import com.ynov.h.blizzardreader.data.model.Card;
import com.ynov.h.blizzardreader.data.remote.ApiUtils;
import com.ynov.h.blizzardreader.data.remote.HSService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private CardsAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private HSService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mService = ApiUtils.getHSService();
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mAdapter = new CardsAdapter(this, new ArrayList<Card>(0), new CardsAdapter.PostItemListener() {



            //builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
            //builder.getWindow().setBackgroundDrawable(
            //      new ColorDrawable(android.graphics.Color.TRANSPARENT));


            public void onPostClick(String img) {

                Dialog builder = new Dialog(MainActivity.this);
                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                builder.getWindow().setLayout(width, height);


                Uri uri = Uri.parse(img); //path of image

                Log.d("url", img);

                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int screenWidth = size.x;
                int screenHeight = size.y;

                int idealWidth = (int) (screenWidth*0.7);

                ImageView imageView = new ImageView(MainActivity.this);

                Picasso.get().load(uri).resize(idealWidth,screenHeight)
                        .centerInside() .into(imageView);
                //set the image in dialog popup

                builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                builder.show();

            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        loadCards("Warlock");

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_druid) {
            loadCards("Druid");
        } else if (id == R.id.nav_hunter) {
            loadCards("Hunter");

        } else if (id == R.id.nav_mage) {
            loadCards("Mage");

        } else if (id == R.id.nav_paladin) {
            loadCards("Paladin");

        } else if (id == R.id.nav_priest) {
            loadCards("Priest");

        } else if (id == R.id.nav_rogue) {
            loadCards("Rogue");

        }else if (id == R.id.nav_shaman) {
            loadCards("Shaman");

        }else if (id == R.id.nav_warlock) {
            loadCards("Warlock");


        }else if (id == R.id.nav_warrior) {
            loadCards("Warrior");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void loadCards(String playerClass) {
        mService.getCards(playerClass).enqueue(new Callback<List<Card>>() {
            @Override
            public void onResponse(Call<List<Card>> call, Response<List<Card>> response) {

                if(response.isSuccessful()) {
                    mAdapter.updateAnswers((List<Card>) response.body());
                    Log.d("MainActivity", "posts loaded from API");
                }else {
                    int statusCode  = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<List<Card>> call, Throwable t) {
                Log.d("error", t.getMessage());
                Log.d("MainActivity", "error loading from API");

            }
        });
    }
}
