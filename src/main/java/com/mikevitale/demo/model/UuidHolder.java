package com.mikevitale.demo.model;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UuidHolder {
	public static final String UUID_REGEX = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[89ABab][0-9a-fA-F]{3}-[0-9a-fA-F]{12}";

	@Pattern(regexp = UUID_REGEX, message = "Allows Version UUID format")
	@Size(min = 36, max = 36, message = "Allows 36 characters")
	public String uuid;

	public UuidHolder() {}

	public UuidHolder(String uuid) {
		this.uuid = uuid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public String toString() {
		return "UuidHolder{" +
		       "uuid='" + uuid + '\'' +
		       '}';
	}
}
