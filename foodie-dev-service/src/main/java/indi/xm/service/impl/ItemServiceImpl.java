package indi.xm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import indi.xm.enums.CommentLevelEnum;
import indi.xm.enums.YesOrNoEnum;
import indi.xm.mapper.*;
import indi.xm.pojo.*;
import indi.xm.service.ItemService;
import indi.xm.utils.DesensitizationUtil;
import indi.xm.utils.PagedGridResult;
import indi.xm.vo.CommentLevelCountVO;
import indi.xm.vo.ItemCommentVO;
import indi.xm.vo.SearchItemsVO;
import indi.xm.vo.ShopCartVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

/**
 * @ProjectName: foodie-dev
 * @Package: indi.xm.service.impl
 * @ClassName: ItemServiceImpl
 * @Author: albert.fang
 * @Description:
 * @Date: 2021/10/14 14:09
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Resource
    private ItemsMapper itemsMapper;

    @Resource
    private ItemsImgMapper itemsImgMapper;

    @Resource
    private ItemsSpecMapper itemsSpecMapper;

    @Resource
    private ItemsParamMapper itemsParamMapper;

    @Resource
    private ItemsCommentsMapper itemsCommentsMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Items queryItemById(String itemId) {
        return itemsMapper.selectByPrimaryKey(itemId);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ItemsImg> queryItemImgList(String itemId) {
        Example example = new Example(ItemsImg.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId",itemId);
        return itemsImgMapper.selectByExample(example);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ItemsSpec> queryItemSpecList(String itemId) {
        Example example = new Example(ItemsSpec.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId",itemId);
        return itemsSpecMapper.selectByExample(example);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ItemsParam queryItemParam(String itemId) {
        Example example = new Example(ItemsParam.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId",itemId);
        return itemsParamMapper.selectOneByExample(example);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public CommentLevelCountVO queryCommentCounts(String itemId) {
        Integer goodCounts = getCommentCounts(itemId, CommentLevelEnum.GOOD.level);
        Integer normalCounts = getCommentCounts(itemId, CommentLevelEnum.NORMAL.level);
        Integer badCounts = getCommentCounts(itemId, CommentLevelEnum.BAD.level);
        Integer totalCounts = goodCounts + normalCounts + badCounts;

        // ??????VO,??????????????????
        CommentLevelCountVO commentLevelCountVO = new CommentLevelCountVO();
        commentLevelCountVO.setGoodCounts(goodCounts);
        commentLevelCountVO.setNormalCounts(normalCounts);
        commentLevelCountVO.setBadCounts(badCounts);
        commentLevelCountVO.setTotalCounts(totalCounts);
        return commentLevelCountVO;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PagedGridResult queryPagedComments(
                                        String itemId, Integer level,
                                        Integer page,Integer pageSize) {
        Map<String,Object> map = new HashMap<>();
        map.put("itemId",itemId);
        map.put("level",level);
        
        // mybatis-pagehelper ????????????
        // ?????????????????????????????????????????? - ????????????sql???????????????????????????
        PageHelper.startPage(page,pageSize);
        List<ItemCommentVO> list = itemsCommentsMapper.queryItemComments(map);

        // ????????????
        for (ItemCommentVO vo : list) {
            vo.setNickname(DesensitizationUtil.commonDisplay(vo.getNickname()));
        }
        return setterPageGrid(list,page);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PagedGridResult searchItems(
            String keywords,String sort, Integer page, Integer pageSize) {
        Map<String,Object> map = new HashMap<>();
        map.put("keywords",keywords);
        map.put("sort",sort);

        // mybatis-pagehelper ????????????
        // ?????????????????????????????????????????? - ????????????sql???????????????????????????
        PageHelper.startPage(page,pageSize);
        List<SearchItemsVO> list = itemsMapper.searchItems(map);

        return setterPageGrid(list,page);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PagedGridResult searchItemsByThirdCat(
            String catId,String sort, Integer page, Integer pageSize) {
        Map<String,Object> map = new HashMap<>();
        map.put("catId",catId);
        map.put("sort",sort);

        // mybatis-pagehelper ????????????
        // ?????????????????????????????????????????? - ????????????sql???????????????????????????
        PageHelper.startPage(page,pageSize);
        List<SearchItemsVO> list = itemsMapper.searchItemsByThirdCat(map);

        return setterPageGrid(list,page);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ShopCartVO> queryItemsBySpecIds(String specIds) {
        String[] ids = specIds.split(",");
        List<String> specIdList = new ArrayList<>();
        // ??? ids ??????????????? specIdList ???
        Collections.addAll(specIdList,ids);
        return itemsMapper.queryItemsBySpecIds(specIdList);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ItemsSpec queryItemSpecById(String specId) {
        return itemsSpecMapper.selectByPrimaryKey(specId);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public String queryItemMainImgById(String itemId) {
        ItemsImg itemsImg = new ItemsImg();
        itemsImg.setItemId(itemId);
        itemsImg.setIsMain(YesOrNoEnum.YES.type);
        ItemsImg result = itemsImgMapper.selectOne(itemsImg);
        return result != null ? result.getUrl() : "";
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void decreaseItemSpecStock(String specId, int buyCounts) {

        // lockUtil.getLock(); ??????

        // 1???????????????
//        int stock = itemsSpecMapper.selectByPrimaryKey(specId).getStock();

        // 2????????????????????????????????????0?????? (???????????? ????????????????????????)
        // (1) ??????????????? synchronized ?????????????????????????????????
        // (2) ??????????????????????????????????????????????????????
        // (3) ???????????? zookeeper + redis (???????????????)


        // (4) ??????????????????????????????????????????
        int resStock = itemsMapper.decreaseItemSpecStock(specId, buyCounts);

        if (resStock != 1){
            throw new RuntimeException("??????????????????????????????????????????");
        }

        // lockUtil.unLock(); ??????
    }

    private PagedGridResult setterPageGrid(List<?> list, Integer page){
        // ????????????
        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult gridResult = new PagedGridResult();
        // ???????????????
        gridResult.setPage(page);
        // list ??????????????????
        gridResult.setRows(list);
        // total ?????????
        gridResult.setTotal(pageList.getPages());
        // records ????????????
        gridResult.setRecords(pageList.getTotal());
        return gridResult;
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    Integer getCommentCounts(String itemId, Integer level){
        ItemsComments condition = new ItemsComments();
        condition.setItemId(itemId);
        if (level != null){
            condition.setCommentLevel(level);
        }
        return itemsCommentsMapper.selectCount(condition);
    }
}
