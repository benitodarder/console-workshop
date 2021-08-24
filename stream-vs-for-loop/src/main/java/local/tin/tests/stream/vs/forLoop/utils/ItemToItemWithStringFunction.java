package local.tin.tests.stream.vs.forLoop.utils;

import java.util.function.Function;
import local.tin.tests.stream.vs.forLoop.model.Item;
import local.tin.tests.stream.vs.forLoop.model.ItemIdentifiedByString;

/**
 *
 * @author benitodarder
 */
public class ItemToItemWithStringFunction implements Function<Item, ItemIdentifiedByString>{

    @Override
    public ItemIdentifiedByString apply(Item t) {
        ItemToItemWithString converter = new ItemToItemWithString();
        return converter.convert(t);
    }
    
}
