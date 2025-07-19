package org.mc.connectx.DTO.MappersDTO;


import lombok.Builder;
import lombok.Data;
import org.mc.connectx.DTO.ReportDTO;
import org.mc.connectx.Entities.ReportEnity;

import static org.mc.connectx.DTO.MappersDTO.PostMapper.convertPostToPostDTO;
import static org.mc.connectx.DTO.MappersDTO.UserDToMapper.toUser2DTO;

@Data
@Builder
public class ReportMapper {

    public static  ReportDTO ReportEntityToReportDTO(ReportEnity reportEnity){
       ReportDTO reportDTO=ReportDTO.builder()
               .reportAt(reportEnity.getReportedAt())
               .userDTO(toUser2DTO(reportEnity.getReporter()))
               .reportType(String.valueOf(reportEnity.getReportType()))
               .id(reportEnity.getId())
               .postDTO(convertPostToPostDTO(reportEnity.getPost()))
                       .build();


        return  reportDTO;

    }
}
