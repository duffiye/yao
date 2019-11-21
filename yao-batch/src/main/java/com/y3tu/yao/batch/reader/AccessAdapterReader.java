package com.y3tu.yao.batch.reader;

import com.y3tu.yao.batch.config.JobConfiguration;
import com.y3tu.yao.feign.AccessFeignClient;
import com.y3tu.yao.model.Access;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * ClassName: AccessAdapterReader
 * Description:
 * date: 2019/10/11 10:13
 *
 * @author zht
 */
public class AccessAdapterReader extends AbstractItemCountingItemStreamItemReader {

    private final static Logger LOGGER = Logger.getLogger(JobConfiguration.class.getName());

    @Autowired
    private AccessFeignClient accessFeignClient;

    private List<Access> list;


    public AccessAdapterReader() {
        setName(ClassUtils.getShortName(AccessAdapterReader.class));
    }


    @Override
    protected Object doRead() throws Exception {
        Access access = list.get(getCurrentItemCount() - 1);
        /*if (access.getId().equals(101)){
            throw new Exception();
        }*/
        LOGGER.info("reader data : " + access.toString());  //模拟  假装处理数据,这里处理就是打印一下
        return access;
    }

    @Override
    protected void doOpen() throws Exception {

        list = accessFeignClient.listAccess();
        if (CollectionUtils.isEmpty(list)) {
            setMaxItemCount(0);
        } else {
            setMaxItemCount(list.size());
        }
    }

    @Override
    protected void doClose() throws Exception {
        if (!CollectionUtils.isEmpty(list)) {
            list.clear();
        }
        setCurrentItemCount(0);
    }
}
