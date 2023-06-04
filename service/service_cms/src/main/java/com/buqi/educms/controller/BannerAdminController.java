package com.buqi.educms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buqi.educms.entity.CrmBanner;
import com.buqi.educms.service.CrmBannerService;
import com.buqi.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/educms/banneradmin")
@CrossOrigin
public class BannerAdminController {

    @Autowired
    private CrmBannerService bannerService;

    //分页查询
    @GetMapping("/{page}/{limit}")
    public R page(@PathVariable Long page,@PathVariable Long limit){
        Page<CrmBanner> banner = new Page<>(page,limit);
        bannerService.page(banner,null);
        List<CrmBanner> item = banner.getRecords();
        long total = banner.getTotal();
        return R.ok().data("item",item).data("total",total);
    }

    //添加Banner
    @PostMapping("/addBanner")
    public R addBanner(@RequestBody CrmBanner banner){
        bannerService.save(banner);
        return R.ok();
    }

    //获取Banner
    @GetMapping("/getBanner/{id}")
    public R getBanner(@PathVariable String id){
        CrmBanner banner = bannerService.getById(id);
        return R.ok().data("banner",banner);
    }

    //修改Banner
    @PostMapping("updateBanner")
    public R updateBanner(@RequestBody CrmBanner banner){
        bannerService.updateById(banner);
        return R.ok();
    }

    //删除Banner
    @PostMapping("deleteBanner/{id}")
    public R deleteBanner(@PathVariable String id){
        bannerService.removeById(id);
        return R.ok();
    }

}
