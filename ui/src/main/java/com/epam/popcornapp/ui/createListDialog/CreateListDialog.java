package com.epam.popcornapp.ui.createListDialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.epam.ui.R;

public class CreateListDialog extends AppCompatDialogFragment {

    private EditText nameEditText;

    private DialogClickListener clickListener;

    public void setClickListener(final DialogClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final Activity activity = getActivity();
        final View view = activity.getLayoutInflater().inflate(R.layout.view_dialog_create_list, null);

        nameEditText = view.findViewById(R.id.dialog_name_edit_text);

        final AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setView(view)
                .setTitle(getString(R.string.create_new_list))
                .setPositiveButton(getString(R.string.create), null)
                .setNegativeButton(getString(R.string.cancel), null)
                .create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialogInterface) {
                final Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        final String name = nameEditText.getText().toString();

                        if (TextUtils.isEmpty(name)) {
                            nameEditText.setError(getString(R.string.error_field_required));

                            return;
                        }

                        if (clickListener != null) {
                            clickListener.onCreateClick(name);
                        }

                        alertDialog.dismiss();
                    }
                });
            }
        });

        return alertDialog;
    }

    public interface DialogClickListener {

        void onCreateClick(String name);
    }
}
