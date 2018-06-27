package com.epam.popcornapp.ui.splash;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.epam.popcornapp.R;
import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.ErrorMessage;
import com.epam.popcornapp.application.ErrorMessage.ErrorType;
import com.epam.popcornapp.ui.all.base.BaseMvpActivity;
import com.epam.popcornapp.ui.all.base.NetworkStateReceiver;
import com.epam.popcornapp.ui.home.MainActivity;
import com.epam.popcornapp.ui.login.sign_in.SignInActivity;

import javax.inject.Inject;

import butterknife.BindView;

public class SplashActivity extends BaseMvpActivity implements
        SplashView,
        NetworkStateReceiver.NetworkStateReceiverListener,
        ErrorMessage.onErrorMessageClickListener {

    @Inject
    NetworkStateReceiver networkStateReceiver;

    @Inject
    ErrorMessage errorMessage;

    @InjectPresenter
    SplashPresenter splashPresenter;

    @BindView(R.id.splash_activity_progress_bar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BaseApplication.getApplicationComponent().inject(this);

        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onViewsBinded() {
        splashPresenter.setScreenSize(getWindowManager().getDefaultDisplay());

        refresh();
    }

    @Override
    protected void onResume() {
        super.onResume();

        networkStateReceiver.addListener(this);
        this.registerReceiver(networkStateReceiver,
                new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();

        this.unregisterReceiver(networkStateReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        errorMessage.removeDialog();
        splashPresenter.onDestroy();
    }

    private void refresh() {
        progressBar.setVisibility(View.VISIBLE);

        splashPresenter.refreshData();
    }

    @Override
    public void onError(@ErrorType final int errorType) {
        progressBar.setVisibility(View.INVISIBLE);

        errorMessage.showAlertDialog(this, this, errorType, false);
    }

    @Override
    public void startLoginActivity() {
        startActivity(SignInActivity.start(this, true));

        finish();
    }

    @Override
    public void startMainActivity() {
        startActivity(MainActivity.start(this));

        finish();
    }

    @Override
    public void onRetryClicked() {
        refresh();
    }

    @Override
    public void networkAvailable() {
        errorMessage.removeDialog();

        refresh();
    }

    @Override
    public void networkUnavailable() {
        onError(ErrorType.INTERNET_CONNECTION_ERROR);
    }
}
