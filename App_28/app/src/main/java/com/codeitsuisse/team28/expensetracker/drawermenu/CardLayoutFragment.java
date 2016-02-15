package com.codeitsuisse.team28.expensetracker.drawermenu;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codeitsuisse.team28.expensetracker.card.Card;
import com.codeitsuisse.team28.expensetracker.card.OnButtonClickListener;
import com.codeitsuisse.team28.expensetracker.listeners.RecyclerItemClickListener;
import com.codeitsuisse.team28.expensetracker.R;
import com.codeitsuisse.team28.expensetracker.card.provider.BasicImageButtonsCardProvider;
import com.codeitsuisse.team28.expensetracker.view.MaterialListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CardLayoutFragment extends android.support.v4.app.Fragment {

    private FragmentActivity mContext;
    private MaterialListView mListView;
    private View row;


    public CardLayoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        row = inflater.inflate(R.layout.fragment_card_layout, container, false);

        // Bind the MaterialListView to a variable
        mListView = (MaterialListView) row.findViewById(R.id.material_listview);

        // Fill the array with mock content
        fillWithCards();


        // Add the ItemTouchListener
        mListView.addOnItemTouchListener(new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Card card, int position) {
            }

            @Override
            public void onItemLongClick(@NonNull Card card, int position) {
            }
        });

        // Inflate the layout for this fragment
        return row;
    }

    private void fillWithCards() {
        for (int i = 0; i < 20; i++) {
            mListView.add(getCard(i));
        }
    }

    private Card getCard(final int position) {
        String title = "Card number " + (position + 1);
        String description = "Card Description";

        final BasicImageButtonsCardProvider provider = new Card.Builder(MainActivity.a)
                .setTag("BASIC_IMAGE_BUTTON_CARD")
                .setDismissible()
                .withProvider(BasicImageButtonsCardProvider.class)
                .setTitle(title)
                .setDescription(description)
                .setDrawable(R.drawable.dog)
                .setLeftButtonText("SHARE")
                .setRightButtonText("LEARN MORE");

        provider.setOnLeftButtonClickListener(new OnButtonClickListener() {
            @Override
            public void onButtonClicked(final View view, final Card card) {
                Toast.makeText(mContext, "You have pressed the left button", Toast.LENGTH_SHORT).show();
            }
        });
        provider.setOnRightButtonClickListener(new OnButtonClickListener() {
            @Override
            public void onButtonClicked(final View view, final Card card) {
                Toast.makeText(mContext, "You have pressed the right button", Toast.LENGTH_SHORT).show();
            }
        });

        provider.setDividerVisible(true);

        return provider.endConfig().build();
    }

    @Override
    public void onAttach(Activity activity) {
        mContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

}

