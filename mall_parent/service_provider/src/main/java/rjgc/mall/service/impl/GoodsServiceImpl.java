package rjgc.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rjgc.mall.entity.Goods;
import rjgc.mall.entity.TradeAction;
import rjgc.mall.entity.User;
import rjgc.mall.entity.vo.GoodsAndUserAndAction;
import rjgc.mall.mapper.GoodsMapper;
import rjgc.mall.mapper.TradeActionMapper;
import rjgc.mall.mapper.UserMapper;
import rjgc.mall.service.GoodsService;
import rjgc.mall.service.exception.goods.GoodsRemoveFailException;
import rjgc.mall.service.exception.goods.PutawayFailException;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品服务
 */
@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private TradeActionMapper tradeActionMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 商品展示服务
     * 运用pagehelper(懒) 帮助分页
     *
     * @param pageNum     当前页
     * @param pageSize    每页的大小
     * @param searchQuery 查询内容
     * @return
     */
    @Override
    public PageInfo<Goods> getGoodsList(Integer pageNum, Integer pageSize, String searchQuery) {
        List<Goods> goodsList = goodsMapper.selectBysearchQuery(searchQuery);
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<Goods> goodsPageInfo = new PageInfo<>(goodsList);
        return goodsPageInfo;
    }

    /**
     * 用户交易成功后 商家移除商品
     * 1.先修改商品状态
     * 2.后修改交易的结果
     *
     * @param goodsId
     */
    @Override
    public void removeGoods(Integer goodsId) {
        int remove = goodsMapper.removeGoods(goodsId);
        if (remove == 0) {
            throw new GoodsRemoveFailException("下架商品时出现错误，数据库修改商品状态错误");
        }
        int action = tradeActionMapper.tradeSuccess(goodsId);
        if (action == 0) {
            throw new GoodsRemoveFailException("下架商品时出现错误，数据库修改交易结果状态错误");
        }
    }

    /**
     * 上架商品
     *
     * @param goodsId
     */
    @Override
    public void putawayGoods(Integer goodsId) {
        int putaway = goodsMapper.putawatGoods(goodsId);
        if (putaway == 0) {
            throw new PutawayFailException("上架商品失败，数据库提交数据失败");
        }
        int action = tradeActionMapper.tradeFail(goodsId);
        if (action == 0) {
            throw new PutawayFailException("上架商品时出现错误，数据库修改交易结果状态错误");
        }
    }

    @Override
    public PageInfo<GoodsAndUserAndAction> viewGoodsList(Integer pageNum, Integer pageSize, Integer sellerId) {
        List<Goods> goodsList = goodsMapper.viewGoodsList(sellerId);
        List<GoodsAndUserAndAction> result = new ArrayList<>();
        for (Goods good : goodsList) {
            GoodsAndUserAndAction g = new GoodsAndUserAndAction();
            g.setGoods(good);
            TradeAction tradeAction = tradeActionMapper.getSuccessTrade(good.getId());
            if (tradeAction != null) {
                g.setTradeAction(tradeAction);
                User user = userMapper.selectByPrimaryKey(tradeAction.getUserId());
                g.setUser(user);
            }
            result.add(g);
        }
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<GoodsAndUserAndAction> goodsPageInfo = new PageInfo<>(result);
        return goodsPageInfo;
    }

    @Override
    public Goods getGoodsBySellerId(Integer sellerId) {
        return null;
    }

    @Override
    public Integer checkGoodsExists(Integer sellerId) {
        Integer status = goodsMapper.checkGoodsExists(sellerId);
        return status;
    }

    @Override
    public void putawayGoods2(Integer goodsId) {
        int putaway = goodsMapper.putawatGoods(goodsId);
        if (putaway == 0) {
            throw new PutawayFailException("上架商品失败，数据库提交数据失败");
        }
    }

    @Override
    public void removeGoods2(Integer goodsId) {
        int remove = goodsMapper.removeGoods(goodsId);
        if (remove == 0) {
            throw new GoodsRemoveFailException("下架商品时出现错误，数据库修改商品状态错误");
        }
    }
}
