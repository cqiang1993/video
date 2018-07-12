package com.cq.video.utils;

import com.cq.video.entity.FileInfo;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;

import java.util.ArrayList;
import java.util.List;

public class ParseFileInfoUtil {

    public static List<FileInfo> parseFileInfo(GridFSFindIterable gridFSFiles){
        List<FileInfo> fileInfos = new ArrayList<>();
        for(GridFSFile gridFSFile:gridFSFiles){
            FileInfo fileInfo = new FileInfo();
            fileInfo.setId(gridFSFile.getObjectId().toString());
            fileInfo.setServerName(gridFSFile.getMetadata().getString("serverName"));
            fileInfo.setName(gridFSFile.getMetadata().getString("name"));
            fileInfo.setUplodaDate(gridFSFile.getUploadDate());
            fileInfo.setMd5(gridFSFile.getMD5());
            fileInfo.setLength(gridFSFile.getLength());
            fileInfo.setType(gridFSFile.getMetadata().getString("type"));
            fileInfo.setSessionId(gridFSFile.getMetadata().getString("sessionId"));
            fileInfo.setAutoDelete(gridFSFile.getMetadata().getString("autoDelete"));
            fileInfo.setContentType(gridFSFile.getMetadata().getString("contentType"));
            fileInfo.setPicturesId(gridFSFile.getMetadata().getString("picturesId"));
            fileInfos.add(fileInfo);
        }
        return fileInfos;
    }

}
