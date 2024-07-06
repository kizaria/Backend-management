package com.lys.web.wx_user.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lys.annotation.Auth.Auth;
import com.lys.annotation.Log.HoneyLogs;
import com.lys.annotation.Log.LogType;
import com.lys.jwt.JwtUtils;
import com.lys.utils.ResultUtils;
import com.lys.utils.ResultVo;
import com.lys.utils.SMSUtils;
import com.lys.utils.ValidateCodeUtils;
import com.lys.web.wx_user.entity.LoginVo;
import com.lys.web.wx_user.entity.UpdatePwParm;
import com.lys.web.wx_user.entity.WxUser;
import com.lys.web.wx_user.entity.WxUserParm;
import com.lys.web.wx_user.service.WxUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@CrossOrigin
@RestController
@RequestMapping("/api/wxUser")
public class WxUserController {

    @Autowired
    WxUserService wxUserService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 前台用户注册，无需登录，需要log
     * @param wxUser
     * @return
     */
    //用户注册
    @PostMapping("/register")
    @HoneyLogs(operation = "用户",type = LogType.RE)
    public ResultVo register(@RequestBody WxUser wxUser){
        //构造
        LambdaQueryWrapper<WxUser> queryWrapper=new LambdaQueryWrapper<WxUser>();
        //条件参数
        queryWrapper.eq(WxUser::getUsername,wxUser.getUsername());
        //查询用户
        WxUser user=wxUserService.getOne(queryWrapper);
        //如果查询到了
        if(user!=null){
            return ResultUtils.error("用户名被占用！！！");
        }
        //获取redis里的验证码
        String code = (String) redisTemplate.opsForValue().get(wxUser.getPhone());
        //对比
        if(!Objects.equals(wxUser.getCode(), code)){
            return ResultUtils.error("验证码错误！");
        }
        //密码加密
        wxUser.setPassword(DigestUtils.md5DigestAsHex(wxUser.getPassword().getBytes()));

        //存到数据库
        if(wxUserService.saveOrUpdate(wxUser)){
            return ResultUtils.success("注册成功！");
        }
        return ResultUtils.error("注册失败！");


    }

    /**
     * 前台用户登录，无需登录，需要log
     * @param wxUser
     * @return
     */
    //登录
    @PostMapping("/login")
    @HoneyLogs(operation = "用户",type = LogType.LOGIN)
    public ResultVo login(@RequestBody WxUser wxUser){
        //构造
        LambdaQueryWrapper<WxUser> queryWrapper=new LambdaQueryWrapper<WxUser>();
        //条件参数
        queryWrapper.eq(WxUser::getUsername,wxUser.getUsername());
        //查询用户
        //如果查询不到
        if(wxUserService.getOne(queryWrapper)==null){
            return ResultUtils.error("此用户未注册！！！");
        }
        queryWrapper.eq(WxUser::getPassword,
                DigestUtils.md5DigestAsHex(wxUser.getPassword().getBytes()));
        WxUser user=wxUserService.getOne(queryWrapper);
        //如果查询到了
        if(user!=null){
            if(user.getStatus().equals("1")){
                 return ResultUtils.error("此账号被封禁，请联系管理员！");
            }
            //设置小程序返回的专用数据
            LoginVo vo=new LoginVo();
            vo.setUserId(user.getUserId());
            vo.setUserName(user.getUsername());
            vo.setPhone(user.getPhone());
            vo.setAvatar(user.getPicture());

            //生成token并存入
            HashMap<String, String> map = new HashMap<>();
            map.put("userId", String.valueOf(user.getUserId()));
            String token = jwtUtils.generateToken(map, String.valueOf(wxUser.getUserId()));
            vo.setToken(token);

            return ResultUtils.success("登录成功",vo);
        }
        return ResultUtils.error("用户名或密码错误！！！");
    }

    /**
     * 后台编辑用户，需要登录，无需log
     * @param wxUser
     * @return
     */
    //编辑用户
    @PutMapping("/edit")
    @Auth
    @HoneyLogs(operation = "用户",type = LogType.UPDATE)
    public ResultVo edit(@RequestBody WxUser wxUser){
        if(wxUserService.updateById(wxUser)){
            return ResultUtils.success("设置成功");
        }else{
            return ResultUtils.error("设置失败");
        }
    }

    /**
     * 后台重置用户密码，需要登录，需要log
     * @param wxUser
     * @return
     */
    //重置密码
    @PostMapping("/restepassword")
    @Auth
    @HoneyLogs(operation = "用户",type = LogType.UPDATE)
    public ResultVo restepassword(@RequestBody WxUser wxUser){
        //默认重置密码1234
        String password="1234";
        LambdaUpdateWrapper<WxUser> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.set(WxUser::getPassword,
                DigestUtils.md5DigestAsHex(password.getBytes()))
                .eq(WxUser::getUserId,wxUser.getUserId());
        if(wxUserService.update(updateWrapper)){
            return ResultUtils.success("重置成功");
        }else{
            return ResultUtils.error("重置失败");
        }
    }


    /**
     * 后台删除用户，需要登录，需要log
     * @param wxUserId
     * @return
     */
    //删除用户
    @DeleteMapping("/{wxUserId}")
    @Auth
    @HoneyLogs(operation = "用户",type = LogType.DELETE)
    public ResultVo delete(@PathVariable Long wxUserId){
        if(wxUserService.removeById(wxUserId)){
            return ResultUtils.success("删除成功");
        }else{
            return ResultUtils.error("删除失败");
        }
    }


    /**
     * 后台用户列表，需要登录，无需log
     * @param pageParm
     * @return
     */
    //列表
    @GetMapping("/getlist")
    @Auth
    public ResultVo getList(WxUserParm pageParm){
        //构造分页对象
        Page<WxUser> page = new Page<>(pageParm.getCurrentPage(), pageParm.getPageSize());
        //构造查询条件
        LambdaQueryWrapper<WxUser> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(pageParm.getName()),
                WxUser::getName,pageParm.getName());

        Page<WxUser> list=wxUserService.page(page,queryWrapper);
        return ResultUtils.success("查询成功",list);

    }

    /**
     * 现为前台用户修改密码，需要登录，无需log
     * @param parm
     * @return
     */
    //小程序修改密码
    @PostMapping("/updatePasword")
    @Auth
    public ResultVo updatePasword(@RequestBody UpdatePwParm parm){
        //判断原密码是否正确
        LambdaQueryWrapper<WxUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WxUser::getUserId,parm.getUserId())
                .eq(WxUser::getPassword,DigestUtils.md5DigestAsHex(parm.getOldPassword().getBytes()));
        WxUser one = wxUserService.getOne(queryWrapper);
        if(one==null){
            return ResultUtils.error("原密码不正确!!!");
        }
        LambdaUpdateWrapper<WxUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(WxUser::getPassword,DigestUtils.md5DigestAsHex(parm.getPassword().getBytes()))
                .eq(WxUser::getUserId,parm.getUserId());
        if(wxUserService.update(updateWrapper)){
            return ResultUtils.success("修改成功");
        }else{
            return ResultUtils.error("修改失败");
        }
    }

    /**
     *原为小程序api，现已废弃
     * @param wxUser
     * @return
     */
    //小程序修改信息
    @PostMapping("/editInfo")
    @Auth
    public ResultVo editInfo(@RequestBody WxUser wxUser){
        if(wxUserService.updateById(wxUser)){
            return ResultUtils.success("修改成功");
        }else{
            return ResultUtils.error("修改失败");
        }
    }

    /**
     * 现为前台查询个人信息，需要登录，无需log
     * @param userId
     * @return
     */
    //小程序查询个人信息
    @GetMapping("/getInfo")
    public ResultVo getInfo(Long userId){
        WxUser user = wxUserService.getById(userId);
        if(user!=null){
            return ResultUtils.success("查询成功",user);
        }
        return ResultUtils.error("查询失败");
    }

    /**
     * 前台手机验证码
     * @param wxUser
     * @return
     */
    //手机验证码
    @PostMapping("/sendMsg")
    public ResultVo sendMsg(@RequestBody WxUser wxUser){
        //获取手机号
        String phone = wxUser.getPhone();
        if(phone==null){
            LambdaQueryWrapper<WxUser> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(WxUser::getUsername,wxUser.getUsername());
            WxUser one = wxUserService.getOne(queryWrapper);
            phone=one.getPhone();
        }


        if(StringUtils.isNotEmpty(phone)){
            //生成随机的验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            //调用阿里云api
            SMSUtils.sendMessage("二手","SMS_154950909",phone,code);

            //删除redis里的验证码
            //清空redis缓存
            redisTemplate.delete(phone);
            //将验证码存入redis
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return ResultUtils.success("验证码发送成功");

        }
        return ResultUtils.error("验证码发送失败");
    }


    /**
     *现为前台忘记密码，需要登录，无需log
     * @param wxUser
     * @return
     */
    //小程序忘记密码
    @PostMapping("/forgotPasword")
    public ResultVo forgotPasword(@RequestBody WxUser wxUser){
        if(wxUser.getPhone()==null){
            LambdaQueryWrapper<WxUser> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(WxUser::getUsername,wxUser.getUsername());
            WxUser one = wxUserService.getOne(queryWrapper);
            wxUser.setPhone(one.getPhone());
        }
        //获取redis里的验证码
        String code = (String) redisTemplate.opsForValue().get(wxUser.getPhone());
        //对比
        if(!Objects.equals(wxUser.getCode(), code)){
            return ResultUtils.error("验证码错误！");
        }
        LambdaQueryWrapper<WxUser> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(WxUser::getUsername, wxUser.getUsername())
                .eq(WxUser::getPhone, wxUser.getPhone());
        WxUser one = wxUserService.getOne(queryWrapper);
        if(one==null){
            return ResultUtils.error("用户名和手机号不匹配！");
        }
        LambdaUpdateWrapper<WxUser> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.set(WxUser::getPassword,DigestUtils.md5DigestAsHex(wxUser.getPassword().getBytes()))
                .eq(WxUser::getUsername,wxUser.getUsername());
        if(wxUserService.update(updateWrapper)){
            return ResultUtils.success("密码修改成功");
        }
        return ResultUtils.error("密码修改失败");

    }

    /**
     * 原为小程序api，现已废弃
     * @param wxUser
     * @return
     */
    //忘记密码
    @PostMapping("/forgotPassword")
    public ResultVo forgotPassword(@RequestBody WxUser wxUser){
        //获取redis里的验证码
        String code = (String) redisTemplate.opsForValue().get(wxUser.getPhone());
        //对比
        if(!Objects.equals(wxUser.getCode(), code)){
            return ResultUtils.error("验证码错误！");
        }
//        LambdaQueryWrapper<WxUser> queryWrapper=new LambdaQueryWrapper<>();
//        queryWrapper.eq(WxUser::getUsername, wxUser.getUsername())
//                .eq(WxUser::getPhone, wxUser.getPhone());
//        WxUser one = wxUserService.getOne(queryWrapper);
//        if(one==null){
//            return ResultUtils.error("用户名和手机号不匹配！");
//        }
        LambdaUpdateWrapper<WxUser> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.set(WxUser::getPassword,DigestUtils.md5DigestAsHex(wxUser.getPassword().getBytes()))
                .eq(WxUser::getUsername,wxUser.getUsername());
        if(wxUserService.update(updateWrapper)){
            return ResultUtils.success("密码修改成功");
        }
        return ResultUtils.error("密码修改失败");

    }


}
