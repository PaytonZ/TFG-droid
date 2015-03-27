package com.bsod.tfg.vista.tablon;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bsod.tfg.ActivityMain;
import com.bsod.tfg.R;

/**
 * Proudly created by Payton on 26/10/2014.
 */
public class DialogSendMessageBoard extends DialogFragment implements View.OnClickListener {

    private TextView dialogTextViewMessage;

    /* Helpers for the DialogFrament */
    private Button dialogButtonSendMessage;
    private CheckBox anonymous;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View v = inflater.inflate(R.layout.dialog_send_message_board, null);
        builder.setView(v);

        dialogTextViewMessage = (TextView) v.findViewById(R.id.dialog_message_text);
        dialogButtonSendMessage = (Button) v.findViewById(R.id.dialog_message_button);
        dialogButtonSendMessage.setOnClickListener(this);
        anonymous = (CheckBox) v.findViewById(R.id.checkBoxAnonymous);
        return builder.create();
    }

    @Override
    public void onClick(View view) {
        if (view == dialogButtonSendMessage) {
            //TODO: horrible hack i have done ;/
            DialogSendMessageBoardListener activity = (DialogSendMessageBoardListener) ((ActivityMain) getActivity()).getCurrentFragment();
            activity.onSendMessageBoard(dialogTextViewMessage.getText().toString(), anonymous.isChecked());

            this.dismiss();
        }
    }


    public interface DialogSendMessageBoardListener {
        void onSendMessageBoard(String inputText, boolean anonymous);
    }
}
