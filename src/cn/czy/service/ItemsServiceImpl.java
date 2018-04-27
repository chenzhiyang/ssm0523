package cn.czy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.czy.dao.ItemsMapper;
import cn.czy.pojo.Items;
import cn.czy.pojo.ItemsExample;

@Service
public class ItemsServiceImpl implements ItemsService {
	@Autowired
	private ItemsMapper itemsMapper;

	@Override
	public List<Items> list() throws Exception {
		//如果不需要任何查询条件，把example对象new出来即可
		ItemsExample example=new ItemsExample();
		List<Items> list=itemsMapper.selectByExampleWithBLOBs(example);
		return list;
	}

	@Override
	public Items findItemById(int id) throws Exception {
		Items item = itemsMapper.selectByPrimaryKey(id);
		return item;
	}

	@Override
	public void updateItems(Items items) throws Exception {
		itemsMapper.updateByPrimaryKeyWithBLOBs(items);
		
	}
}
