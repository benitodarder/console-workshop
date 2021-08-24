package local.tin.tests.stream.vs.forLoop;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import local.tin.tests.stream.vs.forLoop.model.Container;
import local.tin.tests.stream.vs.forLoop.model.Item;
import local.tin.tests.stream.vs.forLoop.model.ItemIdentifiedByString;
import local.tin.tests.stream.vs.forLoop.utils.ItemToItemWithString;
import local.tin.tests.stream.vs.forLoop.utils.Utils;
import org.apache.log4j.Logger;

/**
 * Find unique items and convert
 *
 * @author benitodarder
 */
public class FindUniquesAndConvertClassic {

    private static final Logger LOGGER = Logger.getLogger(FindUniquesAndConvertClassic.class);

    public static void main(String[] args) throws CloneNotSupportedException {
        long t0 = System.currentTimeMillis();
        LOGGER.info("Let's get some containers with items,find the unique ones and convert them.");
        Collection<Container> containers = Utils.getInstance().getContainers03Items04();
        LOGGER.info("Containers: " + containers.size());
        for (Container mainContainer : containers) {
            LOGGER.info(mainContainer + ", items: " + mainContainer.getItems());
        }
        Set<Item> uniques = new HashSet<>();
        for (Container mainContainer : containers) {
            for (Item container01Item : mainContainer.getItems()) {
                uniques.add(container01Item);
            }
        }
        LOGGER.info("Uniques: " + uniques);
        ItemToItemWithString converter = new ItemToItemWithString();
        Set<ItemIdentifiedByString> itemsWithString = new HashSet<>();
        for (Item item : uniques) {
            itemsWithString.add(converter.convert(item));
        }
        LOGGER.info("Converted uniques: " + itemsWithString);
        LOGGER.info("And all this in: " + (System.currentTimeMillis() - t0));
        LOGGER.info("That's all folks!");
    }

}
