package nb.nbpro;


import nb.anno.NbData;
import nb.anno.NbSub;


@NbData
public class NbValveColSet extends BaseEntity{

    @NbSub(length = 1)
    private String dataFlg = "01";


    @NbSub(preProperty = "dataFlg",length = 1)
    private String uploadType;


    @NbSub(preProperty = "uploadType",length = 2)
    private String startTime;


    @NbSub(preProperty = "startTime",length = 2)
    private String endTime;


    @NbSub(preProperty = "endTime",length = 1)
    private String colMode;


    @NbSub(preProperty = "colMode",length = 2)
    private Short colInterval;


    @NbSub(preProperty = "colInterval",length = 2)
    private String timer1;

    @NbSub(preProperty = "timer1",length = 2)
    private String timer2;


    @NbSub(preProperty = "timer2",length = 32,encodeType = "ASCII")
    private String ip;


    @NbSub(preProperty = "ip",length = 2)
    private Short port;
}
