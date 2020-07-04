package cn.itcast.travel.service;

public interface FavoriteService {
    boolean isFavorite(int uid, int rid);

    void addFavorite(int uid, int rid);
}
