package com.pro.www.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.pro.www.common.BaseContext;
import com.pro.www.entity.AddressBook;
import com.pro.www.mapper.AddressBookMapper;
import com.pro.www.service.AddressBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 地址管理 服务实现类
 * </p>
 *
 * @author pro
 * @since 2023-06-04
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

    @Transactional
    @Override
    public void setDefaultById(AddressBook addressBook){
        LambdaUpdateWrapper<AddressBook> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(AddressBook::getUserId, BaseContext.getCurrentId());
        updateWrapper.set(AddressBook::getIsDefault,0);
        this.update(updateWrapper);

        addressBook.setIsDefault(true);
        this.updateById(addressBook);

    }

}
