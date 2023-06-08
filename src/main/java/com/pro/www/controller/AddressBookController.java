package com.pro.www.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.pro.www.common.BaseContext;
import com.pro.www.dto.R;
import com.pro.www.entity.AddressBook;
import com.pro.www.service.impl.AddressBookServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 地址管理 前端控制器
 * </p>
 *
 * @author pro
 * @since 2023-06-04
 */
@RestController
@Slf4j
@RequestMapping("/addressbook")
@Api(tags = "地址薄（用户信息）")
public class AddressBookController {

    @Autowired
    private AddressBookServiceImpl addressBookService;

    @PostMapping
    @ApiOperation(value = "地址保存")
    public R<AddressBook> save(@RequestBody AddressBook addressBook){
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookService.save(addressBook);
        log.info("[INFO] 地址保存成功");

        return R.success(addressBook);
    }

    @PutMapping("default")
    @ApiOperation(value = "设置默认地址")
    public R<AddressBook> setDefault(@RequestBody AddressBook addressBook){
        addressBookService.setDefaultById(addressBook);
        // 避免函数未修改默认地址属性
        addressBook.setIsDefault(true);
        log.info("设置默认地址成功,{}",addressBook.getDetail());
        return R.success(addressBook);

    }
    @GetMapping("/{id}")
    @ApiOperation(value = "按id查找地址")
    public R<AddressBook> get(@PathVariable Long id){
        AddressBook addressBook = addressBookService.getById(id);
        if(addressBook!=null){
            log.info("[INFO] 查询地址成功");
            return R.success(addressBook);
        }else{
            log.error("[ERROR] 查询地址失败");
            return R.error("查询地址失败");
        }
    }

    @GetMapping("/default")
    @ApiOperation(value = "查询默认地址")
    public R<AddressBook> getDefult(){
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getUserId,BaseContext.getCurrentId());
        queryWrapper.eq(AddressBook::getIsDefault,1);
        AddressBook addressBook = addressBookService.getOne(queryWrapper);

        if(addressBook==null){
            log.error("[ERROR] 查询默认地址失败");
            return R.error("查询默认地址失败");
        }
        log.info("[INFO] 查询默认地址成功");
        return R.success(addressBook);
    }

    @GetMapping("/list")
    @ApiOperation(value = "查询所有地址")
    public R<List<AddressBook>> list(AddressBook addressBook){
        addressBook.setUserId(BaseContext.getCurrentId());
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(addressBook.getUserId()!=null,AddressBook::getUserId,addressBook.getUserId());
        queryWrapper.orderByDesc(AddressBook::getUpdateTime);
        List<AddressBook> addressBooks = addressBookService.list(queryWrapper);
        log.info("[INFO] 查询{}所有地址成功",addressBook.getUserId());
        return R.success(addressBooks);

    }
}
