package nb.nbpro.valveunitdata;

import nb.anno.NBDicKey;
import nb.anno.NbSub;
import nb.nbpro.BaseEntity;


@NBDicKey(dict = "dict",key = "A02D**",index = 1,prop = "dataFlg")
public class ValveA02C extends BaseEntity {

    @NbSub(length = 3)
    private String dataFlg = "01";

    @NbSub(preProperty = "dataFlg",length = 2,encodeType = "HEX")
    private Integer cleanCycle;
}
