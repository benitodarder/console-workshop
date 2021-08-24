package local.tin.tests.model.domain.abstracts;

import local.tin.tests.model.domain.interfaces.IEnableable;

/**
 *
 * @author benitodarder
 */
public abstract class AbstractEnableable implements IEnableable {
    
    private boolean enabled;

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }    

    
}
