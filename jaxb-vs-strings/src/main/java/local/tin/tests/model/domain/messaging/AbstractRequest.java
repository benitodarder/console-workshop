package local.tin.tests.model.domain.messaging;

import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import local.tin.tests.model.domain.abstracts.AbstractDomain;
import local.tin.tests.model.domain.abstracts.AbstractIdentifiable;
import local.tin.tests.model.domain.interfaces.IMessage;

/**
 *
 * @author benito.darder
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({AbstractDomain.class})
public abstract class AbstractRequest implements IMessage {

    private String modelClass;
    @XmlElementRef
    private AbstractIdentifiable item;

    public String getModelClass() {
        return modelClass;
    }

    public void setModelClass(String modelClass) {
        this.modelClass = modelClass;
    }

    public AbstractIdentifiable getItem() {
        return item;
    }

    public void setItem(AbstractIdentifiable item) {
        this.item = item;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.modelClass);
        hash = 89 * hash + Objects.hashCode(this.item);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractRequest other = (AbstractRequest) obj;
        if (!Objects.equals(this.modelClass, other.modelClass)) {
            return false;
        }
        return Objects.equals(this.item, other.item);
    }
}
