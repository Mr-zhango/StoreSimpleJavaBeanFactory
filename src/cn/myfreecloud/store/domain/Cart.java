package cn.myfreecloud.store.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * session购物车的实现类
 */
public class Cart {
    //这里在初始化对象的时候就创建了购物车的map集合
    private Map<String, CartItem> items = new HashMap<>();//购物项集合
    //这里在初始化对象的时候就初始化总金额
    private Double total = 0.0;//总金额

    /**
     * 注意这里的items是经过.values方法处理的,得到的就是map对应的值的集合,方便前台直接获取购物项list
     * @return
     */
    public Collection<CartItem> getItems() {
        return items.values();
    }

    public void setItems(Map<String, CartItem> items) {
        this.items = items;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    /**
     * 添加购物项
     *
     * @param ci
     */
    public void addCartItem(CartItem ci) {
        String pid = ci.getProduct().getPid();
        if (items.containsKey(pid)) {
            //表示之前已经有
            CartItem oriItem = items.get(pid);//获取原来的
            oriItem.setNum(oriItem.getNum() + ci.getNum());
        } else {
            items.put(pid, ci);
        }
        //总金额
        total += ci.getSubTotal();
    }

    /**
     * 添加购物项
     *
     * @param ci
     */
    public void removeCartItem(String pid) {
        CartItem remove = items.remove(pid);
        //总金额要减去移除的
        total -= remove.getSubTotal();
    }

    /**
     * 清空购物车
     */
    public void clear() {
        items.clear();
        total = 0.0;
    }


}
