package com.internetitem.logback.elasticsearch.util;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.pattern.PatternLayoutBase;
import com.internetitem.logback.elasticsearch.config.Property;

public class ClassicPropertyAndEncoder extends AbstractPropertyAndEncoder<ILoggingEvent> {
	
	public ClassicPropertyAndEncoder(Property property, Context context) {
		super(property, context);
	}
	
	@Override
	protected PatternLayoutBase<ILoggingEvent> getLayout() {
		MaskingPatternLayout maskingPatternLayout = new MaskingPatternLayout();
		maskingPatternLayout.addMaskPattern("\\\"phoneNumber\\\"\\s*:\\s*\\\"(.*?)\\\"");
		return maskingPatternLayout;
//        return new PatternLayout();
	}
}
