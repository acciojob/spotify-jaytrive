package com.driver;

public class Song {
    private String title;
    private int length;
    private int likes;

    private boolean likeForSong=false;

    public boolean isLikeForSong() {
        return likeForSong;
    }

    public void setLikeForSong(boolean likeForSong) {
        this.likeForSong = likeForSong;
    }

    public Song(){

    }

    public Song(String title, int length){
        this.title = title;
        this.length = length;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
