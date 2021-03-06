package com.es.es;

import com.es.es.mapper.BShopMapper;
import com.es.es.mapper.StatisticsOrderMapper;
import com.es.es.po.*;
import com.es.es.repository.OrderRepository;
import com.es.es.repository.SalaryRepository;
import com.es.es.repository.ShopRepository;
import org.assertj.core.util.Lists;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.InternalDateHistogram;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.NumericMetricsAggregation;
import org.elasticsearch.search.aggregations.metrics.sum.ParsedSum;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHits;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsApplicationTests {

    @Autowired
    private BShopMapper shopMapper;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private StatisticsOrderMapper statisticsOrderMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void contextLoads() {
        List<BShop> shops = shopMapper.selectAll();
        ArrayList<Shop> list = new ArrayList<>();
        for (BShop shop : shops) {
            Shop shopIndex = Shop.builder().build();
            BeanUtils.copyProperties(shop, shopIndex);
            String shopLat = shop.getShopLat();
            String shopLng = shop.getShopLng();
            if(shopLat!=null && !"".equals(shopLat)){
                GeoPoint point = new GeoPoint(Double.parseDouble(shopLat),Double.parseDouble(shopLng));
                shopIndex.setLocation(point);
            }
            list.add(shopIndex);
        }

        shopRepository.saveAll(list);
        shops.forEach(System.out::println);
    }

    @Test
    public void fun(){
        String format = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now());
        System.out.println(LocalDate.now());

        BigDecimal reduce = Stream.of(new BigDecimal(0), new BigDecimal(0.333), new BigDecimal(0.333)).reduce(BigDecimal.ZERO,BigDecimal::add).setScale(2,BigDecimal.ROUND_HALF_UP);
        System.out.println(reduce);
    }

    @Test
    public void fun2(){
        StatisticsOrder byOrderId = statisticsOrderMapper.findByOrderId("18123012292295487260");
        Order order = Order.builder().build();
        BeanUtils.copyProperties(byOrderId,order);
        orderRepository.save(order);
    }

    @Autowired
    private SalaryRepository salaryRepository;

    @Test
    public void funsalary() throws ParseException {
        ArrayList<Salary> salaries = Lists.newArrayList(
                Salary.builder()
                        .id(1)
                        .city("??????")
                        .year("2019")
                        .month("6")
                        .level(Character.valueOf('A').toString())
                        .name("??????")
                        .text("11133vvv")
                        .price3(100).dateTime(new SimpleDateFormat("yyyyMMdd").parse("20190601")).price4(100)
                        .build(),
                Salary.builder()
                        .id(2)
                        .city("??????")
                        .year("2019")
                        .month("5")
                        .level(Character.valueOf('A').toString())
                        .name("??????")
                        .text("11133vvv")
                        .price3(100).dateTime(new SimpleDateFormat("yyyyMMdd").parse("20190501")).price4(100)
                        .build(),
                Salary.builder()
                        .id(3)
                        .city("??????")
                        .year("2019")
                        .month("5")
                        .level(Character.valueOf('A').toString())
                        .name("??????")
                        .text("11133vvv")
                        .price3(100).dateTime(new SimpleDateFormat("yyyyMMdd").parse("20190501")).price4(100)
                        .build(),
                Salary.builder()
                        .id(4)
                        .city("??????")
                        .year("2019")
                        .month("6")
                        .level(Character.valueOf('A').toString())
                        .price("5000.00")
                        .name("??????")
                        .text("11133vvv")
                        .price3(100)
                        .price4(100)
                        .dateTime(new SimpleDateFormat("yyyyMMdd").parse("20190601"))
                        .build(),
        Salary.builder()
                .id(5)
                .city("??????")
                .year("2019")
                .month("6")
                .level(Character.valueOf('A').toString())
                .price("5000.00")
                .name("??????")
                .text("11133vvv")
                .price3(100)
                .price4(100)
                .dateTime(new SimpleDateFormat("yyyyMMdd").parse("20191201"))
                .build(),        Salary.builder()
                .id(6)
                .city("??????")
                .year("2019")
                .month("6")
                .level(Character.valueOf('A').toString())
                .price("5000.00")
                .name("??????")
                .text("11133vvv")
                .price3(100)
                .price4(100)
                .dateTime(new SimpleDateFormat("yyyyMMdd").parse("20190601"))
                .build(),        Salary.builder()
                .id(7)
                .city("??????")
                .year("2019")
                .month("6")
                .level(Character.valueOf('A').toString())
                .price("5000.00")
                .name("??????")
                .text("11133vvv")
                .price3(100)
                .price4(100)
                .dateTime(new SimpleDateFormat("yyyyMMdd").parse("20190601"))
                .build(),        Salary.builder()
                .id(8)
                .city("??????")
                .year("2019")
                .month("6")
                .level(Character.valueOf('A').toString())
                .price("5000.00")
                .name("??????")
                .text("11133vvv")
                .price3(100)
                .price4(100)
                .dateTime(new SimpleDateFormat("yyyyMMdd").parse("20190401"))
                .build(),        Salary.builder()
                .id(9)
                .city("??????")
                .year("2019")
                .month("6")
                .level(Character.valueOf('A').toString())
                .price("5000.00")
                .name("??????")
                .text("11133vvv")
                .price3(100)
                .price4(100)
                .dateTime(new SimpleDateFormat("yyyyMMdd").parse("20190301"))
                .build(),        Salary.builder()
                .id(10)
                .city("??????")
                .year("2019")
                .month("6")
                .level(Character.valueOf('A').toString())
                .price("5000.00")
                .name("??????")
                .text("11133vvv")
                .price3(100)
                .price4(100)
                .dateTime(new SimpleDateFormat("yyyyMMdd").parse("20190201"))
                .build(),        Salary.builder()
                .id(11)
                .city("??????")
                .year("2019")
                .month("6")
                .level(Character.valueOf('A').toString())
                .price("5000.00")
                .name("??????")
                .text("11133vvv")
                .price3(100)
                .price4(100)
                .dateTime(new SimpleDateFormat("yyyyMMdd").parse("20190101"))
                .build());

        salaryRepository.saveAll(salaries);
    }

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Test
    public void funSearch() throws ParseException {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        QueryBuilder query = QueryBuilders.boolQuery()
                .must(QueryBuilders.rangeQuery("dateTime").gte(new SimpleDateFormat("yyyyMMdd").parse("20190501").getTime())
                        .lte(new SimpleDateFormat("yyyyMMdd").parse("20190601").getTime()))
                ;

        queryBuilder.withQuery(query);

//        ((BoolQueryBuilder) query).must(QueryBuilders.matchQuery("name","??????"));

        //??????????????????
//        queryBuilder.addAggregation(AggregationBuilders.terms("cityId").field("cityId").size(1000)
//                .subAggregation(AggregationBuilders.terms("teamId").field("teamId"))//??????????????????
//                .subAggregation(AggregationBuilders.topHits("priceSum2").size(1))
//        );
//??????
        queryBuilder.withPageable(PageRequest.of(2, 5));
//??????
//        queryBuilder.withSort(SortBuilders.fieldSort("month").order(SortOrder.DESC));

        AggregatedPage<Salary> result = elasticsearchTemplate.queryForPage(queryBuilder.build(), Salary.class);

        //????????????
        Aggregations aggr = result.getAggregations();

        //???????????????????????????
        StringTerms terms = aggr.get("group_month");

        //?????????
        List<StringTerms.Bucket> buckets = terms.getBuckets();

        for (StringTerms.Bucket bucket : buckets) {
            String keyAsString = bucket.getKeyAsString();
            Sum priceSum = bucket.getAggregations().get("priceSum");
            System.out.println(priceSum.getValue());
            long docCount = bucket.getDocCount();
            System.out.println("key:"+keyAsString+" Count:"+docCount);
        }
    }


    /**
     * ?????? ??????
     * @throws ParseException
     */
    @Test
    public void aggrDate() throws ParseException {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        DateHistogramAggregationBuilder dateHistogramName = AggregationBuilders
                .dateHistogram("dateHistogramName")
                .field("dateTime")                                  //????????????
                .dateHistogramInterval(DateHistogramInterval.MONTH) //?????????????????????
                .format("yyyy-MM-dd");                              //?????????

        //??????????????? ??????
        dateHistogramName.subAggregation(AggregationBuilders.sum("sumName").field("price3"));
        queryBuilder.addAggregation(dateHistogramName);

        AggregatedPage<Salary> result = elasticsearchTemplate.queryForPage(queryBuilder.build(), Salary.class);

        //????????????
        Aggregations aggr = result.getAggregations();

        //???????????????????????????
        InternalDateHistogram terms = aggr.get("dateHistogramName");

        //?????????
        List<InternalDateHistogram.Bucket> buckets = terms.getBuckets();

        for (InternalDateHistogram.Bucket bucket : buckets) {
            String keyAsString = bucket.getKeyAsString(); //????????????????????????????????????
            Sum priceSum = bucket.getAggregations().get("sumName"); //??????????????????
            System.out.println(priceSum.getValue());
            long docCount = bucket.getDocCount();
            System.out.println("key:"+keyAsString+" Count:"+docCount);
        }
    }


    /**
     * ?????????????????????
     * @throws ParseException
     */
    @Test
    public void aggrTopHits() throws ParseException {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders
                .terms("cityId").field("cityId").size(1000)
                .subAggregation(
                        AggregationBuilders.topHits("topHits")
//                                .fetchSource(new String[]{ "cityName"},null)//???????????? ?????????
                                .size(1)//?????????????????? ??????
                );

        queryBuilder.addAggregation(termsAggregationBuilder);

        AggregatedPage<Salary> result = elasticsearchTemplate.queryForPage(queryBuilder.build(), Salary.class);

        //????????????
        Aggregations aggr = result.getAggregations();

        //???????????????????????????
        StringTerms terms = aggr.get("cityId");

        //?????????
        List<StringTerms.Bucket> buckets = terms.getBuckets();

        for (StringTerms.Bucket bucket : buckets) {
            String keyAsString = bucket.getKeyAsString(); //?????? key

            TopHits topHits = bucket.getAggregations().get("topHits");
            SearchHit[] hits = topHits.getHits().getHits();
            SearchHit hit = hits[0];
            Map<String, Object> sourceAsMap = hit.getSourceAsMap(); //????????????
        }
    }


    @Test
    public void delete(){
        salaryRepository.deleteAll();
        elasticsearchTemplate.deleteIndex(SalaryIndexDto.class);
    }


    @Test
    public void create(){
        elasticsearchTemplate.createIndex(SalaryIndexDto.class);
    }


    @Test
    public void tettt(){

//        elasticsearchTemplate.deleteIndex(Salary.class);

        salaryRepository.save(Salary.builder().id(22341).name("?????????").build());

    }

    @Test
    public void testQuery(){
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        QueryBuilder query = QueryBuilders.matchQuery("name","?????????").operator(Operator.AND);
        queryBuilder.withQuery(query);
        AggregatedPage<Salary> salaries = elasticsearchTemplate.queryForPage(queryBuilder.build(), Salary.class);
        List<Salary> content = salaries.getContent();
    }



}
