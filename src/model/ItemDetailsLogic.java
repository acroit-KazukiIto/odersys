package model;

import java.util.List;

public class ItemDetailsLogic {
    public void calcToppingQuantity(List<ItemDetailsInfo> toppingList, int index, String action) {
        if (toppingList == null || index < 0 || index >= toppingList.size()) {
            return;
        }

        ItemDetailsInfo target = toppingList.get(index);
        int currentQty = target.getToppingQuantity();

        if ("plus".equals(action)) {
            // 20個以上にいかない
            if (currentQty < 20 && currentQty < target.getToppingStock()) {
                target.setToppingQuantity(currentQty + 1);
            }
        } else if ("minus".equals(action)) {
            //0以下は減らさない
            if (currentQty > 0) {
                target.setToppingQuantity(currentQty - 1);
            }
        }
    }
    // 小計の計算
    public int calcSubTotal(int productPrice, List<ItemDetailsInfo> toppingList) {
        int total = productPrice;
        if (toppingList != null) {
            for (ItemDetailsInfo t : toppingList) {
                total += (t.getToppingPrice() * t.getToppingQuantity());
            }
        }
        return total;
    }
}