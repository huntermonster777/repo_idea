package com.lagou.controller;

import com.lagou.domain.Course;
import com.lagou.domain.CourseLesson;
import com.lagou.domain.CourseSection;
import com.lagou.domain.ResponseResult;
import com.lagou.service.CourseContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/courseContent")
public class CourseContentController {
    @Autowired
    private CourseContentService courseContentService;

    @RequestMapping("/findSectionAndLesson")
    public ResponseResult findSectionAndLessonById(@RequestParam int courseId){
        try {
            //调用service
            List<CourseSection> sectionList =courseContentService.findSectionAndLessonByCourseId(courseId);
            //封装数据并返回
            ResponseResult result = new ResponseResult(true,200,"响应成功",sectionList);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
        回显章节对应的课程信息
     */
    @RequestMapping("/findCourseByCourseId")
    public ResponseResult findCourseByCourseId(Integer courseId){
        Course course = courseContentService.findCourseByCourseId(courseId);
        ResponseResult result = new ResponseResult(true, 200, "查询课程信息成功", course);
        return result;
    }
    /*
        新增以及更新章节信息
     */
    @RequestMapping("/saveOrUpdateSection")
    public ResponseResult saveOrUpdateSection(@RequestBody CourseSection courseSection){
        //判断是否携带了章节id
        if (courseSection.getId()==null){
            //新增
            courseContentService.saveSection(courseSection);
            ResponseResult result = new ResponseResult(true, 200, "保存章节成功", null);
            return result;
        }else {
            //更新
            courseContentService.updateSection(courseSection);
            ResponseResult result = new ResponseResult(true, 200, "更新章节成功", null);
            return result;
        }
    }

    /*
        更新章节状态
     */
    @RequestMapping("/updateSectionStatus")
    public ResponseResult updateSectionStatus(@RequestParam int id,@RequestParam int status){
        courseContentService.updateSectionStatus(id,status);
        HashMap<String, Object> map = new HashMap<>();
        map.put("status",status);
        ResponseResult result = new ResponseResult(true, 200, "修改章节状态成功", map);
        return result;
    }

    /*
        新建课时信息
     */
    @RequestMapping("/saveLesson")
    public ResponseResult saveLesson(@RequestBody CourseLesson courseLesson){
        courseContentService.saveLesson(courseLesson);
        ResponseResult result = new ResponseResult(true, 200, "新建课时成功", null);
        return  result;
    }
}
