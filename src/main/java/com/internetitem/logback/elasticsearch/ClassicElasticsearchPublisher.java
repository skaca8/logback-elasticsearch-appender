package com.internetitem.logback.elasticsearch;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Context;
import com.fasterxml.jackson.core.JsonGenerator;
import com.internetitem.logback.elasticsearch.config.ElasticsearchProperties;
import com.internetitem.logback.elasticsearch.config.HttpRequestHeaders;
import com.internetitem.logback.elasticsearch.config.Property;
import com.internetitem.logback.elasticsearch.config.Settings;
import com.internetitem.logback.elasticsearch.util.AbstractPropertyAndEncoder;
import com.internetitem.logback.elasticsearch.util.ClassicPropertyAndEncoder;
import com.internetitem.logback.elasticsearch.util.ErrorReporter;
import com.internetitem.logback.elasticsearch.util.MaskingPatternLayout;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class ClassicElasticsearchPublisher extends AbstractElasticsearchPublisher<ILoggingEvent> {
	public ClassicElasticsearchPublisher(Context context, ErrorReporter errorReporter, Settings settings, ElasticsearchProperties properties, HttpRequestHeaders headers) throws IOException {
		super(context, errorReporter, settings, properties, headers);
	}
	
	protected AbstractPropertyAndEncoder<ILoggingEvent> buildPropertyAndEncoder(Context context, Property property) {
		return new ClassicPropertyAndEncoder(property, context);
	}
	
	protected void serializeCommonFields(JsonGenerator gen, ILoggingEvent event) throws IOException {
		gen.writeObjectField("@timestamp", getTimestamp(event.getTimeStamp()));
		if (this.settings.isRawJsonMessage()) {
			gen.writeFieldName("message");
			gen.writeRawValue(MaskingPatternLayout.maskMessage(event.getFormattedMessage()));
		} else {
			String formattedMessage = event.getFormattedMessage();
			if (this.settings.getMaxMessageSize() > 0 && formattedMessage.length() > this.settings.getMaxMessageSize()) {
				formattedMessage = formattedMessage.substring(0, this.settings.getMaxMessageSize()) + "..";
			}
			gen.writeObjectField("message", MaskingPatternLayout.maskMessage(formattedMessage));
		}
		
		if (this.settings.isIncludeMdc()) {
			Iterator var5 = event.getMDCPropertyMap().entrySet().iterator();
			
			while (var5.hasNext()) {
				Map.Entry<String, String> entry = (Map.Entry) var5.next();
				gen.writeObjectField((String) entry.getKey(), entry.getValue());
			}
		}
		
	}
}
