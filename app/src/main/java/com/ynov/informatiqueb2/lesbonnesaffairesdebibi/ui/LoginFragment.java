package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.ui;

import android.content.Context;
import android.content.Intent;
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

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.model.Announcement;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.service.ApiService;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.utils.AlertUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private Button loginBtn;
    private Button resetBtn;
    private EditText usernameIpt;
    private EditText passwordIpt;
    private HashMap<String,String> params  = new HashMap<String,String>();

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_login, container, false);
        //Initialize fields
        this.resetBtn = v.findViewById(R.id.resetBtn);
        this.loginBtn = v.findViewById(R.id.loginBtn);
        this.usernameIpt = v.findViewById(R.id.usernameIpt);
        this.passwordIpt = v.findViewById(R.id.passwordIpt);

        //Link buttons
        this.resetBtn.setOnClickListener(listener);
        this.loginBtn.setOnClickListener(listener);
        return v;
    }

    // Generic listener
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.loginBtn:
                    LoginFragment.this.onLoginClicked();
                    break;
                case R.id.resetBtn:
                    LoginFragment.this.onCancelClicked();
            }
        }
    };

    protected void onCancelClicked() {
        //Return to default list
        Fragment fragment = AnnouncementListFragment.newInstance();
        ((BaseActivity)getActivity()).navigate(fragment);

    }

    protected void onLoginClicked() {
        boolean error = false;
        //Check if fields are not empty
        if(TextUtils.isEmpty(passwordIpt.getText())){
            passwordIpt.setError(getString(R.string.empty_error));
            error = true;
        }if(TextUtils.isEmpty(usernameIpt.getText())){
            usernameIpt.setError(getString(R.string.empty_error));
            error = true;
        }
        if(!error) {
            //Prepare credentials
            params.put("email",usernameIpt.getText().toString());
            params.put("mdp",passwordIpt.getText().toString());
            //Launch request
            ApiService.getInstance().getOwnedAnnonces(params).enqueue(this.callback);
        }
    }

    private Callback<List<Announcement>> callback = new Callback<List<Announcement>>() {
        @Override
        public void onResponse(@NonNull Call<List<Announcement>> call, Response<List<Announcement>> response) {
            if(response.body() != null && response.body().size() <= 0) {
                AlertUtils.alertFailure(getActivity(), getString(R.string.invalid_credentials)).show();
            }else {
                Fragment fragment =  OwnedAnnouncementFragment.newInstance(params);
                ((BaseActivity)getActivity()).navigate(fragment);
            }
        }

        @Override
        public void onFailure(Call<List<Announcement>> call, Throwable t) {
            AlertUtils.alertFailure(getActivity()).show();
        }
    };


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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
