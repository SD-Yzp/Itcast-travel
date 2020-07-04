package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {
    FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public boolean isFavorite(int uid, int rid) {

        Favorite favorite = favoriteDao.findByUidAndRid(uid,rid);

        return favorite!=null;
    }

    @Override
    public void addFavorite(int uid, int rid) {
        favoriteDao.addFavorite(uid,rid);
    }
}
