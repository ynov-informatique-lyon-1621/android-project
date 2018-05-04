package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;


public class MessageConfirmationFragment extends Fragment {
    public MessageConfirmationFragment() { }


    public static MessageConfirmationFragment newInstance() {
        MessageConfirmationFragment fragment = new MessageConfirmationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_message_confirmation, container, false);
        Button backBtn = v.findViewById(R.id.backBtn);
        Button backToListBtn = v.findViewById(R.id.backToListBtn);
        backBtn.setOnClickListener(this.listener);
        backToListBtn.setOnClickListener(this.listener);
        return v;
    }

    // Generic listener
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.backBtn:
                    getActivity().onBackPressed();
                    break;
                case R.id.backToListBtn:
                    onBackToListPressed();

            }
        }
    };

    private void onBackToListPressed() {
        Fragment fragment = AnnouncementListFragment.newInstance();
        ((BaseActivity)getActivity()).navigate(fragment);
    }

}
