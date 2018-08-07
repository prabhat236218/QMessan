package com.example.prabhat.q;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class second_main extends AppCompatActivity{
private TextView second,ques;
private ArrayList<String> data,al;
private MyAdapter ma;
private RecyclerView h1_view;
private HashMap<String,ArrayList<String>> ss=new HashMap<>(),map;
private  FirebaseDatabase fd;
private EditText answer;
private Button b2;
    private DatabaseReference dr,dr1;
    private Bundle extras;
    private String Question,ans;
    protected void onCreate(Bundle savedInstanceState) {

         super.onCreate(savedInstanceState);
         setContentView(R.layout.second_main);
         answer=(EditText)findViewById(R.id.answer);
         b2=(Button)findViewById(R.id.b2);
         ques=(TextView)findViewById(R.id.ques);
         al=new ArrayList<>();
        h1_view=(RecyclerView)findViewById(R.id.h1_view);
        h1_view.setHasFixedSize(true);
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        // LinearLayoutManager hm = new LinearLayoutManager(this, LinearLayout.HORIZONTAL,false);
         h1_view.setLayoutManager(gridLayoutManager);
         dr=FirebaseDatabase.getInstance().getReference();
         map=new HashMap<>();

         extras=getIntent().getExtras();
         if(extras==null)Toast.makeText(getApplicationContext(),"extras is empty", Toast.LENGTH_LONG).show();
         else {
             data=extras.getStringArrayList("ansList");
             Question=extras.getString("Question");
             ques.setText("Q. "+Question);
             //System.out.println("key is "+ yy);
             b2.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {

                     dr.addValueEventListener(new ValueEventListener()
                     {
                         @Override
                         public void onDataChange(DataSnapshot dataSnapshot) {
                             map= (HashMap<String,ArrayList<String>>) dataSnapshot.getValue();
                             ans = (answer.getText().length())>0?(answer.getText().toString().replace(".","").trim()):"empty";
                             Question=extras.getString("Question");
                             data.add(ans);
                             if(data.size()>=2&&data.contains("this question has no answer"))data.remove("this question has no answer");

                             map.put(Question,data);
                             ss=map;
                             dr.removeValue();
                             dr.setValue(map);

                             // for(String dd:data)System.out.println(dd +" dd hai bha ye");
                         }
                         @Override
                         public void onCancelled(DatabaseError databaseError)
                         {

                         }
                     });


                 }
             });
             dr.addValueEventListener(new ValueEventListener()
             {
                 @Override
                 public void onDataChange(DataSnapshot dataSnapshot) {
                     map= (HashMap<String,ArrayList<String>>) dataSnapshot.getValue();
                     Question=extras.getString("Question");
                     //for (String g : map.keySet()) if (!al.contains(g)) al.add(g);
                     TreeSet<String> ts=new TreeSet<>();
                     for(String ss:map.get(Question))ts.add(ss);
                     if(ts.size()>=2&&ts.contains("empty"))ts.remove("empty");
                     ma = new MyAdapter(new ArrayList<String>(ts));
                     h1_view.setAdapter(ma);
                     // for(String dd:data)System.out.println(dd +" dd hai bha ye");
                 }
                 @Override
                 public void onCancelled(DatabaseError databaseError)
                 {

                 }
             });


         }   //extras wala end;




    }

}
