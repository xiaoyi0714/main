package rjgc.mall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rjgc.mall.entity.TradeAction;
import rjgc.mall.entity.User;
import rjgc.mall.entity.param.OrderInfoParam;
import rjgc.mall.mapper.GoodsMapper;
import rjgc.mall.mapper.OrderUpdateMapper;
import rjgc.mall.mapper.TradeActionMapper;
import rjgc.mall.mapper.UserMapper;
import rjgc.mall.service.UserService;
import rjgc.mall.service.exception.user.GoodsBuyFailException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TradeActionMapper tradeActionMapper;
    @Autowired
    private OrderUpdateMapper orderUpdateMapper;

    /**
     * 用户购买商品 并填写用户信息 购买完成后（即开始交易）冻结商品
     * 购买开发版本1（被舍弃了）
     * @param user
     * @param tradeTime
     * @param tradePlace
     * @param goodsId
     */
    @Override
    public void buy(User user,String tradeTime, String tradePlace,Integer goodsId){
        int status = goodsMapper.findGoodsStatus(goodsId);
        if(status != 0){
            throw new GoodsBuyFailException("商品状态不在正常情况中，不能购买");
        }
        userMapper.insertUser(user);
        TradeAction tradeAction = new TradeAction();
        tradeAction.setUserId(user.getId());
        tradeAction.setBuyTime(new Date());
        tradeAction.setTradeTime(tradeTime);
        tradeAction.setResult(2);
        tradeAction.setTradePlace(tradePlace);
        tradeAction.setGoodsId(goodsId);
        tradeActionMapper.insert(tradeAction);
        freezeGoods(goodsId);
    }

    /**
     * 冻结商品
     * @param goodsId
     */
    public void freezeGoods(Integer goodsId){
        goodsMapper.feezeGoods(goodsId);
    }

    @Override
    public void updateOrderInfo(OrderInfoParam orderInfoParam) {
        System.out.println(orderInfoParam.getOrderId());
        Integer status = tradeActionMapper.getStatus(orderInfoParam.getOrderId());
        System.out.println(status);
        if(status == 3){
            TradeAction tradeAction = new TradeAction();
            tradeAction.setId(orderInfoParam.getOrderId());
            tradeAction.setTradeTime(orderInfoParam.getTradeTime());
            tradeAction.setTradePlace(orderInfoParam.getTradePlace());
            tradeActionMapper.updateByPrimaryKeySelective(tradeAction);
            User user = new User();
            user.setId(orderInfoParam.getUserId());
            user.setName(orderInfoParam.getName());
            user.setTele(orderInfoParam.getTele());
            userMapper.updateByPrimaryKeySelective(user);
        }else {
            orderUpdateMapper.insert(orderInfoParam);
        }
    }

    /**
     * 此方法是根据功能需求修改的购买功能第二版 购买后不直接冻结商品
     * @param user
     * @param tradeTime
     * @param tradePlace
     * @param goodsId
     */
    @Override
    public Map<String,Integer> buy2(User user, String tradeTime, String tradePlace, Integer goodsId) {
        Map<String,Integer> map = new HashMap<>();
        int status = goodsMapper.findGoodsStatus(goodsId);
        if(status == 1 || status == 2){
            throw new GoodsBuyFailException("商品状态不在正常情况中，不能购买");
        }
        Integer userId = userMapper.insertUser(user);
        TradeAction tradeAction = new TradeAction();
        tradeAction.setUserId(user.getId());
        tradeAction.setBuyTime(new Date());
        tradeAction.setTradeTime(tradeTime);
        tradeAction.setResult(3);
        tradeAction.setTradePlace(tradePlace);
        tradeAction.setGoodsId(goodsId);
        tradeActionMapper.insert(tradeAction);
        map.put("userId",userId);
        map.put("orderId",tradeAction.getId());
        return map;
    }
}
