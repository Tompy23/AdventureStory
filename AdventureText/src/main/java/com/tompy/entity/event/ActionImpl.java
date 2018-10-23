package com.tompy.entity.event;

import com.tompy.adventure.AdventureUtils;
import com.tompy.attribute.Attribute;
import com.tompy.entity.Entity;
import com.tompy.entity.EntityService;
import com.tompy.response.Responsive;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public abstract class ActionImpl extends Responsive implements Action {
    protected final String source;
    protected final Entity entity;
    protected final EntityService entityService;
    protected final List<String> responses;

    public ActionImpl(Entity entity, EntityService entityService, String[] responses) {
        this.entity = Objects.requireNonNull(entity, "Entity cannot be null.");
        this.source = entity.getName();
        this.entityService = Objects.requireNonNull(entityService);
        this.responses = responses == null ? Collections.emptyList() : Arrays.asList(responses);
    }

    // ${entity|attribute|applies-text|does-not-apply-text} ${required|required|optional|optional}
    protected String substitution(String text) {
        int start = text.indexOf("${");
        int end = text.indexOf("}");
        if (start == -1 && end == -1) {
            return text;
        }
        String[] parts = text.substring(start + 2, end).split(Pattern.quote("|"));
        if (parts.length == 0) {
            return text;
        }
        Entity thisEntity = entityService.getEntityByName(parts[0]);
        Attribute attribute = AdventureUtils.getAttribute(parts[1]);
        boolean attributable = entityService.is(thisEntity, attribute);

        StringBuilder sb = new StringBuilder();
        sb.append(text.substring(0, start));
        if (parts.length == 4) {
            sb.append(attributable ? parts[2] : parts[3]);
        } else {
            sb.append(attributable ? attribute.getDoesApply() : attribute.getDoesNotApply());
        }
        sb.append(text.substring(end + 1));

        return substitution(sb.toString());
    }
}
