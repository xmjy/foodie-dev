package indi.xm.mapper;

import indi.xm.my.mapper.MyMapper;
import indi.xm.pojo.Items;
import indi.xm.vo.SearchItemsVO;
import indi.xm.vo.ShopCartVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsMapper extends MyMapper<Items> {

    /**
     * 搜索商品
     *
     * @param map
     */
    public List<SearchItemsVO> searchItems(@Param("map") Map<String,Object> map);

    /**
     * 根据分类id 搜索商品
     *
     * @param map
     */
    public List<SearchItemsVO> searchItemsByThirdCat(@Param("map") Map<String,Object> map);

    /**
     * 根据商品规格ids查找商品信息集合
     *
     * @param specIdList
     * @return
     */
    public List<ShopCartVO> queryItemsBySpecIds(@Param("specIdList") List<String> specIdList);

    /**
     * 通过乐观锁来更改库存
     *
     * @param specId
     * @param buyCounts
     */
    public int decreaseItemSpecStock(@Param("specId") String specId,@Param("buyCounts") int buyCounts);
}