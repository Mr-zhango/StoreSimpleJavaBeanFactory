package cn.myfreecloud.store.domain;

public class CartItem {
	private Product product;//关联的商品
	private int num;//购买的数量
	private Double subTotal=0.0;//小计
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public Double getSubTotal() {
		return product.getShop_price()*num;
	}
	public CartItem(Product product, int num) {
		super();
		this.product = product;
		this.num = num;
	}
	public CartItem() {
		super();
	}
	
	
}
