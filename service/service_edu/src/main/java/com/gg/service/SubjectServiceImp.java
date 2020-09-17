package com.gg.service;

import com.gg.beans.Subject;
import com.gg.beans.SubjectWithChildren;
import com.gg.mapper.SubjectMapper;
import com.gg.utils.ExcelUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Transactional
@Service
public class SubjectServiceImp implements SubjectService {
    @Autowired
    SubjectMapper subjectMapper;
    @PersistenceContext
    EntityManager em;

    Integer BATCH_SIZE = 500;

    @Override
    public Integer save(Subject subject) {
        return null;
    }

    @Override
    public List saveExcel(MultipartFile file) {
        Map<String, Object> excelData = ExcelUtil.getExcelData(file, 0);
        ArrayList<Subject> subjectList = new ArrayList<>();
        List<Map> beans = (List) excelData.get("beans");
        for (Map bean : beans) {
            String lvl1Name = (String) bean.get("lvl1");
            Subject subject = subjectMapper.findByTitleAndPid(lvl1Name, 0);

            if (subject == null) {
                subject = new Subject();
                subject.setPid(0);
                subject.setTitle(lvl1Name);
                subjectMapper.save(subject);
                subjectList.add(subject);
            }
            String lvl2Name = (String) bean.get("lvl2");
            Subject subject2 = subjectMapper.findByTitleAndPid(lvl2Name, subject.getId());
            if (subject2 == null) {
                Subject subject1 = new Subject();
                subject1.setTitle(lvl2Name);
                subject1.setPid(subject.getId());
                Subject subject3 = subjectMapper.save(subject1);
                subjectList.add(subject3);
            }

        }
        
        return subjectList;
    }

    public List batchSave(List var1) {
        Iterator iterator = var1.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            em.persist(iterator.next());
            index++;
            if (index % BATCH_SIZE == 0) {
                em.flush();
                em.clear();
            }
        }
        if (index % BATCH_SIZE != 0) {
            em.flush();
            em.clear();
        }
        return var1;
    }

    public List<SubjectWithChildren> findSubjectList(){
        List<Subject> subjectList = subjectMapper.findByPid(0);
        List<Subject> all2Subjects = subjectMapper.findAll2Subjects();
        List<SubjectWithChildren> list = new ArrayList<>();
        for (Subject subject : subjectList) {
            SubjectWithChildren subjectWithChildren = new SubjectWithChildren();
            BeanUtils.copyProperties(subject,subjectWithChildren);
//            subjectWithChildren.setId(subject.getId());
//            subjectWithChildren.setPid(subject.getPid());
//            subjectWithChildren.setTitle(subject.getTitle());
            list.add(subjectWithChildren);
            for (Subject lvl2 : all2Subjects) {
                if (lvl2.getPid()==subject.getId()){
                    SubjectWithChildren subjectWithChildren1 = new SubjectWithChildren();
                    BeanUtils.copyProperties(lvl2,subjectWithChildren1);
                    subjectWithChildren1.setChildren(null);
                    subjectWithChildren.getChildren().add(subjectWithChildren1);

                }
            }
        }
        return list;
    }
}
