package com.sriramr.movieinfo.utils;

import android.util.SparseArray;
public class Utils {

    public static SparseArray<String> getMovieGenreList(){
        SparseArray<String> movieGenre = new SparseArray<>();
        movieGenre.put(28,"Action");
        movieGenre.put(12,"Adventure");
        movieGenre.put(16,"Animation");
        movieGenre.put(35,"Comedy");
        movieGenre.put(80,"Crime");
        movieGenre.put(99,"Documentary");
        movieGenre.put(18,"Drama");
        movieGenre.put(10751,"Family");
        movieGenre.put(14,"Fantasy");
        movieGenre.put(36,"History");
        movieGenre.put(27,"Horror");
        movieGenre.put(10402,"Music");
        movieGenre.put(9468,"Mystery");
        movieGenre.put(10749,"Romance");
        movieGenre.put(878,"Science Fiction");
        movieGenre.put(53,"Thriller");
        movieGenre.put(10752,"War");
        movieGenre.put(37,"Western");
        return movieGenre;
    }

    public static SparseArray<String> getTvShowGenreList(){
        SparseArray<String> showGenre = new SparseArray<>();
        showGenre.put(10759,"Action & Adventure");
        showGenre.put(16,"Animation");
        showGenre.put(35,"Comedy");
        showGenre.put(80,"Crime");
        showGenre.put(99,"Documentary");
        showGenre.put(18,"Drama");
        showGenre.put(10751,"Family");
        showGenre.put(10762,"Kids");
        showGenre.put(9648,"Mystery");
        showGenre.put(10763,"News");
        showGenre.put(10764,"Reality");
        showGenre.put(10765,"Sci-Fi & Fantasy");
        showGenre.put(10766,"Soap");
        showGenre.put(10767,"War & Politics");
        showGenre.put(37,"Western");
        return showGenre;
    }

}
