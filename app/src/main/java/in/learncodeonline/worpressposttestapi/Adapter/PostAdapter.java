package in.learncodeonline.worpressposttestapi.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import in.learncodeonline.worpressposttestapi.Model.Posts;
import in.learncodeonline.worpressposttestapi.R;

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Context context;
    List<Posts> posts;
    TextView vExcerpt,vTitle;
    //create construcvtor
    public PostAdapter(List<Posts> posts, Context context)
    {
        this.posts = posts;
        this.context=context;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        final  Posts post =  posts.get(position);
        String title,desc;
        title=post.getTitle();
        desc=post.getExcerpt();
        vTitle.setText(title);
        vExcerpt.setText(desc);
    }

    @Override
    public int getItemCount() {

        return posts.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(View itemview) {
            super(itemview);
            vTitle = itemview.findViewById(R.id.title);
            vExcerpt=itemview.findViewById(R.id.excerpt);
        }

    }


}