package com.segunfamisa.sample.comics.data.local.mappers;


import com.segunfamisa.sample.comics.data.local.realm.CreatorSummaryRealm;
import com.segunfamisa.sample.comics.data.model.CreatorSummary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Map from {@link com.segunfamisa.sample.comics.data.local.realm.CreatorSummaryRealm}
 * to {@link com.segunfamisa.sample.comics.data.model.CreatorSummary}.
 */
@Singleton
public class CreatorSummaryRealmMapper implements Mapper<CreatorSummaryRealm, CreatorSummary> {

    @Inject
    public CreatorSummaryRealmMapper() {
    }

    @Override
    public CreatorSummary map1(CreatorSummaryRealm creatorSummaryRealm) {
        return new CreatorSummary.Builder()
                .setName(creatorSummaryRealm.getName())
                .setResourceUri(creatorSummaryRealm.getResourceUri())
                .setRole(creatorSummaryRealm.getRole())
                .build();
    }

    @Override
    public Collection<CreatorSummary> mapMany(Collection<CreatorSummaryRealm>
                                                          creatorSummaryRealms) {
        List<CreatorSummary> creatorSummaries = new ArrayList<>();
        for (CreatorSummaryRealm creatorSummaryRealm : creatorSummaryRealms) {
            creatorSummaries.add(map1(creatorSummaryRealm));
        }
        return creatorSummaries;
    }
}
