package com.sgvplayer.sgvplayer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sgvplayer.sgvplayer.AllSongsFragment.OnListFragmentInteractionListener;
import com.sgvplayer.sgvplayer.model.fileNavigator.Mp3File;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Mp3File } and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyAllSongsRecyclerViewAdapter extends RecyclerView.Adapter<MyAllSongsRecyclerViewAdapter.ViewHolder> {

    private final List<Mp3File> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyAllSongsRecyclerViewAdapter(List<Mp3File> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_all_songs_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final int index = holder.getAdapterPosition();
        //holder.mIdView.setText(mValues.get(position));
        holder.mContentView.setText(mValues.get(position).getFile().getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(mValues,index);
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

        //Alv: Does this the display of a full object?
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
