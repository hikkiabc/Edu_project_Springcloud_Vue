package com.gg.service;

import com.gg.beans.Subject;
import com.gg.beans.SubjectWithChildren;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SubjectService {
    Integer save(Subject subject);

    List saveExcel(MultipartFile file);
    public List<SubjectWithChildren> findSubjectList();
}
