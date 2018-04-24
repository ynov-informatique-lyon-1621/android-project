package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;

public class AnnouncementListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_list);
        EditText dateInput = findViewById(R.id.dateIpt);
        dateInput.setOnClickListener(this.dateInputListener);

    }

    private View.OnClickListener dateInputListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DatePickerFragment.newInstance(R.id.dateIpt).show(getSupportFragmentManager(),"dp");
        }
    };


}
