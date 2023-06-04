package com.pro.www.service.impl;

import com.pro.www.entity.AddressBook;
import com.pro.www.mapper.AddressBookMapper;
import com.pro.www.service.IAddressBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 地址管理 服务实现类
 * </p>
 *
 * @author pro
 * @since 2023-06-04
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements IAddressBookService {

}
