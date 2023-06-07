package com.pro.www.service;

import com.pro.www.entity.AddressBook;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 地址管理 服务类
 * </p>
 *
 * @author pro
 * @since 2023-06-04
 */
public interface AddressBookService extends IService<AddressBook> {
    public void setDefaultById(AddressBook addressBook);
}
