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
        users.add(user);
        return user;
    }

    public Artist createArtist(String name) {
        Artist artist = new Artist(name);
        artists.add(artist);
        return artist;
    }

    public Album createAlbum(String title, String artistName) {
        Artist ar1 = null;
        //album must add if artist not found then create artis and save album and if artist found than save album
        Album album = new Album(title);
        List<Album> listOfAlbums = new ArrayList<>();
        albums.add(album);
        //searching for artist
        for (Artist artist : artists) {
            if (artist.getName().equals(artistName) == true)
            {
                ar1 = artist;
                listOfAlbums = artistAlbumMap.get(ar1);    //getting list of album of artist
                listOfAlbums.add(album);                    //adding to list
                artistAlbumMap.put(ar1, listOfAlbums); //saving updated list to hashmap
                return album;
            }
        }
        if (ar1 == null)
        {
            ar1 = new Artist(artistName);
            artists.add(ar1);
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
                List<Song> listOfSong = new ArrayList<>();
                listOfSong = albumSongMap.get(albumName);
                listOfSong.add(song);
                albumSongMap.put(album1, listOfSong);   // adding into album vs song map
                songs.add(song);                        // adding into songs list
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
                playlists.add(playlist);
                creatorPlaylistMap.put(user1, playlist);

                // public HashMap<User, List<Playlist>> userPlaylistMap; single user having multiple playlist so add into that
                List<Playlist> playlists1 = new ArrayList<>();
                playlists1 = userPlaylistMap.get(user1);
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

             /*   //public HashMap<Playlist, List<User>> playlistListenerMap;
                List<User> users1=new ArrayList<>();
                users1=playlistListenerMap.get(playlist);
                users1.add(user1);
                playlistListenerMap.put(playlist,users1);*/

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
                playlists.add(playlist);
                creatorPlaylistMap.put(user1, playlist);

                // public HashMap<User, List<Playlist>> userPlaylistMap; single user having multiple playlist so add into that
                List<Playlist> playlists1 = new ArrayList<>();
                playlists1 = userPlaylistMap.get(user1);
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

              /*  //public HashMap<Playlist, List<User>> playlistListenerMap;
                List<User> users1=new ArrayList<>();
                users1=playlistListenerMap.get(playlist);
                users1.add(user1);
                playlistListenerMap.put(playlist,users1);*/

                return playlist;
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public Playlist findPlaylist(String mobile, String playlistTitle) throws Exception {
        User user1 = null;
        Playlist playlist1 = null;
        boolean flag = false;
        for (User user : users)
        {
            if (user.getMobile().equals(mobile))
                user1 = user;
        }

        /*   public HashMap<Playlist, List<Song>> playlistSongMap;*/
        try {
            if (user1 == null)
                throw new Exception("User does not exist");
            else
            {
                try
                {
                    for (Playlist playlist : playlists)
                    {
                        if (playlist.getTitle().equals(playlistTitle)) {
                            playlist1 = playlist;

                            // public HashMap<User, List<Playlist>> userPlaylistMap;
                            List<Playlist> userPlayLists = userPlaylistMap.get(user1);  //getting existing list of playlist
                            userPlayLists.add(playlist);          //adding new playlist
                            userPlaylistMap.put(user1, playlists); //saving updated list of playlist

                            // public HashMap<User, Playlist> creatorPlaylistMap;
                            creatorPlaylistMap.put(user1, playlist1);

                            // public HashMap<Playlist, List<User>> playlistListenerMap;
                            List<User> users1 = new ArrayList<>();
                            users1 = playlistListenerMap.get(playlist1);
                            users1.add(user1);
                            playlistListenerMap.put(playlist1, users1);

                            return playlist;
                        }
                    }
                } catch (Exception e) {
                    if (playlist1 == null)
                        throw new RuntimeException(e);
                }

            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return playlist1;

    }

    public Song likeSong(String mobile, String songTitle) throws Exception {

        User user1 = null;
        boolean flag = false;
        for (User user : users)
        {
            if (user.getMobile().equals(mobile))
                user1 = user;
        }
        if (user1 == null)
            throw new Exception("User does not exist");


        Song song = null;
        for (Song song1 : songs)
        {
            if (song1.getTitle().equals(songTitle))
                song = song1;
        }
            if (song == null)
                throw new Exception("Song does not exist");

       /* public HashMap<Artist, List<Album>> artistAlbumMap;
        public HashMap<Album, List<Song>> albumSongMap;*/

            if (song != null && user1 != null)
            {
                for (Album album : albumSongMap.keySet())
                {
                    List<Song> listOfSongsInAlbum = albumSongMap.get(album);
                    for (Song s : listOfSongsInAlbum)
                    {
                        if (s.getTitle().equals(songTitle))
                        {
                            Album album1 = album;
                            for (Artist artist : artistAlbumMap.keySet())
                            {
                                if (album == artistAlbumMap.get(artist))
                                {
                                    if (song.isLikeForSong() != true)
                                    {
                                        song.setLikeForSong(true);
                                        song.setLikes(song.getLikes() + 1);
                                        artist.setLikes(artist.getLikes() + 1);
                                    }
                                }
                            }
                        }
                    }

                }
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

