package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * (Admin)实体类
 *
 * @author makejava
 * @since 2019-12-25 17:44:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin implements Serializable {

    @Id
    private String id;
    
    private String username;
    
    private String password;



}