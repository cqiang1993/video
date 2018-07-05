package com.cq.video.web;

import com.cq.video.repository.IVideoInfoRepository;
import com.cq.video.entity.VideoInfo;
import com.cq.video.utils.FileCodecsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class UploadFileController {

    @Autowired
    IVideoInfoRepository videoInfoRepository;

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam("file")MultipartFile file,@RequestParam("title") String title,
                         @RequestParam("descript") String descript){
        if(file.isEmpty()){
            return "false";
        }
        boolean flag = false;    //转码成功与否的标记
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        VideoInfo videoInfo = new VideoInfo();
        String filename = file.getOriginalFilename();
        String[] fileSub = filename.split(".");
        for(String sub : fileSub)
            System.out.println(sub);
        System.out.println(filename);
        String basePath = "videos/";
        String filePath = basePath+"temp/"+filename;
        File saveFile = new File(filePath);
        if (!saveFile.getParentFile().exists()) {
            saveFile.getParentFile().mkdirs();
        }
        try{
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(saveFile));
            out.write(file.getBytes());
            out.flush();
            out.close();
            UUID uuid = UUID.randomUUID();
            String ffmpegPath = "D:/ffmpeg/bin/ffmpeg.exe";
            String codcFilePath = basePath + uuid+".flv";
            System.out.println(codcFilePath);
            String picturePath = basePath + "pictures/"+uuid+".jpg";
            videoInfo.setTitle(title);
            videoInfo.setDescript(descript);
            videoInfo.setSrc(codcFilePath);
            videoInfo.setPicture(picturePath);
            videoInfo.setUpdateTime(sdf.format(new Date()));
            flag = FileCodecsUtil.executeCodecs(ffmpegPath,filePath,codcFilePath,picturePath);
            videoInfoRepository.insertVideoInfo(videoInfo.getTitle(),videoInfo.getSrc(),videoInfo.getPicture(),videoInfo.getDescript(),videoInfo.getUpdateTime());
        }catch (IOException e) {
            e.printStackTrace();
        }
        if(flag){
            return "上传成功";
        }else {
            return "转码失败";
        }
    }
}
