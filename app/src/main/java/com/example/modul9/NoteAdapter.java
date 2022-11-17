package com.example.modul9;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DatabaseReference;
import java.util.ArrayList;
public class NoteAdapter extends
        RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private ArrayList<Note> localDataSet = new ArrayList<>();
    private DatabaseReference databaseReference;
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView textView;
        private final TextView description;
        private final Button delete;
        private final Button update;
        private Context context;
        public ViewHolder(View view) {
            super(view);
            context = view.getContext();
            textView = (TextView)
                    view.findViewById(R.id.card_title);
            description = (TextView)
                    view.findViewById(R.id.description);
            delete = (Button)
                    view.findViewById(R.id.delete_button);
            update = (Button)
                    view.findViewById(R.id.update_button);
        }
        public Button getDelete() {
            return delete;
        }
        public Button getUpdate() {
            return update;
        }
        public TextView getCardTitle() {
            return textView;
        }
        public TextView getCardDescription() {
            return description;
        }
    }
    public NoteAdapter(DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
    }
    public void setItems(ArrayList<Note> dataSet){
        localDataSet = dataSet;
        this.notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int
            viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.note_item, viewGroup, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int
            position) {
        TextView cardTitle = viewHolder.getCardTitle();
        TextView cardDescription = viewHolder.getCardDescription();
        TextView delete = viewHolder.getDelete();
        TextView update = viewHolder.getUpdate();
        Note data = localDataSet.get(position);
        cardTitle.setText(data.getTitle());
        cardDescription.setText(data.getDescription());
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference.child(data.getId().toString()).removeValue();
            }
        });
        update.setOnClickListener(v-> {
            Intent intent = new Intent(viewHolder.context,
                    UpdateActivity.class);
            intent.putExtra("id", data.getId());
            viewHolder.context.startActivity(intent);
        });
    }
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}

