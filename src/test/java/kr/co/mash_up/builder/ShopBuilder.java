package kr.co.mash_up.builder;

import kr.co.mash_up.nine_tique.domain.Shop;

/**
 * Created by Dong on 2017-02-09.
 */
public class ShopBuilder {

    private Long id;

    private String name;

    private String info;

    private boolean enabled;

    private String phone;

    public ShopBuilder withId(Long id){
        this.id = id;
        return this;
    }

    public ShopBuilder withName(String name){
        this.name = name;
        return this;
    }

    public ShopBuilder withInfo(String info){
        this.info = info;
        return this;
    }

    public ShopBuilder withPhone(String phone){
        this.phone = phone;
        return this;
    }

    public ShopBuilder withEnabled(boolean enabled){
        this.enabled = enabled;
        return this;
    }

    public Shop build(){
        Shop shop = new Shop();
        shop.setId(id);
        shop.setName(name);
        shop.setInfo(info);
        shop.setPhone(phone);
        shop.setEnabled(enabled);
        return shop;
    }
}