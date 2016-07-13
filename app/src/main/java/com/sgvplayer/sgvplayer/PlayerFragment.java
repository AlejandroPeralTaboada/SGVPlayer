 package com.sgvplayer.sgvplayer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sgvplayer.sgvplayer.model.fileNavigator.MP3File;
import com.sgvplayer.sgvplayer.model.mp3Service.Mp3Service;
import com.sgvplayer.sgvplayer.model.mp3Service.Mp3ServiceProvided;
import com.sgvplayer.sgvplayer.model.mp3Service.Mp3ServiceProvider;

import java.io.Serializable;


 /**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlayerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayerFragment extends Fragment
         implements Mp3ServiceProvided,
                    View.OnClickListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
     private static final String ARG_MP3FILE = "mp3File";
     //private static final String ARG_MP3SERVICE = "mp3Service";


    private MP3File mp3File;
     private Mp3Service mp3Service; //need to add to bundle--MAYBE NOT
    private OnFragmentInteractionListener mListener;

    public PlayerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param mp3File File to play.
     * @return A new instance of fragment PlayerFragment.
     */
    public static PlayerFragment newInstance(Serializable mp3File) {
        PlayerFragment fragment = new PlayerFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MP3FILE, mp3File);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mp3File = (MP3File) getArguments().getSerializable(ARG_MP3FILE);
        }
        //Para el player:
        Mp3ServiceProvider mp3ServiceProvider = new Mp3ServiceProvider(this, this.getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_player, container, false);
        String name = "Playing " + this.mp3File.getFile().getName();
        TextView fileName = (TextView) view.findViewById(R.id.file_name);
        fileName.setText(name);

        //Initialise buttons:
        ImageButton playPauseButton = (ImageButton) view.findViewById(R.id.play_pause_button);
        playPauseButton.setOnClickListener(this);

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

     @Override
     public void onClick(View v) {
         switch (v.getId()) {
             case R.id.play_pause_button:
                 this.mp3Service.startStop();
                 break;
         }
     }

     //Media Player methods:
     //Called by Mp3ServiceProvider
     @Override
     public void onServiceConnected(Mp3Service mp3Service){
         //implement onServiceConnected
         this.mp3Service = mp3Service;
         this.mp3Service.playSong(mp3File);
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
