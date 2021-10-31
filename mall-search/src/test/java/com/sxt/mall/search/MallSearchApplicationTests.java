package com.sxt.mall.search;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = {MallSearchApplication.class})
public class MallSearchApplicationTests {
//    @Reference
//    private SearchProductService searchProductService;
//    @Autowired
//    private JestClient jestClient;
//
//    @Test
//    public void test1() throws IOException {
//        Search build = new Search.Builder("")
//                .addIndex("product")
//                .addType("info")
//                .build();
//        SearchResult execute = this.jestClient.execute(build);
//        System.out.println(execute.getTotal());
//    }
//
//    @Test
//    public void dslTest(){
//        SearchParam searchParam = new SearchParam();
//        searchParam.setKeyword("手机");
//
//        String[] brand = new String[]{"苹果"};
//        searchParam.setBrand(brand);
//
//        searchParam.setPriceFrom(5000);
//        searchParam.setPriceTo(10000);
//
//        String[] props = new String[]{"45:4.7","46:4G"};
//        searchParam.setProps(props);
//
//        searchProductService.searchProduct(searchParam);
//    }
    public static void main(String[] args){
//        String[] s = {"1:青年-老人-女士","2:苹果-小米-华为"};
//        for (String a: s){
//            String[] split = a.split(":");
//            Arrays.stream(split).forEach(System.out::println);
//            System.out.println("==========");
//            System.out.println(split);
//            System.out.println(split[0]);
//            System.out.println(split[1].split("-"));
//            System.out.println(a);
//        }
        String name = "123.456aBcDeeffDDg";
        String f = "abc";
        System.out.println(name.substring(name.lastIndexOf(".")));
        System.out.println(name.toUpperCase());
        System.out.println( f.equalsIgnoreCase("abc"));
    }
}
