package com.epam.popcornapp.ui.login.account_details;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.epam.popcornapp.R;
import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.application.ErrorMessage;
import com.epam.popcornapp.application.ErrorMessage.ErrorType;
import com.epam.popcornapp.glide.GlideApp;
import com.epam.popcornapp.pojo.account.AccountSimpleData;
import com.epam.popcornapp.ui.all.base.BaseMvpActivity;
import com.epam.popcornapp.ui.all.base.NetworkStateReceiver;

import javax.inject.Inject;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class AccountDetailsActivity extends BaseMvpActivity implements
        AccountDetailsView,
        ErrorMessage.onErrorMessageClickListener,
        NetworkStateReceiver.NetworkStateReceiverListener {

    @InjectPresenter
    AccountDetailsPresenter accountDetailsPresenter;

    @Inject
    ErrorMessage errorMessage;

    @Inject
    NetworkStateReceiver networkStateReceiver;

    @BindView(R.id.transparent_toolbar)
    Toolbar toolbar;

    @BindView(R.id.activity_account_details_main_layout)
    View mainLayout;

    @BindView(R.id.account_image)
    CircleImageView avatar;

    @BindView(R.id.account_username)
    TextView username;

    @BindView(R.id.account_name)
    TextView name;

    @BindView(R.id.account_log_out)
    Button logOutBtn;

    public static Intent start(@NonNull final Context context) {
        return new Intent(context, AccountDetailsActivity.class);
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);

        BaseApplication.getApplicationComponent().inject(this);
    }

    @Override
    protected void onViewsBinded() {
        setToolbarNoTitle(toolbar, true);

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                accountDetailsPresenter.logOutClick();
            }
        });

        accountDetailsPresenter.getDetails();
    }

    @Override
    protected void onResume() {
        super.onResume();

        networkStateReceiver.addListener(this);
        this.registerReceiver(networkStateReceiver,
                new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onLogOutSuccess() {
        finish();
    }

    @Override
    public void setData(final AccountSimpleData details) {
        GlideApp.with(this)
                .load(Constants.GRAVATAR_URL +
                        details.getGravatarHash() +
                        "?s=" +
                        (int) getResources().getDimension(R.dimen.account_icon_big_size))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(avatar);

        username.setText(details.getName());
        name.setText(details.getUserName());
    }

    @Override
    protected void onPause() {
        super.onPause();

        this.unregisterReceiver(networkStateReceiver);
    }

    @Override
    public void onError(@ErrorType final int errorType) {
        errorMessage.showSnackbar(this, mainLayout, this, errorType);
    }

    @Override
    public void onRetryClicked() {
        accountDetailsPresenter.getDetails();
    }

    @Override
    public void networkAvailable() {
        errorMessage.removeSnackbar();

        onRetryClicked();
    }

    @Override
    public void networkUnavailable() {
        onError(ErrorType.INTERNET_CONNECTION_ERROR);
    }
}
