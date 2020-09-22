package nb.nbpro;


import nb.anno.NbData;
import nb.anno.NbSub;

import java.util.Date;
import java.util.List;


@NbData
public class NbOutPro47DataUnitUp extends BaseEntity{

    @NbSub(length = 1)
    private String dataFlg;


    @NbSub(preProperty = "dataFlg",length = 1)
    private String version;

    @NbSub(preProperty = "version",length = 20,sortType = "DESC")
    private String iccid;


    @NbSub(preProperty = "iccid",length = 6)
    private Date modelTime;

    @NbSub(preProperty = "modelTime",length = 1)
    private Integer signal;


    @NbSub(preProperty = "signal",length = 2)
    private Short vol;


    @NbSub(preProperty = "vol",length = 6)
    private Date colTime;

    @NbSub(preProperty = "colTime",length = 1)
    private Integer dataCount;

    @NbSub(preProperty = "dataCount",listCount = "dataCount",listClass = NbInnerPro47Wrapper.class)
    private List<NbInnerPro47Wrapper> data;
}
