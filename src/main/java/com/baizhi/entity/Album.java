package com.baizhi.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * (Album)实体类
 *
 * @author makejava
 * @since 2019-12-30 09:36:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Album implements Serializable {

    @Id
    private String id;
    
    private String title;
    
    private String score;
    
    private String author;
    
    private String broadcast;

    private Integer count;
    @Column(name = "`desc`")
    private String desc;
    
    private String status;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date createDate;
    private String cover;




}