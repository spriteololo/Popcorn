package com.epam.popcornapp.application;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.epam.popcornapp.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.epam.popcornapp.application.ErrorMessage.ErrorType.AUTHORIZATION_ERROR;
import static com.epam.popcornapp.application.ErrorMessage.ErrorType.INTERNET_CONNECTION_ERROR;
import static com.epam.popcornapp.application.ErrorMessage.ErrorType.NOT_FOUND_ERROR;
import static com.epam.popcornapp.application.ErrorMessage.ErrorType.UNKNOWN_ERROR;

public class ErrorMessage {

    private Snackbar snackbar;
    private AlertDialog alertDialog;

    public void showToast(@NonNull final Context context, @ErrorType final int errorType) {
        Toast.makeText(context, errorType, Toast.LENGTH_SHORT).show();
    }

    public void showSnackbar(@NonNull final Context context,
                             @NonNull final View view,
                             @NonNull final onErrorMessageClickListener clickListener,
                             @ErrorType final int errorType) {

        snackbar = Snackbar.make(view, errorType, Snackbar.LENGTH_INDEFINITE)
                .setAction(context.getString(R.string.retry), new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        snackbar.dismiss();
                        snackbar = null;
                        clickListener.onRetryClicked();
                    }
                });

        snackbar.show();
    }

    public void showAlertDialog(@NonNull final Context context,
                                @NonNull final onErrorMessageClickListener clickListener,
                                @ErrorType final int errorType,
                                final boolean isCancelable) {
        removeDialog();

        alertDialog = new AlertDialog.Builder(context)
                .setCancelable(isCancelable)
                .setMessage(errorType)
                .setNegativeButton(context.getString(R.string.try_again), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, final int i) {
                        alertDialog.dismiss();
                        clickListener.onRetryClicked();
                    }
                })
                .create();

        alertDialog.show();
    }

    public void removeSnackbar() {
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
        }
    }

    public void removeDialog() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    public interface onErrorMessageClickListener {
        void onRetryClicked();
    }

    @IntDef({INTERNET_CONNECTION_ERROR, NOT_FOUND_ERROR, AUTHORIZATION_ERROR, UNKNOWN_ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ErrorType {
        int INTERNET_CONNECTION_ERROR = R.string.no_connection_to_internet;
        int NOT_FOUND_ERROR = R.string.not_found_error;
        int AUTHORIZATION_ERROR = R.string.not_authorized;
        int UNKNOWN_ERROR = -1;
    }
}
