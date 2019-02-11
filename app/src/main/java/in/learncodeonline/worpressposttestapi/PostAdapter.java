package in.learncodeonline.worpressposttestapi;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import in.learncodeonline.worpressposttestapi.Posts;
import in.learncodeonline.worpressposttestapi.R;

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int FADE_DURATION = 1000;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
    Context context;
    Bundle bundle=new Bundle();
    private List<Posts> questionList;
    private boolean mWithHeader;
    private boolean mWithFooter;
    private View.OnClickListener mOnClickListener;
    public PostAdapter(List<Posts> posts, Context context, boolean withHeader, boolean withFooter) {
        this.questionList = posts;
        this.context=context;
        this.mWithHeader=withHeader;
        this.mWithFooter=withFooter;
    }
    @Override
    public int getItemViewType(int position) {
        if (mWithHeader && isPositionHeader(position))
            return TYPE_HEADER;
        if (mWithFooter && isPositionFooter(position))
            return TYPE_FOOTER;
        return TYPE_ITEM;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if(viewType==TYPE_HEADER) {

        }
        else if(viewType==TYPE_FOOTER){

        }
        else {
            View itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.itemview, viewGroup, false);

            VideoViewHolder holder = new VideoViewHolder(itemView);
            itemView.setTag(holder);
            itemView.setOnClickListener(mOnClickListener);
            return holder;
        }
        return null;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof  header){
//((header) holder).vName.setText(album_name);
        }
        else if(holder instanceof  footer){
            ((footer) holder).context = context;
        }
        else {
            Posts post=getItem(position);
            ((VideoViewHolder)holder).vTitle.setText(post.getTitle());
            ((VideoViewHolder) holder).context = context;
            ((VideoViewHolder)holder).vExcerpt.setText(Html.fromHtml(post.getExcerpt()));

        }
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    @Override
    public int getItemCount() {
        int itemCount=0;
        if(questionList!=null) {
            itemCount = questionList.size();
            if (mWithHeader)
                itemCount = itemCount + 1;
            if (mWithFooter)
                itemCount = itemCount + 1;
        }
        return itemCount;
    }
    private boolean isPositionHeader(int position) {
        return position == 0;
    }
    private boolean isPositionFooter(int position) {
        return position == getItemCount() - 1;
    }
    public void setOnClickListener(View.OnClickListener lis) {
        mOnClickListener = lis;
    }
    protected Posts getItem(int position) {
        return mWithHeader ? questionList.get(position - 1) : questionList.get(position);
    }
    private int getItemPosition(int position){
        return mWithHeader ? position - 1 : position;
    }
    public void setData(List<Posts> questionList) {
        this.questionList=questionList;
    }
    public class VideoViewHolder extends RecyclerView.ViewHolder {
        protected ImageView vImage;
        protected TextView vName;
        protected TextView vDetails,vTitle;
        protected TextView vExcerpt;

        protected String vFilePath;
        protected  Context context;
        protected   Bundle b;
        protected int position;
        private String album;

        public VideoViewHolder(View v) {
            super(v);
            vTitle = v.findViewById(R.id.title);
            vExcerpt=v.findViewById(R.id.excerpt);
        }
        public void clearAnimation() {
            this.clearAnimation();
        }
    }
    public class header extends RecyclerView.ViewHolder {
        protected  Context context;
        protected int position;
        public header(View v) {
            super(v);
        }
    }
    public class footer extends RecyclerView.ViewHolder {
        protected  Context context;
        protected int position;
        public footer(View v) {
            super(v);
        }
    }
}