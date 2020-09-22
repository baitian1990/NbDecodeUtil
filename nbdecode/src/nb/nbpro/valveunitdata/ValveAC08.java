package nb.nbpro.valveunitdata;


import nb.anno.NBDicKey;
import nb.anno.NbSub;
import nb.nbpro.BaseEntity;


@NBDicKey(dict = "dict",key = "AB08**",index = 1,prop = "dataFlg")
public class ValveAC08 extends BaseEntity {

    @NbSub(length = 3)
    private String dataFlg;

    @NbSub(preProperty = "dataFlg",length = 42,encodeType = "ASCII")
    private String reserveCode;
}
