package com.codeitsuisse.team28.expensetracker;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codeitsuisse.team28.expensetracker.card.Card;
import com.codeitsuisse.team28.expensetracker.card.provider.BasicImageButtonsCardProvider;
import com.codeitsuisse.team28.expensetracker.drawermenu.MainActivity;
import com.codeitsuisse.team28.expensetracker.listeners.RecyclerItemClickListener;
import com.codeitsuisse.team28.expensetracker.view.MaterialListView;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageFragment extends android.support.v4.app.Fragment {

    private FragmentActivity mContext;
    private MaterialListView mListView;
    private View row;

    DBhelper db;
    public HomePageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        row = inflater.inflate(R.layout.fragment_home_page, container, false);

        // Bind the MaterialListView to a variable
        mListView = (MaterialListView) row.findViewById(R.id.material_listview);
        db=new DBhelper(getActivity().getApplicationContext());
        // Fill the array with mock content
        fillWithCards();


        // Add the ItemTouchListener
        mListView.addOnItemTouchListener(new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Card card, int position) {
                // Create new fragment and transaction

                if (position == 0) {
                    BudgetFragment newFragment = new BudgetFragment();


                    FragmentTransaction transaction = getFragmentManager().beginTransaction();

                    transaction.replace(R.id.container, newFragment);
                    transaction.addToBackStack(null);

                    // Commit the transaction
                    transaction.commit();
                }

                if (position == 1) {
                    ExpenseFragment newFragment = new ExpenseFragment();


                    FragmentTransaction transaction = getFragmentManager().beginTransaction();

                    transaction.replace(R.id.container, newFragment);
                    transaction.addToBackStack(null);

                    // Commit the transaction
                    transaction.commit();

                }

                if (position == 2) {
                    SavingFragment newFragment = new SavingFragment();


                    FragmentTransaction transaction = getFragmentManager().beginTransaction();

                    transaction.replace(R.id.container, newFragment);
                    transaction.addToBackStack(null);

                    // Commit the transaction
                    transaction.commit();
                }

            }

            @Override
            public void onItemLongClick(@NonNull Card card, int position) {
            }
        });

        // Inflate the layout for this fragment
        return row;
    }

    private void fillWithCards() {
        int i=0;
        mListView.add(getBudgetCard(i));
        i++;
        mListView.add(getExpenseCard(i));
        i++;
        mListView.add(getSavingsCard(i));
        i++;
        mListView.add(getSavingGoalCard(i));
        i++;

    }

    private Card getSavingGoalCard(int position) {
        String title = "Saving Goal Progress";

        String description = "";
        Calendar calendar=Calendar.getInstance();
        final int thisYear = calendar.get(Calendar.YEAR);

        final int thisMonth = calendar.get(Calendar.MONTH)+1;
        Savings sv=db.getSavings(thisMonth,thisYear);
        if(sv==null)description="You have not set any goals this month,start saving now";
        else{
            float expense=db.sumAllExpense(thisMonth,thisYear),bug,sav=sv.getAmount();
            Budget budget=db.getBudget(thisMonth,thisYear);
            if(budget==null)bug=0;
            else bug=budget.getAmount();
            if(bug==0){
                description="set budget";
            }else{
                float savingdone=bug-expense,per;
                int inc=0;
                description="Saving goals : Rs"+sav+"\n Saving done : Rs "+savingdone+"\n";
                if(savingdone>sav){
                    per=(savingdone-sav)/sav;
                    description+="Savings exceed saving goals by"+per+"%\n Well done";
                }
                else{
                    per=(sav-savingdone)/sav;
                    description+="Savings are less than saving goals by"+per+"%\nSave More";
                }
            }
        }
        final BasicImageButtonsCardProvider provider = new Card.Builder(MainActivity.a)
                .setTag("BASIC_IMAGE_BUTTON_CARD")
                .setDismissible()
                .withProvider(BasicImageButtonsCardProvider.class)
                .setTitle(title)
                .setDescription(description);

        provider.setDividerVisible(true);

        return provider.endConfig().build();

    }

    private Card getSavingsCard(int position) {
        String title = "Savings Goals";
        String description;
        Calendar calendar=Calendar.getInstance();
        final int thisYear = calendar.get(Calendar.YEAR);

        final int thisMonth = calendar.get(Calendar.MONTH)+1;
        Savings sv=db.getSavings(thisMonth,thisYear);
        if(sv==null)description="You have not set any goals this month,start saving now";
        else description= "Savings Goal : Rs "+sv.getAmount();

        final BasicImageButtonsCardProvider provider = new Card.Builder(MainActivity.a)
                .setTag("BASIC_IMAGE_BUTTON_CARD")
                .setDismissible()
                .withProvider(BasicImageButtonsCardProvider.class)
                .setTitle(title)
                .setDescription(description);

        provider.setDividerVisible(false);

        return provider.endConfig().build();

    }

    private Card getExpenseCard(int position) {
        String title = "Expense";
        String description ;

        Calendar calendar=Calendar.getInstance();
        final int thisYear = calendar.get(Calendar.YEAR);
        final int thisMonth = calendar.get(Calendar.MONTH)+1;

        Float expense = db.sumAllExpense(thisMonth, thisYear);

        if(expense==0.0f){
            description="No expenses added in this month,enter now!!";
        }else{
            description = "Total expenses : Rs." + db.sumAllExpense(thisMonth,thisYear)+ " \nPaid expenses : Rs." +db.sumPaid(thisMonth,thisYear) + "\nLiabilities : Rs." + db.sumPayLater(thisMonth,thisYear) ;
        }

        final BasicImageButtonsCardProvider provider = new Card.Builder(MainActivity.a)
                .setTag("BASIC_IMAGE_BUTTON_CARD")
                .setDismissible()
                .withProvider(BasicImageButtonsCardProvider.class)
                .setTitle(title)
                .setDescription(description);

        provider.setDividerVisible(false);


        return provider.endConfig().build();

    }

    private Card getBudgetCard(int position) {
        String title = "Budget";
        Calendar calendar=Calendar.getInstance();
        final int thisYear = calendar.get(Calendar.YEAR);

        final int thisMonth = calendar.get(Calendar.MONTH)+1;
        String description;
        Budget bug=db.getBudget(thisMonth,thisYear);
        if(bug==null){
            description="You have not entered this month's budget,enter now!!";
        }else{
            description="Budget : Rs "+bug.getAmount();
        }

        final BasicImageButtonsCardProvider provider = new Card.Builder(MainActivity.a)
                .setTag("BASIC_IMAGE_BUTTON_CARD")
                .setDismissible()
                .withProvider(BasicImageButtonsCardProvider.class)
                .setTitle(title)
                .setDescription(description);

        provider.setDividerVisible(false);


        return provider.endConfig().build();

    }

    @Override
    public void onAttach(Activity activity) {
        mContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }

}

