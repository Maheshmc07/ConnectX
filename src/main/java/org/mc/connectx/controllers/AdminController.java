package org.mc.connectx.controllers;


import com.opencsv.exceptions.CsvException;
import org.mc.connectx.DTO.ReportDTO;
import org.mc.connectx.DTO.UserBasicDetails;
import org.mc.connectx.Exception.AdminException;
import org.mc.connectx.Utils.ApiResponse;
import org.mc.connectx.service.AdminService;
import org.mc.connectx.service.PostService;
import org.mc.connectx.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("/v1/admin")
@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private ReportService reportService;
@Autowired
    PostService postService;


    @PostMapping("/adduser")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> addUser(@RequestBody UserBasicDetails user) throws AdminException {

        if(!user.getEmail().endsWith("@connectx.com")){
            throw new AdminException("Email address must end with '@socio.com'");
        }

        adminService.CreateUser(user);


return new ResponseEntity<>(ApiResponse.builder().message("New admin has been added").status(true).build(), HttpStatus.OK);
    }

    @PostMapping("/BulkAdd")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?>  importUser(@RequestParam("file")MultipartFile file) throws AdminException, IOException, CsvException {

        String filename = file.getOriginalFilename();
        if (filename.endsWith(".csv")) {

           adminService.importFromCSV(file);
        } else if (filename.endsWith(".xlsx")) {
            adminService.importFromExcel(file);
        } else {
           throw  new AdminException("Dear Admin this file is unsupported");
        }

        return  new ResponseEntity<>(ApiResponse.builder().message("ADMINS are ADDED").status(true).build(),HttpStatus.CREATED);
    }



    @GetMapping("/GetAllAvailableReports")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReportDTO>> getAllAvailableReports() throws AdminException {
       List<ReportDTO> reportDTOS= reportService.getAllReports();
       return  new ResponseEntity<>(reportDTOS,HttpStatus.OK);

    }

    @DeleteMapping("/admin/DeletePost/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deletePost(@PathVariable("id") Long id) {

        boolean flag=postService.DeletePost(id);

        return  new ResponseEntity<>(ApiResponse.builder().message(flag?"Post has been deleted By ADMIN ":"Something went wrong ").
                status(flag),HttpStatus.OK);

    }

}
