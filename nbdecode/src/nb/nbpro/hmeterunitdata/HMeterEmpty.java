package nb.nbpro.hmeterunitdata;
import nb.anno.NbSub;
import nb.nbpro.BaseEntity;


public class HMeterEmpty extends BaseEntity {

    @NbSub(length = 1)
    private String dataFlagD10;

    @NbSub(preProperty = "dataFlagD10",length = 1)
    private String dataFlagD11;

    @NbSub(preProperty = "dataFlagD11",length = 1)
    private String sNo = "00";
}
