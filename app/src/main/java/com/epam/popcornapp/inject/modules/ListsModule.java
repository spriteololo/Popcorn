package com.epam.popcornapp.inject.modules;

import com.epam.popcornapp.inject.ListsInterface;
import com.epam.popcornapp.model.lists.ListDetailInteractor;
import com.epam.popcornapp.model.lists.ListMediaInteractor;
import com.epam.popcornapp.model.lists.ListsInteractor;
import com.epam.popcornapp.model.lists.interfaces.IListDetailInteractor;
import com.epam.popcornapp.model.lists.interfaces.IListMediaInteractor;
import com.epam.popcornapp.model.lists.interfaces.IListsInteractor;
import com.epam.popcornapp.ui.createListDialog.CreateListDialog;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ListsModule {

    @Provides
    @Singleton
    IListsInteractor provideListsInteractor() {
        return new ListsInteractor();
    }

    @Provides
    @Singleton
    IListDetailInteractor provideListDetailInteractor() {
        return new ListDetailInteractor();
    }

    @Provides
    @Singleton
    IListMediaInteractor provideIListMediaInteractor() {
        return new ListMediaInteractor();
    }

    @Provides
    @Singleton
    CreateListDialog provideListDialog() {
        return new CreateListDialog();
    }

    @Provides
    @Singleton
    ListsInterface provideListsInterface(final Retrofit retrofit) {
        return retrofit.create(ListsInterface.class);
    }
}
