package com.sgvplayer.sgvplayer.ui.uiClassifier;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sgvplayer.sgvplayer.R;
import com.sgvplayer.sgvplayer.model.fileNavigator.FileNavigatorImp;
import com.sgvplayer.sgvplayer.ui.uiClassifier.Adapter.ClassifierRecyclerAdapter;


public class ClassifierListFragment extends Fragment {

    private OnClassifierFragmentInteractionListener mListener;

    public ClassifierListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.classifier_songs_list_fragment, container, false);
        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        FileNavigatorImp fileNavigator = FileNavigatorImp.getInstance(getActivity());
        recyclerView.setAdapter(new ClassifierRecyclerAdapter(fileNavigator.getAllMp3Files(), mListener));
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnClassifierFragmentInteractionListener) {
            mListener = (OnClassifierFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnClassifierFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnClassifierFragmentInteractionListener {
        // TODO: Update argument type and name
        void onClassifierFragmentInteraction(int index);
    }
}
