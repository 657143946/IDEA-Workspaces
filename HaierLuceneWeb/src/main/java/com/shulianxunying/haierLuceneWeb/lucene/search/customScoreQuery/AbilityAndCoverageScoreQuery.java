package com.shulianxunying.haierLuceneWeb.lucene.search.customScoreQuery;

import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.queries.CustomScoreProvider;
import org.apache.lucene.queries.CustomScoreQuery;
import org.apache.lucene.search.FieldCache;
import org.apache.lucene.search.Query;

import java.io.IOException;

/**
 * Created by ChrisLee.
 */
public class AbilityAndCoverageScoreQuery extends CustomScoreQuery {
    private float subScoreFactor = 1;
    private float coverageFactor = 1;
    private float abilityFactor = 1;

    public AbilityAndCoverageScoreQuery(Query subQuery) {
        super(subQuery);
    }

    public AbilityAndCoverageScoreQuery(Query subQuery, float subScoreFactor,
                                        float coverageFactor, float abilityFactor) {
        super(subQuery);
        this.abilityFactor = abilityFactor;
        this.coverageFactor = coverageFactor;
        this.subScoreFactor = subScoreFactor;
    }

    @Override
    protected CustomScoreProvider getCustomScoreProvider(
            AtomicReaderContext context) throws IOException {
        /**
         * 自定义的评分provider
         *
         * **/
        return new AbilityCoverageScoreProvider(context, subScoreFactor, coverageFactor, abilityFactor);
    }

    private class AbilityCoverageScoreProvider extends CustomScoreProvider {
        private AtomicReaderContext context = null;
        private float subScoreFactor = 1;
        private float coverageFactor = 1;
        private float abilityFactor = 1;

        public AbilityCoverageScoreProvider(AtomicReaderContext context, float subScoreFactor,
                                            float coverageFactor, float abilityFactor) {
            super(context);
            this.context = context;
            this.abilityFactor = abilityFactor;
            this.coverageFactor = coverageFactor;
            this.subScoreFactor = subScoreFactor;
        }

        @Override
        public float customScore(int doc, float subQueryScore, float valSrcScore)
                throws IOException {
            //获取double型，使用Cache获取更加的快速
            FieldCache.Doubles doubles = FieldCache.DEFAULT.getDoubles(context.reader(), "coverage", false);
            double coverage = doubles.get(doc);
            doubles = FieldCache.DEFAULT.getDoubles(context.reader(), "ability", false);
            double ability = doubles.get(doc);
//            return (float)Math.log10(subQueryScore) +
//                    (float)coverage * 2.5f +
//                    (float)ability * 1 +
//                    (float)(workYear/10f) +
//                    (float)complete;
//            return (float)Math.log10(subQueryScore) + (float)coverage + (float)ability/1.5f;
            return subQueryScore * subScoreFactor +
                    (float)coverage * coverageFactor +
                    (float)ability * abilityFactor;
//            return (float)Math.log10(subQueryScore) + (float)coverage + (float)ability/2f;
//            return subQueryScore;
        }

    }

}
//