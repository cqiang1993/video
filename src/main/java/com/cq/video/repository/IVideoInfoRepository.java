package com.cq.video.repository;

import com.cq.video.entity.VideoInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface IVideoInfoRepository extends JpaRepository<VideoInfo,Integer> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "insert into t_video_info(title,src,picture,descript,update_time) values(?1,?2,?3,?4,?5)",nativeQuery = true)
    void insertVideoInfo(String title,String src,String picture,String descript,String update_time);

}
