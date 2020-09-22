package nb.nbpro;

import nb.anno.NbPrePro;
import nb.anno.NbSub;

import java.util.HashMap;


@NbPrePro(pros = "protocolId")
public class NBOutProApp extends BaseEntity{
    private String protocolId;

    @NbSub(length = 1)
    private String afn;


    @NbSub(preProperty = "afn",length = 1)
    private String seq = "FF";


    @NbSub(preProperty = "seq",length = 1)
    private Integer fn;


    @NbSub(preProperty = "fn",dict = "dict",propKey = {"protocolId","afn","fn"})
    private Object dataUnit;

    private static final HashMap<String,Class> dict = new HashMap<>();
    static {
        dict.put("04001",String.class);
        dict.put("09001",String.class);
        dict.put("08001",String.class);
        dict.put("11001",String.class);
        dict.put("47001",String.class);
        dict.put("4A001",String.class);

        dict.put("090A1", NbOutPro47DataUnitUp.class);
        dict.put("080A1", NbOutPro47DataUnitUp.class);
        dict.put("110A1", NbOutPro47DataUnitUp.class);
//        dict.put("15**1", NbOutPro47DataUnitUp.class);//VAC100阀门
        dict.put("040A1", NbOutPro47DataUnitUp.class);
        dict.put("470A1", NbOutPro47DataUnitUp.class);

        dict.put("4A0A1", NbOutPro4ADataUnitUp.class);


        dict.put("090C1", NbInnerPro47Wrapper.class);
        dict.put("080C1", NbInnerPro47Wrapper.class);
        dict.put("110C1", NbInnerPro47Wrapper.class);
//        dict.put("15**1", NbOutPro47DataUnitUp.class);//VAC100阀门
        dict.put("040C1", NbInnerPro47Wrapper.class);
        dict.put("470C1", NbInnerPro47Wrapper.class);

//        dict.put("4A0A1", NbOutPro4ADataUnitUp.class);
//        dict.put("4705254",NbValveColSet.class);
        dict.put("4705255",NbValveColSetData.class);
    }



}
