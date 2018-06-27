package com.epam.popcornapp.application;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import com.epam.popcornapp.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Constants {

    public static final String GRAVATAR_URL = "https://www.gravatar.com/avatar/";
    public static final String BASE_AVATAR_URL = "https://www.gravatar.com/avatar/%1$s?s=1000";
    public static String CURRENT_SESSION = null;
    public static String CURRENT_USER = null;
    public static String GRAVATAR_HASH = null;
    public static int ACCOUNT_ID = -1;

    public interface MovieDbApi {
        String API_KEY = "92e0a05bf5b6e05f60a73954b743558f";

        String BASE_URL = "https://api.themoviedb.org/3/";
    }

    public static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/";
    public static final String API_KEY = "92e0a05bf5b6e05f60a73954b743558f";

    public static final String QLASSIFIED_SESSION_ID_KEY = "session_id_key";
    public static final String QLASSIFIED_ACCOUNT_ID_KEY = "account_id_key";
    public static final String QLASSIFIED_USERNAME_KEY = "username_key";
    public static final String QLASSIFIED_NAME_KEY = "name_key";
    public static final String QLASSIFIED_GRAVATAR_HASH_KEY = "gravatar_hash_key";

    public static final String EMPTY_STRING = "";
    public static final String DIVIDER = " | ";
    public static final int EMPTY_ID = -1;
    public static final int START_COORDINATE = 0;
    public static final int ANIM_DURATION = 650;
    public static final int ANIM_FAST_DURATION = 300;
    public static final int ONE_PAGE_ITEMS_NUM = 20;
    public static final int ANIM_SWIPE_START_POSITION = 100;
    public static final int ANIM_SWIPE_END_POSITION = 200;
    public static final int SUCCESS_CODE = 0;
    public static final int SEARCH_INPUT_TIMEOUT = 300;

    public static final int TOOLBAR_ANIMATION_DURATION = 250;
    public static final int TOOLBAR_ANIMATION_TRANSLATION = -300;

    public interface MovieDetails {
        int[] TYPES = {
                R.string.Premiere,
                R.string.Theatrical_limited,
                R.string.Theatrical,
                R.string.Digital,
                R.string.Physical,
                R.string.TV
        };
    }

    public class Extras {
        public static final String ACTOR_ID = "extra_actor_id";
        public static final String MOVIE_ID = "extra_movie_id";
        public static final String TV_SHOW_ID = "extra_tv_id";
        public static final String SCREEN_TYPE = "extra_screen_id";
        public static final String GALLERY_ITEM = "extra_gallery_item";
        public static final String TITLE_TYPE = "title_type";
        public static final String TITLE_ID = "title_id";
        public static final String LIST_ID = "list_id";
        public static final String SEASON = "season_start_params";
        public static final String PARAMS = "params";
        public static final String EXIT_POSITION = "exit_position";

        public static final String TAG = "tag";
        public static final String TRANSITION_NAME = "transitionName";
        public static final String LOGIN_BOOL = "is_from_splash";
    }

    public static class Screens {
        public static final String MORE_SCREEN = "more_screen";
        public static final String SEARCH_SCREEN = "search_screen";
        public static final String SETTINGS_SCREEN = "settings_screen";
        public static final String LOGIN_SCREEN = "login_screen";
        public static final String ACCOUNT_SCREEN = "account_screen";

        public static final String HOME_FRAGMENT = "home_fragment";
        public static final String MOVIES_FRAGMENT = "movies_fragment";
        public static final String TV_FRAGMENT = "tv_fragment";
        public static final String PEOPLE_FRAGMENT = "people_fragment";
        public static final String RATED_FRAGMENT = "rated_fragment";
        public static final String WATCHLIST_FRAGMENT = "watchlist_fragment";
        public static final String FAVORITE_FRAGMENT = "favorite_fragment";
        public static final String LISTS_FRAGMENT = "lists_fragment";

        public static final String GALLERY_PHOTO_SCREEN = "gallery_photo_screen";
        public static final String REVIEWS_SCREEN = "reviews_screen";

        public static final String MOVIE_SCREEN = "movie_screen";
        public static final String ACTOR_SCREEN = "actor_screen";
        public static final String TV_SHOW_SCREEN = "tv_show_screen";
        public static final String TV_SEASON_SCREEN = "tv_show_season_screen";
        public static final String TV_EPISODE_SCREEN = "tv_episode_screen";
        public static final String LISTS_SCREEN = "lists_screen";
        public static final String LIST_TITLE_CARD_SCREEN = "list_title_card_screen";
        public static final String LIST_ADD_MEDIA_SCREEN = "list_add_media_screen";
    }

    @IntDef({
            ChangeType.WATCHLIST,
            ChangeType.FAVORITE,
            ChangeType.RATED
    })

    @Retention(RetentionPolicy.SOURCE)
    public @interface ChangeType {
        int WATCHLIST = 2;
        int FAVORITE = 3;
        int RATED = 4;
    }

    @StringDef({
            DataType.MOVIES_POPULAR,
            DataType.MOVIES_UPCOMING,
            DataType.MOVIES_TOP_RATED,
            DataType.TV_AIRING_TODAY,
            DataType.TV_ON_THE_AIR,
            DataType.TV_POPULAR,
            DataType.TV_TOP_RATED,
            DataType.PEOPLE,
            DataType.MOVIES,
            DataType.TV_SHOWS,
            DataType.RATED_MOVIES,
            DataType.RATED_TV_SHOWS
    })

    @Retention(RetentionPolicy.SOURCE)
    public @interface DataType {
        String MOVIES_POPULAR = "popular_movies";
        String MOVIES_UPCOMING = "upcoming_movies";
        String MOVIES_TOP_RATED = "top_rated_movies";
        String TV_AIRING_TODAY = "airing_today_tv";
        String TV_ON_THE_AIR = "on_the_air_tv";
        String TV_POPULAR = "popular_tv";
        String TV_TOP_RATED = "top_rated_tv";
        String PEOPLE = "people";
        String MOVIES = "movies";
        String TV_SHOWS = "tv_shows";
        String RATED_MOVIES = "rated_movies";
        String RATED_TV_SHOWS = "rated_tv_shows";
    }

    public interface NetworkingConfig {
        int TIMEOUT = 20;
    }
}
