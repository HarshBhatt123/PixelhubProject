package com.example.pixelhubproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapter extends RecyclerView.Adapter<adapter.viewHolder> {
    private Context mContext;
    private ArrayList<Item> mList;

    public adapter(Context context, ArrayList<Item> exampleList) {
        mContext = context;
        mList = exampleList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        return new viewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

         final Item currentItem = mList.get(position);
        String imageUrl = currentItem.getImageUrl();
        String creatorName = currentItem.getCreator();
        final String Download = currentItem.getDownload();

        holder.mImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
               // Toast.makeText(mContext, Download, Toast.LENGTH_LONG).show();
                Intent i =new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.addCategory(Intent.CATEGORY_BROWSABLE);
                i.setData(Uri.parse(Download));
                mContext.startActivity(i);
                return false;
            }
        });


//       holder.DownloadBtn.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//              // String Download = currentItem.getDownload();
//               //Toast.makeText(mContext, Download, Toast.LENGTH_LONG).show();
//               Intent i =new Intent();
//               i.setAction(Intent.ACTION_VIEW);
//               i.addCategory(Intent.CATEGORY_BROWSABLE);
//               i.setData(Uri.parse(Download));
//               mContext.startActivity(i);
//           }
//       });

        holder.mTextViewCreator.setText(creatorName);

        Picasso.get().load(imageUrl).fit().centerCrop().into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextViewCreator;
        public Button DownloadBtn;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
            mTextViewCreator = itemView.findViewById(R.id.text_view_creator);
           // DownloadBtn = itemView.findViewById(R.id.BtnDownload);
        }
    }


}
