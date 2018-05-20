package com.xingtan.school.service;

import com.xingtan.school.entity.GradeAlbum;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by xinglongjian on 5/20 0020 10:25.
 */
public interface GradeAlbumService {
    GradeAlbum getAlbumById(long id);
    List<GradeAlbum> getAlbumsByGradeId(long gradeId);
    GradeAlbum getAlbumByName(String name);
    void insertAlbum(GradeAlbum album);
    void updateAlbum(GradeAlbum album);
    void deleteAlbum(long id);
}
