package com.xxxx.crm.controller;

import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.model.TreeModel;
import com.xxxx.crm.service.ModuleService;
import com.xxxx.crm.vo.Module;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("module")
public class ModuleController extends BaseController {
    @Resource
    private ModuleService moduleService;

    /**
     * 查询所有资源列表
     * @return
     */
    @ResponseBody
    @RequestMapping("queryAllModules")
    public List<TreeModel> queryAllModules(Integer roleId){

        List<TreeModel> treeModelList=moduleService.queryAllModules(roleId);
        return treeModelList;
    }


    /**
     * 进入授权页面
     * @param roleId
     * @return
     */
    @RequestMapping("toAddGrantPage")
    public String toAddGrantPage(Integer roleId, HttpServletRequest req){
        req.setAttribute("roleId",roleId);
        return "role/grant";
    }

    /**
     * 进入资源模块界面
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "module/module";
    }


    /**
     * 查询资源列表
     * @return
     */
    @ResponseBody
    @RequestMapping("list")
    public Map<String,Object> queryModuleList(){
        return moduleService.queryModuleList();
    }

    /**
     * 添加资源
     * @param module
     * @return
     */
    @ResponseBody
    @PostMapping("add")
    public ResultInfo addModule(Module module){
        moduleService.addModule(module);
        return success("添加资源成功！");
    }

    /**
     * 进入添加资源页面
     * @param grade
     * @param parentId
     * @param req
     * @return
     */
    @RequestMapping("addModulePage")
    public String addModulePage(Integer grade,Integer parentId,HttpServletRequest req){
        //将数据设置到请求域中
        req.setAttribute("grade",grade);
        req.setAttribute("parentId",parentId);
        return "module/add";
    }


    /**
     * 修改资源
     * @param module
     * @return
     */
    @ResponseBody
    @PostMapping("update")
    public ResultInfo updateModule(Module module){
        moduleService.updateModule(module);
        return success("修改资源成功！");
    }

    /**
     * 进入修改资源页面
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("toUpdateModulePage")
    public String toUpdateModule(Integer id,HttpServletRequest req){
        //将要修改的资源对象设置到请求域中
        req.setAttribute("module",moduleService.selectByPrimaryKey(id));
        return "module/update";
    }


    /**
     * 删除资源
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteModule(Integer id){
        moduleService.deleteModule(id);
        return success("删除资源成功!");
    }


}
