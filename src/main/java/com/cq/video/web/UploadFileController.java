package com.cq.video.web;

import com.cq.video.entity.FileInfo;
import com.cq.video.repository.FileInfoRepository;
import com.cq.video.utils.MD5Util;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class UploadFileController {

    @Value("${server.address}")
    private String serverAddress;

    @Value("${server.port}")
    private String serverPort;


    @Autowired
    FileInfoRepository fileInfoRepository;

    @RequestMapping(value = "/{serverName}/upload",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> upload(@RequestParam("file")MultipartFile file, @PathVariable("serverName") String serverName,
                                 @RequestParam(value = "autoDelete",defaultValue = "true") String autoDelete){
        FileInfo fileInfo= null;
        if(file.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("传入文件不能为空");
        }
        if (serverName == null || serverName.equals("")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("serverName不能为空");
        }
        try {
            String filename = file.getOriginalFilename();
            String caselsh = filename.substring(0,filename.lastIndexOf("."));
            String suffix  = filename.substring(filename.lastIndexOf(".")+1);
            FileInfo fi = new FileInfo(caselsh,file.getContentType(),file.getSize(),file.getBytes(),serverName,suffix);
            fi.setAutoDelete(autoDelete);
            fi.setSessionId(UUID.randomUUID().toString());
            fi.setMd5(MD5Util.getMD5(file.getInputStream()));
            fileInfo= fileInfoRepository.save(fi);
            String path = "http://"+serverAddress+":"+serverPort+"/file/"+serverName+"/"+fileInfo.getId();
            return ResponseEntity.status(HttpStatus.OK).body(path);
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping("/{serverName}/{id}")
    public ResponseEntity<Object> viewFile(@PathVariable String id, @PathVariable String serverName){
        Optional<FileInfo> file = fileInfoRepository.findByIdAndServerName(id,serverName);
        if(file.isPresent()){
            return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "fileName=\"" + file.get().getName() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, file.get().getContentType())
                    .header(HttpHeaders.CONTENT_LENGTH, file.get().getSize() + "").header("Connection", "close")
                    .body(file.get().getContent());
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not fount");
        }
    }
}
