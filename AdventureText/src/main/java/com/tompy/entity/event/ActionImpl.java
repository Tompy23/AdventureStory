package com.tompy.entity.event;

import com.tompy.adventure.AdventureUtils;
import com.tompy.attribute.Attribute;
import com.tompy.entity.Entity;
import com.tompy.entity.EntityService;
import com.tompy.response.Responsive;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public abstract class ActionImpl extends Responsive implements Action, Serializable {
    private static final long serialVersionUID = 1L;
    protected final String source;
    protected final Entity entity;
    protected final List<String> responses;

    public ActionImpl(Entity entity, String[] responses) {
        this.entity = Objects.requireNonNull(entity, "Entity cannot be null.");
        this.source = entity.getName();
        this.responses = responses == null ? Collections.emptyList() : Arrays.asList(responses);
    }

    // ${entity|attribute|applies-text|does-not-apply-text} ${required|required|optional|optional}
    protected String substitution(String text, EntityService entityService) {
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

        return substitution(sb.toString(), entityService);
    }
}
