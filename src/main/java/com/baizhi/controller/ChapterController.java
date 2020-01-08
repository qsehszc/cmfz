package com.baizhi.controller;

import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Chapter;
import com.baizhi.util.HttpUtil;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.RowBounds;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    ChapterDao chapterDao;

    //分页
    @RequestMapping("showChapterById")
    public Map showChapterById(Integer page,Integer rows,String albumId){
        HashMap hashMap = new HashMap();
        Chapter chapter = new Chapter();
        chapter.setAlbumId(albumId);
        //总条数
        int i = chapterDao.selectCount(chapter);
        System.out.println("总条数:"+i);
        //总页数
        Integer total = i%rows==0?i/rows:i/rows+1;
        List<Chapter> chapterDaos = chapterDao.selectByRowBounds(chapter, new RowBounds((page - 1) * rows, rows));
        hashMap.put("records",i);
        hashMap.put("page",page);
        hashMap.put("total",total);
        hashMap.put("rows",chapterDaos);
        return hashMap;
    }
    @RequestMapping("editChapter")
    public Map editChapter(String oper, Chapter chapter, String[] id,String albumId){
        HashMap hashMap = new HashMap();
        if (oper.equals("add")) {
            String chapterId = UUID.randomUUID().toString();
            chapter.setId(chapterId);
            chapter.setAlbumId(albumId);
            chapterDao.insert(chapter);
            hashMap.put("chapterId", chapterId);

        } else if (oper.equals("edit")) {
            chapterDao.updateByPrimaryKeySelective(chapter);
            hashMap.put("chapterId", chapter.getId());
        } else {
            chapterDao.deleteByIdList(Arrays.asList(id));
        }
        return hashMap;
    }
    @RequestMapping("uploadChapter")
    public Map uploadChapter(MultipartFile url, String chapterId, HttpSession session, HttpServletRequest request) throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {
        String realPath = session.getServletContext().getRealPath("/upload/music/");
        File file = new File(realPath);
        if (!file.exists()){
            file.mkdirs();
        }
        String http = HttpUtil.getHttp(url, request, "/upload/music/");
        Chapter chapter = new Chapter();
        chapter.setId(chapterId);
        chapter.setUrl(http);
        Double size = Double.valueOf(url.getSize()/1024/1024);
        chapter.setSize(size);
        String[] split = http.split("/");
        String name = split[split.length-1];
        AudioFile read = AudioFileIO.read(new File(realPath, name));
        MP3AudioHeader audioHeader = (MP3AudioHeader) read.getAudioHeader();
        int trackLength = audioHeader.getTrackLength();
        String time = trackLength/60 + "分" + trackLength%60 + "秒";
        chapter.setTime(time);
        chapterDao.updateByPrimaryKeySelective(chapter);
        HashMap hashMap = new HashMap();
        hashMap.put("status",200);
        return hashMap;
    }
    @RequestMapping("downloadChapter")
    public void downloadChapter(String url, HttpServletResponse response,HttpSession session) throws IOException {
        String[] split = url.split("/");
        String realPath = session.getServletContext().getRealPath("/upload/music/");
        String name = split[split.length-1];
        File file = new File(realPath, name);
        response.setHeader("Content-Disposition", "attachment; filename="+name);
        ServletOutputStream outputStream = response.getOutputStream();
        FileUtils.copyFile(file,outputStream);
    }
}

