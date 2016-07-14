package com.sgvplayer.sgvplayer;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.sgvplayer.sgvplayer.model.fileNavigator.Mp3File;
import com.sgvplayer.sgvplayer.model.mp3Service.Mp3Service;
import com.sgvplayer.sgvplayer.model.mp3Service.Mp3ServiceProvided;
import com.sgvplayer.sgvplayer.model.mp3Service.Mp3ServiceProvider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


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
    private static final String ARG_MP3FILES = "mp3File";
    private static final String ARG_INDEX = "index";

    private int index;
    private Mp3File mp3File;
    private List<Mp3File> mp3Files;
    private Mp3Service mp3Service;
    private OnFragmentInteractionListener mListener;

    //For the media player:
    Thread updateSeekBar;
    SeekBar seekBar;

    public PlayerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param mp3Files File to play.
     * @return A new instance of fragment PlayerFragment.
     */
    public static PlayerFragment newInstance(Serializable mp3Files, int index) {
        PlayerFragment fragment = new PlayerFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MP3FILES, mp3Files);
        args.putSerializable(ARG_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override @SuppressWarnings("unchecked") //Alv:This is SO ugly
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Object o = getArguments().getSerializable(ARG_MP3FILES);
            mp3Files = (List<Mp3File>) o;
            index = (int) getArguments().getSerializable(ARG_INDEX);
            mp3File = mp3Files.get(index);
        }
        //Para el player:
        Mp3ServiceProvider mp3ServiceProvider = new Mp3ServiceProvider(this, this.getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_player, container, false);

        initPlayerDisplay(view);
        initPlayerUI(view);

        return view;
    }

    private void initPlayerDisplay(View view){
        String name = "Playing " + this.mp3File.getFile().getName();
        TextView fileName = (TextView) view.findViewById(R.id.file_name);
        fileName.setText(name);

        String artist = this.mp3File.getArtist();
        TextView artistName = (TextView) view.findViewById(R.id.artist_name);
        artistName.setText(artist);
    }

    /**
     * Initialises the player widget UI
     */
    private void initPlayerUI(View view){
        ImageButton playPauseButton = (ImageButton) view.findViewById(R.id.play_pause_button);
        playPauseButton.setOnClickListener(this);

        ImageButton forwardButton = (ImageButton) view.findViewById(R.id.forward_button);
        forwardButton.setOnClickListener(this);

        ImageButton rewindButton = (ImageButton) view.findViewById(R.id.rewind_button);
        rewindButton.setOnClickListener(this);

        seekBar = (SeekBar) view.findViewById(R.id.seek_bar);

        String songTitle = this.mp3File.getFile().getName();
        TextView scrollingSongTitle = (TextView) view.findViewById(R.id.scrolling_song_title);
        scrollingSongTitle.setText(songTitle);
        scrollingSongTitle.setSelected(true);
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
            case R.id.forward_button:
                forwardButtonAction();
                break;
            case R.id.rewind_button:
                rewindButtonAction();
                break;
        }
    }

    private void forwardButtonAction(){
        mp3Service.nextSong();
        //Update UI
    }

    private void rewindButtonAction(){
       mp3Service.previousSong();
        //Update UI
    }

    //Media Player methods:
    //Called by Mp3ServiceProvider
    @Override
    public void onServiceConnected(Mp3Service mp3Service) {
        //implement onServiceConnected
        this.mp3Service = mp3Service;
        this.mp3Service.playSong(mp3Files,index);
        initSeekBar();
    }

    //Seek bar:
    private void initSeekBar() {
        updateSeekBar = new Thread() {
            @Override
            public void run() {
                int totalDuration = mp3Service.getDuration();
                int currentPosition = mp3Service.getCurrentPosition();
                seekBar.setMax(totalDuration);
                while (currentPosition < totalDuration) {
                    try {
                        sleep(250);
                        currentPosition = mp3Service.getCurrentPosition();
                        if (!seekBar.isPressed())
                            seekBar.setProgress(currentPosition);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        updateSeekBar.start();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp3Service.setCurrentPosition(seekBar.getProgress());
            }
        });
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
