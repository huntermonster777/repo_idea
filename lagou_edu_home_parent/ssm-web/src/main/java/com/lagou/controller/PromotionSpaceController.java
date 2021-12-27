package com.lagou.controller;

import com.lagou.domain.PromotionSpace;
import com.lagou.domain.ResponseResult;
import com.lagou.service.PromotionSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/PromotionSpace")
public class PromotionSpaceController {

    @Autowired
    private PromotionSpaceService promotionSpaceService;

    /*
        查询广告位列表
     */
    @RequestMapping("/findAllPromotionSpace")
    public ResponseResult findAllPromotionSpace() {
        List<PromotionSpace> allPromotionSpace = promotionSpaceService.findAllPromotionSpace();
        ResponseResult result = new ResponseResult(true, 200, "查询所有广告位成功", allPromotionSpace);
        return result;
    }
    /*
        添加以及更新广告位名称
     */
    @RequestMapping("/saveOrUpdatePromotionSpace")
    public ResponseResult saveOrUpdatePromotionSpace(@RequestBody PromotionSpace promotionSpace){
        if (promotionSpace.getId()==null){
            promotionSpaceService.savePromotionSpace(promotionSpace);
            return new ResponseResult(true, 200, "添加广告位成功", null);
        }else {
            promotionSpaceService.updatePromotionSpace(promotionSpace);
            return new ResponseResult(true,200,"更新广告位成功",null);
        }

    }

    /*
        根据ID查询广告位
     */
    @RequestMapping("/findPromotionSpaceById")
    public ResponseResult findPromotionSpaceById(int id){
        PromotionSpace promotionSpaceById = promotionSpaceService.findPromotionSpaceById(id);
        ResponseResult result = new ResponseResult(true, 200, "回显广告位成功", promotionSpaceById);
        return  result;
    }
}
