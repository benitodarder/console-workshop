package local.tin.tests.model.domain.interfaces;

/**
 *
 * @author benitodarder
 */
public interface IEnableable extends IDomain {
    
    /**
     * Returns enabled/disabled status
     * 
     * @return boolean
     */
    public boolean isEnabled();
    
    /**
     * Sets enabled/disabled status
     * 
     * @param enabled boolean
     */
    public void setEnabled(boolean enabled);    
    
}
