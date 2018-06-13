package com.n26Test.deserializer;

import java.io.IOException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * @author shaik Fareed
 * 
 * This class is responsible for deserializing the long timestamp to date object
 *
 */
public class UnixTimestampDeserializer extends JsonDeserializer<Date> {
    Logger logger = LoggerFactory.getLogger(UnixTimestampDeserializer.class);

    /* (non-Javadoc)
     * @see com.fasterxml.jackson.databind.JsonDeserializer#deserialize(com.fasterxml.jackson.core.JsonParser, com.fasterxml.jackson.databind.DeserializationContext)
     */
    @Override
	public Date deserialize(JsonParser parser, DeserializationContext context) 
            throws IOException, JsonProcessingException {
        Long unixTimestamp = parser.getLongValue();
        return new Date(unixTimestamp);
    }
}
