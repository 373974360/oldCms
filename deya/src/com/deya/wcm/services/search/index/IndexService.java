package com.deya.wcm.services.search.index;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.deya.wcm.dao.search.IIndexInfoDao;
import com.deya.wcm.dao.search.impl.IndexInfoDaoImpl;
import com.deya.wcm.services.search.SearchForManager;

/**
 * <p>Title: 组件索引操作接口</p>
 * <p>Description: 组件索引操作接口,每个需要被搜索的组件都要提供相应的实现类</p>
 * 本索引实现类使用的字符编码必须为UTF-8
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author lisp
 * @version 1.0
 */
public interface IndexService {
	
	/**
     * 通过site_id创建该类信息的全部索引
     * @param infos Hashtable  输入信息
     * @return boolean
     */
    public boolean appendALlDocument(Map map);
	
	/**
     * 通过site_id删除该类信息的全部索引
     * @param infos Hashtable  输入信息
     * @return boolean
     */
    public boolean deleteALlDocument(Map map);
    
    /**
     * 往索引文件中追加一条记录
     * @param infos Hashtable  输入信息,如信息id,信息content等
     * @return boolean
     */
    public boolean appendSingleDocument(Map infos);
    
    /**
     * 删除索引文件中的一条索引
     * @param infos Hashtable  输入信息,如信息id等
     * @return boolean
     */
    public boolean deleteSingleDocument(Map infos);
	
}
