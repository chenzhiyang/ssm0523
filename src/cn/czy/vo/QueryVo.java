package cn.czy.vo;

import java.util.List;

import cn.czy.pojo.Items;

public class QueryVo {

	//商品对象
	private Items items;
	//订单对象。。。

	private Integer[] ids;
	private List<Items> itemsList;
	
	public Items getItems() {
		return items;
	}

	public void setItems(Items items) {
		this.items = items;
	}

	public List<Items> getItemsList() {
		return itemsList;
	}

	public void setItemsList(List<Items> itemsList) {
		this.itemsList = itemsList;
	}

	public Integer[] getIds() {
		return ids;
	}

	public void setIds(Integer[] ids) {
		this.ids = ids;
	}
	
	
}
