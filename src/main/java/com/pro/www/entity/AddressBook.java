package com.pro.www.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 地址管理
 * </p>
 *
 * @author pro
 * @since 2023-06-04
 */
@TableName("address_book")
@ApiModel(value = "AddressBook对象", description = "地址管理")
public class AddressBook implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("收货人")
    private String consignee;

    @ApiModelProperty("性别 0 女 1 男")
    private Integer sex;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("省级区划编号")
    private String provinceCode;

    @ApiModelProperty("省级名称")
    private String provinceName;

    @ApiModelProperty("市级区划编号")
    private String cityCode;

    @ApiModelProperty("市级名称")
    private String cityName;

    @ApiModelProperty("区级区划编号")
    private String districtCode;

    @ApiModelProperty("区级名称")
    private String districtName;

    @ApiModelProperty("详细地址")
    private String detail;

    @ApiModelProperty("标签")
    private String label;

    @ApiModelProperty("默认 0 否 1是")
    private Boolean isDefault;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("创建人")
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @ApiModelProperty("修改人")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    @ApiModelProperty("是否删除")
    private Integer isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }
    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }
    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }
    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }
    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }
    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }
    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "AddressBook{" +
            "id=" + id +
            ", userId=" + userId +
            ", consignee=" + consignee +
            ", sex=" + sex +
            ", phone=" + phone +
            ", provinceCode=" + provinceCode +
            ", provinceName=" + provinceName +
            ", cityCode=" + cityCode +
            ", cityName=" + cityName +
            ", districtCode=" + districtCode +
            ", districtName=" + districtName +
            ", detail=" + detail +
            ", label=" + label +
            ", isDefault=" + isDefault +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", createUser=" + createUser +
            ", updateUser=" + updateUser +
            ", isDeleted=" + isDeleted +
        "}";
    }
}
