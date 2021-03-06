package indi.xm.service.impl;

import indi.xm.mapper.CarouselMapper;
import indi.xm.pojo.Carousel;
import indi.xm.service.CarouselService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ProjectName: foodie-dev
 * @Package: indi.xm.service.impl
 * @ClassName: CarouselServiceImpl
 * @Author: albert.fang
 * @Description: 轮播图
 * @Date: 2021/10/14 10:15
 */
@Service
public class CarouselServiceImpl implements CarouselService {

    @Resource
    private CarouselMapper carouselMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Carousel> queryAll(Integer isShow) {
        Example example = new Example(Carousel.class);
        example.orderBy("sort").asc();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isShow",isShow);
        return carouselMapper.selectByExample(example);
    }
}
