package com.shulianxunying.haierLuceneWeb.lucene.manager;

import com.shulianxunying.haierLuceneWeb.lucene.constant.Constant;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

/**
 * Created by AbnerLee on 15-1-25.
 * 管理索引类的管理器
 */
public class IndexManager {
    /**
     * writer默认配置
     */
    private final IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_4_10_2, Constant.ANALYZER_SMART);

    private Directory dir;
    private IndexReader reader;
    private IndexSearcher searcher;
    private IndexWriter writer;

    /**
     * 构造函数
     */
    public IndexManager(String indexPath) {
        this(new File(indexPath));
    }

    private IndexManager(File indexDir) {
        init(indexDir);
    }

    /**
     * 初始化方法
     */
    private void init(File indexDir) {
        /**
         * writer默认配置
         */
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        iwc.setRAMBufferSizeMB(20.0);
        iwc.setMaxBufferedDocs(10000);
        try {
            /**
             * Directory 初始化
             */
            this.dir = FSDirectory.open(indexDir);
            /**
             * IndexWriter 初始化
             */
            this.writer = new IndexWriter(this.dir, this.iwc);
            this.commitWriter();
            /**
             * IndexReader 初始化
             */
            ReaderManager.getInstance().createIndexReader(dir);
            this.reader = ReaderManager.getInstance().getIndexReader(dir);
            /**
             * IndexSearcher 初始化
             */
            this.searcher = new IndexSearcher(this.reader);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public IndexWriter getWriter() {
        return this.writer;
    }

    public void commitWriter() {
        try {
            writer.commit();
        } catch (IOException e) {
            this.rollback();
        }
    }

    private void rollback() {
        try {
            writer.rollback();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public IndexSearcher getSearcher() {
        IndexReader reader = ReaderManager.getInstance().getIndexReader(this.dir);
        if (this.reader == null || this.reader != reader) {
            this.reader = reader;
            searcher = new IndexSearcher(this.reader);
        }
        return this.searcher;

    }

}
