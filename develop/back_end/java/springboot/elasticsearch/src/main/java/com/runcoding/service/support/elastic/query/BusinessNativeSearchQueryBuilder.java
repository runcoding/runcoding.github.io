package com.runcoding.service.support.elastic.query;

import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.facet.FacetRequest;
import org.springframework.data.elasticsearch.core.query.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * @author runcoding
 * @date 2019-06-21
 * @desc: NativeSearchQueryBuilder 扩展
 */
public class BusinessNativeSearchQueryBuilder extends NativeSearchQueryBuilder {

    private Object[] searchAfter;
    private QueryBuilder queryBuilder;
    private QueryBuilder filterBuilder;
    private List<ScriptField> scriptFields = new ArrayList<>();
    private List<SortBuilder> sortBuilders = new ArrayList<>();
    private List<FacetRequest> facetRequests = new ArrayList<>();
    private List<AbstractAggregationBuilder> aggregationBuilders = new ArrayList<>();
    private HighlightBuilder highlightBuilder;
    private HighlightBuilder.Field[] highlightFields;
    private Pageable pageable = Pageable.unpaged();
    private String[] indices;
    private String[] types;
    private String[] fields;
    private SourceFilter sourceFilter;
    private List<IndexBoost> indicesBoost;
    private float minScore;
    private boolean trackScores;
    private Collection<String> ids;
    private String route;
    private SearchType searchType;
    private IndicesOptions indicesOptions;

    public BusinessNativeSearchQueryBuilder withSearchAfter(Object... searchAfter) {
        this.searchAfter = searchAfter;
        return this;
    }

    @Override
    public BusinessNativeSearchQueryBuilder withQuery(QueryBuilder queryBuilder) {
        this.queryBuilder = queryBuilder;
        return this;
    }

    @Override
    public BusinessNativeSearchQueryBuilder withFilter(QueryBuilder filterBuilder) {
        this.filterBuilder = filterBuilder;
        return this;
    }

    @Override
    public BusinessNativeSearchQueryBuilder withSort(SortBuilder sortBuilder) {
        this.sortBuilders.add(sortBuilder);
        return this;
    }
    @Override
    public BusinessNativeSearchQueryBuilder withScriptField(ScriptField scriptField) {
        this.scriptFields.add(scriptField);
        return this;
    }
    @Override
    public BusinessNativeSearchQueryBuilder addAggregation(AbstractAggregationBuilder aggregationBuilder) {
        this.aggregationBuilders.add(aggregationBuilder);
        return this;
    }
    @Override
    public BusinessNativeSearchQueryBuilder withFacet(FacetRequest facetRequest) {
        facetRequests.add(facetRequest);
        return this;
    }
    @Override
    public BusinessNativeSearchQueryBuilder withHighlightBuilder(HighlightBuilder highlightBuilder) {
        this.highlightBuilder = highlightBuilder;
        return this;
    }
    @Override
    public BusinessNativeSearchQueryBuilder withHighlightFields(HighlightBuilder.Field... highlightFields) {
        this.highlightFields = highlightFields;
        return this;
    }
    @Override
    public BusinessNativeSearchQueryBuilder withIndicesBoost(List<IndexBoost> indicesBoost) {
        this.indicesBoost = indicesBoost;
        return this;
    }
    @Override
    public BusinessNativeSearchQueryBuilder withPageable(Pageable pageable) {
        this.pageable = pageable;
        return this;
    }
    @Override
    public BusinessNativeSearchQueryBuilder withIndices(String... indices) {
        this.indices = indices;
        return this;
    }
    @Override
    public BusinessNativeSearchQueryBuilder withTypes(String... types) {
        this.types = types;
        return this;
    }
    @Override
    public BusinessNativeSearchQueryBuilder withFields(String... fields) {
        this.fields = fields;
        return this;
    }
    @Override
    public BusinessNativeSearchQueryBuilder withSourceFilter(SourceFilter sourceFilter) {
        this.sourceFilter = sourceFilter;
        return this;
    }
    @Override
    public BusinessNativeSearchQueryBuilder withMinScore(float minScore) {
        this.minScore = minScore;
        return this;
    }

    /**
     * @param trackScores whether to track scores.
     * @return
     * @since 3.1
     */
    @Override
    public BusinessNativeSearchQueryBuilder withTrackScores(boolean trackScores) {
        this.trackScores = trackScores;
        return this;
    }

    @Override
    public BusinessNativeSearchQueryBuilder withIds(Collection<String> ids) {
        this.ids = ids;
        return this;
    }

    @Override
    public BusinessNativeSearchQueryBuilder withRoute(String route) {
        this.route = route;
        return this;
    }

    @Override
    public BusinessNativeSearchQueryBuilder withSearchType(SearchType searchType) {
        this.searchType = searchType;
        return this;
    }

    @Override
    public BusinessNativeSearchQueryBuilder withIndicesOptions(IndicesOptions indicesOptions) {
        this.indicesOptions = indicesOptions;
        return this;
    }

    @Override
    public BusinessNativeSearchQuery build() {
        BusinessNativeSearchQuery nativeSearchQuery = new BusinessNativeSearchQuery(queryBuilder, filterBuilder, sortBuilders,
                highlightBuilder, highlightFields);

        nativeSearchQuery.setPageable(pageable);
        nativeSearchQuery.setTrackScores(trackScores);

        if (indices != null) {
            nativeSearchQuery.addIndices(indices);
        }

        if (searchAfter != null){
            nativeSearchQuery.addSearchAfter(searchAfter);
        }

        if (types != null) {
            nativeSearchQuery.addTypes(types);
        }

        if (fields != null) {
            nativeSearchQuery.addFields(fields);
        }

        if (sourceFilter != null) {
            nativeSearchQuery.addSourceFilter(sourceFilter);
        }

        if (indicesBoost != null) {
            nativeSearchQuery.setIndicesBoost(indicesBoost);
        }

        if (!isEmpty(scriptFields)) {
            nativeSearchQuery.setScriptFields(scriptFields);
        }

        if (!isEmpty(facetRequests)) {
            nativeSearchQuery.setFacets(facetRequests);
        }

        if (!isEmpty(aggregationBuilders)) {
            nativeSearchQuery.setAggregations(aggregationBuilders);
        }

        if (minScore > 0) {
            nativeSearchQuery.setMinScore(minScore);
        }

        if (ids != null) {
            nativeSearchQuery.setIds(ids);
        }

        if (route != null) {
            nativeSearchQuery.setRoute(route);
        }

        if (searchType != null) {
            nativeSearchQuery.setSearchType(searchType);
        }

        if (indicesOptions != null) {
            nativeSearchQuery.setIndicesOptions(indicesOptions);
        }
        return nativeSearchQuery;
    }
}
