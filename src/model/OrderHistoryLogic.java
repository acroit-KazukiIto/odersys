package model;

import java.util.List;

public class OrderHistoryLogic {
	// 金額合計の計算
	public int calcTotalOrderPrice(List<OrderHistoryInfo> list) {
		int total = 0;
		for (OrderHistoryInfo item : list) {
			total += item.getSubTotal();
			System.out.println("合計金額の計算を行いました");
		}
		return total;
	}
	
	// 数量合計の計算
	public int calcTotalOrderQuantity(List<OrderHistoryInfo> list) {
		int total = 0;
		for (OrderHistoryInfo item : list) {
			total += item.getOrderQuantity();
			System.out.println("数量合計の計算を行いました");
		}
		return total;
	}
	
	// ポップアップ表示判定
	public int showPopUp(List<OrderHistoryInfo> list, String action) {
		System.out.println("ポップアップが表示されていません");
		
		if (list == null || !"checkOut".equals(action)) return 0;
		
		for (OrderHistoryInfo item : list) {
			System.out.println("未提供ポップアップ表示");
			if(item.getOrderFlag() == 0) {
				return 1;
			}
		}
		System.out.println("お会計確認(提供済)ポップアップ表示");
		return 2;
	}
}
