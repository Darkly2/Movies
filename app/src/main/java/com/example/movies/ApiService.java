package com.example.movies;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {


    @Headers({
            "Accept: application/json",
            "X-API-KEY:E9ANJNS-Q28415C-GQSWYCS-8QHFM1J"
    })
    @GET("movie?limit=40&selectFields=id&selectFields=name&selectFields=description&selectFields=year&selectFields=rating&selectFields=poster&notNullFields=id&notNullFields=name&notNullFields=description&notNullFields=year&notNullFields=rating.kp&notNullFields=poster.url&rating.kp=7-10&sortField=rating.kp&sortType=-1&type=movie")
    Single<MovieResponse> loadMovies(@Query("page") int page);

    @Headers({
            "Accept: application/json",
            "X-API-KEY:E9ANJNS-Q28415C-GQSWYCS-8QHFM1J"
    })
    @GET("movie/{id}")
    Single<TrailerResponse> loadTrailers(@Path("id") int id);

    @Headers({
            "Accept: application/json",
            "X-API-KEY:E9ANJNS-Q28415C-GQSWYCS-8QHFM1J"
    })
    @GET("review")
    Single<ReviewResponse> loadReviews(@Query("movieId") int movieId,
                                        @Query("page") int page);
}
