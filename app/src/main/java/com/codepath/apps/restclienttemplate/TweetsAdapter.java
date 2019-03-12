package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.sql.Time;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

    private Context context;
    private List<Tweet> tweets;

    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }
    //Pass in context and list of tweets

    //for each row, inflate the layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, viewGroup, false);
        return new ViewHolder(view);
    }
    //bind values based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Tweet tweet = tweets.get(i);
        viewHolder.tvBody.setText(tweet.body);

        //have twitter handles viewed as clickable links
        new PatternEditableBuilder().
                addPattern(Pattern.compile("\\@(\\w+)"), Color.BLUE,
                        new PatternEditableBuilder.SpannableClickedListener() {
                            @Override
                            public void onSpanClicked(String text) {
                                Toast.makeText(context, "Clicked username: " + text,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }).into(viewHolder.tvBody);
        viewHolder.tvScreenName.setText(tweet.user.screenName);
        viewHolder.tvTime.setText(tweet.createdAt);
        viewHolder.tvName.setText(tweet.user.name);
        Glide.with(context).load(tweet.user.profileImageURL).into(viewHolder.ivProfileImage);


    }
    //Define the ViewHolder
    @Override
    public int getItemCount() {
        return tweets.size();
    }

    //Clean all elements in recycler view
    public void clear(){
        tweets.clear();
        notifyDataSetChanged();
    }

    //Add a list of items
    public void addTweets(List<Tweet> tweetList){
        tweets.addAll(tweetList);
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.ivProfileImage) public ImageView ivProfileImage;
        @BindView(R.id.tvScreenName) public TextView tvScreenName;
        @BindView(R.id.tvBody) public TextView tvBody;
        @BindView(R.id.tvTime) public TextView tvTime;
        @BindView(R.id.tvName) public TextView tvName;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
