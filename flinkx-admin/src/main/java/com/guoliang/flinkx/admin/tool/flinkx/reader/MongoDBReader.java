package com.guoliang.flinkx.admin.tool.flinkx.reader;

import com.google.common.collect.Maps;
import com.guoliang.flinkx.core.util.Constants;
import com.guoliang.flinkx.admin.entity.JobDatasource;
import com.guoliang.flinkx.admin.tool.pojo.FlinkxMongoDBPojo;

import java.util.Map;

public class MongoDBReader extends BaseReaderPlugin implements FlinkxReaderInterface {
    @Override
    public String getName() {
        return "mongodbreader";
    }

    @Override
    public Map<String, Object> sample() {
        return null;
    }

    public Map<String, Object> buildMongoDB(FlinkxMongoDBPojo plugin) {
        //构建
        JobDatasource dataSource = plugin.getJdbcDatasource();
        Map<String, Object> readerObj = Maps.newLinkedHashMap();
        readerObj.put("name", getName());
        Map<String, Object> parameterObj = Maps.newLinkedHashMap();
        String[] addressList = null;
        String str = dataSource.getJdbcUrl().replace(Constants.MONGO_URL_PREFIX, Constants.STRING_BLANK);
        if (str.contains(Constants.SPLIT_AT) && str.contains(Constants.SPLIT_DIVIDE)) {
            addressList = str.substring(str.indexOf(Constants.SPLIT_AT) + 1, str.indexOf(Constants.SPLIT_DIVIDE)).split(Constants.SPLIT_COMMA);
        } else if (str.contains(Constants.SPLIT_DIVIDE)) {
            addressList = str.substring(0, str.indexOf(Constants.SPLIT_DIVIDE)).split(Constants.SPLIT_COMMA);
        }
        parameterObj.put("address", addressList);
        parameterObj.put("userName", dataSource.getJdbcUsername() == null ? Constants.STRING_BLANK : dataSource.getJdbcUsername());
        parameterObj.put("userPassword", dataSource.getJdbcPassword() == null ? Constants.STRING_BLANK : dataSource.getJdbcPassword());
        parameterObj.put("dbName", dataSource.getDatabaseName());
        parameterObj.put("collectionName", plugin.getReaderTable());
        parameterObj.put("column", plugin.getColumns());
        readerObj.put("parameter", parameterObj);
        return readerObj;
    }
}
