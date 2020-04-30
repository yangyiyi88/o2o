package com.imooc.o2o.service;

public interface CacheService {
    /**
     * 依据key前缀删除匹配模式下的所有key-value 如传入shopcategory则首批 category_allfirstlevel等
     * 以shopcategory打头的key_value都会被清空
     * @param keyPrefix
     */
    void removeFromCache(String keyPrefix);
}
