package com.example.vipul.blogapp.Data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vipul.blogapp.Model.Blog;
import com.example.vipul.blogapp.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class BlogRecyclerAdapter extends RecyclerView.Adapter<BlogRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<Blog> blogList;

    public BlogRecyclerAdapter(Context context, List<Blog> blogList) {
        this.context = context;
        this.blogList = blogList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_row,viewGroup,false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Blog blog = blogList.get(i);
        //String imageurl =null;
        viewHolder.titleView.setText(blog.getTitle1());
        viewHolder.desc.setText(blog.getDecription());
        java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
        String formattedate = dateFormat.format(new Date(Long.valueOf(blog.getTimestamp())).getTime());
        viewHolder.timestamp.setText(formattedate);
        String imageurl = blog.getImage1();
        Picasso.get().load(imageurl).into(viewHolder.imageView);


    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleView;
        public TextView desc;
        public ImageView imageView;
        public TextView timestamp;
        String userid;
        public ViewHolder(@NonNull View itemView,Context ctx) {
            super(itemView);
            context=ctx;
            titleView=(TextView)itemView.findViewById(R.id.postTitleList);
            desc=(TextView)itemView.findViewById(R.id.postTextList);
            timestamp=(TextView)itemView.findViewById(R.id.timestampList);
            imageView=(ImageView)itemView.findViewById(R.id.postImageList);
            userid=null;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
