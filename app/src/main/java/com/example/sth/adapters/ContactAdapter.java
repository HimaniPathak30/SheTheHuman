package com.example.sth.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sth.AddContactData;
import com.example.sth.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactVH> {
    Context context;
    List<AddContactData> contactList;

    public ContactAdapter(Context context, List<AddContactData> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ContactAdapter.ContactVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_data,parent,false);
        return new ContactAdapter.ContactVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ContactVH holder, int position) {
        String sname = contactList.get(position).getName();
        String snumber = contactList.get(position).getNumber();
        String suid = contactList.get(position).getUid();
        String con_id = contactList.get(position).getCon_id();

        holder.name.setText(sname);;
        holder.number.setText(snumber);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog pd = new ProgressDialog(view.getContext());
                pd.setMessage("Deleting...");

                Query fquery = FirebaseDatabase.getInstance().getReference("Contacts").orderByChild("con_id").equalTo(con_id);
                fquery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds : snapshot.getChildren())
                        {
                            ds.getRef().removeValue();
                        }
                        Toast.makeText(view.getContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*String phone = snumber;
                String call = "tel:" + phone.trim();
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse(call));*/
                Uri uri = Uri.parse("tel:" + snumber);
                Intent it = new Intent(Intent.ACTION_CALL, uri);
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(it);

            }
        });

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ContactVH extends RecyclerView.ViewHolder {
        TextView name,number;
        ImageView delete,call;
        public ContactVH(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            number = itemView.findViewById(R.id.contact);
            delete = itemView.findViewById(R.id.delete);
            call = itemView.findViewById(R.id.call);
        }
    }
}
