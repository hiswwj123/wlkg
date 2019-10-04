package com.wlkg.item.pojo;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "tb_spec_param")
public class SpecParam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cid;
    private Long groupId;
    private String name;
    @Column(name = "`numeric`")
    private Boolean numeric;
    private String unit;
    private Boolean generic;
    private Boolean Searching;
    private String segments;

}
