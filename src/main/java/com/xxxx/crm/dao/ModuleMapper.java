package com.xxxx.crm.dao;

import com.xxxx.crm.base.BaseMapper;
import com.xxxx.crm.model.TreeModel;
import com.xxxx.crm.vo.Module;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ModuleMapper extends BaseMapper<Module,Integer> {
    //查询所有的资源列表
    public List<TreeModel> queryAllModules();

    //查询所有的资源数据
    public List<Module> queryModuleList();

    //通过层级与模块名查询资源对象
    Object queryModuleByGradeAndModuleName(@Param("grade") Integer grade, @Param("moduleName") String moduleName);
    //通过层级与URL查询资源对象
    Object queryModuleByGradeAndUrl(@Param("grade") Integer grade, @Param("url") String url);
    //通过权限码查询资源对象
    Object queryModuleByOptValue(String optValue);

    //查询指定资源是否存在子记录
    Integer queryModuleByParentId(Integer id);

}
