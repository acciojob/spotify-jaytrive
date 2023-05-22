package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class SpotifyRepository {
    public HashMap<Artist, List<Album>> artistAlbumMap;
    public HashMap<Album, List<Song>> albumSongMap;
    public HashMap<Playlist, List<Song>> playlistSongMap;
    public HashMap<Playlist, List<User>> playlistListenerMap;
    public HashMap<User, Playlist> creatorPlaylistMap;
    public HashMap<User, List<Playlist>> userPlaylistMap;
    public HashMap<Song, List<User>> songLikeMap;

    public List<User> users;
    public List<Song> songs;
    public List<Playlist> playlists;
    public List<Album> albums;
    public List<Artist> artists;

//    public boolean likeForSong=false;
//    public boolean isLikeForArtist=false;

    public SpotifyRepository() {
        //To avoid hitting apis multiple times, initialize all the hashmaps here with some dummy data
        artistAlbumMap = new HashMap<>();
        albumSongMap = new HashMap<>();
        playlistSongMap = new HashMap<>();
        playlistListenerMap = new HashMap<>();
        creatorPlaylistMap = new HashMap<>();
        userPlaylistMap = new HashMap<>();
        songLikeMap = new HashMap<>();

        users = new ArrayList<>();
        songs = new ArrayList<>();
        playlists = new ArrayList<>();
        albums = new ArrayList<>();
        artists = new ArrayList<>();
    }

    public User createUser(String name, String mobile) {
        User user = new User(name, mobile);
        if(users.contains(user)==false)
        users.add(user);
        return user;
    }

    public Artist createArtist(String name) {
        Artist artist = new Artist(name);
        if(artists.contains(artist)==false)
        artists.add(artist);
        return artist;
    }

    public Album createAlbum(String title, String artistName) {
        Artist ar1 = null;
        //album must add if artist not found then create artis and save album and if artist found than save album
        Album album = new Album(title);
        if(albums.contains(album)==false)
        albums.add(album);

        //searching for artist
        for (Artist artist : artists) {
            if (artist.getName().equals(artistName) == true)
            {
                ar1 = artist;
                List<Album> listOfAlbums = new ArrayList<>();
                listOfAlbums = artistAlbumMap.get(ar1);    //getting list of album of artist
                listOfAlbums.add(album);                    //adding to list
                artistAlbumMap.put(ar1, listOfAlbums); //saving updated list to hashmap
                return album;
            }
        }
        if (ar1 == null)
        {
            ar1 = new Artist(artistName);
            if(artists.contains(ar1)==false)
            artists.add(ar1);
            List<Album> listOfAlbums = new ArrayList<>();
            if(listOfAlbums.contains(album)==false)
            listOfAlbums.add(album);
            artistAlbumMap.put(ar1, listOfAlbums);
        }
        return album;
    }

    public Song createSong(String title, String albumName, int length) throws Exception {
        Album album1 = null;
        Song song = null;

        for (Album album : albums)
        {
            if (album.getTitle().equals(albumName))
                album1 = album;
        }
        try {
            if (album1 == null) throw new Exception("Album does not exist");
            else
            {
                song = new Song(title, length);
                if(songs.contains(song)==false)
                    songs.add(song);
                List<Song> listOfSong = new ArrayList<>();
                listOfSong = albumSongMap.get(album1);
                if(listOfSong.contains(song)==false)
                listOfSong.add(song);
                albumSongMap.put(album1, listOfSong);
                return song;
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

    }

    public Playlist createPlaylistOnLength(String mobile, String title, int length) throws Exception {
        User user1 = null;
        for (User user : users)
        {
            if (user.getMobile().equals(mobile))
                user1 = user;
        }
        try {
            if (user1 == null)
                throw new Exception("User does not exist");
            else
            {
                //public HashMap<User, Playlist> creatorPlaylistMap; playlist nu nam and user nam valo object
                Playlist playlist = new Playlist(title);
                if(playlists.contains(playlist)==false)
                playlists.add(playlist);
                creatorPlaylistMap.put(user1, playlist);

                // public HashMap<User, List<Playlist>> userPlaylistMap; single user having multiple playlist so add into that
                List<Playlist> playlists1 = new ArrayList<>();
                playlists1 = userPlaylistMap.get(user1);
                if(playlists1.contains(playlist)==false)
                playlists1.add(playlist);
                userPlaylistMap.put(user1, playlists1);


                //public HashMap<Playlist, List<Song>> playlistSongMap;
                List<Song> listOfSongInPlayList = new ArrayList<>();
                for (Song song : songs)
                {
                    if (song.getLength() == length)
                        listOfSongInPlayList.add(song);
                }
                if (listOfSongInPlayList.size() > 0)
                    playlistSongMap.put(playlist, listOfSongInPlayList);

                //public HashMap<Playlist, List<User>> playlistListenerMap;
                List<User> users1=new ArrayList<>();
                if(users1.contains(user1)==false)
                users1=playlistListenerMap.get(playlist);
                users1.add(user1);
                playlistListenerMap.put(playlist,users1);

                return playlist;
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public Playlist createPlaylistOnName(String mobile, String title, List<String> songTitles) throws Exception {
        User user1 = null;
        for (User user : users)
        {
            if (user.getMobile().equals(mobile))
                user1 = user;
        }
        try {
            if (user1 == null)
                throw new Exception("User does not exist");
            else
            {
                //public HashMap<User, Playlist> creatorPlaylistMap; playlist nu nam and user nam valo object
                Playlist playlist = new Playlist(title);
                if(playlists.contains(playlist)==false)
                playlists.add(playlist);
                creatorPlaylistMap.put(user1, playlist);

                // public HashMap<User, List<Playlist>> userPlaylistMap; single user having multiple playlist so add into that
                List<Playlist> playlists1 = new ArrayList<>();
                playlists1 = userPlaylistMap.get(user1);
                if(playlists1.contains(playlist)==false)
                playlists1.add(playlist);
                userPlaylistMap.put(user1, playlists1);


                //public HashMap<Playlist, List<Song>> playlistSongMap;
                List<Song> listOfSongInPlayList = new ArrayList<>();
                for (Song song : songs)
                {
                    if (song.getTitle().equals(title))
                        listOfSongInPlayList.add(song);
                }
                if (listOfSongInPlayList.size() > 0)
                    playlistSongMap.put(playlist, listOfSongInPlayList);

               //public HashMap<Playlist, List<User>> playlistListenerMap;
                List<User> users1=new ArrayList<>();
                users1=playlistListenerMap.get(playlist);
                if(users.contains(user1)==false)
                users1.add(user1);
                playlistListenerMap.put(playlist,users1);

                return playlist;
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public Playlist findPlaylist(String mobile, String playlistTitle) throws Exception {
        boolean userFlage=false;
        boolean playsListFalg=false;
        Playlist p=null;
       for(User u:creatorPlaylistMap.keySet()){
           if(u.getMobile().equals(mobile))
           {
               userFlage=true;
               p=creatorPlaylistMap.get(u);
               if(p.getTitle().equals(playlistTitle)){
                   playsListFalg=true;
                  // public HashMap<Playlist, List<User>> playlistListenerMap;
                   List<User> users1=new ArrayList<>();
                   users1=playlistListenerMap.get(p);
                   if(users1.contains(u)==false)
                       users1.add(u);
                   playlistListenerMap.put(p,users1);
               }

           }
       }
       try {
           if(userFlage==false)
               throw new Exception("User does not exist");
           if(playsListFalg==false)
               throw new Exception("Playlist does not exist");
           return p;
       } catch (Exception e) {
           throw new RuntimeException(e);
       }

    }

    public Song likeSong(String mobile, String songTitle) throws Exception {

        User user = null;
        for (User user1: users)
        {
            if (user.getMobile().equals(mobile))
                user = user1;
        }



        Song song = null;
        for (Song song1 : songs)
        {
            if (song1.getTitle().equals(songTitle))
                song = song1;
        }
        try {
            if (user == null)
                throw new Exception("User does not exist");
            if (song == null)
                throw new Exception("Song does not exist");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


       /* public HashMap<Artist, List<Album>> artistAlbumMap;
        public HashMap<Album, List<Song>> albumSongMap;*/

            if (song != null && user != null)
            {

            }
            return song;
        }

        public String mostPopularArtist () {
            int likes = 0;
            String artistWithMostLikes = "";
            for (Artist artist : artists)
            {
                if (artist.getLikes() > likes)
                {
                    likes = artist.getLikes();
                    artistWithMostLikes = artist.getName();
                }
            }
            return artistWithMostLikes;

        }

        public String mostPopularSong () {
            int likes = 0;
            String songWithMostLikes = "";
            for (Song song : songs)
            {
                if (song.getLikes() > likes)
                {
                    likes = song.getLikes();
                    songWithMostLikes = song.getTitle();
                }
            }
            return songWithMostLikes;
        }
}

