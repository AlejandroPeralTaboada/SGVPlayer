package com.sgvplayer.sgvplayer.ui.uiMusicTabs.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sgvplayer.sgvplayer.model.mp3Service.Mp3Service;
import com.sgvplayer.sgvplayer.ui.uiMusicTabs.AllSongsFragment.OnListFragmentInteractionListener;
import com.sgvplayer.sgvplayer.R;
import com.sgvplayer.sgvplayer.model.fileNavigator.Mp3File;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Mp3File} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class MyAllSongsRecyclerViewAdapter extends RecyclerView.Adapter<MyAllSongsRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private final List<Mp3File> mValues;
    private final List<RoundedBitmapDrawable> mImages;
    private final OnListFragmentInteractionListener mListener;

    public MyAllSongsRecyclerViewAdapter(List<Mp3File> items, OnListFragmentInteractionListener listener, Context context) {
        mContext = context;
        mValues = items;
        mListener = listener;
        mImages = returnMusicIcons(items);
    }

    private List<RoundedBitmapDrawable> returnMusicIcons(List<Mp3File> items){
        List<RoundedBitmapDrawable> toReturn = new ArrayList<>();
        for (Mp3File m : items){
            Bitmap source = m.getAlbumCover();
            RoundedBitmapDrawable toAdd = RoundedBitmapDrawableFactory.create(mContext.getResources(), source);
            toAdd.setCornerRadius(R.dimen.round_system_icon_radius);
            toReturn.add(toAdd);
        }
        return toReturn;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_all_songs_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final int index = holder.getAdapterPosition();
        holder.mContentView.setText(mValues.get(position).getTitle());
        holder.mIconView.setImageDrawable(mImages.get(position));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onAllSongsListFragmentInteraction(mValues,index);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final ImageView mIconView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            mIconView = (ImageView) view.findViewById(R.id.icon);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
