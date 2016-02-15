package com.codeitsuisse.team28.expensetracker.view;
//package com.dexafree.materialList.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codeitsuisse.team28.expensetracker.card.Card;
import com.codeitsuisse.team28.expensetracker.card.CardLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MaterialListAdapter extends RecyclerView.Adapter<MaterialListAdapter.ViewHolder> implements IMaterialListAdapter, Observer {
    private final List<Card> mCardList = new ArrayList<>();
    private final MaterialListView.OnSwipeAnimation mAnimation;

    public MaterialListAdapter(@NonNull final MaterialListView.OnSwipeAnimation animation) {
        mAnimation = animation;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CardLayout view;

        public ViewHolder(@NonNull final View v) {
            super(v);
            view = (CardLayout) v;
        }

        public void build(Card card) {
            view.build(card);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.build(mCardList.get(position));
    }

    @Override
    public int getItemCount() {
        return mCardList.size();
    }

    @Override
    public int getItemViewType(final int position) {
        return mCardList.get(position).getConfig().getLayout();
    }

    public void add(@NonNull final Card card) {
        mCardList.add(card);
        card.getConfig().addObserver(this);
        notifyDataSetChanged();
    }

    @Override
    public void addAtStart(@NonNull Card card) {

    }

    @Override
    public void addAll(@NonNull Card... cards) {

    }

    @Override
    public void addAll(@NonNull Collection<Card> cards) {

    }

    @Override
    public void remove(@NonNull Card card, boolean animate) {

    }

    public boolean isEmpty() {
        return mCardList.isEmpty();
    }

    @Override
    public void clear() {

    }

    @Override
    public void clearAll() {

    }

    public Card getCard(int position) {
        if(position > 0 && position < mCardList.size()) {
            return mCardList.get(position);
        }
        return null;
    }

    public int getPosition(@NonNull Card card) {
        return mCardList.indexOf(card);
    }

    @Override
    public void update(final Observable observable, final Object data) {
        if(data == null) {
            notifyDataSetChanged();
        }
    }
}
