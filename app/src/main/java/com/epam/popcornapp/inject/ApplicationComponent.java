package com.epam.popcornapp.inject;

import com.epam.popcornapp.inject.modules.AccountModule;
import com.epam.popcornapp.inject.modules.ActorsModule;
import com.epam.popcornapp.inject.modules.ConfigModule;
import com.epam.popcornapp.inject.modules.ErrorMessageModule;
import com.epam.popcornapp.inject.modules.ListsModule;
import com.epam.popcornapp.inject.modules.LoginModule;
import com.epam.popcornapp.inject.modules.MoviesModule;
import com.epam.popcornapp.inject.modules.NavigationModule;
import com.epam.popcornapp.inject.modules.NetworkModule;
import com.epam.popcornapp.inject.modules.QlassifiedModule;
import com.epam.popcornapp.inject.modules.RealmModule;
import com.epam.popcornapp.inject.modules.RetrofitModule;
import com.epam.popcornapp.inject.modules.RootModule;
import com.epam.popcornapp.inject.modules.SearchModule;
import com.epam.popcornapp.inject.modules.SplashModule;
import com.epam.popcornapp.inject.modules.TvModule;
import com.epam.popcornapp.model.actors.ActorInfoInteractor;
import com.epam.popcornapp.model.actors.PopularActorsInteractor;
import com.epam.popcornapp.model.favorite.FavoriteMovieInteractor;
import com.epam.popcornapp.model.favorite.FavoriteTvInteractor;
import com.epam.popcornapp.model.lists.ListDetailInteractor;
import com.epam.popcornapp.model.lists.ListMediaInteractor;
import com.epam.popcornapp.model.lists.ListsInteractor;
import com.epam.popcornapp.model.login.LoginInteractor;
import com.epam.popcornapp.model.movie.MovieInfoInteractor;
import com.epam.popcornapp.model.movie.PopularMoviesInteractor;
import com.epam.popcornapp.model.movie.TopRatedMoviesInteractor;
import com.epam.popcornapp.model.movie.UpcomingMoviesInteractor;
import com.epam.popcornapp.model.rated.RatedMovieInteractor;
import com.epam.popcornapp.model.rated.RatedTvInteractor;
import com.epam.popcornapp.model.splash.SplashInteractor;
import com.epam.popcornapp.model.tv.TvAiringTodayInteractor;
import com.epam.popcornapp.model.tv.TvOnTheAirInteractor;
import com.epam.popcornapp.model.tv.TvPopularInteractor;
import com.epam.popcornapp.model.tv.TvTopRatedInteractor;
import com.epam.popcornapp.model.tv.episode.TvEpisodeInteractor;
import com.epam.popcornapp.model.tv.info.TvInfoInteractor;
import com.epam.popcornapp.model.tv.season.TvSeasonInfoInteractor;
import com.epam.popcornapp.model.watchlist.WatchlistMovieInteractor;
import com.epam.popcornapp.model.watchlist.WatchlistTvInteractor;
import com.epam.popcornapp.ui.account.AccountPresenter;
import com.epam.popcornapp.ui.actors.ActorsFragment;
import com.epam.popcornapp.ui.actors.PopularActorsPresenter;
import com.epam.popcornapp.ui.actors.info.ActorInfoDetails;
import com.epam.popcornapp.ui.actors.info.ActorInfoPresenter;
import com.epam.popcornapp.ui.all.base.BaseMoreMvpPresenter;
import com.epam.popcornapp.ui.favorite.presenters.FavoriteMoviePresenter;
import com.epam.popcornapp.ui.favorite.presenters.FavoriteTvPresenter;
import com.epam.popcornapp.ui.home.HomeFragment;
import com.epam.popcornapp.ui.home.MainActivity;
import com.epam.popcornapp.ui.home.MainPresenter;
import com.epam.popcornapp.ui.lists.ListsActivity;
import com.epam.popcornapp.ui.lists.ListsFragment;
import com.epam.popcornapp.ui.lists.detail.ListDetailActivity;
import com.epam.popcornapp.ui.lists.presenters.ListDetailPresenter;
import com.epam.popcornapp.ui.lists.presenters.ListMediaPresenter;
import com.epam.popcornapp.ui.lists.presenters.ListsPresenter;
import com.epam.popcornapp.ui.login.account_details.AccountDetailsActivity;
import com.epam.popcornapp.ui.login.account_details.AccountDetailsPresenter;
import com.epam.popcornapp.ui.login.sign_in.SignInActivity;
import com.epam.popcornapp.ui.login.sign_in.SignInPresenter;
import com.epam.popcornapp.ui.more.MoreActivity;
import com.epam.popcornapp.ui.movies.MoviesFragment;
import com.epam.popcornapp.ui.movies.PopularMoviesPresenter;
import com.epam.popcornapp.ui.movies.TopRatedMoviesPresenter;
import com.epam.popcornapp.ui.movies.UpcomingMoviesPresenter;
import com.epam.popcornapp.ui.movies.info.MovieInfoData;
import com.epam.popcornapp.ui.movies.info.MovieInfoPresenter;
import com.epam.popcornapp.ui.rated.presenters.RatedMoviePresenter;
import com.epam.popcornapp.ui.rated.presenters.RatedTvPresenter;
import com.epam.popcornapp.ui.review.ReviewActivity;
import com.epam.popcornapp.ui.review.ReviewPresenter;
import com.epam.popcornapp.ui.search.SearchActivity;
import com.epam.popcornapp.ui.search.SearchPresenter;
import com.epam.popcornapp.ui.splash.SplashActivity;
import com.epam.popcornapp.ui.splash.SplashPresenter;
import com.epam.popcornapp.ui.titles.TitleCardActivity;
import com.epam.popcornapp.ui.titles.TitlePresenter;
import com.epam.popcornapp.ui.tv.TvFragment;
import com.epam.popcornapp.ui.tv.airing_today.TvAiringTodayPresenter;
import com.epam.popcornapp.ui.tv.episode.TvEpisodeActivity;
import com.epam.popcornapp.ui.tv.episode.TvEpisodeData;
import com.epam.popcornapp.ui.tv.episode.TvEpisodePresenter;
import com.epam.popcornapp.ui.tv.info.TvInfoPresenter;
import com.epam.popcornapp.ui.tv.info.TvShowData;
import com.epam.popcornapp.ui.tv.on_the_air.TvOnTheAirPresenter;
import com.epam.popcornapp.ui.tv.popular.TvPopularPresenter;
import com.epam.popcornapp.ui.tv.season.TvSeasonActivity;
import com.epam.popcornapp.ui.tv.season.TvSeasonInfo;
import com.epam.popcornapp.ui.tv.season.TvSeasonPresenter;
import com.epam.popcornapp.ui.tv.top_rated.TvTopRatedPresenter;
import com.epam.popcornapp.ui.watchlist.presenters.WatchlistMoviePresenter;
import com.epam.popcornapp.ui.watchlist.presenters.WatchlistTvPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RootModule.class, NetworkModule.class, RetrofitModule.class,
        ErrorMessageModule.class, MoviesModule.class, TvModule.class, ActorsModule.class,
        LoginModule.class, AccountModule.class, NavigationModule.class, ConfigModule.class,
        SearchModule.class, RealmModule.class, ListsModule.class, SplashModule.class,
        QlassifiedModule.class})
public interface ApplicationComponent {

    void inject(SplashPresenter splashPresenter);

    void inject(MainActivity mainActivity);

    void inject(MoreActivity moreActivity);

    void inject(PopularActorsPresenter actorsPresenter);

    void inject(PopularMoviesPresenter moviesPresenter);

    void inject(UpcomingMoviesPresenter moviesPresenter);

    void inject(TopRatedMoviesPresenter moviesPresenter);

    void inject(TvPopularPresenter tvPopularPresenter);

    void inject(TvTopRatedPresenter tvTopRatedPresenter);

    void inject(TvAiringTodayPresenter tvAiringTodayPresenter);

    void inject(TvOnTheAirPresenter tvOnTheAirPresenter);

    void inject(ActorInfoPresenter presenter);

    void inject(MovieInfoPresenter movieInfoPresenter);

    void inject(TvInfoPresenter presenter);

    void inject(TvFragment tvFragment);

    void inject(TvShowData tvShowData);

    void inject(BaseMoreMvpPresenter baseMoreMvpPresenter);

    void inject(MainPresenter mainPresenter);

    void inject(HomeFragment homeFragment);

    void inject(MoviesFragment moviesFragment);

    void inject(ActorsFragment actorsFragment);

    void inject(ActorInfoDetails actorInfoDetails);

    void inject(TitleCardActivity titleCardActivity);

    void inject(TitlePresenter titlePresenter);

    void inject(ReviewActivity reviewActivity);

    void inject(ReviewPresenter reviewPresenter);

    void inject(SplashActivity splashActivity);

    void inject(SearchPresenter searchPresenter);

    void inject(SearchActivity searchActivity);

    void inject(TvTopRatedInteractor tvTopRatedInteractor);

    void inject(TvInfoInteractor tvInfoInteractor);

    void inject(TvAiringTodayInteractor tvAiringTodayInteractor);

    void inject(TvOnTheAirInteractor tvOnTheAirInteractor);

    void inject(TvPopularInteractor tvPopularInteractor);

    void inject(PopularActorsInteractor popularActorsInteractor);

    void inject(ActorInfoInteractor actorInfoInteractor);

    void inject(SplashInteractor splashInteractor);

    void inject(PopularMoviesInteractor popularMoviesInteractor);

    void inject(TopRatedMoviesInteractor topRatedMoviesInteractor);

    void inject(UpcomingMoviesInteractor upcomingMoviesInteractor);

    void inject(MovieInfoInteractor movieInfoInteractor);

    void inject(MovieInfoData movieInfoData);

    void inject(TvSeasonPresenter tvSeasonPresenter);

    void inject(TvSeasonInfoInteractor tvSeasonInfoInteractor);

    void inject(TvSeasonInfo tvSeasonInfo);

    void inject(TvSeasonActivity tvSeasonActivity);

    void inject(TvShowData.Builder builder);

    void inject(TvEpisodeInteractor tvEpisodeInteractor);

    void inject(TvEpisodePresenter tvEpisodePresenter);

    void inject(TvEpisodeActivity tvEpisodeActivity);

    void inject(TvEpisodeData tvEpisodeData);

    void inject(SignInPresenter signInPresenter);

    void inject(SignInActivity signInActivity);

    void inject(AccountDetailsActivity accountDetailsActivity);

    void inject(AccountDetailsPresenter accountDetailsPresenter);

    void inject(AccountPresenter accountPresenter);

    void inject(RatedMovieInteractor ratedInteractor);

    void inject(RatedTvInteractor ratedTvInteractor);

    void inject(RatedMoviePresenter ratedMoviePresenter);

    void inject(RatedTvPresenter ratedTvPresenter);

    void inject(WatchlistMovieInteractor watchlistMovieInteractor);

    void inject(WatchlistTvInteractor watchlistTvInteractor);

    void inject(WatchlistMoviePresenter watchlistMoviePresenter);

    void inject(WatchlistTvPresenter watchlistTvPresenter);

    void inject(FavoriteMovieInteractor favoriteMovieInteractor);

    void inject(FavoriteTvInteractor favoriteTvInteractor);

    void inject(FavoriteMoviePresenter favoriteMoviePresenter);

    void inject(FavoriteTvPresenter favoriteTvPresenter);

    void inject(ListsInteractor listsInteractor);

    void inject(ListDetailInteractor listDetailInteractor);

    void inject(ListMediaInteractor listMediaInteractor);

    void inject(ListsPresenter listsPresenter);

    void inject(ListDetailPresenter listDetailPresenter);

    void inject(ListMediaPresenter listMediaPresenter);

    void inject(ListsActivity listsActivity);

    void inject(ListDetailActivity listDetailActivity);

    void inject(ListsFragment listsFragment);

    void inject(LoginInteractor loginInteractor);
}
