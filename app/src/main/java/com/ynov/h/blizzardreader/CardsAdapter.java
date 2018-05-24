package com.ynov.h.blizzardreader;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ynov.h.blizzardreader.data.model.Card;

import java.util.List;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder> {

    private List<Card> mCards;
    private Context mContext;
    private PostItemListener mItemListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView titleTv;
        PostItemListener mItemListener;

        public ViewHolder(View itemView, PostItemListener postItemListener) {
            super(itemView);
            titleTv = (TextView) itemView.findViewById(android.R.id.text1);

            this.mItemListener = postItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Card item = getCard(getAdapterPosition());
            this.mItemListener.onPostClick(item.getImg());

            notifyDataSetChanged();
        }
    }

    public CardsAdapter(Context context, List<Card> posts, PostItemListener itemListener) {
        mCards = posts;
        mContext = context;
        mItemListener = itemListener;
    }

    @Override
    public CardsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);

        ViewHolder viewHolder = new ViewHolder(postView, this.mItemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CardsAdapter.ViewHolder holder, int position) {

        Card item = mCards.get(position);
        TextView textView = holder.titleTv;
        textView.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return mCards.size();
    }

    public void updateAnswers(List<Card> items) {
        mCards = items;
        notifyDataSetChanged();
    }

    private Card getCard(int adapterPosition) {
        return mCards.get(adapterPosition);
    }

    public interface PostItemListener {
        void onPostClick(String img);
    }
}