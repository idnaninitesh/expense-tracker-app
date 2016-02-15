package com.codeitsuisse.team28.expensetracker.drawermenu;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toolbar;

import com.codeitsuisse.team28.expensetracker.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends android.support.v4.app.Fragment {

    private Toolbar toolbar;

    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    private FragmentActivity myContext;
    private TravelListAdapter mAdapter;
    private Menu menu;
    private View row;




    public GalleryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        row = inflater.inflate(R.layout.fragment_gallery, container, false);

        mStaggeredLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        mRecyclerView = (RecyclerView) row.findViewById(R.id.list);
        mRecyclerView.setLayoutManager(mStaggeredLayoutManager);

        mRecyclerView.setHasFixedSize(true); //Data size is fixed - improves performance
        mAdapter = new TravelListAdapter(myContext);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(onItemClickListener);

        // Inflate the layout for this fragment
        return row;
    }

    TravelListAdapter.OnItemClickListener onItemClickListener = new TravelListAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            //Intent transitionIntent = new Intent(GalleryFragment.this, DetailActivity.class);
            Intent transitionIntent = new Intent(getActivity(),DetailActivity.class);
            transitionIntent.putExtra(DetailActivity.EXTRA_PARAM_ID, position);
            ImageView placeImage = (ImageView) v.findViewById(R.id.placeImage);
            LinearLayout placeNameHolder = (LinearLayout) v.findViewById(R.id.placeNameHolder);

            View navigationBar = row.findViewById(android.R.id.navigationBarBackground);
            View statusBar = row.findViewById(android.R.id.statusBarBackground);

            Pair<View, String> imagePair = Pair.create((View) placeImage, "tImage");
            Pair<View, String> holderPair = Pair.create((View) placeNameHolder, "tNameHolder");
            Pair<View, String> navPair = Pair.create(navigationBar, Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME);
            Pair<View, String> statusPair = Pair.create(statusBar, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME);
            Pair<View, String> toolbarPair = Pair.create((View) toolbar, "tActionBar");

            startActivity(transitionIntent);

            //ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), imagePair, holderPair, navPair, statusPair, toolbarPair);
            //ActivityCompat.startActivity(getActivity(), transitionIntent, options.toBundle());
        }
    };

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

}
