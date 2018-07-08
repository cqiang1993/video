package com.cq.video.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Document
public class FileInfo {
    @Id
    private String id;
    private String name;
    private String contentType;
    private String path;
    private String serverName;
    private String sessionId;
    private String autoDelete;
    private String type;


    private String picturesId;

    public FileInfo(){}

    public FileInfo(String name,String contentType,String serverName,String type){
        this.name = name;
        this.contentType = contentType;
        this.serverName = serverName;
        this.type = type;
    }

    public FileInfo(String name,String contentType,String serverName,String picturesId,String type){
        this.name = name;
        this.contentType = contentType;
        this.serverName = serverName;
        this.picturesId = picturesId;
        this.type = type;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getAutoDelete() {
        return autoDelete;
    }

    public void setAutoDelete(String autoDelete) {
        this.autoDelete = autoDelete;
    }
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getPicturesId() {
        return picturesId;
    }

    public void setPicturesId(String picturesId) {
        this.picturesId = picturesId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
