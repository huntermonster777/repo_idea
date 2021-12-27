package com.lagou.service;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.ResponseResult;
import com.lagou.domain.Role;
import com.lagou.domain.User;
import com.lagou.domain.UserVo;

import java.util.List;

public interface UserService {
    /*
        用户分页&多条件查询
     */
    public PageInfo findAllUserByPage(UserVo userVo);


    /*
    用户状态修改
 */
    public void updateUserStatus(User user);

    /**
     * 用户登录
     */
    User login(User user) throws Exception;
    /**
     * 分配角色（回显）
     */
    public List<Role> findUserRelationById(Integer id);
    /**
     * 用户关联角色
     */
    public void userContextRole(UserVo userVo);
    /**
     * 获取用户权限，进行菜单动态展示
     */
    public ResponseResult getUserPermissions(Integer userid);
}
