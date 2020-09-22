# NbDecodeUtil
解析base64编码的码流为对象，通过注解指定每个属性对应的字节

1.用于解析和编码base64编码后的码流

2.支持String,Date,Integer,Double,List,Object,Short六种数据类型,支持嵌套解析与嵌套编码。解码自动cs校验，校验失败抛出RuntimeException.编码自动生成数据长度与cs校验码（如果用于编码的bean中有长度，并且长度不正确，麻烦在编码时设置为null，不然会补0）

3.解析，传入base64编码后的字符串和需要生成的对象的class

```
 public static <T> T decode(String data, Class<T> tClass) throws IllegalAccessException, InstantiationException, NoSuchFieldException, ParseException {
        if (data == null || "".equals(data.trim())) return null;
        byte[] decode = Base64.getDecoder().decode(data);

        return decodeFromByte(decode, tClass);

    }
```
4.编码，传入对象t，编码为base64字符串
```
  public static <T> String encode(T t) throws IllegalAccessException, InstantiationException, NoSuchFieldException, ParseException, UnsupportedEncodingException {
        List<Byte> bytesList = encode2Bytes(t, null);
        byte[] bytes = listToArr(bytesList);
        return Base64.getEncoder().encodeToString(bytes);
    }
```
5.注解说明
```
@Target({ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NbSub {

    int length() default 0; //属性长度

    String lenthProperty() default "";//属性长度由其它属性指定时，对应的其它属性的属性名

    String preProperty() default ""; //上一个属性的名称，如果是第一个属性，不用指定

    String encodeType() default ""; //编码方式：HEX：用于Integer和Double类型，代表由低位在前高位在后的两个16进制数拼接而成，如代表两个字节的16进制1234解析为Integer为3412.
    								//ASCII:代表该属性的这一段码流为ASCII编码，用于String类型

    String dict() default "";//字典：嵌套解析时在Object类型上使用，字典中存入需要的类型

    String[] propKey() default {};//确定Object类型的几个属性名

    String listCount() default ""; //用于List类型，代表List的size的属性名

    Class listClass() default Object.class; //用于List类型，list中存入的对象的Class

    String sortType() default "";//用于String，DESC表示倒序

    int scale() default 0; //用于Double，指定当前属性所要保留的小数位数

    boolean csVer() default false;

    String csStart() default ""; //用于cs校验的属性，校验起始位置的属性名

    String csEnd() default "";//校验终止位置的属性名
}
```
```
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NbPrePro {
    String[] pros() default {}; //嵌套解析时，需要把外层的属性解析到内层，在内层的类上添加该属性指定需要的属性名，并在内层类中定义与外层相同属性名的属性
}
```
```
//将低位在前高位在后的16进制串解析成二进制的每一位，不能与NbSub一同使用，需要写在需要解析属性的后面，需要解析属性为低位在前高位在后的16进制串
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NbBitSub {
    int index() default 0;//二进制起始位置，由低位起
    String prop() default "";//需要解析的属性
    int len() default 1;//解析的二进制长度
}
```
```
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NbProp {
    String value() default "";
}
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NbData {

}
//类上添加NbData注解使用decode方法时，该类中的属性及数据被存储到当前线程的map中，调用此方法将数据存储到t中标注了NbProp并指定了属性名的属性中去
 public static <T> void takeNbData(T t) throws IllegalAccessException {
    readNbData(t);
    nbDataThreadLocal.remove();
 }
 //清除当前线程中存储的属性
 public static void cleanNbData() {
    nbDataThreadLocal.remove();
 }
```
```
//用于嵌套解析确定Object类型时，如果外层属性无法确定Object类型，还需内层属性，则在内层类上添加该注解,使用方式较抽象请看示例代码。
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NBDicKey {
    String[] key() default {};
    int index() default 0;
    String dict() default "";
    String prop() default "";
}
```
