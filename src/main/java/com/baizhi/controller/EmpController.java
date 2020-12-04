package com.baizhi.controller;

import com.baizhi.entity.Emp;
import com.baizhi.service.EmpService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("emp")
@CrossOrigin
public class EmpController {
    @Autowired
    private EmpService empService;

    //上传的文件路径
    @Value("${photo.dir}")
    private String realPath;

    //查询所有员工信息
    @RequestMapping("findAll")
    public List<Emp> findAll(){
        return empService.findAll();
    }

    //添加员工信息
    @PostMapping("save")
    public Map<String,Object> save(Emp emp, MultipartFile photo) throws IOException {
        System.out.printf(emp.toString());
        System.out.printf(photo.getOriginalFilename());
        Map<String, Object> map = new HashMap<>();

        try {
            //为了避免图片名称重复，需要重新命名图片名称
            String newFileName = UUID.randomUUID().toString()+"."+ FilenameUtils.getExtension(photo.getOriginalFilename());
            //上传图片
            photo.transferTo(new File(realPath,newFileName));

            //设置头像地址
            emp.setPath(newFileName);
            //保存员工信息
            empService.save(emp);
            map.put("status",true);
            map.put("msg","保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",false);
            map.put("msg","保存失败");
        }

        return map;
    }
}
