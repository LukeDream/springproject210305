package com.luke.springproject.controller.rest;

import com.luke.springproject.dto.Respstat;
import com.luke.springproject.entity.Permission;
import com.luke.springproject.service.PermissionService;
import com.luke.springproject.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ManagerRestController
 * @Description restful风格的URI的controller，只和用户交换json数据
 * @Author lulu
 * @Date 2021/1/7 15:13
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api/v1/manager/role")
public class RoleManagerRestController {

    @Autowired
    PermissionService permissionService;

    @Autowired
    RoleService roleService;

    @RequestMapping("update")
    public Respstat update (@RequestBody Permission permission){
        permissionService.update(permission);
        return Respstat.build(200);
    }

    @RequestMapping("/permission/add")
    public Respstat add (@RequestParam int[]  permissions,@RequestParam int id){

        roleService.addPermission(id,permissions);
        return Respstat.build(200);
    }

}
