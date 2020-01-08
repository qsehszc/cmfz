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
 * (Banner)实体类
 *
 * @author makejava
 * @since 2019-12-26 11:22:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Banner implements Serializable {

    @Id
    private String id;
    
    private String title;
    
    private String url;
    
    private String href;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date createDate;
    @Column(name = "`desc`")
    private String desc;
    
    private String status;





}