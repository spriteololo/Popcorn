package com.epam.popcornapp.ui.review;


import com.arellomobile.mvp.MvpView;
import com.epam.popcornapp.pojo.movies.reviews.MovieResultRev;

import java.util.List;

public interface ReviewPresenterView extends MvpView{
    void setData(List<MovieResultRev> resultRevs);

    void error();

    void completed();
}
