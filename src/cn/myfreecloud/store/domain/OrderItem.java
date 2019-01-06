package cn.myfreecloud.store.domain;

public class OrderItem {
		//订单项id
		private String itemid;
		//订单项数量
		private Integer count;
		//订单项小计
		private Double subtotal;
		private String pid;
		private String oid;
		//表示包含那个商品 
		private transient Product product;
		//表示属于那个订单
		private transient Order order;
		public String getItemid() {
			return itemid;
		}
		public void setItemid(String itemid) {
			this.itemid = itemid;
		}
		public Integer getCount() {
			return count;
		}
		public void setCount(Integer count) {
			this.count = count;
		}
		public Double getSubtotal() {
			return subtotal;
		}
		public void setSubtotal(Double subtotal) {
			this.subtotal = subtotal;
		}
		public String getPid() {
			return pid;
		}
		public void setPid(String pid) {
			this.pid = pid;
		}
		public String getOid() {
			return oid;
		}
		public void setOid(String oid) {
			this.oid = oid;
		}
		public Product getProduct() {
			return product;
		}
		public void setProduct(Product product) {
			this.product = product;
		}
		public Order getOrder() {
			return order;
		}
		public void setOrder(Order order) {
			this.order = order;
		}
		
		
		
}
