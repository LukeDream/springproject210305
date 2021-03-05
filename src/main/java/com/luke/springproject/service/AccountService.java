package com.luke.springproject.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luke.springproject.dto.Respstat;
import com.luke.springproject.entity.Account;
import com.luke.springproject.entity.AccountExample;
import com.luke.springproject.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName AccountService
 * @Description TODO
 * @Author lulu
 * @Date 2020/11/19 19:59
 * @Version 1.0
 **/
@Service
public class AccountService {

    @Autowired
    AccountMapper accountMapper;

    public Account findByLoginNameAndPassword(String loginName, String password) {
        AccountExample example = new AccountExample();
        example.createCriteria().andLoginNameEqualTo(loginName)
                .andPasswordEqualTo(password);
        List<Account> list = accountMapper.selectByExample(example);

        return list.size() == 0?null:list.get(0);

    }

    public List<Account> findAll() {

        AccountExample example = new AccountExample();
        return accountMapper.selectByExample(example);
    }

    public PageInfo<Account> findByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        AccountExample example = new AccountExample();
        List<Account> accList= accountMapper.selectByExample(example);

        System.out.println("pageinfo"+new PageInfo<>(accList));
        return new PageInfo<>(accList,5);
    }

    public Respstat deleteById(int id) {
        int row = accountMapper.deleteByPrimaryKey(id);

        if(row==1){
            return Respstat.build(200);
        }else{
            return Respstat.build(500,"删除失败");
        }

    }

    public void update(Account account) {
        accountMapper.updateByPrimaryKeySelective(account);
    }
}
