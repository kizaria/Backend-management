package com.lys.upload.image;


import com.lys.utils.ResultUtils;
import com.lys.utils.ResultVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
@CrossOrigin
public class ImageUploadController {
    //属性注入
    @Value("${web.uploadpath}")
    private String webUploadpath;

    //使用RequestMapping方法主要是为了不受请求方法的限定
    @RequestMapping("/uploadImage")
    public ResultVo uploadImage(@RequestParam("file")MultipartFile file){
        System.out.println(file);
        String Url="";
        //获得文件名
        String fileName= file.getOriginalFilename();
        //获取拓展名
        String fileExtenionName= fileName.substring(fileName.lastIndexOf("."));

        //生成新的拓展名   UUID.randomUUID()用于创建唯一的标识符
        String newName = UUID.randomUUID().toString()+fileExtenionName;
        //上传路径
        String path=webUploadpath;
        File fileDir=new File(path);
        if(!fileDir.exists()){
            fileDir.mkdirs();
            //设置权限
            fileDir.setWritable(true);
        }
        File targetFile=new File(path,newName);
        try {
            //transferTo是复制file文件到指定位置(比如D盘下的某个位置),不然程序执行完,
            // 文件就会消失,程序运行时,临时存储在temp这个文件夹中
            file.transferTo(targetFile);
            //返回图片的路径给前端 targetFile.getName()为文件名
            Url="/"+targetFile.getName();
        } catch (Exception e) {
            return null;
        }
        return ResultUtils.success("上传成功","/images"+Url);
    }


}
