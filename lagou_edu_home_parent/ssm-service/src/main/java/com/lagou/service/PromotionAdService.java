package com.lagou.service;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.PromotionAd;
import com.lagou.domain.PromotionAdVO;

public interface PromotionAdService {


    /*
        分页查询广告信息
     */
    public PageInfo<PromotionAd> findAllPromotionAdByPage(PromotionAdVO promotionAdVO);

    /*
        广告动态上下线
     */
    public void updatePromotionAdStatus(int id, int status);

    /*
    添加广告信息
 */
    public void savePromotionAd(PromotionAd promotionAd);

    /*
        修改广告信息
     */
    public void updatePromotionAd(PromotionAd promotionAd);
    /*
        回显广告接口
     */
    PromotionAd findPromotionAdById(int id);
}
