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
 * (Chapter)实体类
 *
 * @author makejava
 * @since 2019-12-30 09:49:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chapter implements Serializable {

    @Id
    private String id;
    
    private String title;
    
    private String url;
    
    private Double size;
    
    private String time;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date createTime;
    
    private String albumId;





}