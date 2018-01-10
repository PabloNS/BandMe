package com.bandme.controller;

import com.bandme.service.StorageService;
import com.bandme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("file")
public class FileController {

    @Autowired
    StorageService storageService;

    @Autowired
    private UserService userService;

    List<String> files = new ArrayList<String>();

    // Multiple file upload
    @PostMapping("/upload")
    public String uploadFileMulti(
            @RequestParam("uploadfile") MultipartFile file) {

        try {
            storageService.store(file);
            files.add(file.getOriginalFilename());
            return "You successfully uploaded - " + file.getOriginalFilename();
        } catch (Exception e) {
            return "FAIL! Maybe You had uploaded the file before or the file's size > 500KB";
        }
    }

    @GetMapping("/all")
    public List<String> getListFiles() {
        List<String> lstFiles = new ArrayList<String>();

        try{
            lstFiles = files.stream()
                    .map(fileName -> MvcUriComponentsBuilder
                            .fromMethodName(FileController.class, "getFile", fileName).build().toString())
                    .collect(Collectors.toList());
        }catch(Exception e){
            throw e;
        }

        return lstFiles;
    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}
