package lesbonnesaffairesdebibi.ynov.informatiqueb2.galvani.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import lesbonnesaffairesdebibi.ynov.informatiqueb2.galvani.R;
import lesbonnesaffairesdebibi.ynov.informatiqueb2.galvani.controller.EntryController;

import lesbonnesaffairesdebibi.ynov.informatiqueb2.galvani.controller.EntryController;

public class HomepageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage );

        new EntryController(HomepageActivity.this).execute();
    }

    // Menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity, menu);
        return true;
    }
}

