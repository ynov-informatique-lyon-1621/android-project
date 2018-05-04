package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.model.Announcement;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.service.ApiService;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.utils.AlertUtils;
import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.utils.FormUtils;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment {
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
        EditText[] toCheck = {this.passwordIpt,this.usernameIpt};
        error = !FormUtils.validateNotEmpty(getActivity(),toCheck);
        error = !FormUtils.validateEmail(getActivity(),this.usernameIpt) || error;
        if(!error) {
            //Prepare credentials
            params.put("email",usernameIpt.getText().toString());
            params.put("mdp",passwordIpt.getText().toString());
            //Launch request
            ApiService.getInstance().getOwnedAnnonces(params).enqueue(this.callback);
            Log.i("AUTH","init login");
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
}
