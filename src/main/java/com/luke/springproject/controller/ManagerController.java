package com.luke.springproject.controller;

import com.github.pagehelper.PageInfo;
import com.luke.springproject.dto.Respstat;
import com.luke.springproject.entity.Account;
import com.luke.springproject.entity.Permission;
import com.luke.springproject.entity.Role;
import com.luke.springproject.service.AccountService;
import com.luke.springproject.service.PermissionService;
import com.luke.springproject.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sun.jvm.hotspot.debugger.Page;
import sun.net.ftp.FtpDirEntry;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
*@Author lulu
*@Description
*@Date 18:11 2020/12/10
*@Param
*@return
**/

@Controller
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    AccountService accountService;
    @Autowired
    PermissionService permissionService;
    @Autowired
    RoleService roleService;

    @RequestMapping("accountList")
    public String accountList(@RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "5") int pageSize,Model model){
        PageInfo<Account> page = accountService.findByPage(pageNum, pageSize);
        model.addAttribute("page", page);
        return "manager/accountList";
    }


    @RequestMapping("permissionList")
    public String permissionList(@RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue ="5") int pageSize,Model model){
        PageInfo<Permission> page = permissionService.findByPage(pageNum, pageSize);
        model.addAttribute("page", page);
        return "manager/permissionList";
    }

    @RequestMapping("roleList")
    public String roleList(@RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue ="5") int pageSize,Model model){
        PageInfo<Role> page = roleService.findByPage(pageNum, pageSize);
        model.addAttribute("page", page);
        return "manager/roleList";
    }

    @RequestMapping("rolePermission/{id}")
    public String rolePermission(@PathVariable int id, Model model){
        Role role = roleService.findById(id);

        List<Permission> pList = permissionService.findAll();
        model.addAttribute("pList",pList);
        model.addAttribute("role",role);
        return "manager/rolePermission";
    }

    @RequestMapping("permissionModify")
    public String permissionModify(@RequestParam int id,Model model){

        Permission permission = permissionService.findById(id);
        model.addAttribute("permission", permission);

        return "manager/permissionModify";
    }

    @RequestMapping("permissionAdd")
    public String permissionAdd(Model model){

        return "manager/permissionModify";
    }


}
