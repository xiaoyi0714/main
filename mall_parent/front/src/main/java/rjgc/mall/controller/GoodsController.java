package rjgc.mall.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rjgc.mall.common.MessageConstant;
import rjgc.mall.common.Result;
import rjgc.mall.entity.Goods;
import rjgc.mall.service.GoodsService;
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    GoodsService goodsService;

    /**
     *商品展示
     * 获取商品列表
     * @param pageNum 当前要获取的页码
     * @param pageSize 每页的大小
     * @param searchQuery 查询内容
     * @return 分好页的商品
     */
    @RequestMapping("/getGoodsList")
    public Result getGoodsList(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize,
                               @RequestParam(defaultValue = "") String searchQuery){
        PageInfo<Goods> goodsPageInfo = goodsService.getGoodsList(pageNum,pageSize,searchQuery);
        return new Result(true,MessageConstant.GOODSLIST_GET_SUCCESS,goodsPageInfo);
    }
}
