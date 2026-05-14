package model;

import java.util.List;

public class OrderHistoryLogic {
	// 金額合計の計算
	public int calcTotalOrderPrice(List<OrderHistoryInfo> list) {
		int total = 0;
		for (OrderHistoryInfo item : list) {
			total += item.getSubTotal();
		}
		return total;
	}
	
	// 数量合計の計算
	public int calcTotalOrderQuantity(List<OrderHistoryInfo> list) {
		int total = 0;
		for (OrderHistoryInfo item : list) {
			total += item.getOrderQuantity();
		}
		return total;
	}
	
	// ポップアップ表示判定
	public int showPopUp(List<OrderHistoryInfo> list, String action) {
		if (list == null || !"checkOut".equals(action)) return 0;
		
		for (OrderHistoryInfo item : list) {
			if(item.getOrderFlag() == 0) {
				return 1;
			}
		}
		return 2;
	}
}
