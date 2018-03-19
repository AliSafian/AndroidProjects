package com.facefive.meetbook;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Ashar on 1/20/2018.
 */

public class ChangeNameDialogFragment extends DialogFragment implements View.OnClickListener {

    EditText et_name ;
    Button save;
    Button cencel;
    Communicator comm;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        comm = (Communicator) activity;
    }

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View dialog = inflater.inflate(R.layout.dialog_fragment_change_name , container ,false);
         save = (Button)dialog.findViewById(R.id.save);
         cencel = (Button)dialog.findViewById(R.id.cencel);
         et_name = (EditText)dialog.findViewById(R.id.db_name);
         save.setOnClickListener(this);
        cencel.setOnClickListener(this);
        setCancelable(false);
        return dialog;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.save)
        {
            String name = et_name.getText().toString();
            if(!name.equals(""))
            {
                comm.onDialogName(name);
            }
            dismiss();

        }
        else if(v.getId() == R.id.cencel)
        {
            dismiss();
        }

    }
}
interface Communicator
{
    public void onDialogName(String name);

}
