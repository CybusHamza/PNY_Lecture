package pnytrainings.com.volleyexample;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by PNY Trainings on 9/23/2018.
 */

public class ContactsAdapter  extends  RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    ArrayList <Contacts> contactses ;
    Activity mContext;

    public ContactsAdapter(Activity context , ArrayList<Contacts> contactses ) {
        mContext = context;
        this.contactses =contactses;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_contacts, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Contacts contact = contactses.get(position);

        holder.mName.setText(contact.getName());
        holder.mEmail.setText(contact.getEmail());
        holder.mPhone.setText(contact.getPhone().getHome());

        holder.mainVIew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "name is : "+contact.getName(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return contactses.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout mainVIew;
        TextView mName,mEmail,mPhone;
        public ViewHolder(View itemView) {
            super(itemView);

            mainVIew = itemView.findViewById(R.id.LL);
            mName = itemView.findViewById(R.id.name);
            mEmail = itemView.findViewById(R.id.email);
            mPhone = itemView.findViewById(R.id.mobile);

        }
    }

}
