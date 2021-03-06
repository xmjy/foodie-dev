package indi.xm.service;

import indi.xm.pojo.Category;
import indi.xm.vo.CategoryVO;
import indi.xm.vo.NewItemsVO;

import java.util.List;

/**
 * @ProjectName: foodie-dev
 * @Package: indi.xm.service
 * @ClassName: CategoryService
 * @Author: albert.fang
 * @Description: 首页分类
 * @Date: 2021/10/14 10:44
 */
public interface CategoryService {

    /**
     * 查询所有一级分类
     *
     * @return
     */
    public List<Category> queryAllRootLevelCats();

    /**
     * 根据一级分类id自连接查询自己分类
     *
     * @param rootCatId
     * @return
     */
    public List<CategoryVO> getSubCatList(Integer rootCatId);

    /**
     * 查询首页每个一级分类下的6条最新商品数据
     *
     * @param rootCatId
     * @return
     */
    public List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId);
}
