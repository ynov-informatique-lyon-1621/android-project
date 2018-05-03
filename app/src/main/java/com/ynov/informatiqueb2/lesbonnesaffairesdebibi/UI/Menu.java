package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //gère le click sur une action de l'ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_menu_un:
                Intent mainActivityIntent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(mainActivityIntent);
                return true;
            case R.id.action_menu_deux:
                Intent deposerAnnonceIntent = new Intent(getBaseContext(), DeposerAnnonceActivity.class);
                startActivity(deposerAnnonceIntent);
                return true;
            /*case R.id.action_menu_trois:
                Intent modifierIntent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(modifierIntent);
                return true;*/
            case R.id.action_menu_quatre:
                Intent favorisIntent = new Intent(getBaseContext(), IdentificationActivity.class);
                startActivity(favorisIntent);
                return true;
            case R.id.action_menu_cinq:
                Intent connexionIntent = new Intent(getBaseContext(), IdentificationActivity.class);
                startActivity(connexionIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
