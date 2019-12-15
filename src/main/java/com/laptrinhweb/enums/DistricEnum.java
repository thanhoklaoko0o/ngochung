package com.laptrinhweb.enums;

public enum DistricEnum {
	 QUAN_1("Quận 1"),
	  QUAN_2("Quận 2"),
	    QUAN_3("Quận 3"),
	    QUAN_4("Quận 4");
	    

	    private String value;

	    DistricEnum(String value) {
	        this.value = value;
	    }

		public String getValue() {
			return value;
		}

		

	   
}
