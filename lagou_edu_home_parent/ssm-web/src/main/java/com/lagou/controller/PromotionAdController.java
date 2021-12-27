package com.lagou.controller;

import com.github.pagehelper.PageInfo;
import com.lagou.dao.PromotionAdMapper;
import com.lagou.domain.PromotionAd;
import com.lagou.domain.PromotionAdVO;
import com.lagou.domain.ResponseResult;
import com.lagou.service.PromotionAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/PromotionAd")
public class PromotionAdController {
    @Autowired
    private PromotionAdService promotionAdService;
    /*
        广告分页查询
     */
    @RequestMapping("/findAllPromotionAdByPage")
    public ResponseResult findAllAdByPage(PromotionAdVO promotionAdVO){

        PageInfo<PromotionAd> pageInfo = promotionAdService.findAllPromotionAdByPage(promotionAdVO);

        return new ResponseResult(true, 200, "广告分页查询成功", pageInfo);
    }

    /*
    图片上传
     */
    @RequestMapping("/PromotionAdUpload")
    public ResponseResult fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        //1.判断接收到的上传文件是否为空
        if (file.isEmpty()) {
            throw new RuntimeException();
        }

        //2.获取项目部署路径
        //D:\apache-tomcat-8.5.56\webapps\ssm-web\
        String realPath = request.getServletContext().getRealPath("/");
        //D:\apache-tomcat-8.5.56\webapps\
        String substring = realPath.substring(0, realPath.indexOf("ssm-web"));

        //3.获取原文件名
        //lagou.jpg
        String originalFilename = file.getOriginalFilename();

        //4.生成新文件名
        //123123123.jpg
        String newFileName = System.currentTimeMillis() + originalFilename.substring(originalFilename.lastIndexOf("."));

        //5.文件上传
        //
        String uploadPath = substring + "upload/";
        File filePath = new File(uploadPath, newFileName);
        //如果目录不存在就创建目录
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
            System.out.println("创建目录：" + filePath);
        }
        //图片就进行了真正的上传
        file.transferTo(filePath);
        //6.将文件名和文件路径返回，进行响应
        HashMap<String, String> map = new HashMap<>();
        map.put("fileName", newFileName);
        map.put("filePath", "http://localhost:8080/upload/" + newFileName);
        ResponseResult result = new ResponseResult(true, 200, "图片上传成功", map);
        return result;
    }

    /**
     *  广告动态上下线
     */
    @RequestMapping("/updatePromotionAdStatus")
    public ResponseResult updatePromotionStatus(Integer id,Integer status){
        promotionAdService.updatePromotionAdStatus(id,status);
        ResponseResult result = new ResponseResult(true, 200, "广告动态上下线成功", null);
        return  result;
    }

    /*
        广告信息的修改和添加
     */
    @RequestMapping("/saveOrUpdatePromotionAd")
    public ResponseResult createOrUpdatePromotionAd(@RequestBody PromotionAd promotionAd){
        if (promotionAd.getId()==null){
            promotionAdService.savePromotionAd(promotionAd);
            ResponseResult result = new ResponseResult(true, 200, "添加广告成功", null);
            return  result;
        }else {
            promotionAdService.updatePromotionAd(promotionAd);
            ResponseResult result = new ResponseResult(true, 200, "修改广告成功", null);
            return  result;
        }
    }

    /*
    回显广告信息
     */
    @RequestMapping("/findPromotionAdById")
    public ResponseResult findPromotionAdById(int id){
        PromotionAd promotionAd = promotionAdService.findPromotionAdById(id);
        return new ResponseResult(true, 200, "回显广告成功", promotionAd);
    }
}
