package com.tenco.bank.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class TimeUtils {
	
	// Timestamp --> String 변경하는 코드 작성
	public static String timestampToString(Timestamp timestamp) {
		// yyyy-MM-dd HH:mm:ss
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(timestamp);
	}

}
