package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * (Guru)实体类
 *
 * @author makejava
 * @since 2019-12-31 15:33:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Guru implements Serializable {

    @Id
    private String id;
    
    private String name;
    
    private String photo;
    
    private String status;
    
    private String nickName;




}