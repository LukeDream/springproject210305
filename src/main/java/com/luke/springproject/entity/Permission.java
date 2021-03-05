package com.luke.springproject.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * permission
 * @author 
 */
@Data
public class Permission implements Serializable {
    private Integer id;

    private String uri;

    private String name;

    private Boolean c;

    private Boolean r;

    private Boolean u;

    private Boolean d;

    private static final long serialVersionUID = 1L;
}