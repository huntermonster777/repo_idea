package com.lagou.service;

import com.lagou.domain.Course;
import com.lagou.domain.CourseLesson;
import com.lagou.domain.CourseSection;

import java.util.List;

public interface CourseContentService {
    /*
        根据课程ID查询对应的课程内容（章节+课时）
     */
    public List<CourseSection> findSectionAndLessonByCourseId(int courseId);

    /*
        回显章节对应的课程信息
     */
    public Course findCourseByCourseId(int courseId);


    /*
        添加章节信息
     */

    public void saveSection(CourseSection courseSection);

    /*
        更新章节信息
     */
    public void updateSection(CourseSection courseSection);

    /*
        更新章节状态
     */
    void updateSectionStatus(int id,int status);

    /*
        添加课时信息
     */
    void saveLesson(CourseLesson courseLesson);
}
