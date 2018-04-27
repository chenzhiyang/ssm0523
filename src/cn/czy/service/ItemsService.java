package cn.czy.service;

import java.util.List;

import cn.czy.pojo.Items;

public interface ItemsService {

	public List<Items> list() throws Exception;
	
	public Items findItemById(int id) throws Exception;
	
	public void updateItems(Items items) throws Exception;
}
