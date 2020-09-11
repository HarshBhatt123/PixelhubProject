package com.example.pixelhubproject;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment  {
    private RecyclerView mRecyclerView;
    private EditText mSearchEditText;
    private Button mSearchButton;
    private adapter mExampleAdapter;

    private ArrayList<Item> mList;
    private RequestQueue mRequestQueue;
    String q;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v= inflater.inflate(R.layout.fragment_home,container,false);
        context = container.getContext();
       mRecyclerView = v.findViewById(R.id.recycler);
       mSearchEditText = v.findViewById(R.id.et);
       mSearchButton =v.findViewById(R.id.btnSearch);
       mSearchEditText.setSingleLine();


        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mList = new ArrayList<>();
        getFirstAPIdata();

       mSearchButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
//               q = mSearchEditText.getText().toString();
//                getAPIdata();

               if(mSearchEditText.getText().toString().trim().equals("")){
                   Toast.makeText(context, "Type Something", Toast.LENGTH_SHORT).show();
               }else{
                   clearDataRecyclerView();
                   Toast.makeText(context, "Please wait , fetching data", Toast.LENGTH_LONG).show();
                   q = mSearchEditText.getText().toString();
                   getAPIdata();
               }
           }
       });


    return v;
    }



    private void getAPIdata(){
        RequestQueue queue ;
        queue = Volley.newRequestQueue(context);


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
                                String description = resultsObject.getString("alt_description");

                                JSONObject linkObject = resultsObject.getJSONObject("urls");

                                String ImgLink = linkObject.getString("full");


                               mList.add(new Item( ImgLink,description));

                              mExampleAdapter = new adapter(context, mList);
                               mRecyclerView.setAdapter(mExampleAdapter);


                            }

                            // Log.d(TAG, "onResponse: working"+ response.getString("title"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, "Something is  wrong", Toast.LENGTH_SHORT).show();
                // Log.d(TAG, "onErrorResponse: Something wrong");
            }
        });

        queue.add(jsonObjectRequest);


    }

    private void getFirstAPIdata(){
        RequestQueue queue ;
        queue = Volley.newRequestQueue(context);


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                "https://api.unsplash.com/photos?per_page=50&client_id=rxHIYpkhK4lK-6kv3akg9-iJjlBsDX-IRsg9l50lcsg"
                , null,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                          //  JSONArray array = response.getJSONArray("array");


                            for (int i = 0; i < response.length(); i++){
                                JSONObject resultsObject = response.getJSONObject(i);

                                //JSONObject dataObject = resultsObject.getJSONObject("description");
                                String description = resultsObject.getString("alt_description");

                                JSONObject linkObject = resultsObject.getJSONObject("urls");

                                String ImgLink = linkObject.getString("small");


                                mList.add(new Item( ImgLink,description));

                                mExampleAdapter = new adapter(context, mList);
                                mRecyclerView.setAdapter(mExampleAdapter);

                            }

                            // Log.d(TAG, "onResponse: working"+ response.getString("title"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, "Something is  wrong", Toast.LENGTH_SHORT).show();
                // Log.d(TAG, "onErrorResponse: Something wrong");
            }
        });

        queue.add(jsonArrayRequest);
    }

    public  void clearDataRecyclerView(){
        mList.clear();
        mExampleAdapter.notifyDataSetChanged();
    }
}
