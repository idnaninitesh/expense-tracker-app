package com.codeitsuisse.team28.expensetracker;


import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddExpenseFragment extends android.support.v4.app.Fragment {


    public AddExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_add_expense, container, false);
        final Button cat=(Button)view.findViewById(R.id.selectcat);
        cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {"Food","Travel","Shopping","Rent","Loan"};
                final String[] ans = {null};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select Category");
                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        ans[0] = (String) items[item];
                        Toast.makeText(getActivity().getApplicationContext(), items[item],
                                Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                cat.setText(ans[0]);
                                dialog.dismiss();
                            }
                        });
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        Button sub= (Button)view.findViewById(R.id.addexpense);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day,month,year,type;
                float amt;
                String cate,com;
                EditText dy=(EditText)view.findViewById(R.id.day);
                day=new Integer(dy.getText().toString());
                month=new Integer(((EditText)view.findViewById(R.id.month)).getText().toString());
                year=new Integer(((EditText)view.findViewById(R.id.year)).getText().toString());
                RadioGroup rgp=(RadioGroup)view.findViewById(R.id.type);
                String anstemp=((RadioButton)view.findViewById(rgp.getCheckedRadioButtonId())).getText().toString();
                if(anstemp.equals("Paid")){
                    type=1;
                }
                else{
                    type=0;
                }
                amt=new Float(((EditText)view.findViewById(R.id.amount)).getText().toString());
                cate=cat.getText().toString();
                com=((EditText)view.findViewById(R.id.comments)).getText().toString();
                DBhelper db=new DBhelper(getActivity());
                db.insertexpense(day,month,year,amt,type,cate,com);
                BasicExpense newFragment = new BasicExpense();


                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.container, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });
        return view;

    }


}
