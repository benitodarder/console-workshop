package local.tin.tests.stream.vs.forLoop.utils;

import local.tin.tests.stream.vs.forLoop.model.Item;
import local.tin.tests.stream.vs.forLoop.model.ItemIdentifiedByString;

/**
 *
 * @author benitodarder
 */
public class ItemToItemWithString {
    
    public ItemIdentifiedByString convert(Item item) {
        ItemIdentifiedByString itemIdentifiedByString = new ItemIdentifiedByString();
        itemIdentifiedByString.setId(String.valueOf(item.getId()));
        itemIdentifiedByString.setName(item.getName());
        return itemIdentifiedByString;
    }
    
    
}
