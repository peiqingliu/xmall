package cn.exrick.manager.controller;

import cn.exrick.common.pojo.DataTablesResult;
import cn.exrick.common.pojo.Result;
import cn.exrick.common.utils.ResultUtil;
import cn.exrick.manager.dto.RoleDto;
import cn.exrick.manager.pojo.TbPermission;
import cn.exrick.manager.pojo.TbRole;
import cn.exrick.manager.pojo.TbUser;
import cn.exrick.manager.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(description= "管理员管理")
public class UserController {

    final static Logger log= LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    @ApiOperation(value = "用户登录")
    public Result<Object> login(String username, String password){

        Subject subject = SecurityUtils.getSubject() ;
        //MD5加密
        String md5Pass = DigestUtils.md5DigestAsHex(password.getBytes());
        UsernamePasswordToken token = new UsernamePasswordToken(username,md5Pass);
        try {
            subject.login(token);
            log.info("验证成功");
            return new ResultUtil<Object>().setData(null);
        }catch (Exception e){
            log.info("验证失败");
            return new ResultUtil<Object>().setErrorMsg("用户名或密码错误");
        }
    }

    @RequestMapping(value = "/user/logout",method = RequestMethod.GET)
    @ApiOperation(value = "退出登录")
    public Result<Object> logout(){

        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/user/userInfo",method = RequestMethod.GET)
    @ApiOperation(value = "获取登录用户信息")
    public Result<TbUser> getUserInfo(){

        String username= SecurityUtils.getSubject().getPrincipal().toString();
        TbUser tbUser=userService.getUserByUsername(username);
        tbUser.setPassword(null);
        return new ResultUtil<TbUser>().setData(tbUser);
    }

    @RequestMapping(value = "/user/roleList",method = RequestMethod.GET)
    @ApiOperation(value = "获取角色列表")
    public DataTablesResult getRoleList(){

        DataTablesResult result=userService.getRoleList();
        return result;
    }

    @RequestMapping(value = "/user/getAllRoles",method = RequestMethod.GET)
    @ApiOperation(value = "获取所有角色")
    public Result<List<TbRole>> getAllRoles(){

        List<TbRole> list=userService.getAllRoles();
        return new ResultUtil<List<TbRole>>().setData(list);
    }

    @RequestMapping(value = "/user/addRole",method = RequestMethod.POST)
    @ApiOperation(value = "添加角色")
    public Result<Object> addRole(){

        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/user/updateRole",method = RequestMethod.POST)
    @ApiOperation(value = "更新角色")
    public Result<Object> updateRole(){

        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/user/delRole/{id}",method = RequestMethod.DELETE)
    @ApiOperation(value = "删除角色")
    public Result<Object> delRole(@PathVariable int id){

        int result=userService.deleteRole(id);
        if(result==1){
            return new ResultUtil<Object>().setData(null);
        }else {
            return new ResultUtil<Object>().setErrorMsg("该角色被使用中，不能删除！");
        }
    }

    @RequestMapping(value = "/user/roleCount",method = RequestMethod.GET)
    @ApiOperation(value = "统计角色数")
    public Result<Object> getRoleCount(){

        Long result=userService.countRole();
        return new ResultUtil<Object>().setData(result);
    }

    @RequestMapping(value = "/user/permissionList",method = RequestMethod.GET)
    @ApiOperation(value = "获取权限列表")
    public DataTablesResult getPermissionList(){

        DataTablesResult result=userService.getPermissionList();
        return result;
    }

    @RequestMapping(value = "/user/addPermission",method = RequestMethod.POST)
    @ApiOperation(value = "添加权限")
    public Result<Object> addPermission(@ModelAttribute TbPermission tbPermission){

        userService.addPermission(tbPermission);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/user/updatePermission",method = RequestMethod.POST)
    @ApiOperation(value = "更新权限")
    public Result<Object> updatePermission(@ModelAttribute TbPermission tbPermission){

        userService.updatePermission(tbPermission);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/user/delPermission/{id}",method = RequestMethod.DELETE)
    @ApiOperation(value = "删除权限")
    public Result<Object> delPermission(@PathVariable int id){

        userService.deletePermission(id);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/user/permissionCount",method = RequestMethod.GET)
    @ApiOperation(value = "统计权限数")
    public Result<Object> getPermissionCount(){

        Long result=userService.countPermission();
        return new ResultUtil<Object>().setData(result);
    }

    @RequestMapping(value = "/user/userList",method = RequestMethod.GET)
    @ApiOperation(value = "获取用户列表")
    public DataTablesResult getUserList(){

        DataTablesResult result=userService.getUserList();
        return result;
    }

    @RequestMapping(value = "/user/username",method = RequestMethod.GET)
    @ApiOperation(value = "判断用户名是否存在")
    public boolean getUserByName(String username){

        return userService.getUserByName(username);
    }

    @RequestMapping(value = "/user/phone",method = RequestMethod.GET)
    @ApiOperation(value = "判断手机是否存在")
    public boolean getUserByPhone(String phone){

        return userService.getUserByPhone(phone);
    }

    @RequestMapping(value = "/user/email",method = RequestMethod.GET)
    @ApiOperation(value = "判断邮箱是否存在")
    public boolean getUserByEmail(String email){

        return userService.getUserByEmail(email);
    }

    @RequestMapping(value = "/user/addUser",method = RequestMethod.POST)
    @ApiOperation(value = "添加用户")
    public Result<Object> addUser(@ModelAttribute TbUser tbUser){

        userService.addUser(tbUser);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/user/updateUser",method = RequestMethod.POST)
    @ApiOperation(value = "更新用户")
    public Result<Object> updateUser(@ModelAttribute TbUser tbUser){

        userService.updateUser(tbUser);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/user/edit/username/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "判断编辑用户名是否存在")
    public boolean getUserByEditName(@PathVariable Long id, String username){

        return userService.getUserByEditName(id,username);
    }

    @RequestMapping(value = "/user/edit/phone/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "判断编辑手机是否存在")
    public boolean getUserByEditPhone(@PathVariable Long id, String phone){

        return userService.getUserByEditPhone(id,phone);
    }

    @RequestMapping(value = "/user/edit/email/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "判断编辑用户名是否存在")
    public boolean getUserByEditEmail(@PathVariable Long id, String email){

        return userService.getUserByEditEmail(id,email);
    }

    @RequestMapping(value = "/user/stop/{id}",method = RequestMethod.PUT)
    @ApiOperation(value = "停用用户")
    public Result<Object> stopUser(@PathVariable Long id){

        userService.changeUserState(id,0);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/user/start/{id}",method = RequestMethod.PUT)
    @ApiOperation(value = "启用用户")
    public Result<Object> startUser(@PathVariable Long id){

        userService.changeUserState(id,1);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/user/changePass",method = RequestMethod.POST)
    @ApiOperation(value = "修改用户密码")
    public Result<Object> changePass(@ModelAttribute TbUser tbUser){

        userService.changePassword(tbUser);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/user/delUser/{id}",method = RequestMethod.DELETE)
    @ApiOperation(value = "删除用户")
    public Result<Object> delUser(@PathVariable Long id){

        userService.deleteUser(id);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/user/userCount",method = RequestMethod.GET)
    @ApiOperation(value = "统计用户数")
    public Result<Object> getUserCount(){

        Long result=userService.countUser();
        return new ResultUtil<Object>().setData(result);
    }
}