package local.tin.tests.stream.vs.forLoop.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import local.tin.tests.stream.vs.forLoop.model.Container;
import local.tin.tests.stream.vs.forLoop.model.Item;

/**
 *
 * @author benitodarder
 */
public class Utils {

    private Utils() {
    }

    public static Utils getInstance() {
        return UtilsHolder.INSTANCE;
    }

    private static class UtilsHolder {

        private static final Utils INSTANCE = new Utils();
    }

    /**
     * Generates a 2 Container collection and 3 Item.
     * Each container contains 2 Item, and 1 Item is in both Container
     * 
     * @return Collection of Container
     */
    public Collection<Container> getContainers02Items03() {
        List<Container> containers = new ArrayList<>();
        Item item01 = new Item();
        item01.setId(666);
        item01.setName("Item 01");
        Item item02 = new Item();
        item02.setId(13);
        item02.setName("Item 02");
        Item item03 = new Item();
        item03.setId(74);
        item03.setName("Item 03");
        Container container01 = new Container();
        container01.setId(666);
        container01.setName("Container 01");
        container01.getItems().add(item01);
        container01.getItems().add(item02);
        containers.add(container01);
        Container container02 = new Container();
        container02.setId(13);
        container02.setName("Container 02");
        container02.getItems().add(item02);
        container02.getItems().add(item03);
        containers.add(container02);
        return containers;
    }
    
    /**
     * Generates a 3 Container collection and 4 Item.
     * Each container contains 2 Item, and 1 Container have 1 Item from
     * each Container
     * 
     * @return Collection of Container
     */    
    public Collection<Container> getContainers03Items04() {
        List<Container> containers = new ArrayList<>();
        Item item01 = new Item();
        item01.setId(666);
        item01.setName("Item 01");
        Item item02 = new Item();
        item02.setId(13);
        item02.setName("Item 02");
        Item item03 = new Item();
        item03.setId(73);
        item03.setName("Item 03");
        Item item04 = new Item();
        item04.setId(69);
        item04.setName("Item 04");        
        Container container01 = new Container();
        container01.setId(666);
        container01.setName("Container 01");
        container01.getItems().add(item01);
        container01.getItems().add(item02);
        containers.add(container01);
        Container container02 = new Container();
        container02.setId(13);
        container02.setName("Container 02");
        container02.getItems().add(item03);
        container02.getItems().add(item04);
        containers.add(container02);
        Container container03 = new Container();
        container03.setId(69);
        container03.setName("Container 03");
        container03.getItems().add(item01);
        container03.getItems().add(item03);
        containers.add(container03);        
        return containers;
    }    
}
