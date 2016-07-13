package com.sgvplayer.sgvplayer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class MusicFragment extends Fragment
                    implements View.OnClickListener {

    View view;
    OnFragmentInteractionListener mListener;
    public MusicFragment(){
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_music, container, false);
        setButtonListener(R.id.all_songs_button);
        setButtonListener(R.id.artists_button);
        setButtonListener(R.id.albums_button);
        setButtonListener(R.id.genres_button);
        setButtonListener(R.id.playlists_button);
        setButtonListener(R.id.folders_button);
        return view;
    }

    private void setButtonListener(int id){
        Button button = (Button) view.findViewById(id);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.all_songs_button:
                mListener.onFragmentInteraction(MusicTabHostFragment.ALL_SONGS);
                break;
            case R.id.artists_button:
                mListener.onFragmentInteraction(MusicTabHostFragment.ARTIST);
                break;
            case R.id.albums_button:
                mListener.onFragmentInteraction(MusicTabHostFragment.ALBUM);
                break;
            case R.id.genres_button:
                mListener.onFragmentInteraction(MusicTabHostFragment.GENRES);
                break;
            case R.id.playlists_button:
                mListener.onFragmentInteraction(MusicTabHostFragment.PLAYLISTS);
                break;
            case R.id.folders_button:
                mListener.onFragmentInteraction(MusicTabHostFragment.FOLDERS);
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) getParentFragment();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(int id);
    }
}


