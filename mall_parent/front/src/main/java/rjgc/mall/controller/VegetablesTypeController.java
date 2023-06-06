package rjgc.mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rjgc.mall.common.MessageConstant;
import rjgc.mall.common.Result;
import rjgc.mall.service.VegetablesTypeService;

@RestController
@RequestMapping("/vegetablesType")
public class VegetablesTypeController {
    @Autowired
    private VegetablesTypeService vegetablesTypeService;
    @RequestMapping("/getAllType")
    public Result getAllType(){
        return new Result(true, MessageConstant.GET_TYPE_SUCCESS,vegetablesTypeService.getAllType());
    }
}
