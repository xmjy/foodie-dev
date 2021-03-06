package indi.xm.controller;

import indi.xm.enums.YesOrNoEnum;
import indi.xm.pojo.Carousel;
import indi.xm.pojo.Category;
import indi.xm.service.CarouselService;
import indi.xm.service.CategoryService;
import indi.xm.utils.XMJSONResult;
import indi.xm.vo.CategoryVO;
import indi.xm.vo.NewItemsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ProjectName: foodie-dev
 * @Package: indi.xm.controller
 * @ClassName: IndexController
 * @Author: albert.fang
 * @Description: 首页控制器
 * @Date: 2021/10/14 10:18
 */
@Api(value = "首页",tags = {"首页展示的相关接口"})
@RestController
@RequestMapping("index")
public class IndexController {

    @Resource
    private CarouselService carouselService;

    @Resource
    private CategoryService categoryService;

    @GetMapping("/carousel")
    @ApiOperation(value = "首页轮播图",notes = "首页轮播图",httpMethod = "GET")
    public XMJSONResult carousel(){
        List<Carousel> carousels = carouselService.queryAll(YesOrNoEnum.YES.type);
        return XMJSONResult.ok(carousels);
    }

    /**
     * 首页分类展示需求
     * 1、第一次刷新主页查询大分类，渲染展示到首页
     * 2、如果鼠标上移到大分类，则加载其子分类内容，如果存在子分类，则不需要加载（懒加载模式）
     */
    @GetMapping("/cats")
    @ApiOperation(value = "加载首页大分类",notes = "加载首页大分类",httpMethod = "GET")
    public XMJSONResult cats(){
        List<Category> categories = categoryService.queryAllRootLevelCats();
        return XMJSONResult.ok(categories);
    }

    @GetMapping("/subCat/{rootCatId}")
    @ApiOperation(value = "获取商品子分类",notes = "获取上面子分类",httpMethod = "GET")
    public XMJSONResult subCat(@ApiParam(name = "rootCatId",value = "一级分类id",required = true)
                                   @PathVariable Integer rootCatId){
        if (rootCatId == null){
            return XMJSONResult.errorMsg("分类不存在");
        }
        List<CategoryVO> list = categoryService.getSubCatList(rootCatId);
        return XMJSONResult.ok(list);
    }

    @GetMapping("/sixNewItems/{rootCatId}")
    @ApiOperation(value = "查询每个一级分类下的最新6个商品数据",notes = "查询每个一级分类下的最新6个商品数据",httpMethod = "GET")
    public XMJSONResult sixNewItems(@ApiParam(name = "rootCatId",value = "一级分类id",required = true)
                               @PathVariable Integer rootCatId){
        if (rootCatId == null){
            return XMJSONResult.errorMsg("分类不存在");
        }
        List<NewItemsVO> list = categoryService.getSixNewItemsLazy(rootCatId);
        return XMJSONResult.ok(list);
    }
}