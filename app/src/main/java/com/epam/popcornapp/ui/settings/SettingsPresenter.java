package com.epam.popcornapp.ui.settings;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.epam.popcornapp.model.settings.ISettingsInteractor;
import com.epam.popcornapp.model.settings.SettingsInteractor;
import com.epam.popcornapp.pojo.splash_settings.ImagesConfig;
import com.epam.popcornapp.ui.all.base.BaseInternetMvpPresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

@InjectViewState
public class SettingsPresenter extends BaseInternetMvpPresenter<SettingsView> implements
        ISettingsInteractor.Actions {

    private ISettingsInteractor interactor;
    private Disposable disposable;

    SettingsPresenter() {
        interactor = new SettingsInteractor(this);
    }

    void refreshData() {
        interactor.getData();
    }

    @Override
    public void onSuccess(final Observable<ImagesConfig> observable) {
        if (disposable != null && !disposable.isDisposed()) {
            return;
        }

        observable.observeOn(AndroidSchedulers.mainThread())
                .map(new Function<ImagesConfig, List<String[]>>() {
                    @Override
                    public List<String[]> apply(final ImagesConfig images) throws Exception {
                        final List<String[]> valueList = new ArrayList<>();

                        valueList.add(convertListToArray(images.getBackdropSizes()));
                        valueList.add(convertListToArray(images.getLogoSizes()));
                        valueList.add(convertListToArray(images.getPosterSizes()));
                        valueList.add(convertListToArray(images.getProfileSizes()));
                        valueList.add(convertListToArray(images.getStillSizes()));

                        return valueList;
                    }
                })
                .subscribe(new Observer<List<String[]>>() {
                    @Override
                    public void onSubscribe(final Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(final List<String[]> valueList) {
                        getViewState().setInfo(valueList);
                        disposable.dispose();
                    }

                    @Override
                    public void onError(final Throwable e) {
                        e.printStackTrace();
                        disposable.dispose();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private String[] convertListToArray(@NonNull final List<String> value) {
        return value.toArray(new String[value.size()]);
    }

    @Override
    public void onError() {
    }
}
