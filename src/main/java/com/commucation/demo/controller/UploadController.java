package com.commucation.demo.controller;

import com.commucation.demo.common.lang.Result;
import com.commucation.demo.mapper.GroupMapper;
import com.commucation.demo.mapper.UserMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/upload")
public class UploadController {
    @Resource
    private UserMapper userMapper;
    @Resource
    private GroupMapper groupMapper;

    @PostMapping("/head/{identity_no}")
    public Result uploadHead(HttpServletRequest request, MultipartFile file, @PathVariable(name = "identity_no") String identity_no) {
        try {
            //生成uuid
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //得到上传时的文件名
            String filename = file.getOriginalFilename();
            //获取当前时间
            LocalDateTime localDateTime = LocalDateTime.now();
            //上传目录地址
            String property = System.getProperty("user.dir");
            String uploadDir = property + "/src/main/resources/static/upload/head";

            //如果目录不存在，自动创建文件夹
            File dir = new File(uploadDir);
            if(!dir.exists()){
                dir.mkdir();
            }

            String pathname = uploadDir+"\\"+ uuid + localDateTime.toString().replace(".", "-").replace(":", "-") + filename;
            File serverFile = new File(pathname);
            file.transferTo(serverFile);

            userMapper.changeHead(identity_no, pathname);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("上传失败");
        }
        return Result.success("上传成功");
    }

    @PostMapping("/groupHead/{group_id}")
    public Result uploadGroupHead(HttpServletRequest request, MultipartFile file, @PathVariable(name = "group_id") Integer group_id) {
        try {
            //生成uuid
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //得到上传时的文件名
            String filename = file.getOriginalFilename();
            //获取当前时间
            LocalDateTime localDateTime = LocalDateTime.now();
            //上传目录地址
            String property = System.getProperty("user.dir");
            String uploadDir = property + "/src/main/resources/static/upload/groupHead";

            //如果目录不存在，自动创建文件夹
            File dir = new File(uploadDir);
            if(!dir.exists()){
                dir.mkdir();
            }

            String pathname = uploadDir+"\\"+ uuid + localDateTime.toString().replace(".", "-").replace(":", "-") + filename;
            File serverFile = new File(pathname);
            file.transferTo(serverFile);

            groupMapper.changHead(group_id, pathname);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("上传失败");
        }
        return Result.success("上传成功");
    }
}
