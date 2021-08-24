package local.tin.tests.stream.vs.forLoop;

import java.util.Collection;
import java.util.ListIterator;
import local.tin.tests.stream.vs.forLoop.model.Container;
import local.tin.tests.stream.vs.forLoop.model.Item;
import local.tin.tests.stream.vs.forLoop.utils.Utils;
import org.apache.log4j.Logger;

/**
 * 
 * Find repeated items in several lists, and replaces them with a clone.
 *
 * @author benitodarder
 */
public class FindEqualsAndCloneClassic {

    private static final Logger LOGGER = Logger.getLogger(FindEqualsAndCloneClassic.class);

    public static void main(String[] args) throws CloneNotSupportedException {
        long t0 = System.currentTimeMillis();
        LOGGER.info("Let's get some containers with items, with repetitions and try to find them.");
        Collection<Container> containers = Utils.getInstance().getContainers03Items04();
        LOGGER.info("Containers: " + containers.size());
        for (Container mainContainer : containers) {  
            LOGGER.info(mainContainer + ", items: " + mainContainer.getItems());
        }
        for (Container mainContainer : containers) {
            for (Container secondLevelContainer : containers) {
                if (!mainContainer.equals(secondLevelContainer)) {
                    for (Item container01Item : mainContainer.getItems()) {
                        ListIterator<Item> itemIterator = secondLevelContainer.getItems().listIterator();
                        while (itemIterator.hasNext()) {
                            Item container02Item = itemIterator.next();
                            if (container01Item.equals(container02Item)) {
                                itemIterator.remove();
                                itemIterator.add(container01Item.clone());
                            }
                        }
                    }

                }
            }
        }
        LOGGER.info("After processing:");
        LOGGER.info("Containers: " + containers.size());
        for (Container mainContainer : containers) {  
            LOGGER.info(mainContainer + ", items: " + mainContainer.getItems());
        }
        LOGGER.info("And all this in: " + (System.currentTimeMillis() - t0));        
        LOGGER.info("That's all folks!");
    }

}
