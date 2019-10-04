package com.wlkg.controller;

import com.wlkg.item.pojo.SpecGroup;
import com.wlkg.item.pojo.SpecParam;
import com.wlkg.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("spec")
public class SpecificationController {
    @Autowired
    private SpecificationService specificationService;

    @GetMapping("/groups/{cid}")
    public ResponseEntity<List<SpecGroup>> querySpecGropById(@PathVariable("cid") Long cid) {
        List<SpecGroup> specGrops = specificationService.querySpecGropByCid(cid);
        return ResponseEntity.ok(specGrops);
    }

    @PutMapping("/group")
    public ResponseEntity<Void> updateGroup(@RequestBody SpecGroup specGrop) {
        specificationService.updateGroup(specGrop);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据Cid获取规格数组
     * @param cid
     * @return
     */
    @GetMapping("/group")
    public ResponseEntity<List<SpecGroup>>
            querySpecsByCid(@RequestParam("cid") Long cid){

        return ResponseEntity.ok(specificationService.querySpecgropAndSpecByCid(cid));
    }

    @PostMapping("/group")
    public ResponseEntity<Void> addGroup(@RequestBody SpecGroup specGrop) {
        specificationService.addGroup(specGrop);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/group/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable("id") Long id) {
        specificationService.deleteGroupByid(id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/params")
    public ResponseEntity<List<SpecParam>> querySpecParam(
            @RequestParam(value="gid", required = false) Long gid,
            @RequestParam(value="cid", required = false) Long cid,
            @RequestParam(value="searching", required = false) Boolean searching,
            @RequestParam(value="generic", required = false) Boolean generic
    ){
        System.out.println("gid: " + gid + ",cid: " + cid + ",searching: " + searching + ",generic: " + generic);
        List<SpecParam> list =
                this.specificationService.querySpecParams(gid,cid,searching,generic);
        return ResponseEntity.ok(list);
    }

    @PutMapping("/param")
    public ResponseEntity<Void> updateParams(@RequestBody SpecParam specParam) {
        specificationService.updateSpecParam(specParam);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/param")
    public ResponseEntity<Void> addParams(@RequestBody SpecParam specParam) {
        specificationService.addSpecParam(specParam);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/param/{id}")
    public ResponseEntity<Void> deleteParam(@PathVariable("id") Long id) {
        specificationService.deleteSpecParamById(id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}