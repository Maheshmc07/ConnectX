package org.mc.connectx.controllers;


import com.opencsv.exceptions.CsvException;
import org.mc.connectx.DTO.UserBasicDetails;
import org.mc.connectx.Exception.AdminException;
import org.mc.connectx.Utils.ApiResponse;
import org.mc.connectx.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/v1/admin")
@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;


    @PostMapping("/adduser")
    public ResponseEntity<ApiResponse> addUser(@RequestBody UserBasicDetails user) throws AdminException {

        if(!user.getEmail().endsWith("@connectx.com")){
            throw new AdminException("Email address must end with '@socio.com'");
        }

        adminService.CreateUser(user);


return new ResponseEntity<>(ApiResponse.builder().message("New admin has been added").status(true).build(), HttpStatus.OK);
    }

    @PostMapping("/BulkAdd")
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


}
