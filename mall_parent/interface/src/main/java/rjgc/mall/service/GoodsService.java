package rjgc.mall.service;

import com.github.pagehelper.PageInfo;
import rjgc.mall.entity.Goods;
import rjgc.mall.entity.vo.GoodsAndUserAndAction;

public interface GoodsService {
    public PageInfo<Goods> getGoodsList(Integer pageNum , Integer pageSize , String searchQuery);
    public void removeGoods(Integer goodsId);
    public void putawayGoods(Integer goodsId);
    public PageInfo<GoodsAndUserAndAction> viewGoodsList(Integer pageNum , Integer pageSize , Integer sellerId);
    Goods getGoodsBySellerId(Integer sellerId);
    Integer checkGoodsExists(Integer sellerId);
    void putawayGoods2(Integer goodsId);
    void removeGoods2(Integer goodsId);
}
