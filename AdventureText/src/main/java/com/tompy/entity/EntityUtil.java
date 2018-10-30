package com.tompy.entity;

import com.tompy.entity.feature.Feature;
import com.tompy.entity.item.Item;
import com.tompy.io.UserIO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.tompy.attribute.Attribute.VISIBLE;

/**
 * Utility class of functions dealing with Entities.
 */
public class EntityUtil {
    private static final Logger LOGGER = LogManager.getLogger(EntityUtil.class);

    /**
     * Find an entity from a list based on the description.  If necessary the user may have to select from
     * the best choices.
     *
     * @param items       - The list of items from which to choose
     * @param description - The description supplied by the user.
     * @param io          - In case the user must supply a response
     * @return - The Item selected.
     */
    public static Long findEntityByDescription(List<? extends Entity> items, String description, UserIO io) {
        Map<Long, Integer> scores = computeScores(items, description);
        Long itemKey = null;
        if (!scores.isEmpty()) {
            List<Long> finalists = computeFinalists(scores);

            if (finalists.size() == 0) {
                return null;
            } else if (finalists.size() == 1) {
                itemKey = finalists.get(0);
            } else {
                itemKey = makeChoice(items, finalists, io);
            }
        }

        return itemKey;
    }

    /**
     * @param items       - The list of items from which to choose
     * @param description - The description supplied by the user.
     * @param io          - In case the user must supply a response
     * @return - The Item selected.
     */
    public static Optional<Item> findItemByDescription(List<Item> items, String description, UserIO io) {
        Long objectKey = EntityUtil.findEntityByDescription(items, description, io);
        return items.stream().filter((i) -> i.getKey().equals(objectKey)).findFirst();
    }

    /**
     * @param entityService - The entity service where the managers are located.
     * @param items         - The list of items from which to choose
     * @param description   - The description supplied by the user.
     * @param io            - In case the user must supply a response
     * @return - The Item selected.
     */
    public static Optional<Item> findVisibleItemByDescription(EntityService entityService, List<Item> items,
            String description, UserIO io) {
        List<Entity> entities =
                items.stream().filter((f) -> entityService.is(f, VISIBLE)).collect(Collectors.toList());
        Long objectKey = EntityUtil.findEntityByDescription(entities, description, io);
        return items.stream().filter((i) -> i.getKey().equals(objectKey) ).findFirst();
    }

    /**
     * @param features    - The list of features from which to choose
     * @param description - The description supplied by the user.
     * @param io          - In case the user must supply a response
     * @return - The Item selected.
     */
    public static Optional<Feature> findFeatureByDescription(List<Feature> features, String description, UserIO io) {
        Long objectKey = EntityUtil.findEntityByDescription(features, description, io);
        return features.stream().filter((i) -> i.getKey().equals(objectKey)).findFirst();
    }

    /**
     * @param entityService - The entity service where the managers are located.
     * @param features      - The list of features from which to choose
     * @param description   - The description supplied by the user.
     * @param io            - In case the user must supply a response
     * @return - The Item selected.
     */
    public static Optional<Feature> findVisibleFeatureByDescription(EntityService entityService, List<Feature> features,
            String description, UserIO io) {
        List<Entity> entities =
                features.stream().filter((f) -> entityService.is(f, VISIBLE)).collect(Collectors.toList());
        Long objectKey = EntityUtil.findEntityByDescription(entities, description, io);
        return features.stream().filter((f) -> f.getKey().equals(objectKey)).findFirst();
    }

    private static Map<Long, Integer> computeScores(List<? extends Entity> items, String description) {
        Map<Long, Integer> scores = new HashMap<>();
        items.stream().forEach(e -> scores.put(e.getKey(), 0));
        for (Entity entity : items) {
            for (String word : entity.getDescriptionWords()) {
                for (String d : description.split(Pattern.quote(" "))) {
                    if (d.toUpperCase().contains(word.toUpperCase())) {
                        scores.put(entity.getKey(), scores.get(entity.getKey()) + 1);
                    }
                }
            }
        }

        return scores;
    }

    private static List<Long> computeFinalists(Map<Long, Integer> scores) {
        List<Long> finalists = new ArrayList<>();
        Integer highestScore = scores.values().stream().reduce(Integer::max).get();

        for (Long key : scores.keySet()) {
            if (scores.get(key) == highestScore) {
                finalists.add(key);
            }
        }

        return finalists;
    }

    private static Long makeChoice(List<? extends Entity> entities, List<Long> finalists, UserIO io) {
        Map<Long, String> choices = new HashMap<>();
        for (Long finalist : finalists) {
            for (Entity entity : entities) {
                if (entity.getKey() == finalist) {
                    choices.put(entity.getKey(), entity.getDescription());
                }
            }
        }

        return io.getSelection(choices);
    }

    public static Entity findAreaByName(List<Entity> entities, String name) {
        return entities.stream().filter(name::equals).findFirst().get();
    }

    /**
     * Make an entity visible for an entityService
     *
     * @param entityService
     * @param entity
     */
    public static void makeVisisble(EntityService entityService, Entity entity) {
        entityService.add(entity, VISIBLE);
    }
}
