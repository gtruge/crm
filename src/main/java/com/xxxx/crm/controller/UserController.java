package com.xxxx.crm.controller;

import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.exceptions.ParamsException;
import com.xxxx.crm.model.UserModel;
import com.xxxx.crm.query.UserQuery;
import com.xxxx.crm.service.UserService;
import com.xxxx.crm.utils.LoginUserUtil;
import com.xxxx.crm.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @PostMapping("login")
    @ResponseBody
    public ResultInfo userLogin(String userName, String userPwd, HttpServletRequest request){
        ResultInfo resultInfo=new ResultInfo();
        //通过try catch捕获Service层的异常，如果service层抛出异常，则表示登录异常，否则登录失败
        //调用service层登录方法
        UserModel userModel=userService.userLogin(userName,userPwd);

        resultInfo.setResult(userModel);

//        try {
//            //调用service层登录方法
//            UserModel userModel=userService.userLogin(userName,userPwd);
//            resultInfo.setResult(userModel);
//        }catch (ParamsException p){
//            resultInfo.setCode(p.getCode());
//            resultInfo.setMsg(p.getMsg());
//            p.printStackTrace();
//        }catch (Exception e){
//            resultInfo.setCode(500);
//            resultInfo.setMsg("登录失败！");
//        }
        return resultInfo;
    }

    @PostMapping("updatePwd")
    @ResponseBody
    public ResultInfo updateUserPassword(HttpServletRequest request,String oldPwd,String newPwd,String repeatPwd){
        ResultInfo resultInfo=new ResultInfo();

        //获取cookie中的userId
        Integer userId= LoginUserUtil.releaseUserIdFromCookie(request);
        //调用service层的修改密码方法
        userService.updatePassword(userId,oldPwd,newPwd,repeatPwd);

//        try{
//            //获取cookie中的userId
//            Integer userId= LoginUserUtil.releaseUserIdFromCookie(request);
//            //调用service层的修改密码方法
//            userService.updatePassword(userId,oldPwd,newPwd,repeatPwd);
//        }catch (ParamsException p){
//            resultInfo.setCode(p.getCode());
//            resultInfo.setMsg(p.getMsg());
//            p.printStackTrace();
//        }catch (Exception e){
//            resultInfo.setCode(500);
//            resultInfo.setMsg("修改密码失败！");
//            e.printStackTrace();
//        }

        return resultInfo;
    }

    /**
     * 进入修改密码页面
     * @return
     */
    @RequestMapping("toPasswordPage")
    public String toPasswordPage(){
        return "user/password";
    }

    /**
     * 查询所有销售人员
     * @return
     */
    @RequestMapping("queryAllSales")
    @ResponseBody
    public List<Map<String,Object>> queryAllSales(){
        List list=userService.queryAllSales();
        System.out.println(list);
        return list;
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> selectByParams(UserQuery userQuery){
        return userService.queryByParamsForTable(userQuery);
    }

    /**
     * 进入用户列表页面
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "user/user";
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @PostMapping("add")
    @ResponseBody
    public ResultInfo addUser(User user){
        userService.addUser(user);
        return success("用户添加成功！");
    }

    /**
     * 打开添加或修改用户页面
     * @return
     */
    @RequestMapping("addOrUpdateUserPage")
    public String addOrUpdateUserPage(Integer id,HttpServletRequest req){
        //判断Id是否为空，不为空表示更新操作，查询用户对象
        if (id!=null){
            //通过id查询用户对象
            User user=userService.selectByPrimaryKey(id);
            //将数据设置到请求域中
            req.setAttribute("userInfo",user);
        }
        return "user/add_update";
    }


    /**
     * 添加用户
     * @param user
     * @return
     */
    @PostMapping("update")
    @ResponseBody
    public ResultInfo updateUser(User user){
        userService.updateUser(user);
        return success("用户更新成功！");
    }

    /**
     * 用户删除
     * @param ids
     * @return
     */
    @ResponseBody
    @PostMapping("delete")
    public ResultInfo deleteUser(Integer[] ids){
        userService.deleteByids(ids);
        return success("用户删除成功！");
    }

}
