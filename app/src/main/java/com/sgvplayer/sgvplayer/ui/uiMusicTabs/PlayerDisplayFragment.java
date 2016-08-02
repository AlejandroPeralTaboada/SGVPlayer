package com.sgvplayer.sgvplayer.ui.uiMusicTabs;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sgvplayer.sgvplayer.Mp3ServiceSingleton;
import com.sgvplayer.sgvplayer.R;
import com.sgvplayer.sgvplayer.model.mp3Service.Mp3Service;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlayerDisplayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlayerDisplayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayerDisplayFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SONGHEROIMAGE = "song_hero_image";

    private Mp3Service mp3Service;
    private OnFragmentInteractionListener mListener;
    //UI:
    private View view;
    private ImageView mSongHeroImage;

    public PlayerDisplayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment PlayerDisplayFragment.
     */
    public static PlayerDisplayFragment newInstance() {
        PlayerDisplayFragment fragment = new PlayerDisplayFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mp3ServiceSingleton mp3ServiceSingleton = Mp3ServiceSingleton.getInstance();
        mp3Service = mp3ServiceSingleton.getService();
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_player_display, container, false);

        mSongHeroImage = (ImageView) view.findViewById(R.id.songHeroImage);
        mSongHeroImage.setImageBitmap(mp3Service.getSong().getAlbumCover());

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
