package net.qmdboss.enums;

public enum HongbaoType {
	/**
	 * 注册红包
	 */
	 REGISTER(1,1,"注册红包"),
	/**
	 * 土豪奖
	 */
	WEALTHY(4,4,"土豪奖"),
	/**
	 * 截标奖
	 */
	FINISH_BORROW(5,5,"截标奖");
	
	private	int id;// 红包id
	private int type;//红包类型
	private String typeName;//红包类型名
	
	private HongbaoType(int id, int type, String typeName) {
		this.id = id;
		this.type = type;
		this.typeName = typeName;
	}

	/**
	 * 通过红包类型 得到 红包类型名称
	 * @param type
	 * @return
	 */
	public String getNameByType(Integer type){
		if(type!=null){
			for(HongbaoType hongbao:HongbaoType.values()){
				if(hongbao.getType()==type){
					return hongbao.getTypeName();
				}
			}
		}
		return null;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	
	
	
}
