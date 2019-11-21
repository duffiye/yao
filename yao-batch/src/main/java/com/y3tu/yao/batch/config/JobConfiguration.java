package com.y3tu.yao.batch.config;

import com.y3tu.yao.batch.listener.JobListener;
import com.y3tu.yao.batch.model.AccessTest;
import com.y3tu.yao.batch.reader.AccessAdapterReader;
import com.y3tu.yao.batch.repository.AccessTestRepository;
import com.y3tu.yao.model.Access;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * ClassName: JobConfiguration
 * Description:
 * date: 2019/10/9 14:20
 *
 * @author zht
 */
@Configuration
public class JobConfiguration {
    private final static Logger LOGGER = Logger.getLogger(JobConfiguration.class.getName());

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    /**
     * 注入实例化Factory 访问数据
     */
    @Resource
    private EntityManagerFactory emf;

    @Resource
    private AccessTestRepository accessTestRepository;

    /**
     * 简单的JOB listener
     */
    @Resource
    private JobListener jobListener;

    @Bean
    public Job job1() {
        return jobBuilderFactory.get("dataHandleJob").
                incrementer(new RunIdIncrementer()).
                //start是JOB执行的第一个step
                        start(step1()).
                        listener(jobListener).
                        build();
    }


    @Bean
    public Step step1() {
        return stepBuilderFactory.get("getData").
                // <输入,输出> 。chunk通俗的讲类似于SQL的commit; 这里表示处理(processor)100条后写入(writer)一次。
                        <Access, Access>chunk(100).
                //捕捉到异常就重试,重试100次还是异常,JOB就停止并标志失败
                        faultTolerant().retryLimit(3).retry(Exception.class).skipLimit(100).skip(IndexOutOfBoundsException.class).
                //指定ItemReader
                        reader(getDataReader()).
                //指定ItemProcessor
                        processor(getDataProcessor()).
                //指定ItemWriter
                        writer(getDataWriter()).
                        build();
    }

    @Bean
    public ItemReader<Access> getDataReader() {
        AccessAdapterReader accessAdapterReader = new AccessAdapterReader();
        return accessAdapterReader;
    }


    /*@Bean
    public ItemReader<? extends Access> getDataReader1() {
        //读取数据,这里可以用JPA,JDBC,JMS 等方式 读入数据
        JpaPagingItemReader<Access> reader = new JpaPagingItemReader<>();
        //这里选择JPA方式读数据 一个简单的 native SQL
        String sqlQuery = "SELECT * FROM access";
        try {
            JpaNativeQueryProvider<Access> queryProvider = new JpaNativeQueryProvider<>();
            queryProvider.setSqlQuery(sqlQuery);
            queryProvider.setEntityClass(Access.class);
            queryProvider.afterPropertiesSet();
            reader.setEntityManagerFactory(emf);
            reader.setPageSize(3);
            reader.setQueryProvider(queryProvider);
            reader.afterPropertiesSet();
            //所有ItemReader和ItemWriter实现都会在ExecutionContext提交之前将其当前状态存储在其中,如果不希望这样做,可以设置setSaveState(false)
            reader.setSaveState(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reader;
    }*/


    @Bean
    public ItemProcessor<Access, Access> getDataProcessor() {
        return access -> {
            LOGGER.info("processor data : " + access.toString());  //模拟  假装处理数据,这里处理就是打印一下
            return access;
        };
    }

    @Bean
    public ItemWriter<Access> getDataWriter() {
        return list -> {
            List<AccessTest> list1 = new ArrayList<>();
            for (Access access : list) {
                if (access.getId().equals(20)) {
                    ArrayList list2 = new ArrayList<>();
                    list2.get(2);
                }
                AccessTest accessTest = new AccessTest();
                BeanUtils.copyProperties(access, accessTest);
                list1.add(accessTest);
            }
            accessTestRepository.saveAll(list1);
        };
    }
}
