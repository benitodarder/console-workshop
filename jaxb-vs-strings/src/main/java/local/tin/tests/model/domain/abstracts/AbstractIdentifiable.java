package local.tin.tests.model.domain.abstracts;

import java.util.Objects;
import local.tin.tests.model.domain.interfaces.IIdentifiable;

/**
 *
 * @author benitodarder
 */
public abstract class AbstractIdentifiable extends AbstractEnableable implements IIdentifiable {

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.getId());
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
        final AbstractIdentifiable other = (AbstractIdentifiable) obj;
        return Objects.equals(this.getId(), other.getId());
    }
}
