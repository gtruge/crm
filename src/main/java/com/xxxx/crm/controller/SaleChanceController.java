package com.xxxx.crm.controller;

import com.xxxx.crm.annoation.RequiredPermission;
import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.enums.StateStatus;
import com.xxxx.crm.query.SaleChanceQuery;
import com.xxxx.crm.service.SaleChanceService;
import com.xxxx.crm.utils.CookieUtil;
import com.xxxx.crm.utils.LoginUserUtil;
import com.xxxx.crm.vo.SaleChance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("sale_chance")
public class SaleChanceController extends BaseController {
    @Autowired
    private SaleChanceService saleChanceService;

    /**
     * 营销机会数据查询（分页多条件查询）
     *      如果flag的值不为空，且值为1，则表示当前查询的是客户开发计划，否则查询营销机会数据
     * @param saleChanceQuery
     * @return
     */
    @RequiredPermission(code = "101001")
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> querySaleChanceByParams(SaleChanceQuery saleChanceQuery,Integer flag,HttpServletRequest req){
        //判断flag的值
        if (flag!=null && flag == 1){
            //查询客户开发计划
            //设置分配状态
            saleChanceQuery.setState(StateStatus.STATED.getType());
            //设置指派人（当前登录用户的id）
            //从cookie中获取当前登录用户的id
            Integer userId= LoginUserUtil.releaseUserIdFromCookie(req);
            saleChanceQuery.setAssignMan(userId);
        }
        return saleChanceService.querySaleChanceByParams(saleChanceQuery);
    }

    /**
     * 进入营销机会管理页面
     * @return
     */
    @RequiredPermission(code = "1010")
    @RequestMapping("index")
    public String index(){
        return "saleChance/sale_chance";
    }

    /**
     * 进入营销机会添加或修改页面
     * @param id
     * @param req
     * @return
     */
    @RequestMapping("addOrUpdateSaleChancePage")
    public String addOrUpdateSaleChancePage(Integer id,HttpServletRequest req){
        System.out.println(id);
        if (id != null){
            //通过Id查询营销机会数据
            SaleChance saleChance=saleChanceService.selectByPrimaryKey(id);
            //将数据设置到请求域中
            req.setAttribute("saleChance",saleChance);
        }
        return "saleChance/add_update";
    }

    /**
     * 添加营销机会
     * @param saleChance
     * @param request
     * @return
     */
    @RequiredPermission(code = "101002")
    @PostMapping  ("add")
    @ResponseBody
    public ResultInfo addSaleChance(SaleChance saleChance,HttpServletRequest request){
        //从cookie中获取当前登录的用户名
        String userName= CookieUtil.getCookieValue(request,"userName");
        //将用户名设置到营销机会
        saleChance.setCreateMan(userName);
        //调用service层方法
        saleChanceService.addSaleChance(saleChance);
        return success("营销机会添加成功！");
    }

    /**
     * 修改营销机会
     * @param saleChance
     * @param request
     * @return
     */
    @RequiredPermission(code = "101004")
    @PostMapping("update")
    @ResponseBody
    public ResultInfo updateSaleChance(SaleChance saleChance,HttpServletRequest request){
        //调用Service层添加方法
        saleChanceService.updateSaleChance(saleChance);
        return success("营销机会更新成功！");
    }

    /**
     * 删除营销机会
     * @param ids
     * @return
     */

    @RequiredPermission(code = "101003")
    @PostMapping("delete")
    @ResponseBody
    public ResultInfo deleteSaleChance(Integer[] ids){
        //调用service层方法
        saleChanceService.deleteSaleChance(ids);
        return success("删除成功！");
    }

    /**
     * 更新营销机会的开发状态
     * @param id
     * @param devResult
     * @return
     */
    @ResponseBody
    @RequestMapping("updateSaleChanceDevResult")
    public ResultInfo updateSaleChanceDevResult(Integer id,Integer devResult){
        saleChanceService.updateSaleChanceDevResult(id,devResult);
        return success("开发状态更新成功！");
    }

}
