package com.example.pixelhubproject;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyPicFragment extends Fragment {
    private Context c;
    private RecyclerView mRV;
    private ImageAdapter mAdapt;
    private ProgressBar mProgressCircle;
    private static final String TAG = "MyPicFragment";
    private DatabaseReference mDBRef;
    private List<Upload> mUploads;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vv= inflater.inflate(R.layout.fragment_mypics,container,false);

        c = container.getContext();
        mRV = vv.findViewById(R.id.recycler_view);
        mRV.setHasFixedSize(true);
        mRV.setLayoutManager(new LinearLayoutManager(c));
        mProgressCircle = vv.findViewById(R.id.progress_circle);

        mUploads = new ArrayList<>();
        mDBRef = FirebaseDatabase.getInstance().getReference("uploads");
        dataBase();
        Log.d(TAG, "onCreateView: success");
      return  vv;

    }
    private void dataBase(){
        mDBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Upload up  = postSnapshot.getValue(Upload.class);
                    mUploads.add(up);
                }
                mAdapt = new ImageAdapter(c, mUploads);
                mRV.setAdapter(mAdapt);
                mProgressCircle.setVisibility(View.INVISIBLE);
                Log.d(TAG, "onDataChange: success");
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(c, error.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
                Log.d(TAG, "onCancelled: success");

            }
        });


    }
}
