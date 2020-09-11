package com.example.pixelhubproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

//    private RecyclerView mRecyclerView;
//    private adapter mExampleAdapter;
//    private ArrayList<Item> mList;
//    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mRecyclerView = findViewById(R.id.recycler);
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mList = new ArrayList<>();




        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_home:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                break;
            case R.id.nav_addPhoto:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AddPicFragment()).commit();
                break;
            case R.id.nav_photos:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MyPicFragment()).commit();
                break;
            case R.id.nav_insta:
                //Toast.makeText(this, "Insta", Toast.LENGTH_SHORT).show();
            {
                Intent i =new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.addCategory(Intent.CATEGORY_BROWSABLE);
                i.setData(Uri.parse("https://www.instagram.com/pixel_hub_19"));
                startActivity(i);
            }
                break;
            case R.id.nav_exit: {
                finish();
                System.exit(0);
            }

                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void getAPIdata(){
        RequestQueue queue ;
        queue = Volley.newRequestQueue(this);
        String q ="";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://api.unsplash.com/search/photos?query="+q+"&per_page=50&client_id=rxHIYpkhK4lK-6kv3akg9-iJjlBsDX-IRsg9l50lcsg"
                          , null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //JSONObject collectionObject = response.getJSONObject("collection");
                            JSONArray resultsArray = response.getJSONArray("results");

                            for (int i = 0; i < resultsArray.length(); i++){
                                JSONObject resultsObject = resultsArray.getJSONObject(i);

                                //JSONObject dataObject = resultsObject.getJSONObject("description");
                                String description = resultsObject.getString("description");

                                JSONObject linkObject = resultsObject.getJSONObject("urls");

                                String ImgLink = linkObject.getString("full");

//                                mList.add(new Item( ImgLink,title));
//
//                                mExampleAdapter = new adapter(MainActivity.this, mList);
//                                mRecyclerView.setAdapter(mExampleAdapter);
                               // mExampleAdapter.setOnItemClickListener(MainActivity.this);


//                        ListElementsArrayList.add(ImgLink+"  "+description+"  "+nasaId+"  "+title+"  "+mediaType);
//                        adapter.notifyDataSetChanged();



                                //    Toast.makeText(ImageLib.this, ImgLink, Toast.LENGTH_LONG).show();

                            }

                            Log.d(TAG, "onResponse: working"+ response.getString("title"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this, "Something is  wrong", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onErrorResponse: Something wrong");
            }
        });

        queue.add(jsonObjectRequest);


    }
}