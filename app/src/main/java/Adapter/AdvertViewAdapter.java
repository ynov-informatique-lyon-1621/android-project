package Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ynov.informatiqueb2.lesbonnesaffairesdebibi.R;

import java.util.List;

import Model.AdvertList;

public class AdvertViewAdapter extends ArrayAdapter<AdvertList> {

    public AdvertViewAdapter(@NonNull Context context, int resource, @NonNull List<AdvertList> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        final AdvertList listItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_dashboard, parent, false);
        }

        // Permet de remplacer le TextView comportant les IDs
        TextView nom = convertView.findViewById(R.id.NameView);
        TextView espece = convertView.findViewById(R.id.SpecieView);

        // Remplace les informations comportant Nom et Espece dans la liste
        nom.setText(listItem.getNom());
        espece.setText(listItem.getEspece());

        return convertView;
    }

    @Nullable
    @Override
    public AdvertList getItem(int position) {
        return super.getItem(position);
    }


}

