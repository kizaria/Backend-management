package com.lys.web.address.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lys.annotation.Auth.Auth;
import com.lys.utils.ResultUtils;
import com.lys.utils.ResultVo;
import com.lys.web.address.entity.Address;
import com.lys.web.address.entity.addressParm;
import com.lys.web.address.service.AddressService;
import com.lys.web.goods.entity.Goods;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    /**
     * 前台用户获取地址，需要登录，无需log
     * @param userId
     * @return
     */
    //用于前台用户获取地址  根据id获取
    @Auth
    @GetMapping("/getAddress")
    public ResultVo getAddress(Long userId){
        LambdaQueryWrapper<Address> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Address::getUserid,userId);
        List<Address> list = addressService.list(queryWrapper);
        return ResultUtils.success("查询成功",list);
    }

    /**
     * 前台用户保存地址，需要登录，无需log
     * @param address
     * @return
     */
    //用于前台用户保存地址
    @Auth
    @PostMapping("/saveAddress")
    public ResultVo saveAddress(@RequestBody Address address){
        if(addressService.save(address)){
            return ResultUtils.success("保存成功");
        }else {
            return ResultUtils.error("保存失败");
        }
    }

    /**
     * 前台用户编辑地址，需要登录，无需log
     * @param address
     * @return
     */
    //用于前台用户编辑地址
    @Auth
    @PostMapping("/editAddress")
    public ResultVo editAddress(@RequestBody Address address){
        if(addressService.updateById(address)){
            return ResultUtils.success("更新成功");
        }else {
            return ResultUtils.error("更新失败");
        }
    }

    /**
     * 前台用户设为默认地址，需要登录，无需log
     * @param address
     * @return
     */
    //用于前台用户设为默认地址
    @Auth
    @PostMapping("/defaultAddress")
    public ResultVo defaultAddress(@RequestBody Address address){
        LambdaUpdateWrapper<Address> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(Address::getIsdefault,"0")
                .eq(Address::getUserid,address.getUserid());
        addressService.update(updateWrapper);
        address.setIsdefault("1");

        if(addressService.updateById(address)){
            return ResultUtils.success("设置成功");
        }else {
            return ResultUtils.error("设置失败");
        }
    }

    /**
     * 前台用户删除地址，需要登录，无需log
     * @param address
     * @return
     */
    //用于前台用户删除地址
    @Auth
    @PostMapping("/delete")
    public ResultVo delete(@RequestBody Address address){
        if(addressService.removeById(address.getAddressid())){
            return ResultUtils.success("删除成功");
        }else {
            return ResultUtils.error("删除失败");
        }
    }

    /**
     * 前台用户获取地址，需要登录，无需log
     * @param address
     * @return
     */
    //用于前台用户在 我的地址里 获取地址列表
    @Auth
    @PostMapping("/getList")
    public ResultVo getList(@RequestBody addressParm address){
        //构造分页对象
        Page<Address> page = new Page<>(address.getCurrentPage(), address.getPageSize());
        //构造查询条件
        LambdaQueryWrapper<Address> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Address::getUserid,address.getUserId());
        Page<Address> list = addressService.page(page, queryWrapper);
        return ResultUtils.success("查询成功",list);
    }
}
