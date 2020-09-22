package nb;

import nb.anno.*;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * nb协议解析类
 */

public class NbDecodeUtil {
    private static final ThreadLocal<Map<String, Object>> nbDataThreadLocal = new ThreadLocal<>();

    public static String decodeToHex(String string) {
        if (string == null || "".equals(string.trim())) return null;
        byte[] decode = Base64.getDecoder().decode(string);
        return byteToSb(decode);
    }

    public static <T> T decode(String data, Class<T> tClass) throws IllegalAccessException, InstantiationException, NoSuchFieldException, ParseException {
        if (data == null || "".equals(data.trim())) return null;
        byte[] decode = Base64.getDecoder().decode(data);

        return decodeFromByte(decode, tClass);

    }

    public static <T> void takeNbData(T t) throws IllegalAccessException {
        readNbData(t);
        nbDataThreadLocal.remove();
    }

    public static void cleanNbData() {
        nbDataThreadLocal.remove();
    }

    public static <T> void readNbData(T t) throws IllegalAccessException {
        Field[] declaredFields = t.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(NbProp.class)) {
                NbProp prop = declaredField.getDeclaredAnnotation(NbProp.class);
                declaredField.setAccessible(true);
                Object o = nbDataThreadLocal.get().get(prop.value());
                if (o != null) {
                    if (declaredField.getType() == String.class) {
                        if (o instanceof Date) {
                            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date) o);
                            declaredField.set(t, format);
                        } else {
                            declaredField.set(t, o.toString());
                        }

                    } else {
                        declaredField.set(t, o);
                    }
                }

            }
        }
    }

    public static <T> String encode(T t) throws IllegalAccessException, InstantiationException, NoSuchFieldException, ParseException, UnsupportedEncodingException {
        List<Byte> bytesList = encode2Bytes(t, null);
        byte[] bytes = listToArr(bytesList);
        return Base64.getEncoder().encodeToString(bytes);
    }

    private static <T> List<Byte> encode2Bytes(T t, List<Byte> list) throws IllegalAccessException, InstantiationException, NoSuchFieldException, ParseException {
        if (t == null) return null;
        if (list == null)
            list = new ArrayList<>();
        Class tClass = t.getClass();
        Field[] fields = tClass.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(NbSub.class)) {

                encodeField(t, list, tClass, field);
            }
        }

        return list;
    }

    private static <T> void encodeField(T t, List<Byte> list, Class tClass, Field field) throws IllegalAccessException, NoSuchFieldException, ParseException, InstantiationException {
        NbSub sub = field.getDeclaredAnnotation(NbSub.class);
        field.setAccessible(true);
        Object fieldVal = field.get(t);


        Integer startIndex = getStartIndex(field, t, tClass, null);
        Integer lenth = getLenth(field, t, tClass, null);

        if (lenth != null) {
            if (lenth == 0) {
                lenth = list.size() - startIndex;
            }
            while (list.size() < startIndex + lenth) {
                list.add((byte) 0);
            }
        }
        if (field.getType() == String.class) {
            if (lenth == null) return;
            if (fieldVal != null) {
                String val = (String) fieldVal;
                if ("".equals(sub.encodeType())) {


                    if (!"".equals(sub.dict())) {
                        GetSubDictKey getSubDictKey = new GetSubDictKey<T>(tClass, t, sub, null).invoke();
                        for (Object key : getSubDictKey.getDictMap().keySet()) {
                            if (getSubDictKey.getDictMap().get(key).equals(val)) {
                                val = (String) key;
                            }
                        }

                    }

                    if ("DESC".equals(sub.sortType())) {
                        setSubBytesDesc(list, startIndex, lenth, val);

                    } else {

                        int k = startIndex;
                        for (int i = 0; i < lenth; i++) {
                            byte b = subToByte(val, i);
                            setBytesVal(list, k, b);
                            k++;
                        }
                    }

                } else if ("ASCII".equals(sub.encodeType())) {
                    byte[] bytes = val.getBytes(StandardCharsets.US_ASCII);
                    int k = startIndex;
                    for (int i = 0; i < lenth; i++) {
                        byte num ;
                        if (i<bytes.length){
                            num  = bytes[i];
                        }else {
                            num = 0;
                        }
                        setBytesVal(list, k, num);
                        k++;
                    }
                }
            }

            if (!"".equals(sub.csStart()) && !"".equals(sub.csEnd())) {
                //cs校验
                Field csStartField = tClass.getDeclaredField(sub.csStart());
                Field csEndField = tClass.getDeclaredField(sub.csEnd());
                byte[] csBytes = listToArr(list);

                Integer csStart = getStartIndex(csStartField, t, tClass, csBytes);

                Integer endLen = getLenth(csEndField, t, tClass, csBytes);

                if (endLen == null) {
                    encodeField(t, list, tClass, csEndField);
                    endLen = getLenth(csEndField, t, tClass, csBytes);
                }
                int csEnd = getStartIndex(csEndField, t, tClass, csBytes) + endLen;
                int sum = 0;
                for (int i = csStart; i < csEnd; i++) {
                    String hex = byteToHex(list.get(i));
                    sum += Integer.parseInt(hex, 16);

                }
                setBytesVal(list, startIndex, (byte) sum);

            }


        } else if (field.getType() == List.class) {

            List listField = (List) fieldVal;
            int j = startIndex;
            for (Object o : listField) {
                List<Byte> encodeList = encode2Bytes(o, null);
                for (Byte aByte : encodeList) {
                    setBytesVal(list, j, aByte);
                    j++;
                }
            }

            if (lenth == null) {
                String s = sub.lenthProperty();
                Field declaredField = tClass.getDeclaredField(s);
                declaredField.setAccessible(true);
                if (declaredField.getType() == Short.class) {
                    declaredField.set(t, (short) (j + 1));
                } else {
                    declaredField.set(t, j + 1);
                }
                encodeField(t, list, tClass, declaredField);
            }


        } else if (field.getType() == Integer.class) {
            if (lenth == null || fieldVal == null) return;
            if ("HEX".equals(sub.encodeType())) {
                String val = add0(fieldVal.toString(), lenth);

                setSubBytesDesc(list, startIndex, lenth, val);

            } else {
                Integer j = (Integer) fieldVal;
                byte[] bytes = int2ByteArray(j);

                int k = startIndex;
                for (int i = 0; i < lenth; i++) {
                    setBytesVal(list, k, bytes[i]);
                    k++;
                }

            }
        } else if (field.getType() == Short.class) {
            if (lenth == null || fieldVal == null) return;
            Short j = (Short) fieldVal;
            byte[] bytes = short2ByteArray(j);

            int k = startIndex;
            for (int i = 0; i < lenth; i++) {

                setBytesVal(list, k, bytes[i]);
                k++;
            }

        } else if (field.getType() == Date.class) {
            if (lenth == null || fieldVal == null) return;
            SimpleDateFormat dateFormat;
            if (lenth == 7) {
                dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            } else {
                dateFormat = new SimpleDateFormat("yyMMddHHmmss");
            }
            String str = dateFormat.format((Date) fieldVal);

            setSubBytesDesc(list, startIndex, lenth, str);

        } else if (field.getType() == Double.class) {
            if (lenth == null || fieldVal == null) return;
            Double dVal = (Double) fieldVal;

            if ("HEX".equals(sub.encodeType())) {
                BigDecimal bigDecimal = new BigDecimal(dVal);
                BigDecimal result = bigDecimal.setScale(sub.scale(), RoundingMode.HALF_UP);
                String str = add0(result.toString().replace(".", ""), lenth);

                setSubBytesDesc(list, startIndex, lenth, str);

            }

        } else {
            if (fieldVal == null)return;
            List<Byte> encodeList = encode2Bytes(fieldVal, null);

            int j = startIndex;
            for (Byte aByte : encodeList) {

                setBytesVal(list, j, aByte);
                j++;
            }

            if (lenth == null) {
                String s = sub.lenthProperty();
                Field declaredField = tClass.getDeclaredField(s);
                declaredField.setAccessible(true);
                if (declaredField.getType() == Short.class) {
                    declaredField.set(t, (short) encodeList.size());
                } else {
                    declaredField.set(t, encodeList.size());
                }
                encodeField(t, list, tClass, declaredField);
            }

        }
    }

    private static byte[] listToArr(List<Byte> list) {
        byte[] csBytes = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            csBytes[i] = list.get(i);
        }
        return csBytes;
    }

    private static void setSubBytesDesc(List<Byte> list, Integer startIndex, Integer lenth, String val) {
        int k = startIndex;
        for (int i = lenth - 1; i >= 0; i--) {
            byte b = subToByte(val, i);
            setBytesVal(list, k, b);
            k++;
        }
    }

    private static void setBytesVal(List<Byte> bytes, int index, Byte val) {
        if (index > bytes.size() - 1) {
            while (bytes.size() < index + 1)
                bytes.add((byte) 0);
        }
        bytes.set(index, val);
    }

    private static String add0(String str, int len) {

        StringBuilder strBuilder = new StringBuilder(str);
        while (strBuilder.length() < len * 2) {
            strBuilder.insert(0, "0");
        }
        str = strBuilder.toString();
        return str;
    }

    private static byte[] short2ByteArray(short i) {
        byte[] result = new byte[2];
        // 由低位到高位
        result[1] = (byte) ((i >> 8) & 0xFF);
        result[0] = (byte) (i & 0xFF);
        return result;
    }

    private static byte[] int2ByteArray(int i) {
        byte[] result = new byte[4];
        // 由低位到高位
        result[3] = (byte) ((i >> 24) & 0xFF);
        result[2] = (byte) ((i >> 16) & 0xFF);
        result[1] = (byte) ((i >> 8) & 0xFF);
        result[0] = (byte) (i & 0xFF);
        return result;
    }

    private static byte subToByte(String val, Integer index) {

        byte b = 0;
        if (val != null) {
            String hex = val.substring(index * 2, (index + 1) * 2);
            b = (byte) (Integer.parseInt(hex, 16) & 0xFF);

        }
        return b;
    }


    private static <T> T decodeFromByte(byte[] decode, Class<T> tClass) throws IllegalAccessException, InstantiationException, NoSuchFieldException, ParseException {
        T t = tClass.newInstance();
        decodeToInstance(decode, t);
        return t;

    }

    private static <T> T decodeToInstance(byte[] decode, T t) throws IllegalAccessException, InstantiationException, NoSuchFieldException, ParseException {
        Class tClass = t.getClass();
        Field[] fields = tClass.getDeclaredFields();
        for (Field field : fields) {

            if (field.isAnnotationPresent(NbSub.class)) {
                initField(decode, tClass, t, field);
                putThreadData(t, tClass, field);

            } else if (field.isAnnotationPresent(NbBitSub.class)) {
                field.setAccessible(true);
                NbBitSub bitSub = field.getDeclaredAnnotation(NbBitSub.class);
                Field declaredField = tClass.getDeclaredField(bitSub.prop());
                declaredField.setAccessible(true);
                Object o = declaredField.get(t);
                if (o == null) {
                    initField(decode, tClass, t, declaredField);
                    o = declaredField.get(t);
                }
                if (o instanceof String) {
                    String str = (String) o;
                    StringBuilder sb = new StringBuilder();
                    for (int i = str.length(); i > 0; i -= 2) {
                        String substring = str.substring(i - 2, i);
                        String binaryString = Integer.toBinaryString(Integer.parseInt(substring, 16));
                        StringBuilder binSb = new StringBuilder(binaryString);
                        while (binSb.length() < 8) {
                            binSb.insert(0, "0");
                        }
                        sb.append(binSb);
                    }
                    String substring = sb.substring(sb.length() - bitSub.index()- bitSub.len(), sb.length() - bitSub.index());
                    if (field.getType() == String.class) {
                        field.set(t, substring);
                    } else {
                        field.set(t, Integer.parseInt(substring));
                    }


                }
                putThreadData(t, tClass, field);

            }


        }

        return t;
    }

    private static <T> void putThreadData(T t, Class tClass, Field field) throws IllegalAccessException {
        if (tClass.isAnnotationPresent(NbData.class)) {
            Map<String, Object> stringObjectMap = nbDataThreadLocal.get();
            if (stringObjectMap == null) {
                stringObjectMap = new HashMap<>();
                nbDataThreadLocal.set(stringObjectMap);
            }
            stringObjectMap.put(field.getName(), field.get(t));

        }
    }

    private static <T> void initField(byte[] decode, Class<T> tClass, T t, Field field) throws NoSuchFieldException, IllegalAccessException, InstantiationException, ParseException {
        NbSub sub = field.getDeclaredAnnotation(NbSub.class);
        field.setAccessible(true);
        Integer startIndex = getStartIndex(field, t, tClass, decode);
        Integer lenth = getLenth(field, t, tClass, decode);
        byte[] bytes;

        if (lenth == 0) {
            if (decode.length > startIndex) {
                bytes = copyOfRange(decode, startIndex, decode.length);
            } else {
                return;
            }

        } else {
            bytes = copyOfRange(decode, startIndex, startIndex + lenth);
        }


        if (field.getType() == String.class) {
            String subStr = null;
            if ("".equals(sub.encodeType())) {

                if ("DESC".equals(sub.sortType())) {
                    byte[] bytesCopy = new byte[bytes.length];
                    for (int j = 0; j < bytes.length; j++) {
                        bytesCopy[j] = bytes[bytes.length - j - 1];
                    }
                    subStr = byteToSb(bytesCopy);
                } else {
                    subStr = byteToSb(bytes);
                }
                if (!"".equals(sub.dict())) {
                    GetSubDictKey getSubDictKey = new GetSubDictKey<T>(tClass, t, sub, bytes).invoke();
                    Map dictMap = getSubDictKey.getDictMap();
                    subStr = (String) dictMap.get(subStr);
                }
                field.set(t, subStr);

            } else if ("ASCII".equals(sub.encodeType())) {
                String s = new String(bytes, StandardCharsets.US_ASCII);
                field.set(t, s.trim());
            }

            if (!"".equals(sub.csStart()) && !"".equals(sub.csEnd())) {
                //cs校验
                Field csStartField = tClass.getDeclaredField(sub.csStart());
                Field csEndField = tClass.getDeclaredField(sub.csEnd());
                Integer csStart = getStartIndex(csStartField, t, tClass, decode);

                Integer endLen = getLenth(csEndField, t, tClass, decode);
                if (endLen == null) {
                    initField(decode, tClass, t, csEndField);
                    endLen = getLenth(csEndField, t, tClass, decode);
                }
                int csEnd = getStartIndex(csEndField, t, tClass, decode) + endLen;
                int sum = 0;
                for (int i = csStart; i < csEnd; i++) {
                    String hex = byteToHex(decode[i]);
                    sum += Integer.parseInt(hex, 16);
                }
                String hexSum = Integer.toHexString(sum);

                String csCode = hexSum.substring(hexSum.length() - 2);
                if (!csCode.equalsIgnoreCase(subStr)) {

                    throw new RuntimeException("校验失败");
                }

            }

        } else if (field.getType() == List.class) {
            ArrayList<Object> list = new ArrayList<>();
            Class aClass = sub.listClass();
            Field countFiled = tClass.getDeclaredField(sub.listCount());
            countFiled.setAccessible(true);
            Integer count = (Integer) countFiled.get(t);
            for (int j = 0; j < count; j++) {
                Object o = decodeFromByte(copyOfRange(bytes, j * bytes.length / count, (j + 1) * bytes.length / count), aClass);
                list.add(o);
            }
            field.set(t, list);
        } else if (field.getType() == Integer.class) {

            int result = 0;

            if ("HEX".equals(sub.encodeType())) {
                String s = byte2DescSub(bytes);

                result = Integer.parseInt(s);

            } else {
                for (int j = bytes.length - 1; j >= 0; j--) {
                    String s = byteToHex(bytes[j]);

                    int intJ = Integer.parseInt(s,16);
                    result = result ^ (intJ << (8 * j));


                }
            }

            field.set(t, result);
        } else if (field.getType() == Short.class) {
            short result = 0;
            for (int j = bytes.length - 1; j >= 0; j--) {
                short shortJ = bytes[j];
                result = (short) (result ^ (shortJ << (8 * j)));
            }
            field.set(t, result);
        } else if (field.getType() == Date.class) {
            StringBuilder stringBuilder = new StringBuilder();
            SimpleDateFormat dateFormat;
            if (bytes.length == 7) {
                dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            } else {
                dateFormat = new SimpleDateFormat("yyMMddHHmmss");
            }
            for (int j = bytes.length - 1; j >= 0; j--) {
                byte aByte = bytes[j];
                String s = byteToHex(aByte);
                stringBuilder.append(s);

            }
            field.set(t, dateFormat.parse(stringBuilder.toString()));
        } else if (field.getType() == Double.class) {
            Double result = null;

            if ("HEX".equals(sub.encodeType())) {

                String s = byte2DescSub(bytes);

                BigDecimal resultD = new BigDecimal(s.substring(0, s.length() - sub.scale()) + "." + s.substring(s.length() - sub.scale()));
                result = resultD.doubleValue();

            }

            field.set(t, result);

        } else {
            Object target;
            Object o;
            if (field.getType() == Object.class) {
                GetSubDictKey getSubDictKey = new GetSubDictKey<T>(tClass, t, sub, bytes).invoke();
                target = getSubDictKey.getTarget();
            } else {
                target = field.getType().newInstance();
            }
            if (target instanceof String) {
                o = byteToSb(bytes);
            } else {

                if (field.getType().isAnnotationPresent(NbPrePro.class)) {
                    NbPrePro nbPrePro = field.getType().getDeclaredAnnotation(NbPrePro.class);
                    for (int i = 0; i < nbPrePro.pros().length; i++) {
                        Field declaredField = tClass.getDeclaredField(nbPrePro.pros()[i]);
                        declaredField.setAccessible(true);
                        Object pro = declaredField.get(t);
                        if (pro == null) {
                            initField(decode, tClass, t, declaredField);
                        }
                        pro = declaredField.get(t);
                        Field targetField = field.getType().getDeclaredField(nbPrePro.pros()[i]);
                        targetField.setAccessible(true);
                        targetField.set(target, pro);
                    }

                }
                o = decodeToInstance(bytes, target);
            }
            field.set(t, o);
        }
    }

    private static String byte2DescSub(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int j = bytes.length - 1; j >= 0; j--) {
            String s = byteToHex(bytes[j]);
            sb.append(s);

        }
        return sb.toString();
    }

    private static String byteToHex(byte aByte) {
        String s = Integer.toHexString(aByte);
        s = s.length() == 1 ? "0" + s : s.length() > 2 ? s.substring(s.length() - 2) : s;
        return s.toUpperCase();
    }

    private static String byteToSb(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int j = 0; j < bytes.length; j++) {
            byte aByte = bytes[j];
            String s = byteToHex(aByte);
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }

    private static <T> Integer getStartIndex(Field field, T t, Class<T> tClass, byte[] decode) throws NoSuchFieldException, IllegalAccessException, ParseException, InstantiationException {

        if (field.isAnnotationPresent(NbSub.class)) {
            NbSub sub = field.getDeclaredAnnotation(NbSub.class);
            if ("".equals(sub.preProperty())) {
                return 0;
            } else {
                Field declaredField = tClass.getDeclaredField(sub.preProperty());
                return getStartIndex(declaredField, t, tClass, decode) + getLenth(declaredField, t, tClass, decode);
            }


        }
        return null;
    }

    private static <T> Integer getLenth(Field field, T t, Class<T> tClass, byte[] decode) throws NoSuchFieldException, IllegalAccessException, ParseException, InstantiationException {

        if (field.isAnnotationPresent(NbSub.class)) {
            NbSub sub = field.getDeclaredAnnotation(NbSub.class);
            if (!"".equals(sub.lenthProperty())) {
                Field declaredField = tClass.getDeclaredField(sub.lenthProperty());
                declaredField.setAccessible(true);
                Object o = declaredField.get(t);
                if (o == null && decode != null) {
                    initField(decode, tClass, t, declaredField);
                    o = declaredField.get(t);
                }
                if (o == null) {
                    return null;
                }
                return ((Number) o).intValue();

            } else {
                return sub.length();
            }

        }
        return null;
    }

    private static class GetSubDictKey<T> {
        private Class<T> tClass;
        private T t;
        private NbSub sub;
        private Map dictMap;
        private String key;
        private Object target;
        private byte[] bytes;

        public GetSubDictKey(Class<T> tClass, T t, NbSub sub, byte[] bytes) {
            this.tClass = tClass;
            this.t = t;
            this.sub = sub;
            this.bytes = bytes;
        }

        public Map getDictMap() {
            return dictMap;
        }

        public String getKey() {
            return key;
        }

        public Object getTarget() {
            return target;
        }

        public GetSubDictKey invoke() throws NoSuchFieldException, IllegalAccessException, InstantiationException, ParseException {
            String dict = sub.dict();
            String[] propKey = sub.propKey();
            Field dictField = tClass.getDeclaredField(dict);
            dictField.setAccessible(true);
            dictMap = (Map) dictField.get(t);
            int dictKeyI = 0;
            boolean isCon = true;

            Iterator iterator = dictMap.keySet().iterator();
            String dictKey = null;
            while (iterator.hasNext()) {
                if (isCon)
                    dictKey = (String) iterator.next();

                StringBuilder sb = new StringBuilder();
                Object o = dictMap.get(dictKey);

                for (int i = 0; i < propKey.length; i++) {
                    if (o instanceof Class) {
                        if (((Class) o).isAnnotationPresent(NBDicKey.class)) {
                            NBDicKey anno = (NBDicKey) ((Class) o).getDeclaredAnnotation(NBDicKey.class);
                            if (dict.equals(anno.dict()) && i == anno.index()) {
                                Field declaredField = ((Class) o).getDeclaredField(anno.prop());
                                declaredField.setAccessible(true);
                                Object o1 = ((Class) o).newInstance();
                                initField(bytes, (Class) o, o1, declaredField);
                                Object o2 = declaredField.get(o1);
                                int checkEq = checkEq( o2.toString().toCharArray(), anno.key()[dictKeyI].toCharArray());
                                if (checkEq == 1||checkEq==2) {
                                    sb.append(o2);
                                    o = o1;
                                }

                                if (dictKeyI == anno.key().length - 1) {
                                    isCon = true;
                                    dictKeyI = 0;
                                } else {
                                    isCon = false;
                                    dictKeyI++;
                                }
                            }

                        }
                    }
                    String s = propKey[i];
                    Field keyFiled = tClass.getDeclaredField(s);
                    keyFiled.setAccessible(true);
                    String fieldKey = keyFiled.get(t).toString();
                    sb.append(fieldKey);
                }
                key = sb.toString();
                char[] keyChars = key.toCharArray();

                char[] chars = ((String) dictKey).toCharArray();
                if (chars.length != keyChars.length) continue;

                int checkEq = checkEq(keyChars, chars);


                if (checkEq == 1) {

                    if (o instanceof Class)
                        target = ((Class) o).newInstance();
                    else target = o;
                } else if (checkEq == 2) {

                    if (o instanceof Class)
                        target = ((Class) o).newInstance();
                    else target = o;

                    break;
                }

            }
            return this;
        }

        private int checkEq(char[] sq1, char[] sq2) {
            boolean eq = true;
            boolean equ = true;

            for (int i = 0; i < sq2.length; i++) {
                if (sq1[i] != sq2[i]) {
                    if (sq2[i] != '*') {
                        eq = false;

                        break;
                    }
                    equ = false;
                }

            }

            if (eq&&!equ) return 1;
            if (eq) return 2;
            else return 3;
        }
    }
    public static byte[] copyOfRange(byte[] original, int from, int to) {
        int newLength = to - from;
        if (newLength < 0) {
            throw new IllegalArgumentException(from + " > " + to);
        } else {
            byte[] copy = new byte[newLength];
            System.arraycopy(original, from, copy, 0, Math.min(original.length - from, newLength));
            return copy;
        }
    }
}
