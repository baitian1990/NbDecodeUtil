package nb.nbpro.valveunitdata;


import nb.anno.NBDicKey;
import nb.anno.NbSub;
import nb.nbpro.BaseEntity;


@NBDicKey(dict = "dict",key = "AB0F**",index = 1,prop = "dataFlg")
public class ValveAC0F extends BaseEntity {

    @NbSub(length = 3)
    private String dataFlg;

    @NbSub(preProperty = "dataFlg",length = 1)
    private Integer isOnFaultAutoM;


    @NbSub(preProperty = "isOnFaultAutoM",length = 1)
    private Integer toAutoMTimes;


    @NbSub(preProperty = "toAutoMTimes",length = 1)
    private String autoMode;

    @NbSub(preProperty = "autoMode",length = 1)
    private String isStoreLastValue;

    @NbSub(preProperty = "isStoreLastValue",length = 4)
    private String reverseCode;

}
