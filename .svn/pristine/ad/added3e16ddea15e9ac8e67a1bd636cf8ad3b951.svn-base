//package com.yhyt.health.service.impl;
//
//import com.github.pagehelper.Page;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import com.yhyt.health.mapper.SysUpgradeMapper;
//import com.yhyt.health.model.SysUpgrade;
//import com.yhyt.health.model.query.UpgradeQuery;
//import com.yhyt.health.service.SysService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//import java.util.List;
//
//@Service
//public class SysServiceImpl implements SysService {
//
//    @Autowired
//    private SysUpgradeMapper sysUpgradeMapper;
//
//    @Override
//    public PageInfo<SysUpgrade> selectUpgradeListPage(UpgradeQuery upgradeQuery) {
//        Page page = PageHelper.startPage(upgradeQuery.getPageIndex(), upgradeQuery.getPageSize());
//        List<SysUpgrade> sysUpgrades = sysUpgradeMapper.selectUpgradeListPage(upgradeQuery);
//        PageInfo<SysUpgrade> pageInfo = new PageInfo<>(sysUpgrades);
//        return pageInfo;
//    }
//
//    @Override
//    public int addUpgrade(SysUpgrade sysUpgrade) {
//        sysUpgrade.setCreateTime(new Date());
//        sysUpgrade.setUpdateTime(new Date());
//        return sysUpgradeMapper.insertSelective(sysUpgrade);
//    }
//
//    @Override
//    public int editUpgrade(SysUpgrade sysUpgrade) {
//        sysUpgrade.setUpdateTime(new Date());
//        return sysUpgradeMapper.updateByPrimaryKeySelective(sysUpgrade);
//    }
//
//    @Override
//    public SysUpgrade selectById(long id) {
//        return sysUpgradeMapper.selectByPrimaryKey(id);
//    }
//}
