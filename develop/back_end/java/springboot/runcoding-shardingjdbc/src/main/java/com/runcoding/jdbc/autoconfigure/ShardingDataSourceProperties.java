
package com.github.benyzhous.springboot.sharding.jdbc.autoconfigure;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sharding.jdbc")
public class ShardingDataSourceProperties {
	private List<Map<String, String>> masters;

	public List<Map<String, String>> getMasters() {
		return masters;
	}

	public void setMasters(List<Map<String, String>> masters) {
		this.masters = masters;
	}

}
