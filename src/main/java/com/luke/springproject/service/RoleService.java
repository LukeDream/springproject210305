package com.luke.springproject.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luke.springproject.entity.Role;
import com.luke.springproject.entity.RoleExample;
import com.luke.springproject.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName RoleService
 * @Description TODO
 * @Author lulu
 * @Date 2020/12/28 16:33
 * @Version 1.0
 **/
@Service
public class RoleService {
    @Autowired
    RoleMapper roleMapper;

    public PageInfo<Role> findByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        RoleExample example = new RoleExample();
        PageInfo<Role> pageInfo = new PageInfo<>(roleMapper.selectByExample(example));

        return pageInfo;
    }

    public Role findByid(int id) {

       return roleMapper.selectByPrimaryKey(id);
    }

    public void addPermission(int id, int[] permissions) {

//        for(int p:permissions){
//            roleMapper.addPermission(id,p);
//        }

        roleMapper.addPermissions(id,permissions);
    }

    public Role findById(int id) {
        return roleMapper.findById(id);
    }
}
