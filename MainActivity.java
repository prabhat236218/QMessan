package com.example.prabhat.q;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.*;

import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity  {
    private static final String TAG = "MainActivity";
    private SwipeRefreshLayout swipeContainer;
    private DatabaseReference d2,d3,d1;
    private RecyclerView vertical_recycler_view,h_view;
    private ArrayList<String> horizontalList,verticalList;
    private MyAdapter ma;
    private int GET_FROM_GALLERY=3,cnt;
    private  Button btn2;
    private  final ArrayList<model> l=new ArrayList<>();
    private HashMap<String,ArrayList<String>>  ss,map;
    private  ArrayList<String> l1,al;
    private ArrayList<ArrayList<String>> bigList;
    private Button b1;
    private EditText t1;
    private boolean isTouched;
    private second_main sec;
    // private  VerticalAdapter verticalAdapter;
    private DatabaseReference dr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1 = (EditText) findViewById(R.id.t1);
        b1 = (Button) findViewById(R.id.b1);

        h_view = (RecyclerView) findViewById(R.id.h_view);
        h_view.setHasFixedSize(true);

        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        // LinearLayoutManager hm = new LinearLayoutManager(this, LinearLayout.HORIZONTAL,false);
        h_view.setLayoutManager(gridLayoutManager);
        al = new ArrayList<>();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        dr = FirebaseDatabase.getInstance().getReference();
        map = new HashMap<>();
        l1 = new ArrayList<String>();

        ItemClickSupport.addTo(h_view)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        String s = al.get(position);

                        Toast.makeText(getApplicationContext(), s + " this is clicked", Toast.LENGTH_SHORT).show();
                        //sec.loadData(dr);
                        Intent intent = new Intent(MainActivity.this, second_main.class);
                        intent.putExtra("Question", s);
                        if (map.get(s) != null)
                            intent.putStringArrayListExtra("ansList", map.get(s));
                        else {
                            ArrayList<String> lll = new ArrayList<>();
                            lll.add("NO ANSWER");
                            //  intent.putExtra("FIREBASE_URL", "https://console.firebase.google.com/u/2/project/qfirebaseproject-7c17b/database/qfirebaseproject-7c17b/data");
                            intent.putStringArrayListExtra("ansList", lll);
                        }
                        startActivity(intent);

                    }
                });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  HashMap<String,ArrayList<String>> map1=new HashMap<String,ArrayList<String>>();
                String s = (t1.getText().length()) > 0 ? (t1.getText().toString().replace(".", "").trim()) : null;
                String s1 = "";
                if (s == null) {
                    s1 = "please write a question that make sense";
                   // ArrayList<String> ll = new ArrayList<>();
                    //ll.add("this question has no answer");
                    //map.put(s1, ll);
                    //dr.setValue(map);
                    Toast.makeText(getApplicationContext(), s1, Toast.LENGTH_SHORT).show();
                }
               else if (s != null) {

                    if(!l1.contains(s))
                    {
                        l1.add(s);
                        ArrayList<String> ll = new ArrayList<>();
                        ll.add("this question has no answer");
                        map.put(s, ll);
                        dr.setValue(map);
                    }
                else
                    Toast.makeText(getApplicationContext(), "this question is already posted , write another question", Toast.LENGTH_SHORT).show();

                }

            }
        });

            dr.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    map = (HashMap<String, ArrayList<String>>) dataSnapshot.getValue();
                    // if(map.values()!=null)  System.out.println("output is this:=:"+map.values());
                     if(map!=null) {
                         for (String g : map.keySet()) if (!al.contains(g)) al.add(g);
                         ma = new MyAdapter(al);
                         h_view.setAdapter(ma);
                     }
                     else
                     {
                          ArrayList<String> ll = new ArrayList<>();
                         ll.add("this question has no answer");
                         map.put("prabhat", ll);
                         dr.setValue(map);
                     }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            // method ending
        }


 /* protected void onStart()
  {
      super.onStart();
      dataref.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {


              for(DataSnapshot child:dataSnapshot.getChildren())
              {
                  model m1=child.getValue(model.class);
                  l.add(m1);
                  Log.d(TAG, " name added : " + m1.getName());
              }
           //  model m=new model("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTmGJk2BBVu3CsmV_viOeFP42EVnBNqSk8PqfGrU13OYKUHb1iYKg","prabhat",cnt,"cjecj");
             // l.add(m);
              Log.d(TAG, "Picture Array : " + l.size());
              horizontalAdapter=new HorizontalAdapter(l);
              horizontal_recycler_view.setAdapter(horizontalAdapter);
          }

          @Override
          public void onCancelled(DatabaseError databaseError) {

          }
      });

  }
*/

 /*   public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {

        private List<model> hList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public ImageView img;
            public TextView t1;
            public TextView t2;
            public TextView t3;
            public MyViewHolder(View view) {
                super(view);
                t1 = (TextView) view.findViewById(R.id.t1);
                t2 = (TextView) view.findViewById(R.id.t2);
                t3 = (TextView) view.findViewById(R.id.t3);
                img=(ImageView) view.findViewById(R.id.img);
            }
        }


        public HorizontalAdapter(List<model> l) {
            this.hList = l;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.horizontal_item_view, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            // holder.txtView.setText(horizontalList.get(position));
            model m=hList.get(position);
            holder.t1.setText(m.getName());
            holder.t2.setText(m.getPrice());
            holder.t3.setText(m.getDp());

            //System.out.println(m.getName());
           /* holder.t1.setText("noiwoenicnjebbcrererf");
            holder.t2.setText("686");
            holder.t3.setText("6888y8y9");
            Picasso.with(getApplicationContext())
                    .load(m.getImg())
                    .into(holder.img);
            holder.t1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this,holder.t1.getText().toString(),Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return hList.size();
        }
    }

*/

}