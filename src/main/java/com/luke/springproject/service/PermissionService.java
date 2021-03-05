package com.luke.springproject.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luke.springproject.entity.Permission;
import com.luke.springproject.entity.PermissionExample;
import com.luke.springproject.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName PermissionService
 * @Description TODO
 * @Author lulu
 * @Date 2020/12/28 16:33
 * @Version 1.0
 **/
@Service
public class PermissionService {
    @Autowired
    PermissionMapper pMapper;
    public PageInfo<Permission> findByPage(int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        PermissionExample example = new PermissionExample();
        List<Permission> list = pMapper.selectByExample(example);
        return new PageInfo<>(list);
    }

    public Permission findById(int id) {
        return pMapper.selectByPrimaryKey(id);
    }

    public void update(Permission permission) {
        PermissionExample  example = new PermissionExample();
        pMapper.updateByPrimaryKeySelective(permission);
    }

    public void add(Permission permission) {
        PermissionExample  example = new PermissionExample();
        pMapper.insert(permission);
    }

    public List<Permission> findAll() {

        PermissionExample permissionExample = new PermissionExample();
        return pMapper.selectByExample(permissionExample);
    }
}
