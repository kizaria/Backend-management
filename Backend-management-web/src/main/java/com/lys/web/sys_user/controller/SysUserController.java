package com.lys.web.sys_user.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.lys.annotation.Auth.Auth;
import com.lys.annotation.Log.HoneyLogs;
import com.lys.annotation.Log.LogType;
import com.lys.jwt.JwtUtils;
import com.lys.utils.ResultUtils;
import com.lys.utils.ResultVo;
import com.lys.web.sys_menu.entity.SysMenu;
import com.lys.web.sys_menu.service.SysMenuService;
import com.lys.web.sys_user.entity.*;
import com.lys.web.sys_user.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/sysUser")
public class SysUserController {

    @Autowired
    public SysUserService sysUserService;

    @Autowired
    public DefaultKaptcha defaultKaptcha;

    @Autowired
    public SysMenuService sysMenuService;

    @Autowired
    public JwtUtils jwtUtils;

    /**
     * 后台新增管理员，需要登录，需要log
     * @param sysUser
     * @return
     */
    //新增管理员
    @PostMapping
    @Auth
    @HoneyLogs(operation = "管理员",type = LogType.ADD)
    public ResultVo add(@RequestBody SysUser sysUser) {
        sysUser.setPassword(DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes()));
        if (sysUserService.save(sysUser)) {
            return ResultUtils.success("新增成功");
        } else {
            return ResultUtils.error("新增失败");

        }
    }

    /**
     * 后台编辑更新管理员，需要登录，需要log
     * @param sysUser
     * @return
     */
    //更新管理员
    @PutMapping
    @Auth
    @HoneyLogs(operation = "管理员",type = LogType.UPDATE)
    public ResultVo edit(@RequestBody SysUser sysUser) {
        if (sysUserService.updateById(sysUser)) {
            return ResultUtils.success("编辑成功");
        } else {
            return ResultUtils.error("编辑失败");
        }
    }

    /**
     * 后台重置管理员密码，需要登录，需要log
     * @param sysUser
     * @return
     */
    //重置密码
    @PostMapping("/restepassword")
    @Auth
    @HoneyLogs(operation = "管理员",type = LogType.UPDATE)
    public ResultVo restepassword(@RequestBody SysUser sysUser){
        //默认重置密码1234
        String password="1234";
        LambdaUpdateWrapper<SysUser> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.set(SysUser::getPassword,
                        DigestUtils.md5DigestAsHex(password.getBytes()))
                .eq(SysUser::getUserId,sysUser.getUserId());
        if(sysUserService.update(updateWrapper)){
            return ResultUtils.success("重置成功");
        }else{
            return ResultUtils.error("重置失败");
        }
    }

    /**
     * 后台删除管理员，需要登录，需要log
     * @param userId
     * @return
     */
    //删除
    @DeleteMapping("/{userId}")
    @Auth
    @HoneyLogs(operation = "管理员",type = LogType.DELETE)
    public ResultVo delete(@PathVariable Long userId) {
        if (sysUserService.removeById(userId)) {
            return ResultUtils.success("删除成功");
        } else {
            return ResultUtils.error("删除失败");
        }
    }

    /**
     * 后台管理员列表，需要登录，无需log
     * @param pageParm
     * @return
     */
    //列表
    @GetMapping("/getlist")
    @Auth
    public ResultVo getList(PageParm pageParm) {
        //构造分页对象
        Page<SysUser> page = new Page<>(pageParm.getCurrentPage(), pageParm.getPageSize());
        //构造查询条件
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(pageParm.getNickName()), SysUser::getNickName, pageParm.getNickName());

        Page<SysUser> list = sysUserService.page(page, queryWrapper);
        return ResultUtils.success("查询成功", list);

    }

    /**
     * 前台后台公用验证码
     * @param request
     * @return
     */
    //验证码
    @PostMapping("/image")
    public ResultVo imagecode(HttpServletRequest request) {
        //生成验证码
        String text = defaultKaptcha.createText();
        //把验证码存入session 用于前端验证
        HttpSession session = request.getSession();
        session.setAttribute("code", text);
        //生成图片,转换为base64
        BufferedImage bufferedImage = defaultKaptcha.createImage(text);
        ByteArrayOutputStream outputStream = null;
        try {

            outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);
            BASE64Encoder encoder = new BASE64Encoder();
            String base64 = encoder.encode(outputStream.toByteArray());
            String captchaBase64 = "data:image/jpeg;base64," + base64.replaceAll("\r\n", "");
            ResultVo result = new ResultVo("生成成功", 200, captchaBase64);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }


    /**
     * 后台管理员登录，无需登录，需要log
     * @param parm
     * @param request
     * @return
     */
    //登录
    @PostMapping("/login")
    @HoneyLogs(operation = "管理员",type = LogType.LOGIN)
    public ResultVo login(@RequestBody LoginParm parm,HttpServletRequest request){
        //获取session里的验证码
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute("code");
        //获取前端验证码
        String code1=parm.getCode();
        if(StringUtils.isEmpty(code)){
            return ResultUtils.error("验证码过期");
        }
        if(!code.equals(code1)){
            return ResultUtils.error("验证码错误");
        }

        LambdaQueryWrapper<SysUser> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername,parm.getUsername())
                .eq(SysUser::getPassword, DigestUtils.md5DigestAsHex(parm.getPassword().getBytes()));

        SysUser user =sysUserService.getOne(queryWrapper);
        if(user==null){
            return ResultUtils.error("用户名或密码错误");
        }
        if(user.getStatus().equals("1")){
            return ResultUtils.error("此账号已停用，请联系超级管理员");
        }
        //全部通过
        session.setAttribute("userId", user.getUserId());
        LoginVo vo=new LoginVo();
        vo.setUserId(user.getUserId());
        vo.setNickName(user.getNickName());
        List<SysMenu> menuList = new ArrayList<>();
        //返回用户的菜单和权限
        //如果是超级管理员，全部返回
        if(StringUtils.isNotEmpty(user.getIsAdmin())&&user.getIsAdmin().equals("1")){
            LambdaQueryWrapper<SysMenu> queryWrapper1=new LambdaQueryWrapper<>();
            queryWrapper1.orderByAsc(SysMenu::getOrderNum);
            menuList = sysMenuService.list(queryWrapper1);
        }else {
            menuList=sysMenuService.getMenuByUserId(user.getUserId());
        }
        //获取权限字段
        List<String> codeList = Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .map(item -> item.getCode()).collect(Collectors.toList());
        vo.setCodeList(codeList);
        //获取菜单
        List<MenuVo> menuVoList = Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(item->item.getType().equals("1"))
                .map(item -> new MenuVo(item.getMenuId(),item.getTitle(),item.getPath(),item.getIcon())).collect(Collectors.toList());
        vo.setMenuList(menuVoList);

        //生成token并存入
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", String.valueOf(user.getUserId()));
        String token = jwtUtils.generateToken(map, String.valueOf(user.getUserId()));
        vo.setToken(token);
        return ResultUtils.success("登录成功",vo);
    }

    /**
     * 后台修改密码，需要登录，需要log
     * @param parm
     * @return
     */
    //修改密码
    @PutMapping("/updatePassword")
    @Auth
    @HoneyLogs(operation = "管理员",type = LogType.UPDATE)
    public ResultVo UpdatePasswordParm(@RequestBody UpdatePasswordParm parm){
        SysUser user =sysUserService.getById(parm.getUserId());
        //原密码加密
        String oldPassword = DigestUtils.md5DigestAsHex(parm.getOldPassword().getBytes());
        if(!oldPassword.equals(user.getPassword())){
            return ResultUtils.error("请输入正确的原密码");
        }
        LambdaUpdateWrapper<SysUser> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.set(SysUser::getPassword,DigestUtils.md5DigestAsHex(parm.getPassword().getBytes()))
                .eq(SysUser::getUserId,parm.getUserId());
        if(sysUserService.update(updateWrapper)){
            return ResultUtils.success("新密码设置成功");
        }else {
            return ResultUtils.error("新密码设置失败");
        }

    }





}
