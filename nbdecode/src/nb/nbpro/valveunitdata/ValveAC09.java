package nb.nbpro.valveunitdata;


import nb.anno.NBDicKey;
import nb.anno.NbSub;
import nb.nbpro.BaseEntity;

@NBDicKey(dict = "dict",key = "AB09**",index = 1,prop = "dataFlg")
public class ValveAC09 extends BaseEntity {

    @NbSub(length = 3)
    private String dataFlg;

    @NbSub(preProperty = "dataFlg",length = 1)
    private String isScreenCycle;

    @NbSub(preProperty = "isScreenCycle",length = 1)
    private String waterTSensor;
}
