package ooo.gyoo.speedrunwrs.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.Duration;

public class NumberToDurationDeserializer extends StdDeserializer<Duration> {

    public NumberToDurationDeserializer(){
        this(null);
    }

    public NumberToDurationDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Duration deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        long duration = jsonParser.getLongValue();
        return Duration.ofSeconds(duration);
    }
}
