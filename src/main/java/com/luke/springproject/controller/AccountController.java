package com.luke.springproject.controller;

import com.github.pagehelper.PageInfo;
import com.luke.springproject.dto.Respstat;
import com.luke.springproject.entity.Account;
import com.luke.springproject.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName AccountController
 * @Description 用户账号相关
 * @Author lulu
 * @Date 2020/11/19 19:09
 * @Version 1.0
 **/

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountService accountSrv;

    @RequestMapping("login")
    public String login(){
        return "account/login";
    }

    /**
    *@Author lulu
    *@Description 
    *@Date 19:57 2020/11/19
    *@Param [loginName, password]
    *@return java.lang.String
    **/
    @RequestMapping("validataAccount")
    @ResponseBody
    public String validateAccount(String loginName, String password, HttpServletRequest request){
        System.out.println("loginName"+loginName);
        System.out.println("password"+password);

        Account account =accountSrv.findByLoginNameAndPassword(loginName, password);
        if(account == null){
            return "登录失败";
        }else{
            request.getSession().setAttribute("account",account);
            return "success";
        }

    }

    @RequestMapping("/logOut")
    public String logOut(HttpServletRequest request){

        request.getSession().removeAttribute("account");
        return "index";
    }

    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "2") int pageSize , Model model){

        PageInfo<Account> page = accountSrv.findByPage(pageNum,pageSize);
        model.addAttribute("page", page);
        return "account/list";
    }

    @RequestMapping("/deleteById")
    @ResponseBody
    public Respstat deleteById(int id){

        Respstat stat = accountSrv.deleteById(id);
        return stat;
    }

    @RequestMapping("/profile")
    public String profile () {

        try {
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            File upload = new File(path.getAbsolutePath(), "/Users/lulu/Documents/uploads/");
            System.out.println(upload.getAbsolutePath());
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        };
        return "account/profile";
    }


    /**
     * 中文字符
     * @param filename
     * @param password
     * @return
     */
    @RequestMapping("/fileUploadController")
    public String fileUpload (MultipartFile filename, String password,HttpServletRequest httpServletRequest) {
        Account account =(Account) httpServletRequest.getSession().getAttribute("account");
        System.out.println("password:" + password);
        System.out.println("file:" + filename.getOriginalFilename());
        try {

//            File path = new File(ResourceUtils.getURL("classpath:").getPath());
//            File upload = new File(path.getAbsolutePath(),"static/uploads");

            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            File upload = new File(path.getAbsolutePath(),"static/uploads");
            System.out.println("upload:" + upload);

            //文件转存
            filename.transferTo(new File("/users/lulu/Documents/uploads/"+"/"+filename.getOriginalFilename()));

            account.setPassword(password);
            account.setLocation(filename.getOriginalFilename());
            accountSrv.update(account);

        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "account/profile";
    }
}
