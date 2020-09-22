package nb.nbpro.valveunitdata;


import nb.anno.NbSub;
import nb.nbpro.BaseEntity;


public class ValveA017 extends BaseEntity {
    @NbSub(length = 3)
    private String dataFlg;

    @NbSub(preProperty = "dataFlg",length = 1)
    private String tapCrl;
}
