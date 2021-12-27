package com.lagou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.dao.UserMapper;
import com.lagou.domain.*;
import com.lagou.service.UserService;
import com.lagou.utils.Md5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public PageInfo findAllUserByPage(UserVo userVo) {
        PageHelper.startPage(userVo.getCurrentPage(),userVo.getPageSize());
        List<User> list = userMapper.findAllUserByPage(userVo);
        PageInfo<User> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public void updateUserStatus(User user) {
        user.setUpdate_time(new Date());
        userMapper.updateUserStatus(user);
    }

    /**
     * 用户登录
     */
    public User login(User user) throws Exception {

        //1.调用mapper方法 user2;
        User user2 = userMapper.login(user);

        if (user2!=null&& Md5.verify(user.getPassword(),"lagou",user2.getPassword())){
            return user2;
        }else {
            return null;
        }
    }

    @Override
    public List<Role> findUserRelationById(Integer id) {
        List<Role> userRelationById = userMapper.findUserRelationById(id);
        return userRelationById;
    }

    @Override
    public void userContextRole(UserVo userVo) {
     //1.根据用户ID清空中间表关联关系
        userMapper.deleteUserContextRole(userVo.getUserId());
     //2.在重新建立关联关系
        List<Integer> roleIdList = userVo.getRoleIdList();
        for (Integer roleId : roleIdList) {
            User_Role_relation user_role_relation = new User_Role_relation();
            user_role_relation.setUserId(userVo.getUserId());
            user_role_relation.setRoleId(roleId);
            Date date = new Date();
            user_role_relation.setCreatedTime(date);
            user_role_relation.setUpdatedTime(date);
            user_role_relation.setCreatedBy("system");
            user_role_relation.setUpdatedby("system");
            userMapper.userContextRole(user_role_relation);
        }

    }

    /**
     * 获取用户权限信息
     * @param userid
     * @return
     */
    @Override
    public ResponseResult getUserPermissions(Integer userid) {
        //1.获取当前用户拥有的角色
        List<Role> roleList = userMapper.findUserRelationById(userid);

        //2.获取角色ID ，保存到List集合中
        ArrayList<Integer> roleIds = new ArrayList<>();
        for (Role role : roleList) {
            roleIds.add(role.getId());
        }

        //3.根据角色ID查询父菜单
        List<Menu> parentMenu = userMapper.findParentMenuByRoleId(roleIds);
        //4.查询封装父菜单关联的子菜单
        for (Menu menu : parentMenu) {
            List<Menu> subMenu = userMapper.findSubMenuByPid(menu.getId());
            menu.setSubMenuList(subMenu);
        }
        //5.获取资源信息
        List<Resource> resourceByRoleId = userMapper.findResourceByRoleId(roleIds);
        //6.封装数据并返回
        HashMap<String, Object> map = new HashMap<>();
        map.put("menuList",parentMenu);
        map.put("resourceList",resourceByRoleId);
        ResponseResult result = new ResponseResult(true, 200, "动态分配菜单", map);
        return result;
    }
}
