package com.wlkg.item.api;

import com.wlkg.item.pojo.SpecGroup;
import com.wlkg.item.pojo.SpecParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("spec")
public interface SpecificationAPI {
    @GetMapping("groups/{cid}")
    String querySpecGroups(@PathVariable("cid") Long cid);

    @GetMapping("/params")
    List<SpecParam> querySpecParam(
            @RequestParam(value = "gid", required = false) Long gid,
            @RequestParam(value = "cid", required = false) Long cid,
            @RequestParam(value = "searching", required = false) Boolean searching,
            @RequestParam(value = "generic", required = false) Boolean generic
    );

    /**
     * 根据Cid获取规格数组
     * @param cid
     * @return
     */
    @GetMapping("/group")
    List<SpecGroup> querySpecsByCid(@RequestParam("cid") Long cid);
}