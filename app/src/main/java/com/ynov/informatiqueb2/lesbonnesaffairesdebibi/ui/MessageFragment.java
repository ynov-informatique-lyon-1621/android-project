package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.model.Announcement;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MessageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFragment extends Fragment {
    private static final String ARG_ANNOUNCEMENT = "ann";
    private Announcement announcement;
    private MessageFragment.OnFragmentInteractionListener mListener;

    private EditText nameIpt;
    private EditText mailIpt;
    private EditText phoneIpt;
    private EditText messageIpt;

    public MessageFragment() {
        // Required empty public constructor
    }


    public static MessageFragment newInstance(Announcement announcement) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ANNOUNCEMENT, announcement);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            announcement =(Announcement)getArguments().getSerializable(ARG_ANNOUNCEMENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_message, container, false);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.smallDetailDsp,DetailSmallFragment.newInstance(this.announcement))
                .commit();

        this.nameIpt = v.findViewById(R.id.nameIpt);
        this.mailIpt = v.findViewById(R.id.mailIpt);
        this.phoneIpt = v.findViewById(R.id.phoneIpt);
        this.messageIpt = v.findViewById(R.id.messageIpt);
        Button sendBtn = v.findViewById(R.id.sendBtn);
        Button backBtn = v.findViewById(R.id.backButton);
        Button resetBtn = v.findViewById(R.id.resetBtn);

        sendBtn.setOnClickListener(this.listener);
        backBtn.setOnClickListener(this.listener);
        resetBtn.setOnClickListener(this.listener);
        return v;
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    protected boolean checkForm() {
        boolean hasError = false;
        EditText[] toChek = {this.nameIpt, this.mailIpt, this.phoneIpt,
                this.messageIpt};
        for (EditText input : toChek) {
            if (TextUtils.isEmpty(input.getText())) {
                input.setError(getString(R.string.empty_error));
                hasError = true;
            }
        }
        return !hasError;
    }

    private void onSendClicked(){
        if(this.checkForm()){

        }
    }

    private void OnResetClicked(){
        EditText[] toChek = {this.nameIpt, this.mailIpt, this.phoneIpt,
                this.messageIpt};
        for (EditText input : toChek) {
            input.setText("");
        }
    }

    private void onBackClicked(){
        ((BaseActivity)getActivity()).onBackPressed();
    }

    // Generic listener
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.sendBtn:
                    onSendClicked();
                    break;
                case R.id.backButton:
                    onBackClicked();
                    break;
                case R.id.resetBtn:
                    OnResetClicked();
            }
        }
    };
}
