package nb.nbpro.valveunitdata;


import nb.anno.NBDicKey;
import nb.anno.NbSub;
import nb.nbpro.BaseEntity;

@NBDicKey(dict = "dict",key = {"AC****","BBFF**","DDAA**"},index = 1,prop = "dataFlg")
public class ValveEmpty extends BaseEntity {

    @NbSub(length = 3)
    private String dataFlg;

}
