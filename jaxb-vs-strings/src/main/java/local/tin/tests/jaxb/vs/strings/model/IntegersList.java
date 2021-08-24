package local.tin.tests.jaxb.vs.strings.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import local.tin.tests.jaxb.vs.strings.model.databinding.DateJSONSerializer;
import local.tin.tests.model.domain.abstracts.AbstractDomain;

/**
 *
 * @author benitodarder
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class IntegersList extends AbstractDomain {

    @XmlElementWrapper(name = "values")
    @XmlElement(name = "integer")
    private List<Integer> integers;
    private Integer result;
    private Operation operation;
    @JsonSerialize(using = DateJSONSerializer.class)
    private Date timestamp;
    
    public List<Integer> getIntegers() {
        if (integers == null) {
            integers = new ArrayList<>();
        }
        return integers;
    }

    public void setIntegers(List<Integer> integers) {
        this.integers = integers;
    }

    public Integer getResult() {
        if (result == null) {
            result = 0;
        }
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    
}
