package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.model.Message;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.service.ApiService;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.utils.AlertUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MessageFormFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MessageFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFormFragment extends Fragment {
    private static final String ARG_ID = "id";
    private static final String ARG_VENDOR_NAME = "vendor";

    private int id;
    private String vendorName;
    private EditText nameIpt;
    private EditText mailIpt;
    private EditText phoneIpt;
    private EditText messageIpt;
    private OnFragmentInteractionListener mListener;

    public MessageFormFragment() {
        // Required empty public constructor
    }

    public static MessageFormFragment newInstance(int id,String vendorName) {
        MessageFormFragment fragment = new MessageFormFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        args.putString(ARG_VENDOR_NAME, vendorName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(ARG_ID);
            vendorName = getArguments().getString(ARG_VENDOR_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_message_form, container, false);
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
        void onMessageSent();
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
            Message message = new Message();
            message.setEmail(this.mailIpt.getText().toString());
            message.setMessage(this.messageIpt.getText().toString());
            message.setNom(this.nameIpt.getText().toString());
            message.setNumeroTelephone(this.phoneIpt.getText().toString());
            message.setIdAnnonce(String.valueOf(this.id));
            message.setNomVendeur(this.vendorName);
            ApiService.getInstance().sendMessage(message).enqueue(this.onMessageSend);
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

    private Callback<Message> onMessageSend = new Callback<Message>() {
        @Override
        public void onResponse(@NonNull Call<Message> call, Response<Message> response) {
            if(response.code() == 200){
                mListener.onMessageSent();
            }else {
                AlertUtils.alertFailure(getActivity(),getString(R.string.message_not_send)).show();
            }
        }

        @Override
        public void onFailure(@NonNull Call<Message> call, Throwable t) {

        }
    };
}
