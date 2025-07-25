package org.mc.connectx.service;

import jakarta.transaction.Transactional;
import org.mc.connectx.AllEnums.ReportType;
import org.mc.connectx.DTO.ReportDTO;
import org.mc.connectx.Entities.Post;
import org.mc.connectx.Entities.ReportEnity;
import org.mc.connectx.Entities.User;
import org.mc.connectx.Exception.AdminException;
import org.mc.connectx.Repositories.Postrepo;
import org.mc.connectx.Repositories.ReportRepo;
import org.mc.connectx.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.mc.connectx.DTO.MappersDTO.ReportMapper.ReportEntityToReportDTO;

@Service
@Transactional
public class ReportService  {


    @Autowired
    private ReportRepo  reportRepo;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private Postrepo postrepo;
@Transactional
    public void reportapost(Long  id, User user, ReportDTO reportDTO) {
        Post post =postrepo.findPostById(id);
        ReportEnity reportEnity = new ReportEnity();
        reportEnity.setReporter(user);
        reportEnity.setPost(post);
        reportEnity.setReportType(ReportType.valueOf(reportDTO.getReportType()));
        reportRepo.save(reportEnity);


        return;


    }

    public List<ReportDTO> getAllReports(int pageno,int pagesize) throws AdminException {
       Page<ReportEnity> reportEnityList= reportRepo.findAll(PageRequest.of(pageno, pagesize, Sort.by("reportedAt").ascending()));
       List<ReportDTO> reportDTOList=new ArrayList<>();
       for(ReportEnity reportEnity:reportEnityList){
           reportDTOList.add(ReportEntityToReportDTO(reportEnity));
       }

       return reportDTOList;

    }
}
