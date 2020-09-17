package com.gg.controller;

import com.gg.beans.Subject;
import com.gg.service.SubjectService;
import com.gg.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
@RestController
@ResponseBody
@CrossOrigin
@RequestMapping("/teacher/subject")
public class SubjectController {
    @PersistenceContext
    EntityManager em;
    @Autowired
    SubjectService subjectService;

    @PostMapping("/upload")
    public CommonResult uploadExcel(MultipartFile file) {
        CommonResult commonResult = new CommonResult();
        List res = subjectService.saveExcel(file);
        commonResult.setData(res);
        return commonResult;
    }

    @GetMapping("/getlist")
        public CommonResult getList () {
            CommonResult commonResult = new CommonResult();
            commonResult.setData(subjectService.findSubjectList());
            return commonResult;
        }

}
