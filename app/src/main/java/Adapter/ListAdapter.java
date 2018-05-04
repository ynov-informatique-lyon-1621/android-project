package Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oli.projetb2.R;
import com.example.oli.projetb2.Ui.AdvertCellActivity;

import java.util.List;

import Model.Advert;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private Context context;
    private List<Advert> list;

    public void updateData(List<Advert> dataset) {
        list.clear();
        list.addAll(dataset);
        notifyDataSetChanged();
    }

    public ListAdapter(Context context, List<Advert> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.advert, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) { //on attache nos données à nos view
        Advert advert = list.get(position);
        holder.textTitre.setText(advert.getTitre());
        holder.textCategorie.setText(advert.getCategorie());
        holder.textPrix.setText(String.valueOf(advert.getPrix()) + "€");
        holder.textDate.setText(String.valueOf(advert.getDateCreation()));
        holder.imageViewset.setImageResource(R.drawable.polo);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textTitre, textPrix, textDate, textCategorie;
        public ImageView imageViewset;

        public ViewHolder(View itemView) { //on déclare nos veiw
            super(itemView);
            textTitre = itemView.findViewById(R.id.titre);
            textPrix = itemView.findViewById(R.id.prix);
            textDate = itemView.findViewById(R.id.date);
            textCategorie = itemView.findViewById(R.id.categorie);
            imageViewset = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {


            Advert advert = list.get(getAdapterPosition());

            //Redirection vers l'activité
            Intent intent = new Intent(context, AdvertCellActivity.class);
            intent.putExtra("titre", advert.getTitre());
            intent.putExtra("prix", advert.getPrix());
            intent.putExtra("desc", advert.getDescritpion());
            intent.putExtra("date", advert.getDateCreation());
            context.startActivity(intent);


        }

    }
}