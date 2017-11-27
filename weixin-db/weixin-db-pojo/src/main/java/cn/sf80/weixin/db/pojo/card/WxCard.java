package cn.sf80.weixin.db.pojo.card;

import java.io.Serializable;
import java.util.List;

public class WxCard implements Serializable {
    private String logo_url;
    private String code_type;
    private String brand_name;
    private String title;
    private String notice;
    private String color;
    private DateInfo date_info;
    private String service_phone;
    private boolean use_all_locations;
    private List<String> location_id_list;
    private Sku sku;
    private Integer get_limit;
    private Integer use_limit;
    private boolean can_give_friend;
    private boolean can_share;
    private String description;

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getCode_type() {
        return code_type;
    }

    public void setCode_type(String code_type) {
        this.code_type = code_type;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public DateInfo getDate_info() {
        return date_info;
    }

    public void setDate_info(DateInfo date_info) {
        this.date_info = date_info;
    }

    public String getService_phone() {
        return service_phone;
    }

    public void setService_phone(String service_phone) {
        this.service_phone = service_phone;
    }

    public boolean getUse_all_locations() {
        return use_all_locations;
    }

    public void setUse_all_locations(boolean use_all_locations) {
        this.use_all_locations = use_all_locations;
    }

    public List<String> getLocation_id_list() {
        return location_id_list;
    }

    public void setLocation_id_list(List<String> location_id_list) {
        this.location_id_list = location_id_list;
    }

    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    public Integer getGet_limit() {
        return get_limit;
    }

    public void setGet_limit(Integer get_limit) {
        this.get_limit = get_limit;
    }

    public Integer getUse_limit() {
        return use_limit;
    }

    public void setUse_limit(Integer use_limit) {
        this.use_limit = use_limit;
    }

    public Boolean getCan_give_friend() {
        return can_give_friend;
    }

    public void setCan_give_friend(Boolean can_give_friend) {
        this.can_give_friend = can_give_friend;
    }

    public Boolean getCan_share() {
        return can_share;
    }

    public void setCan_share(Boolean can_share) {
        this.can_share = can_share;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
