package com.epam.popcornapp.ui.search;

import android.support.v7.widget.SearchView;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class SearchObservable {

    public static Observable<String> fromView(final SearchView searchView) {
        final PublishSubject<String> subject = PublishSubject.create();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                subject.onNext(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                subject.onNext(newText);
                return true;
            }
        });

        return subject;
    }
}
