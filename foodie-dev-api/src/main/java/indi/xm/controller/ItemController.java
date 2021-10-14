package indi.xm.controller;

import indi.xm.pojo.Items;
import indi.xm.pojo.ItemsImg;
import indi.xm.pojo.ItemsParam;
import indi.xm.pojo.ItemsSpec;
import indi.xm.service.ItemService;
import indi.xm.utils.PagedGridResult;
import indi.xm.utils.XMJSONResult;
import indi.xm.vo.CommentLevelCountVO;
import indi.xm.vo.InfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ProjectName: foodie-dev
 * @Package: indi.xm.controller
 * @ClassName: ItemController
 * @Author: albert.fang
 * @Description: 商品控制器
 * @Date: 2021/10/14 14:49
 */
@Api(value = "商品接口",tags = {"商品信息展示的相关接口"})
@RestController
@RequestMapping("items")
public class ItemController {

    @Resource
    private ItemService itemService;

    @GetMapping("/info/{itemId}")
    @ApiOperation(value = "查询商品详情",notes = "查询商品详情",httpMethod = "GET")
    public XMJSONResult info(
            @ApiParam(name = "itemId",value = "商品id",required = true)
            @PathVariable String itemId){
        if (StringUtils.isBlank(itemId)){
            return XMJSONResult.errorMsg("商品id不能为空");
        }

        Items items = itemService.queryItemById(itemId);
        List<ItemsImg> itemsImgs = itemService.queryItemImgList(itemId);
        List<ItemsSpec> itemsSpecs = itemService.queryItemSpecList(itemId);
        ItemsParam itemsParam = itemService.queryItemParam(itemId);

        InfoVO infoVO = new InfoVO();
        infoVO.setItem(items);
        infoVO.setItemImgList(itemsImgs);
        infoVO.setItemSpecList(itemsSpecs);
        infoVO.setItemParams(itemsParam);
        return XMJSONResult.ok(infoVO);
    }

    @GetMapping("/commentLevel")
    @ApiOperation(value = "查询商品评价等级",notes = "查询商品评价等级",httpMethod = "GET")
    public XMJSONResult commentLevel(
            @ApiParam(name = "itemId",value = "商品id",required = true)
            @RequestParam String itemId){
        if (StringUtils.isBlank(itemId)){
            return XMJSONResult.errorMsg("商品id为null");
        }
        CommentLevelCountVO commentLevelCountVO = itemService.queryCommentCounts(itemId);
        return XMJSONResult.ok(commentLevelCountVO);
    }

    @GetMapping("/comments")
    @ApiOperation(value = "查询商品评论",notes = "查询商品评论",httpMethod = "GET")
    public XMJSONResult comments(
            @ApiParam(name = "itemId",value = "商品id",required = true)
            @RequestParam String itemId,
            @ApiParam(name = "level",value = "评论等级")
            @RequestParam(required = false,defaultValue = "1") Integer level,
            @ApiParam(name = "page",value = "第几页")
            @RequestParam(required = false,defaultValue = "1") Integer page,
            @ApiParam(name = "pageSize",value = "一页大小")
            @RequestParam(required = false,defaultValue = "10") Integer pageSize){
        if (StringUtils.isBlank(itemId)){
            return XMJSONResult.errorMsg("商品id为null");
        }
        PagedGridResult pagedGridResult = itemService.queryPagedComments(itemId, level, page, pageSize);
        return XMJSONResult.ok(pagedGridResult);
    }

    @GetMapping("/search")
    @ApiOperation(value = "商品搜索",notes = "商品搜索",httpMethod = "GET")
    public XMJSONResult search(
            @ApiParam(name = "keywords",value = "关键字",required = true)
            @RequestParam String keywords,
            @ApiParam(name = "sort",value = "优先排序")
            @RequestParam(required = false,defaultValue = "k") String sort,
            @ApiParam(name = "page",value = "第几页")
            @RequestParam(required = false,defaultValue = "1") Integer page,
            @ApiParam(name = "pageSize",value = "一页大小")
            @RequestParam(required = false,defaultValue = "20") Integer pageSize){
        if (StringUtils.isBlank(keywords)){
            return XMJSONResult.errorMsg("关键字为空");
        }
        PagedGridResult pagedGridResult = itemService.searchItems(keywords, sort, page, pageSize);
        return XMJSONResult.ok(pagedGridResult);
    }
}
