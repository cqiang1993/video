package com.cq.video.web;

import com.cq.video.entity.FileInfo;
import com.cq.video.repository.FileInfoRepository;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.gridfs.GridFS;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${server.address}")
    private String serverAddress;

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    GridFsOperations gridFsOperations;

    @Autowired
    GridFsTemplate gridFsTemplate;

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
            FileInfo fi = new FileInfo(caselsh,file.getContentType(),serverName,suffix);
            String sessionName = UUID.randomUUID().toString();
            fi.setAutoDelete(sessionName);
            fi.setSessionId(UUID.randomUUID().toString());
            ObjectId objectId = gridFsOperations.store(file.getInputStream(),sessionName,fi);
            String path = "http://"+serverAddress+":"+serverPort+"/file/"+serverName+"/"+objectId;
            return ResponseEntity.status(HttpStatus.OK).body(path);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping("/{serverName}/{id}")
    public ResponseEntity<Object> viewFile(@PathVariable String id, @PathVariable String serverName){
        try {
            GridFSFile gridFSFile = gridFsTemplate.findOne(new Query().addCriteria(Criteria.where("_id").is(id).and("metadata.serverName").is(serverName)));
            GridFsResource gridFsResource = gridFsTemplate.getResource(gridFSFile.getFilename());
            return ResponseEntity.status(HttpStatus.OK)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "fileName=\"" + gridFSFile.getFilename() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, gridFSFile.getMetadata().get("contentType").toString())
                    .header(HttpHeaders.CONTENT_LENGTH, gridFSFile.getLength() + "").header("Connection", "close")
                    .body(gridFsResource);
        }catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("文件不存在");
        }

    }

}
