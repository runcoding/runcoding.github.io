package com.runcoding.service.support.elastic.query;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;

import java.util.List;

/**
 * @author runcoding
 * @date 2019-06-21
 * @desc: NativeSearchQuery 扩展
 */
public class BusinessNativeSearchQuery extends NativeSearchQuery {

    private Object[] searchAfter;

    public BusinessNativeSearchQuery(QueryBuilder query) {
        super(query);
    }

    public BusinessNativeSearchQuery(QueryBuilder query, QueryBuilder filter) {
        super(query, filter);
    }

    public BusinessNativeSearchQuery(QueryBuilder query, QueryBuilder filter, List<SortBuilder> sorts) {
        super(query, filter, sorts);
    }

    public BusinessNativeSearchQuery(QueryBuilder query, QueryBuilder filter, List<SortBuilder> sorts, HighlightBuilder.Field[] highlightFields) {
        super(query, filter, sorts, highlightFields);
    }

    public BusinessNativeSearchQuery(QueryBuilder query, QueryBuilder filter, List<SortBuilder> sorts, HighlightBuilder highlighBuilder, HighlightBuilder.Field[] highlightFields) {
        super(query, filter, sorts, highlighBuilder, highlightFields);
    }


    public BusinessNativeSearchQuery addSearchAfter(Object... searchAfter) {
        this.searchAfter = searchAfter;
        return this;
    }

    public Object[] getSearchAfter() {
        return searchAfter;
    }
}
