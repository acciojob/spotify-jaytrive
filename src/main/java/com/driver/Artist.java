package com.driver;

import java.util.List;

public class Artist {
    private String name;
    private int likes;
       private boolean likeForArtist=false;

    public boolean isLikeForArtist() {
        return likeForArtist;
    }

    public void setLikeForArtist(boolean likeForArtist) {
        this.likeForArtist = likeForArtist;
    }

    public Artist(){

    }

    public Artist(String name){
        this.name = name;
        this.likes = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
