package com.example.movies.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainViewModel extends AndroidViewModel {

    private static MovieDatabase database;
    private LiveData<List<Movie>> movies;
    private LiveData<List<FavouriteMovie>> favouriteMovies;

    public MainViewModel(@NonNull Application application) {
        super(application);
        database = MovieDatabase.getInstance(getApplication());
        favouriteMovies = database.movieDao().getAllFavouriteMovies();
        movies = database.movieDao().getAllMovies();
    }

    public LiveData<List<FavouriteMovie>> getFavouriteMovies() {
        return favouriteMovies;
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public Movie getMovieById(int id){
        try {
            return new GetMovieTask().execute(id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public FavouriteMovie getFavouriteMovieById(int id){
        try {
            return new GetFavouriteMovieTask().execute(id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteAllMovies(){
new DeleteMoviesTask().execute();
    }

    public void insertMovie(Movie movie){
    new InsertTask().execute(movie);
    }

    public void deleteMovie(Movie movie){
        new DeleteTask().execute(movie);
    }

    public void insertFavouriteMovie(FavouriteMovie movie){
        new InsertFavouriteTask().execute(movie);
    }

    public void deleteFavouriteMovie(FavouriteMovie movie){
        new DeleteFavouriteTask().execute(movie);
    }

    private static class GetMovieTask extends AsyncTask<Integer,Void,Movie>{

        @Override
        protected Movie doInBackground(Integer... integers) {
            if (integers!=null && integers.length>0){
                return database.movieDao().getMovieById(integers[0]);
            }
            return null;
        }
    }

    private static class GetFavouriteMovieTask extends AsyncTask<Integer,Void,FavouriteMovie>{

        @Override
        protected FavouriteMovie doInBackground(Integer... integers) {
            if (integers!=null && integers.length>0){
                return database.movieDao().getFavouriteMovieById(integers[0]);
            }
            return null;
        }
    }

    private static class DeleteMoviesTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... integers) {
                database.movieDao().deleteAllMovies();
                return null;
        }
    }

    private static class InsertTask extends AsyncTask<Movie,Void,Void>{

        @Override
        protected Void doInBackground(Movie... integers) {
            if (integers!=null && integers.length>0){
                database.movieDao().insertMovie(integers[0]);
            }
            return null;
        }
    }

    private static class DeleteTask extends AsyncTask<Movie,Void,Void>{

        @Override
        protected Void doInBackground(Movie... integers) {
            if (integers!=null && integers.length>0){
                database.movieDao().deleteMovie(integers[0]);
            }
            return null;
        }
    }

    private static class InsertFavouriteTask extends AsyncTask<FavouriteMovie,Void,Void>{

        @Override
        protected Void doInBackground(FavouriteMovie... integers) {
            if (integers!=null && integers.length>0){
                database.movieDao().insertFavouriteMovie(integers[0]);
            }
            return null;
        }
    }

    private static class DeleteFavouriteTask extends AsyncTask<FavouriteMovie,Void,Void>{

        @Override
        protected Void doInBackground(FavouriteMovie... integers) {
            if (integers!=null && integers.length>0){
                database.movieDao().deleteFavouriteMovie(integers[0]);
            }
            return null;
        }
    }

}
