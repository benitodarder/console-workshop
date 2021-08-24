package local.tin.tests.stream.vs.forLoop;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import local.tin.tests.stream.vs.forLoop.model.Container;
import local.tin.tests.stream.vs.forLoop.model.Item;
import local.tin.tests.stream.vs.forLoop.model.ItemIdentifiedByString;
import local.tin.tests.stream.vs.forLoop.utils.ItemToItemWithString;
import local.tin.tests.stream.vs.forLoop.utils.ItemToItemWithStringFunction;
import local.tin.tests.stream.vs.forLoop.utils.Utils;
import org.apache.log4j.Logger;

/**
 * Find unique items and convert
 *
 * @author benitodarder
 */
public class FindUniquesAndConvertStreams {

    private static final Logger LOGGER = Logger.getLogger(FindUniquesAndConvertStreams.class);

    public static void main(String[] args) throws CloneNotSupportedException {
        long t0 = System.currentTimeMillis();
        LOGGER.info("Let's get some containers with items,find the unique ones and convert them.");
        Collection<Container> containers = Utils.getInstance().getContainers03Items04();
        LOGGER.info("Containers: " + containers.size());
        containers.forEach((mainContainer) -> {
            LOGGER.info(mainContainer + ", items: " + mainContainer.getItems());
        });
        Set<Item> uniques = new HashSet<>();
        containers.forEach((mainContainer) -> {
            mainContainer.getItems().forEach((container01Item) -> {
                uniques.add(container01Item);
            });
        });
        LOGGER.info("Uniques: " + uniques);
        Set<ItemIdentifiedByString> itemsWithString = uniques.stream().map(new ItemToItemWithStringFunction()).collect(Collectors.toSet());
        LOGGER.info("Converted uniques: " + itemsWithString);
        LOGGER.info("And all this in: " + (System.currentTimeMillis() - t0));
        LOGGER.info("That's all folks!");
    }

}
