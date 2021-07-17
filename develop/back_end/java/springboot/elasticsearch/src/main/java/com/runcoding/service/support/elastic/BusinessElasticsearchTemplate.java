package com.runcoding.service.support.elastic;

import com.runcoding.service.support.elastic.query.BusinessNativeSearchQuery;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.VersionType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.core.convert.MappingElasticsearchConverter;
import org.springframework.data.elasticsearch.core.facet.FacetRequest;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext;
import org.springframework.data.elasticsearch.core.query.*;
import com.runcoding.service.support.elastic.query.CriteriaQueryProcessor;
import com.runcoding.service.support.elastic.query.CriteriaFilterProcessor;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.wrapperQuery;
import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * @author runcoding
 * @date 2019-06-21
 * @desc: 业务扩展ElasticsearchTemplate, 处理一些不兼容问题
 */
public class BusinessElasticsearchTemplate extends ElasticsearchTemplate {

    private static final Logger QUERY_LOGGER = LoggerFactory.getLogger("org.springframework.data.elasticsearch.core.QUERY");
    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchTemplate.class);
    private static final String FIELD_SCORE = "_score";

    private Client client;
    private ElasticsearchConverter elasticsearchConverter;
    private ResultsMapper resultsMapper;
    private String searchTimeout;

    public BusinessElasticsearchTemplate(Client client) {
        this(client, new MappingElasticsearchConverter(new SimpleElasticsearchMappingContext()));
    }

    public BusinessElasticsearchTemplate(Client client, EntityMapper entityMapper) {
        this(client, new MappingElasticsearchConverter(new SimpleElasticsearchMappingContext()), entityMapper);
    }

    public BusinessElasticsearchTemplate(Client client, ElasticsearchConverter elasticsearchConverter, EntityMapper entityMapper) {
        this(client, elasticsearchConverter,
                new DefaultResultMapper(elasticsearchConverter.getMappingContext(), entityMapper));
    }

    public BusinessElasticsearchTemplate(Client client, ResultsMapper resultsMapper) {
        this(client, new MappingElasticsearchConverter(new SimpleElasticsearchMappingContext()), resultsMapper);
    }

    public BusinessElasticsearchTemplate(Client client, ElasticsearchConverter elasticsearchConverter) {
        this(client, elasticsearchConverter, new DefaultResultMapper(elasticsearchConverter.getMappingContext()));
    }

    public BusinessElasticsearchTemplate(Client client, ElasticsearchConverter elasticsearchConverter, ResultsMapper resultsMapper) {
        super(client, elasticsearchConverter, resultsMapper);
        this.client = client;
        this.elasticsearchConverter = elasticsearchConverter;
        this.resultsMapper = resultsMapper;
    }


    @Override
    public <T> T queryForObject(CriteriaQuery query, Class<T> clazz) {
        Page<T> page = queryForPage(query, clazz);
        Assert.isTrue(page.getTotalElements() < 2, "Expected 1 but found " + page.getTotalElements() + " results");
        return page.getTotalElements() > 0 ? page.getContent().get(0) : null;
    }

    @Override
    public <T> T queryForObject(StringQuery query, Class<T> clazz) {
        Page<T> page = queryForPage(query, clazz);
        Assert.isTrue(page.getTotalElements() < 2, "Expected 1 but found " + page.getTotalElements() + " results");
        return page.getTotalElements() > 0 ? page.getContent().get(0) : null;
    }

    @Override
    public <T> AggregatedPage<T> queryForPage(SearchQuery query, Class<T> clazz) {
        return queryForPage(query, clazz, resultsMapper);
    }

    @Override
    public <T> AggregatedPage<T> queryForPage(SearchQuery query, Class<T> clazz, SearchResultMapper mapper) {
        SearchResponse response = doSearch(prepareSearch(query, clazz), query);
        return mapper.mapResults(response, clazz, query.getPageable());
    }

    @Override
    public <T> T query(SearchQuery query, ResultsExtractor<T> resultsExtractor) {
        SearchResponse response = doSearch(prepareSearch(query), query);
        return resultsExtractor.extract(response);
    }

    @Override
    public <T> List<T> queryForList(CriteriaQuery query, Class<T> clazz) {
        return queryForPage(query, clazz).getContent();
    }

    @Override
    public <T> List<T> queryForList(StringQuery query, Class<T> clazz) {
        return queryForPage(query, clazz).getContent();
    }

    @Override
    public <T> List<T> queryForList(SearchQuery query, Class<T> clazz) {
        return queryForPage(query, clazz).getContent();
    }

    @Override
    public <T> List<String> queryForIds(SearchQuery query) {
        SearchRequestBuilder request = prepareSearch(query).setQuery(query.getQuery());
        if (query.getFilter() != null) {
            request.setPostFilter(query.getFilter());
        }
        SearchResponse response = getSearchResponse(request);
        return extractIds(response);
    }

    @Override
    public <T> Page<T> queryForPage(CriteriaQuery criteriaQuery, Class<T> clazz) {
        QueryBuilder elasticsearchQuery = new CriteriaQueryProcessor().createQueryFromCriteria(criteriaQuery.getCriteria());
        QueryBuilder elasticsearchFilter = new CriteriaFilterProcessor()
                .createFilterFromCriteria(criteriaQuery.getCriteria());
        SearchRequestBuilder searchRequestBuilder = prepareSearch(criteriaQuery, clazz);

        if (elasticsearchQuery != null) {
            searchRequestBuilder.setQuery(elasticsearchQuery);
        } else {
            searchRequestBuilder.setQuery(QueryBuilders.matchAllQuery());
        }

        if (criteriaQuery.getMinScore() > 0) {
            searchRequestBuilder.setMinScore(criteriaQuery.getMinScore());
        }

        if (elasticsearchFilter != null){
            searchRequestBuilder.setPostFilter(elasticsearchFilter);
        }

        SearchResponse response = getSearchResponse(searchRequestBuilder);
        return resultsMapper.mapResults(response, clazz, criteriaQuery.getPageable());
    }

    @Override
    public <T> Page<T> queryForPage(StringQuery query, Class<T> clazz) {
        return queryForPage(query, clazz, resultsMapper);
    }

    @Override
    public <T> Page<T> queryForPage(StringQuery query, Class<T> clazz, SearchResultMapper mapper) {
        SearchResponse response = getSearchResponse(prepareSearch(query, clazz).setQuery(wrapperQuery(query.getSource())));
        return mapper.mapResults(response, clazz, query.getPageable());
    }

    private SearchResponse doSearch(SearchRequestBuilder searchRequest, SearchQuery searchQuery) {
        if (searchQuery.getFilter() != null) {
            searchRequest.setPostFilter(searchQuery.getFilter());
        }

        if (!isEmpty(searchQuery.getElasticsearchSorts())) {
            for (SortBuilder sort : searchQuery.getElasticsearchSorts()) {
                searchRequest.addSort(sort);
            }
        }

        if (!searchQuery.getScriptFields().isEmpty()) {
            // _source should be return all the time
            // searchRequest.addStoredField("_source");
            for (ScriptField scriptedField : searchQuery.getScriptFields()) {
                searchRequest.addScriptField(scriptedField.fieldName(), scriptedField.script());
            }
        }

        if (searchQuery.getHighlightFields() != null || searchQuery.getHighlightBuilder() != null) {
            HighlightBuilder highlightBuilder = searchQuery.getHighlightBuilder();
            if (highlightBuilder == null) {
                highlightBuilder = new HighlightBuilder();
            }
            for (HighlightBuilder.Field highlightField : searchQuery.getHighlightFields()) {
                highlightBuilder.field(highlightField);
            }
            searchRequest.highlighter(highlightBuilder);
        }

        if (!isEmpty(searchQuery.getIndicesBoost())) {
            for (IndexBoost indexBoost : searchQuery.getIndicesBoost()) {
                searchRequest.addIndexBoost(indexBoost.getIndexName(), indexBoost.getBoost());
            }
        }

        if (!isEmpty(searchQuery.getAggregations())) {
            for (AbstractAggregationBuilder aggregationBuilder : searchQuery.getAggregations()) {
                searchRequest.addAggregation(aggregationBuilder);
            }
        }

        if (!isEmpty(searchQuery.getFacets())) {
            for (FacetRequest aggregatedFacet : searchQuery.getFacets()) {
                searchRequest.addAggregation(aggregatedFacet.getFacet());
            }
        }
        return getSearchResponse(searchRequest.setQuery(searchQuery.getQuery()));
    }

    private SearchResponse getSearchResponse(SearchRequestBuilder requestBuilder) {

        if (QUERY_LOGGER.isDebugEnabled()) {
            QUERY_LOGGER.debug(requestBuilder.toString());
        }

        return getSearchResponse(requestBuilder.execute());
    }

    private SearchResponse getSearchResponse(ActionFuture<SearchResponse> response) {
        return searchTimeout == null ? response.actionGet() : response.actionGet(searchTimeout);
    }

    private <T> SearchRequestBuilder prepareSearch(Query query, Class<T> clazz) {
        setPersistentEntityIndexAndType(query, clazz);
        return prepareSearch(query);
    }



    /*** support prepareSearch */
    private SearchRequestBuilder prepareSearch(Query query) {
        Assert.notNull(query.getIndices(), "No index defined for Query");
        Assert.notNull(query.getTypes(), "No type defined for Query");

        int startRecord = 0;
        SearchRequestBuilder searchRequestBuilder = super.getClient().prepareSearch(toArray(query.getIndices()))
                .setSearchType(query.getSearchType())
                .setTypes(toArray(query.getTypes()))
                .setVersion(true)
                .setTrackScores(query.getTrackScores());

        if (query.getSourceFilter() != null) {
            SourceFilter sourceFilter = query.getSourceFilter();
            searchRequestBuilder.setFetchSource(sourceFilter.getIncludes(), sourceFilter.getExcludes());
        }

        if (query.getPageable().isPaged()) {
            startRecord = query.getPageable().getPageNumber() * query.getPageable().getPageSize();
            searchRequestBuilder.setSize(query.getPageable().getPageSize());
        }
        searchRequestBuilder.setFrom(startRecord);

        if (!query.getFields().isEmpty()) {
            searchRequestBuilder.setFetchSource(toArray(query.getFields()), null);
        }

        if (query.getIndicesOptions() != null) {
            searchRequestBuilder.setIndicesOptions(query.getIndicesOptions());
        }

        if(query instanceof BusinessNativeSearchQuery){
            Object[] searchAfter = ((BusinessNativeSearchQuery) query).getSearchAfter();
            if(searchAfter != null){
               searchRequestBuilder.searchAfter(((BusinessNativeSearchQuery) query).getSearchAfter());
            }
        }

        if (query.getSort() != null) {
            for (Sort.Order order : query.getSort()) {
                SortOrder sortOrder = order.getDirection().isDescending() ? SortOrder.DESC : SortOrder.ASC;

                if (FIELD_SCORE.equals(order.getProperty())) {
                    ScoreSortBuilder sort = SortBuilders
                            .scoreSort()
                            .order(sortOrder);

                    searchRequestBuilder.addSort(sort);
                } else {
                    FieldSortBuilder sort = SortBuilders
                            .fieldSort(order.getProperty())
                            .order(sortOrder);

                    if (order.getNullHandling() == Sort.NullHandling.NULLS_FIRST) {
                        sort.missing("_first");
                    } else if (order.getNullHandling() == Sort.NullHandling.NULLS_LAST) {
                        sort.missing("_last");
                    }

                    searchRequestBuilder.addSort(sort);
                }
            }
        }

        if (query.getMinScore() > 0) {
            searchRequestBuilder.setMinScore(query.getMinScore());
        }
        return searchRequestBuilder;
    }

    private void setPersistentEntityIndexAndType(Query query, Class clazz) {
        if (query.getIndices().isEmpty()) {
            query.addIndices(retrieveIndexNameFromPersistentEntity(clazz));
        }
        if (query.getTypes().isEmpty()) {
            query.addTypes(retrieveTypeFromPersistentEntity(clazz));
        }
    }

    private String[] retrieveIndexNameFromPersistentEntity(Class clazz) {
        if (clazz != null) {
            return new String[] { getPersistentEntityFor(clazz).getIndexName() };
        }
        return null;
    }

    private String[] retrieveTypeFromPersistentEntity(Class clazz) {
        if (clazz != null) {
            return new String[] { getPersistentEntityFor(clazz).getIndexType() };
        }
        return null;
    }

    private VersionType retrieveVersionTypeFromPersistentEntity(Class clazz) {
        if (clazz != null) {
            return getPersistentEntityFor(clazz).getVersionType();
        }
        return VersionType.EXTERNAL;
    }

    private List<String> extractIds(SearchResponse response) {
        List<String> ids = new ArrayList<>();
        for (SearchHit hit : response.getHits()) {
            if (hit != null) {
                ids.add(hit.getId());
            }
        }
        return ids;
    }

    private static String[] toArray(List<String> values) {
        String[] valuesAsArray = new String[values.size()];
        return values.toArray(valuesAsArray);
    }
}
