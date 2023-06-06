package rjgc.mall.mapper;


import org.apache.ibatis.annotations.Mapper;
import rjgc.mall.entity.Goods;

import java.util.List;
@Mapper
public interface GoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);
    List<Goods> selectBysearchQuery(String searchQuery);
    int feezeGoods(Integer goodsId);
    int removeGoods(Integer goodsId);
    int putawatGoods(Integer goodsId);
    int findGoodsStatus(Integer goodsId);
    List<Goods> viewGoodsList(Integer sellerId);
    Goods getGoodsBySellerId(Integer sellerId);
    Integer checkGoodsExists(Integer sellerId);
    Integer checkgood();
    void chooseUser(Integer goods_id);
    List<Goods> selectGoods(Integer sellerId);
}
