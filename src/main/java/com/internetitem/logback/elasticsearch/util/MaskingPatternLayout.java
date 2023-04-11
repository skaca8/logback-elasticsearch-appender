package com.internetitem.logback.elasticsearch.util;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.internetitem.logback.elasticsearch.constant.LoggingConstant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class MaskingPatternLayout extends PatternLayout {
	public void addMaskPattern(String maskPattern) {
		Boolean isDuplicated = LoggingConstant.MAKS_PATTERNS.stream()
									   .anyMatch(s -> s.equals(maskPattern));
		
		if (Boolean.TRUE.equals(isDuplicated)) {
			return;
		}
		
		LoggingConstant.MAKS_PATTERNS.add(maskPattern);
		LoggingConstant.APLPLIED_PATTERN = Pattern.compile(String.join("|", LoggingConstant.MAKS_PATTERNS), Pattern.MULTILINE);
	}
	
	@Override
	public String doLayout(ILoggingEvent event) {
		return maskMessage(super.doLayout(event));
	}
	
	public static String maskMessage(String message) {
		if (LoggingConstant.APLPLIED_PATTERN == null) {
			return message;
		}
		StringBuilder sb = new StringBuilder(message);
		Matcher matcher = LoggingConstant.APLPLIED_PATTERN.matcher(sb);
		while (matcher.find()) {
			IntStream.rangeClosed(1, matcher.groupCount()).forEach(group -> {
				if (matcher.group(group) != null) {
					IntStream.range(matcher.start(group),
							matcher.end(group)).forEach(i -> sb.setCharAt(i, '*'));
				}
			});
		}
		return sb.toString();
	}
}
