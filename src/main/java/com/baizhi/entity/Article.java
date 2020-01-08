package com.baizhi.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * (Article)实体类
 *
 * @author makejava
 * @since 2019-12-30 14:44:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article implements Serializable {

    @Id
    private String id;
    
    private String title;
    
    private String img;
    
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date createDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date publishDate;
    
    private String status;
    
    private String guruId;



}