package com.luke.springproject.controller;

import com.github.pagehelper.PageInfo;
import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.luke.springproject.dto.Respstat;
import com.luke.springproject.entity.Account;
import com.luke.springproject.service.AccountService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Autowired
    FastFileStorageClient fc;

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
        // 元数据
        Set<MetaData> metaDataSet = new HashSet<MetaData>();
        metaDataSet.add(new MetaData("Author", "yimingge"));
        metaDataSet.add(new MetaData("CreateDate", "2016-01-05"));
        try {

//            File path = new File(ResourceUtils.getURL("classpath:").getPath());
//            File upload = new File(path.getAbsolutePath(),"static/uploads");

//            File path = new File(ResourceUtils.getURL("classpath:").getPath());
//            File upload = new File(path.getAbsolutePath(),"static/uploads");
//            System.out.println("upload:" + upload);
//
//            //文件转存
//            filename.transferTo(new File("/users/lulu/Documents/uploads/"+"/"+filename.getOriginalFilename()));

            StorePath uploadFile = null;
//            uploadFile = fc.uploadFile(filename.getInputStream(), filename.getSize(), FilenameUtils.getExtension(filename.getOriginalFilename()), metaDataSet);
            uploadFile = fc.uploadImageAndCrtThumbImage(filename.getInputStream(),filename.getSize(), FilenameUtils.getExtension(filename.getOriginalFilename()),metaDataSet);


            account.setPassword(password);
            account.setLocation(uploadFile.getPath());
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


    @RequestMapping("/down")
    @ResponseBody
    public ResponseEntity<byte[]> down(){
        DownloadByteArray cb = new DownloadByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment","aaa.xx");

        byte[] bs = fc.downloadFile("group1","M00/00/00/CtM3A2BmvJ-AQ5n6AAKhLNdnGro86.jpeg",cb);

        return  new ResponseEntity<>(bs,headers, HttpStatus.OK);
    }
}
