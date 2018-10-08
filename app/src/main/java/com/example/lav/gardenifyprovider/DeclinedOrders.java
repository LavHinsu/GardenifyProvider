package com.example.lav.gardenifyprovider;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeclinedOrders extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    ListView declinedorders;
    final String myprefs="UserPrefs";
    final String key="Name";
    final String key2="City";
    String city,name;
    ArrayList<String> declinedorders2;
    DatabaseReference mdatabase2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declined_orders);

        setTitle("Declined Orders");

        declinedorders=findViewById(R.id.declinedlist);

        sharedPreferences=getSharedPreferences(myprefs, Context.MODE_PRIVATE);



        city=sharedPreferences.getString(key2,"");
        name=sharedPreferences.getString(key,"");

        mdatabase2= FirebaseDatabase.getInstance().getReference().child(city.toUpperCase()).child("PROVIDER").child(name);


        mdatabase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user= new User();
                user=dataSnapshot.getValue(User.class);
                declinedorders2=new ArrayList<String>();
                declinedorders2=user.getDeclineorder();
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,declinedorders2);
                declinedorders.setAdapter(arrayAdapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Check your internet connection",Toast.LENGTH_SHORT).show();

            }
        });

    }
}
