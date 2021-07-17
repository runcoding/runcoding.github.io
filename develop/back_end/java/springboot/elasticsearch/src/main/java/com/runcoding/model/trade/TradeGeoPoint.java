package com.runcoding.model.trade;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeGeoPoint {

	/**
	 * 地区
	 */
	@Field()
	private String name;

	/**
	 * 纬度
	 */
	@Field(type = FieldType.Double)
	private Double latitude;
	/**
	 * 精度
	 */
	@Field(type = FieldType.Double)
	private Double longitude;


}