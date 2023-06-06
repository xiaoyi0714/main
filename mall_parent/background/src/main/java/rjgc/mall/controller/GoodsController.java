package rjgc.mall.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rjgc.mall.common.MessageConstant;
import rjgc.mall.common.MySessionContext;
import rjgc.mall.common.Result;
import rjgc.mall.entity.Goods;
import rjgc.mall.entity.Seller;
import rjgc.mall.entity.vo.GoodsAndUserAndAction;
import rjgc.mall.service.GoodsService;
import rjgc.mall.service.UserService;

import javax.servlet.http.HttpSession;

/**
 * 商家 商品管理
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
//    即将到死啊
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private UserService userService;

    /**
     * 商家确认交易成功后 下架商品
     * @param goodsId 商品id
     * @return
     */
    @RequestMapping("/removeGoods")
    public Result removeGoods(Integer goodsId) {
        goodsService.removeGoods(goodsId);
        return new Result(true, MessageConstant.GOODS_REMOVE_SUCCESS);
    }

    /**
     * 上架商品
     * @param goodsId
     * @return
     */
    @RequestMapping("/putawayGoods")
    public Result putawayGoods(Integer goodsId) {
        goodsService.putawayGoods(goodsId);
        return new Result(true, MessageConstant.GOODS_PUTAWAY_SUCCESS);
    }
    @RequestMapping("/viewGoods")
    public Result viewGoodsList (@RequestHeader("token") String token,
                                 @RequestParam(defaultValue = "1") Integer pageNum,
                                 @RequestParam(defaultValue = "10") Integer pageSize){
        MySessionContext myContext = MySessionContext.getInstance();
        HttpSession session = myContext.getSession(token);
        Seller seller=(Seller) session.getAttribute("seller");
        Integer sellerId = seller.getId();
        PageInfo<GoodsAndUserAndAction> goodsPageInfo= goodsService.viewGoodsList(pageNum,pageSize,sellerId);
        return new Result(true,MessageConstant.GOODS_VIEW_SUCCESS,goodsPageInfo);
    }

    /**
     * 根据用户id 查询正在交易或者上架的商品
     * @param token 请求头上携带的令牌 通过令牌来寻找登录商家的id
     * @return
     */
    @RequestMapping("/getGoodsBySellerId")
    public Result getGoodsBySellerId (@RequestHeader("token") String token){
        MySessionContext myContext = MySessionContext.getInstance();
        HttpSession session = myContext.getSession(token);
        Seller seller=(Seller) session.getAttribute("seller");
        Integer sellerId = seller.getId();
        return null;
    }
    @RequestMapping("/freezeGoods")
    public Result freezeGoods(Integer goodsId){
        userService.freezeGoods(goodsId);
        return new Result(true,"冻结商品成功");
    }

    @RequestMapping("/checkGoodsExists")
    public Result checkGoodsExists(@RequestHeader("token") String token){
        MySessionContext myContext = MySessionContext.getInstance();
        HttpSession session = myContext.getSession(token);
        Seller seller=(Seller) session.getAttribute("seller");
        Integer sellerId = seller.getId();
        Integer exists = goodsService.checkGoodsExists(sellerId);
        return new Result(true,MessageConstant.CHECK_EXISTS_SUCCESS,exists);
    }

    /**
     * 商家冻结商品后选择上架商品
     * @param goodsId
     * @return
     */
    @RequestMapping("/putawayGoods2")
    public Result putawayGoods2(Integer goodsId) {
        goodsService.putawayGoods2(goodsId);
        return new Result(true, MessageConstant.GOODS_PUTAWAY_SUCCESS);
    }

    /**
     * 商家冻结商品后选择 下架商品
     * @param goodsId 商品id
     * @return
     */
    @RequestMapping("/removeGoods2")
    public Result removeGoods2(Integer goodsId) {
        goodsService.removeGoods2(goodsId);
        return new Result(true, MessageConstant.GOODS_REMOVE_SUCCESS);
    }
}
