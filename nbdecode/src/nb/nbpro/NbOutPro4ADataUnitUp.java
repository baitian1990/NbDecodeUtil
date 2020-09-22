package nb.nbpro;


import nb.anno.NbBitSub;
import nb.anno.NbData;
import nb.anno.NbSub;

import java.util.Date;
import java.util.List;

@NbData
public class NbOutPro4ADataUnitUp extends BaseEntity{


    @NbSub(length = 1)
    private String dataFlg;


    @NbSub(preProperty = "dataFlg",length = 20,encodeType = "ASCII")
    private String model;

    @NbSub(preProperty = "model",length = 1)
    private String operators;

    @NbSub(preProperty = "operators",length = 1)
    private String version;


    @NbSub(preProperty = "version",length = 20,sortType = "DESC")
    private String iccid;


    @NbSub(preProperty = "iccid",length = 6)
    private Date modelTime;

    @NbSub(preProperty = "modelTime",length = 1)
    private String signal;


    @NbSub(preProperty = "signal",length = 2)
    private Short vol;

    @NbSub(preProperty = "vol",length = 2)
    private Integer connectTime;

    @NbSub(preProperty = "connectTime",length = 4)
    private String reserve;


    @NbSub(preProperty = "reserve",length = 2)
    private String ST;


    @NbSub(preProperty = "ST",length = 1)
    private Integer dataCount;


    @NbSub(preProperty = "dataCount",listCount = "dataCount",listClass = NbInnerPro47Wrapper.class)
    private List<NbDataSensor> data;

    @NbBitSub(prop = "ST",index = 0,len = 1)
    private Integer moveAlarm;


    @NbBitSub(prop = "ST",index = 1,len = 1)
    private Integer thFault;


    @NbBitSub(prop = "ST",index = 2,len = 1)
    private Integer volLow;
}
