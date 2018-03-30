package com.enation.app.shop.core.other.model;

/**
 * 
 * (单品立减促销活动详细实体类) 
 * @author zjp
 * @version v1.0
 * @since v6.2
 * 2017年3月2日 下午4:04:19
 */
public class SingleReductionActivityDetail {

	/** 促销活动详细ID */
	private Integer detail_id;
	
	/** 促销活动ID */
	private Integer activity_id;
	
	
	/** 立减-减多少钱 */
	private Double single_reduction_value;

	public Integer getDetail_id() {
		return detail_id;
	}

	public void setDetail_id(Integer detail_id) {
		this.detail_id = detail_id;
	}

	public Integer getActivity_id() {
		return activity_id;
	}

	public void setActivity_id(Integer activity_id) {
		this.activity_id = activity_id;
	}

	public Double getSingle_reduction_value() {
		return single_reduction_value;
	}

	public void setSingle_reduction_value(Double single_reduction_value) {
		this.single_reduction_value = single_reduction_value;
	}
	
	
}
