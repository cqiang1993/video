package com.cq.video.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;

@Document
public class FileInfo {
    @Id
    private String id;//文件ObjectId
    private String name;//文件名称
    private String serverName;//服务名称(保留字段，用于后续服务化扩展)
    private String sessionId;//唯一标识
    private String autoDelete;//是否自动删除
    private String type;//文件基础类型
    private String contentType;//网络文件类型
    private String picturesId;


    private Date uplodaDate;
    private String md5;
    private long length;



    public FileInfo(){}

    public FileInfo(String name,String contentType,String serverName,String type){
        this.name = name;
        this.serverName = serverName;
        this.type = type;
        this.contentType = contentType;
    }

    public FileInfo(String name,String contentType,String serverName,String picturesId,String type){
        this.name = name;
        this.serverName = serverName;
        this.picturesId = picturesId;
        this.type = type;
        this.contentType = contentType;
    }


    public Date getUplodaDate() {
        return uplodaDate;
    }

    public void setUplodaDate(Date uplodaDate) {
        this.uplodaDate = uplodaDate;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
