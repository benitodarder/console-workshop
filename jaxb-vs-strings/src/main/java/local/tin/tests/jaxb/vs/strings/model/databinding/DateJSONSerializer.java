package local.tin.tests.jaxb.vs.strings.model.databinding;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.util.Date;
import local.tin.tests.jaxb.vs.strings.Utils;

/**
 *
 * @author benitodarder
 */
public class DateJSONSerializer extends StdSerializer<Date> {
     
    public DateJSONSerializer() {
        this(null);
    }
   
    public DateJSONSerializer(Class<Date> t) {
        super(t);
    }
 
    @Override
    public void serialize(Date t, JsonGenerator jg, SerializerProvider sp) throws IOException {
        jg.writeStartObject();
        jg.writeStringField("timestamp", local.tin.tests.jaxb.vs.strings.Utils.getInstance().getISO8601Date(t));
        jg.writeEndObject();
    }
}
