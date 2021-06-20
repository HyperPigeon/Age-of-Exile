package com.robertx22.age_of_exile.uncommon.interfaces;

import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.library_of_exile.registry.IGUID;

import java.util.ArrayList;
import java.util.List;

public interface IElementalGenerated<T extends IGUID> extends IGenerated {

    public abstract T newGeneratedInstance(Elements element);

    default List<T> generateAllSingleVariations() {
        List<T> list = new ArrayList<>();
        Elements.getAllSingleElementals()
            .forEach(x -> list.add(newGeneratedInstance(x)));
        return list;

    }

    @Override
    public default List<T> generateAllPossibleStatVariations() {
        List<T> list = new ArrayList<>();
        Elements.getAllSingleElementals()
            .forEach(x -> list.add(newGeneratedInstance(x)));
        return list;
    }

}
