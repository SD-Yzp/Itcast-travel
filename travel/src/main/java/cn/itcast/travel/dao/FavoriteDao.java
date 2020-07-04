package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

public interface FavoriteDao {
    Favorite findByUidAndRid(int uid, int rid);

    void addFavorite(int uid, int rid);
}
