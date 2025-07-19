package org.mc.connectx.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mc.connectx.DTO.UserBasicDetails;
import org.mc.connectx.Entities.User;
import org.mc.connectx.Exception.AdminException;
import org.mc.connectx.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;

@Service
public class AdminService {

    private UserRepo userRepo;
    public AdminService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void CreateUser(UserBasicDetails userBasicDetails) {
        User user = User.builder()
                .fullName(userBasicDetails.getFullName())
                .username(userBasicDetails.getUsername())
                .password(passwordEncoder.encode(userBasicDetails.getPassword()))
                .role("ADMIN")
                .bio(userBasicDetails.getBio())
                .phoneNo(userBasicDetails.getPhoneNo())
                .email(userBasicDetails.getEmail())
                .build();
        userRepo.save(user);
        return;// ✅ Correct, passing entity
    }

    public void importFromCSV(MultipartFile file) throws IOException, CsvException {


        try (Reader reader = new InputStreamReader(file.getInputStream())) {
            CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
            List<String[]> records = csvReader.readAll();

            for (int i = 0; i < records.size(); i++) {
                String[] row = records.get(i);

                    UserBasicDetails newadmin=UserBasicDetails.builder().
                            fullName(row[0]).
                            username(row[1]).
                            password(row[3]).
                            email(row[2]).
                            bio(row[5]).
                            phoneNo(row[4]).
                            build();


                    if(!newadmin.getEmail().endsWith("@connectx.com")){
                        throw  new AdminException("Email address must end with '@connectx.com"+"error at "+newadmin.username);
                    }

                    CreateUser(newadmin);


            }
        } catch (AdminException e) {
            throw new RuntimeException(e);
        }

        return;
    }




    public void importFromExcel(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0); // First sheet
            Iterator<Row> rows = sheet.iterator();
            rows.next(); // Skip header

            while (rows.hasNext()) {
                Row row = rows.next();

                UserBasicDetails newadmin = UserBasicDetails.builder()
                        .fullName(getString(row.getCell(0)))
                        .username(getString(row.getCell(1)))
                        .email(getString(row.getCell(2)))
                        .password(getString(row.getCell(3)))
                        .phoneNo(getString(row.getCell(4)))
                        .bio(getString(row.getCell(5)))
                        .build();

                if (!newadmin.getEmail().endsWith("@connectx.com")) {
                    throw new AdminException("Email must end with @connectx.com — error at: " + newadmin.getUsername());
                }

                CreateUser(newadmin); // Your method to save the user
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to import from Excel: " + e.getMessage(), e);
        }
    }

    // Helper method to get string value safely
    private String getString(Cell cell) {
        if (cell == null) return "";
        if (cell.getCellType() == CellType.STRING) return cell.getStringCellValue().trim();
        if (cell.getCellType() == CellType.NUMERIC) return String.valueOf((long) cell.getNumericCellValue());
        return cell.toString().trim();
    }


}
