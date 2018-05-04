package lesbonnesaffairesdebibi.ynov.informatiqueb2.galvani.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import lesbonnesaffairesdebibi.ynov.informatiqueb2.galvani.R;

public class EditEntryActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.menu_activity, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.addMenu) {
            Intent intent = new Intent( this, AddEntryActivity.class );
            this.startActivity( intent );
            return true;
        }

        if (id == R.id.homeMenu) {
            Intent intent = new Intent( this, HomepageActivity.class );
            this.startActivity( intent );
            return true;
        }

        if (id == R.id.editMenu) {
            Intent intent = new Intent( this, EditEntryActivity.class );
            this.startActivity( intent );
            return true;
        }

        if (id == R.id.favoriteMenu) {
            Intent intent = new Intent( this, FavoriteActivity.class );
            this.startActivity( intent );
            return true;
        }

        return super.onOptionsItemSelected( item );
    }
}
