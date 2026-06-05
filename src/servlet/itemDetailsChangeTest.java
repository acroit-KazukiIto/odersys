package servlet;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import model.ItemDetailsInfo;
import model.ItemDetailsLogic;

class ItemDetailsLogicTest {

    @Test
    void testCalcSubTotal() {

        ItemDetailsLogic logic = new ItemDetailsLogic();

        List<ItemDetailsInfo> list = new ArrayList<>();

        ItemDetailsInfo t1 = new ItemDetailsInfo();
        t1.setToppingPrice(100);
        t1.setToppingQuantity(2);

        list.add(t1);

        int result = logic.calcSubTotal(300, list);

        assertEquals(500, result);
    }
}
