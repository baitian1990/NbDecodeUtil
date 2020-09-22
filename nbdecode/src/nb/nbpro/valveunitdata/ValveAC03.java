package nb.nbpro.valveunitdata;

import nb.anno.NBDicKey;
import nb.anno.NbSub;
import nb.nbpro.BaseEntity;


@NBDicKey(dict = "dict",key = "AB03**",index = 1,prop = "dataFlg")
public class ValveAC03 extends BaseEntity {

    @NbSub(length = 3)
    private String dataFlg;

    @NbSub(preProperty = "dataFlg",length = 1)
    private String isUpModiH;




}
