package com.sxt.mall.pms.service.impl;

/**
 * @author 李涵林
 * @data 2021/4/12 12:57
 */
public interface IMdelService{
    void showMedal();
}

class GuardMedalServiceImpl implements IMdelService{

    @Override
    public void showMedal() {
        System.out.println("展示守护勋章");
    }
} 

class GuestMedalServiceImpl implements IMdelService{

    @Override
    public void showMedal() {
        System.out.println("嘉宾勋章");
    }
}

class VipMedalServiceImpl implements IMdelService{

    @Override
    public void showMedal() {
        System.out.println("会员勋章");
    }
}
