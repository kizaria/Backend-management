package com.lys.web.sys_menu.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lys.annotation.Auth.Auth;
import com.lys.annotation.Log.HoneyLogs;
import com.lys.annotation.Log.LogType;
import com.lys.utils.ResultUtils;
import com.lys.utils.ResultVo;
import com.lys.web.sys_menu.entity.MakeMenuTree;
import com.lys.web.sys_menu.entity.PermissonVo;
import com.lys.web.sys_menu.entity.SysMenu;
import com.lys.web.sys_menu.service.SysMenuService;
import com.lys.web.sys_user.entity.SysUser;
import com.lys.web.sys_user.service.SysUserService;
import com.lys.web.user_menu.entity.AssignParm;
import com.lys.web.user_menu.service.UserMenuService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/menu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private UserMenuService userMenuService;

    //新增
    @PostMapping
    @Auth
    @HoneyLogs(operation = "菜单管理",type = LogType.ADD)
    public ResultVo add(@RequestBody SysMenu sysMenu){
        sysMenu.setCreateTime(new Date());
        if(sysMenuService.save(sysMenu)){
            return ResultUtils.success("新增成功");
        }else{
            return ResultUtils.error("新增失败");

        }
    }


    //更新
    @PutMapping
    @Auth
    @HoneyLogs(operation = "菜单管理",type = LogType.UPDATE)
    public ResultVo edit(@RequestBody SysMenu sysMenu){
        if(sysMenuService.updateById(sysMenu)){
            return ResultUtils.success("编辑成功");
        }else{
            return ResultUtils.error("编辑失败");
        }
    }

    //删除
    @DeleteMapping("/{menuId}")
    @Auth
    @HoneyLogs(operation = "菜单管理",type = LogType.DELETE)
    public ResultVo delete(@PathVariable Long menuId){
        if(sysMenuService.removeById(menuId)){
            return ResultUtils.success("删除成功");
        }else{
            return ResultUtils.error("删除失败");
        }
    }

    //列表
    @GetMapping("/list")
    @Auth
    public ResultVo list(){
        LambdaQueryWrapper<SysMenu> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(SysMenu::getOrderNum);
        List<SysMenu> menuList=sysMenuService.list(queryWrapper);
        //组装树数据
        List<SysMenu> list = MakeMenuTree.makeTree(menuList,0L);
        return ResultUtils.success("查询成功",list);

    }

    //查询上级菜单
    @GetMapping("/getParent")
    @Auth
    public ResultVo getParent(){
        List<SysMenu> list=sysMenuService.getParent();
        return ResultUtils.success("查询成功",list);
    }


    /**
     * 后台在管理员分配菜单，返回当前管理员权限，和分配者权限，需要登录，无需log
     * @param userId
     * @param assId
     * @return
     */
    //分配菜单树数据查询和回显
    @GetMapping("/getAssignTree")
    @Auth
    public ResultVo getAssignTree(Long userId,Long assId){
        //查询当前用户信息
        SysUser user=sysUserService.getById(userId);
        List<SysMenu> menuList=null;
        //判断当前用户是否是超级管理员
        if(StringUtils.isNotEmpty(user.getIsAdmin())&&user.getIsAdmin().equals("1")){
            menuList=sysMenuService.list();
        }else {
            menuList=sysMenuService.getMenuByUserId(userId);
        }
        //组装树数据
        List<SysMenu> menus=MakeMenuTree.makeTree(menuList,0L);
        //设置菜单数据
        PermissonVo vo=new PermissonVo();
        vo.setMenuList(menus);
        //查询回显的数据
        List<SysMenu> menuByUserId = sysMenuService.getMenuByUserId(assId);
        List<Long> ids=new ArrayList<>();
        Optional.ofNullable(menuByUserId).orElse(new ArrayList<>())
                .stream()
                .filter(item->item!=null)
                .forEach(item->{
                    ids.add(item.getMenuId());
                });
        vo.setCheckList(ids.toArray());
        return ResultUtils.success("查询成功",vo);
    }


    /**
     * 将分配好的权限保存到数据库，需要登录，需要log
     * @param parm
     * @return
     */
    //保存菜单
    @PostMapping("/assignSave")
    @Auth
    @HoneyLogs(operation = "管理员权限",type = LogType.UPDATE)
    public ResultVo assignSave(@RequestBody AssignParm parm){
        //判断是不是超级管理员,超级管理员无法分配
        SysUser user=sysUserService.getById(parm.getAssId());
        if(StringUtils.isNotEmpty(user.getIsAdmin())&&user.getIsAdmin().equals("1")){
            return ResultUtils.error("无法为超级管理员分配菜单");
        }
        userMenuService.saveMenu(parm);
        return ResultUtils.success("分配成功");
    }
}
