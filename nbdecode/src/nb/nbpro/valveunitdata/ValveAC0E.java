package nb.nbpro.valveunitdata;


import nb.anno.NBDicKey;
import nb.anno.NbSub;
import nb.nbpro.BaseEntity;

@NBDicKey(dict = "dict",key = "AB0E**",index = 1,prop = "dataFlg")
public class ValveAC0E extends BaseEntity {

    @NbSub(length = 3)
    private String dataFlg;

    @NbSub(preProperty = "dataFlg",length = 4)
    private Integer tapToggleTimes;
}
