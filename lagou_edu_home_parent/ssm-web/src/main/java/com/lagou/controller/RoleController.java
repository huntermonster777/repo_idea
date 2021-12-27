package com.lagou.controller;

import com.lagou.domain.*;
import com.lagou.service.MenuService;
import com.lagou.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /*
        查询所有角色（条件）
     */
    @RequestMapping("/findAllRole")
    public ResponseResult findAllRole(@RequestBody Role role) {
        List<Role> allPole = roleService.findAllPole(role);
        ResponseResult result = new ResponseResult(true, 200, "查询所有角色成功", allPole);
        return result;
    }

    @Autowired
    private MenuService menuService;

    @RequestMapping("/findAllMenu")
    public ResponseResult findAllMenuListById() {
        //-1表示查询所有的父级菜单
        List<Menu> menuList = menuService.findSubMenuListByPid(-1);
        //响应数据
        HashMap<Object, Object> map = new HashMap<>();
        map.put("parentMenuList",menuList);
        return new ResponseResult(true, 200, "查询所有的父子级菜单", map);
    }
    /**
     * 根据角色id查询关联的菜单信息Id
     */
    @RequestMapping("/findMenuByRoleId")
    public ResponseResult findMenuByRoleId(Integer roleId){
        List<Integer> menuList = roleService.findMenuByRoleId(roleId);
        ResponseResult result = new ResponseResult(true, 200, "查询角色关联的菜单信息成功", menuList);
        return result;
    }

    /**
     * 为角色分配菜单
     */
    @RequestMapping("/RoleContextMenu")
    public ResponseResult roleContextMenu(@RequestBody RoleMenuVo roleMenuVo){
        roleService.roleContextMenu(roleMenuVo);
        return new ResponseResult(true, 200, "角色分配菜单成功", null);
    }
    /**
     *  删除角色
     */
    @RequestMapping("/deleteRole")
    public ResponseResult deleteRole(Integer id){
        roleService.deleteRole(id);
        ResponseResult result = new ResponseResult(true, 200, "删除角色成功", null);
        return result;
    }
}
