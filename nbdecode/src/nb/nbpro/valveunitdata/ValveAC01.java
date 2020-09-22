package nb.nbpro.valveunitdata;

import nb.anno.NBDicKey;
import nb.anno.NbData;
import nb.anno.NbSub;
import nb.nbpro.BaseEntity;


@NbData
@NBDicKey(dict = "dict",key = "AB01**",index = 1,prop = "dataFlg")
public class ValveAC01 extends BaseEntity {
    private String devId;

    @NbSub(length = 3)
    private String dataFlg;

    @NbSub(preProperty = "dataFlg",length = 2,sortType = "DESC")
    private String hstartDate;


    @NbSub(preProperty = "hstartDate",length = 2,sortType = "DESC")
    private String hendDate;



    @NbSub(preProperty = "hendDate",length = 2,encodeType = "HEX")
    private Integer tapOpenUpLimit;

    @NbSub(preProperty = "tapOpenUpLimit",length = 2,encodeType = "HEX")
    private Integer tapOpenLowLimit;



    @NbSub(preProperty = "tapOpenLowLimit",length = 2,encodeType = "HEX")
    private Integer tapCleanCycle;



    @NbSub(preProperty = "tapCleanCycle",length = 1)
    private String modiMode;

    @NbSub(preProperty = "modiMode",length = 1)
    private String isUpModiH;


    @NbSub(preProperty = "isUpModiH",length = 2,encodeType = "HEX")
    private Integer modiValue;


    @NbSub(preProperty = "modiValue",length = 2,encodeType = "HEX")
    private Integer modiRecycle;



    @NbSub(preProperty = "modiRecycle",length = 2,encodeType = "HEX",scale = 2)
    private Double modiDead;


    @NbSub(preProperty = "modiDead",length = 2,encodeType = "HEX",scale = 2)
    private Double pparam;



    @NbSub(preProperty = "pparam",length = 2,encodeType = "HEX",scale = 2)
    private Double iparam;


    @NbSub(preProperty = "iparam",length = 2,encodeType = "HEX",scale = 2)
    private Double dparam;

    @NbSub(preProperty = "dparam",length = 2,encodeType = "HEX")
    private Integer ratioModi;
}
