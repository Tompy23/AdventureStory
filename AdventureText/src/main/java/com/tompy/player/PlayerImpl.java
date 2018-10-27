package com.tompy.player;

import com.tompy.entity.area.Area;
import com.tompy.entity.compartment.Compartment;
import com.tompy.entity.item.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.*;

import static com.tompy.player.PlayerInfo.SEARCHES;
import static com.tompy.player.PlayerInfo.VISITS;

public class PlayerImpl implements Player, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LogManager.getLogger(PlayerImpl.class);
    private String name;
    private Area currentArea;
    private Map<String, PlayerInfo> areaInfoMap = new HashMap<>();
    private List<Item> inventory = new ArrayList<>();
    private List<Item> equipped = new ArrayList<>();
    private int money;

    public PlayerImpl(String name, Area area) {
        this.name = name;
        currentArea = area;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Area getArea() {
        return currentArea;
    }

    @Override
    public void setArea(Area area) {
        currentArea = area;
    }

    @Override
    public void visitArea(String areaName) {
        if (!areaInfoMap.containsKey(areaName)) {
            areaInfoMap.put(areaName, new PlayerInfo());
        }
        areaInfoMap.get(areaName).incrementCount(VISITS);
    }

    @Override
    public int areaVisitCount(String areaName) {
        int visits = 0;
        if (!areaInfoMap.containsKey(areaName)) {
            visits = areaInfoMap.get(areaName).getCount(VISITS);
        }
        return visits;
    }

    @Override
    public void searchArea(String roomName) {
        if (!areaInfoMap.containsKey(roomName)) {
            areaInfoMap.put(roomName, new PlayerInfo());
        }
        areaInfoMap.get(roomName).incrementCount(SEARCHES);
    }

    @Override
    public int areaSearchCount(String roomName) {
        int searches = 0;
        if (!areaInfoMap.containsKey(roomName)) {
            searches = areaInfoMap.get(roomName).getCount(SEARCHES);
        }
        return searches;
    }

    @Override
    public boolean addItem(Item item) {
        if (!inventory.contains(item)) {
            inventory.add(item);
            LOGGER.info("Adding item [{}] to player inventory.", item.getName());
            return true;
        }
        return false;
    }

    @Override
    public void removeItem(Item item) {
        inventory.remove(item);
    }

    @Override
    public boolean dropItem(Item item, Compartment compartment) {
        if (inventory.contains(item)) {
            // TODO When Compartment is fleshed out, we'll deal with this
            compartment.addItem(item);
            inventory.remove(item);
            LOGGER.info("Removing item [{}] for player inventory", item.getName());
            return true;
        }
        return false;
    }

    @Override
    public boolean equip(Item item) {
        if (inventory.contains(item)) {
            if (equipped.stream().mapToInt(Item::hands).sum() < 2) {
                inventory.remove(item);
                equipped.add(item);
                LOGGER.info("Equipping item [{}]", item.getName());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean unequip(Item item) {
        if (equipped.contains(item)) {
            equipped.remove(item);
            inventory.add(item);
            LOGGER.info("Unequipping item [{}]", item.getName());
            return true;
        }
        return false;
    }

    @Override
    public List<Item> getInventory() {
        return Collections.unmodifiableList(inventory);
    }

    @Override
    public int moneyValue() {
        return money;
    }

    @Override
    public void addMoney(int value) {
        money += value;
    }

    @Override
    public boolean pay(int value) {
        if (money >= value) {
            money -= value;
            return true;
        }
        return false;
    }
}
