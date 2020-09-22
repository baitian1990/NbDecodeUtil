package nb.nbpro.valveunitdata;


import nb.anno.NbSub;
import nb.nbpro.BaseEntity;


public class ValveAC04 extends BaseEntity {

    @NbSub(length = 3)
    private String dataFlg;

    @NbSub(preProperty = "dataFlg",length = 1)
    private String modMode;

    @NbSub(preProperty = "modMode",length = 2,encodeType = "HEX")
    private Integer modValue;

}
