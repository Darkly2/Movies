package com.example.movies;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailViewModel extends AndroidViewModel {

    private int page = 1;

    private static final String TAG = "MovieDetailViewModel";

    private final MutableLiveData<List<Trailer>> trailers = new MutableLiveData<>();

    private final MutableLiveData<List<Review>> reviews = new MutableLiveData<>();

    private final MoviesDao moviesDao;

//    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();


    public LiveData<List<Trailer>> getTrailers() {
        return trailers;
    }

    public LiveData<List<Review>> getReviews() {
        return reviews;
    }


    //    public LiveData<Boolean> getIsLoading() {
//        return isLoading;
//    }
    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
        moviesDao = MovieDataBase.getInstance(application).moviesDao();
    }

    public LiveData<Movie> getFavouriteMovie(int movieId) {
        return moviesDao.getFavouriteMovie(movieId);
    }

    public void insertMovie(Movie movie) {
        Disposable disposable = moviesDao.insertMovie(movie)
                .subscribeOn(Schedulers.io())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, throwable.getMessage());
                    }
                })
                .subscribe(new Action() {
                    @Override
                    public void run() throws Throwable {
                        Log.d(TAG, movie.getId() + "inserted in database");
                    }
                });
        compositeDisposable.add(disposable);
    }

    public void removeMovie(int movieId) {
        Disposable disposable = moviesDao.removeMovie(movieId)
                .subscribeOn(Schedulers.io())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                       Log.d(TAG, throwable.getMessage());
                    }
                })
                .subscribe(new Action() {
                    @Override
                    public void run() throws Throwable {
                        Log.d(TAG, movieId + "removed from database");
                    }
                });
        compositeDisposable.add(disposable);
    }

    public void loadTrailers(int id) {
        Disposable disposable = ApiFactory.apiService.loadTrailers(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<TrailerResponse, List<Trailer>>() {
                    @Override
                    public List<Trailer> apply(TrailerResponse trailerResponse) throws Throwable {
                        return trailerResponse.getTrailersList().getTrailers();
                    }
                })
                .subscribe(new Consumer<List<Trailer>>() {
                    @Override
                    public void accept(List<Trailer> trailerList) throws Throwable {
                        trailers.setValue(trailerList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, throwable.toString());
                    }
                });
        compositeDisposable.add(disposable);
    }

    public void loadReviews(int movieId) {
//        Boolean loading = isLoading.getValue();
//        if (loading && loading != null){
//            return;
//        }
        Disposable disposable = ApiFactory.apiService.loadReviews(movieId, page).
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(new Action() {
                    @Override
                    public void run() throws Throwable {
//                        isLoading.setValue(false);
                    }
                })
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Throwable {
//                        isLoading.setValue(true);
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, throwable.toString());
                    }
                })
                .subscribe(new Consumer<ReviewResponse>() {
                    @Override
                    public void accept(ReviewResponse reviewResponse) throws Throwable {
                        List<Review> loadedReviews = reviews.getValue();
                        if (loadedReviews != null) {
                            loadedReviews.addAll(reviewResponse.getReviews());
                            reviews.setValue(loadedReviews);
                        } else {
                            reviews.setValue(reviewResponse.getReviews());
                        }
                        page++;
                        Log.d(TAG, reviews.getValue().toString());
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
