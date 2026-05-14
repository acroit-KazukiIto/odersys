package model;

import java.util.List;

public class OrderHistoryLogic {
	// 金額合計
	public int calcTotalOrderPrice(List<OrderHistoryInfo> list) {
		int total = 0;
		for (OrderHistoryInfo item : list) {
			total += item.getSubTotal();
		}
		return total;
	}
	
	// 数量合計
	public int calcTotalOrderQuantity(List<OrderHistoryInfo> list) {
		int total = 0;
		for (OrderHistoryInfo item : list) {
			total += item.getOrderQuantity();
		}
		return total;
	}
	
	// 全て提供済みかチェック
	public boolean hasUnprovidedItems(List<OrderHistoryInfo> list) {
		for (OrderHistoryInfo item : list) {
			if (item.getOrderFlag() == 0) { // 0:未提供
				return true;
			}
		}
		return false;
	}
}
