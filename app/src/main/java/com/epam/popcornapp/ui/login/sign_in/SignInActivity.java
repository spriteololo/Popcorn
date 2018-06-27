package com.epam.popcornapp.ui.login.sign_in;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.epam.popcornapp.R;
import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.application.ErrorMessage;
import com.epam.popcornapp.application.ErrorMessage.ErrorType;
import com.epam.popcornapp.ui.all.base.BaseMvpActivity;
import com.epam.popcornapp.ui.all.base.NetworkStateReceiver;
import com.epam.popcornapp.ui.home.MainActivity;
import com.rengwuxian.materialedittext.MaterialEditText;

import javax.inject.Inject;

import butterknife.BindView;

public class SignInActivity extends BaseMvpActivity implements
        SignInView,
        ErrorMessage.onErrorMessageClickListener,
        NetworkStateReceiver.NetworkStateReceiverListener {

    @InjectPresenter
    SignInPresenter signInPresenter;

    @Inject
    NetworkStateReceiver networkStateReceiver;

    @Inject
    ErrorMessage errorMessage;

    @BindView(R.id.activity_sign_in_main_layout)
    View mainLayout;

    @BindView(R.id.username)
    MaterialEditText usernameView;

    @BindView(R.id.password)
    MaterialEditText passwordView;

    @BindView(R.id.sign_in_button)
    Button signInButton;

    @BindView(R.id.skip_button)
    Button skipButton;

    @BindView(R.id.sign_in_app_icon)
    ImageView appIcon;

    @BindView(R.id.check_box_remember)
    CheckBox checkBoxRemember;

    @BindView(R.id.sign_in_activity_powered_by)
    View poweredByView;

    @BindView(R.id.screen_blackout)
    FrameLayout screenBlackout;

    private boolean startMode;

    public static Intent start(@NonNull final Context context, final boolean isRunFromSplash) {
        final Intent intent = new Intent(context, SignInActivity.class);

        intent.putExtra(Constants.Extras.LOGIN_BOOL, isRunFromSplash);

        return intent;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        BaseApplication.getApplicationComponent().inject(this);
    }

    @Override
    protected void onViewsBinded() {
        saveParams();

        skipButton.setVisibility(startMode ? View.VISIBLE : View.GONE);
        poweredByView.setVisibility(startMode ? View.VISIBLE : View.INVISIBLE);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                signIn();
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                onSuccess();
            }
        });

        checkBoxRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton compoundButton, final boolean b) {
                signInPresenter.setRememberMe(b);
            }
        });

        signInPresenter.initializeFields();
    }

    private void saveParams() {
        startMode = getIntent().getBooleanExtra(Constants.Extras.LOGIN_BOOL, false);
    }

    @Override
    protected void onResume() {
        super.onResume();

        networkStateReceiver.addListener(this);
        this.registerReceiver(networkStateReceiver,
                new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    private void signIn() {
        screenBlackout.setVisibility(View.VISIBLE);
        errorMessage.removeSnackbar();

        usernameView.setError(null);
        passwordView.setError(null);

        signInPresenter.signIn(
                usernameView.getText().toString(), passwordView.getText().toString(), startMode);
    }

    @Override
    public void initializeFields(final String username) {
        usernameView.setText(username);
    }

    @Override
    public void invalidUserName() {
        screenBlackout.setVisibility(View.GONE);

        usernameView.setError(getString(R.string.error_field_required));
    }

    @Override
    public void invalidPassword() {
        screenBlackout.setVisibility(View.GONE);

        passwordView.setError(getString(R.string.error_invalid_password));
    }

    @Override
    public void onSuccess() {
        if (startMode) {
            startActivity(MainActivity.start(this));
        }

        finish();
    }

    @Override
    public void onRetryClicked() {
        signIn();
    }

    @Override
    protected void onPause() {
        super.onPause();

        this.unregisterReceiver(networkStateReceiver);
    }

    @Override
    public void onError(@ErrorType final int errorType) {
        screenBlackout.setVisibility(View.GONE);

        errorMessage.showSnackbar(this, mainLayout, this, errorType);
    }

    @Override
    public void networkAvailable() {
        errorMessage.removeSnackbar();
    }

    @Override
    public void networkUnavailable() {
        onError(ErrorType.INTERNET_CONNECTION_ERROR);
    }
}
