package br.com.stanzione.uoltest.noticias.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import br.com.stanzione.uoltest.R;
import br.com.stanzione.uoltest.data.News;
import br.com.stanzione.uoltest.util.DateUtil;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnNewsSelectedListener {
        void onNewsSelected(News news);
    }

    private static final int WITH_IMAGE = 1;
    private static final int NO_IMAGE = 2;
    private static final int BANNER = 3;

    private int viewsToCount = 0;

    private Context context;
    private List<News> newsList = new ArrayList<>();
    private OnNewsSelectedListener listener;

    public NewsAdapter(Context context, OnNewsSelectedListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if (viewsToCount > 0 && position > 0 && position % viewsToCount == 0) {
            return BANNER;
        }

        News currentNews = newsList.get(getRealPosition(position));

        if(TextUtils.isEmpty(currentNews.getThumbUrl())){
            return NO_IMAGE;
        }
        else{
            return WITH_IMAGE;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == WITH_IMAGE) {
            View view = LayoutInflater.from(context).inflate(R.layout.row_news, parent, false);
            return new ViewHolderWithImage(view);
        }
        else if(viewType == NO_IMAGE){
            View view = LayoutInflater.from(context).inflate(R.layout.row_news_no_image, parent, false);
            return new ViewHolderNoImage(view);
        }
        else {
            View view = LayoutInflater.from(context).inflate(R.layout.row_banner, parent, false);
            return new ViewHolderBanner(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()){
            case WITH_IMAGE:
                bindWithImage((ViewHolderWithImage) holder, position);
                break;
            case NO_IMAGE:
                bindNoImage((ViewHolderNoImage) holder, position);
                break;
            case BANNER:
                break;
        }

    }

    @Override
    public int getItemCount() {

        if(null == newsList){
            return 0;
        }

        int additionalContent = 0;
        if (newsList.size() > 0 && viewsToCount > 0 && newsList.size() > viewsToCount) {
            additionalContent = (newsList.size() + (newsList.size() / viewsToCount)) / viewsToCount;
        }
        return newsList.size() + additionalContent;
    }

    private void bindWithImage(ViewHolderWithImage holder, int position){
        News currentNews = newsList.get(getRealPosition(position));

        holder.newsTitleTextView.setText(currentNews.getTitle());
        holder.newsTimeTextView.setText(DateUtil.formatHourMinute(currentNews.getUpdatedDate()));

        holder.newsConstraintLayout.setOnClickListener(view -> listener.onNewsSelected(currentNews));

        Glide.with(context)
                .load(currentNews.getThumbUrl())
                .apply(
                        new RequestOptions()
                                .centerCrop()
                                .fitCenter()
                )
                .into(holder.newsThumbImageView);
    }

    private void bindNoImage(ViewHolderNoImage holder, int position){
        News currentNews = newsList.get(getRealPosition(position));

        holder.newsTitleTextView.setText(currentNews.getTitle());
        holder.newsTimeTextView.setText(DateUtil.formatHourMinute(currentNews.getUpdatedDate()));

        holder.newsConstraintLayout.setOnClickListener(view -> listener.onNewsSelected(currentNews));
    }

    public void setItems(List<News> newsList) {
        this.newsList = newsList;
        notifyDataSetChanged();
    }

    public void setBannerAfterViews(int viewsToCount){
        this.viewsToCount = viewsToCount;
    }

    public News getItemInPosition(int position) {
        return newsList.get(getRealPosition(position));
    }

    private int getRealPosition(int position) {
        if (viewsToCount == 0) {
            return position;
        } else {
            return position - position / viewsToCount;
        }
    }

    class ViewHolderWithImage extends RecyclerView.ViewHolder {

        @BindView(R.id.newsConstraintLayout)
        ConstraintLayout newsConstraintLayout;

        @BindView(R.id.newsThumbImageView)
        ImageView newsThumbImageView;

        @BindView(R.id.newsTitleTextView)
        TextView newsTitleTextView;

        @BindView(R.id.newsTimeTextView)
        TextView newsTimeTextView;

        ViewHolderWithImage(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    class ViewHolderNoImage extends RecyclerView.ViewHolder {

        @BindView(R.id.newsConstraintLayout)
        ConstraintLayout newsConstraintLayout;

        @BindView(R.id.newsTitleTextView)
        TextView newsTitleTextView;

        @BindView(R.id.newsTimeTextView)
        TextView newsTimeTextView;

        ViewHolderNoImage(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    class ViewHolderBanner extends RecyclerView.ViewHolder {

        ViewHolderBanner(View itemView) {
            super(itemView);
        }

    }
}
