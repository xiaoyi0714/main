package rjgc.mall.mapper;

import org.apache.ibatis.annotations.Mapper;
import rjgc.mall.entity.TradeAction;

import java.util.List;

@Mapper
public interface TradeActionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TradeAction record);

    int insertSelective(TradeAction record);

    TradeAction selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TradeAction record);

    int updateByPrimaryKey(TradeAction record);

    int tradeSuccess(Integer goodsId);

    int tradeFail(Integer goodsId);

    Integer trading(Integer id);

    Integer getStatus(Integer id);

    int getUserId(Integer id);

    int getGoodsId(Integer id);

    Integer getGoodsId2(Integer gooosId);
    TradeAction getActopm(Integer gooosId);
    Integer getActionId(Integer gooosId);
    List<TradeAction> intendingUser(Integer goods_id);
    TradeAction getSuccessTrade(Integer goodsId);
    Integer getTradecount(Integer goodsId);
}
