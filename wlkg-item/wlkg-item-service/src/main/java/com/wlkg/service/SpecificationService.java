package com.wlkg.service;

import com.wlkg.common.enums.ExceptionEnums;
import com.wlkg.common.exception.WlkgException;
import com.wlkg.item.pojo.SpecGroup;
import com.wlkg.item.pojo.SpecParam;
import com.wlkg.mapper.SpecGroupMapper;
import com.wlkg.mapper.SpecParamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class SpecificationService {
    @Autowired
    private SpecGroupMapper specGroupMapper;
    @Autowired
    private SpecParamMapper specParamMapper;

    /**
     * 查询商品规格参数
     * @param gid
     * @param cid
     * @param searching
     * @param generic
     * @return
     */
    public List<SpecParam> querySpecParams(Long gid,Long cid,Boolean searching,Boolean generic ){
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        specParam.setCid(cid);
        specParam.setSearching(searching);
        specParam.setGeneric(generic);
        List<SpecParam> specParamList = specParamMapper.select(specParam);
        /*if(CollectionUtils.isEmpty(specParamList)){
            throw new WlkgException(ExceptionEnums.SPEC_PARAM_IS_EMPTY);
        }*/
        return specParamList;
    }

    /**
     * 查询商品规格
     * @param cid
     * @return
     */
    public List<SpecGroup> querySpecGropByCid(Long cid){
        SpecGroup specGrop = new SpecGroup();
        specGrop.setCid(cid);
        List<SpecGroup> specGropList = specGroupMapper.select(specGrop);

        if(CollectionUtils.isEmpty(specGropList)){
            throw new WlkgException(ExceptionEnums.SPEC_GROP_IS_NULL);
        }
        return specGropList;
    }

    /*public List<SpecParam> querySpecParamByGid(Long gid,Long cid,Boolean searching,Boolean generic){
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        specParam.setCid(cid);
        specParam.setSearching(searching);
        specParam.setGeneric(generic);

        List<SpecParam> specParamList = specParamMapper.select(specParam);
        //判断集合是否为空
        if(CollectionUtils.isEmpty(specParamList)){
            throw new WlkgException(ExceptionEnums.SPEC_PARAM_IS_NULL);
        }
        return specParamList;
    }*/

    /**
     * 添加商品规格
     * @param specGrop
     */
    public void addGroup(SpecGroup specGrop){
        specGroupMapper.insert(specGrop);
    }

    /**
     * 更新商品的规格
     * @param specGrop
     */
    public void updateGroup(SpecGroup specGrop){
        specGroupMapper.updateByPrimaryKeySelective(specGrop);
    }

    /**
     * 删除商品规格
     * @param id
     */
    public void deleteGroupByid(Long id){
        int i = specGroupMapper.deleteByPrimaryKey(id);
    }

    /**
     * 新增商品规格参数
     * @param specParam
     */
    public void addSpecParam(SpecParam specParam){
        specParamMapper.insert(specParam);
    }

    /**
     * 更新商品规格参数
     * @param specParam
     */
    public void updateSpecParam(SpecParam specParam){
        specParamMapper.updateByPrimaryKeySelective(specParam);
    }

    /**
     * 删除商品规格参数
     * @param id
     */
    public void deleteSpecParamById(Long id){
        specParamMapper.deleteByPrimaryKey(id);
    }

    /**
     * 组装一个specGrop对象
     * @param cid
     * @return
     */
    public List<SpecGroup> querySpecgropAndSpecByCid(Long cid) {
        //查询当前类所有的规格组
        List<SpecGroup> specGroups = querySpecGropByCid(cid);
        //查询当前类所有的规格参数
        List<SpecParam> params = querySpecParams(null, cid, null, null);

        /*将对应的参数塞到对应的规格主体中
          规格主题Id为键，规格参数组为值
         */
        Map<Long,List<SpecParam>> map = new HashMap<>();

        for (SpecParam param : params){
            if(!map.containsKey(param.getGroupId())){
                //这个组 id 在 map 中不存在，新增一个 list
                map.put(param.getGroupId(), new ArrayList<>());
            }
            map.get(param.getGroupId()).add(param);
        }
        //填充 param 到 group
        for (SpecGroup specGroup: specGroups){
            specGroup.setParams(map.get(specGroup.getId()));
        }

        return specGroups;
    }
}
