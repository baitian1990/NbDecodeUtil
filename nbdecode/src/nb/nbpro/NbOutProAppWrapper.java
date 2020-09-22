package nb.nbpro;


import nb.anno.NbData;
import nb.anno.NbSub;



@NbData
public class NbOutProAppWrapper extends BaseEntity{

    @NbSub(length = 1)
    private String crlCode;

    @NbSub(preProperty = "crlCode",length = 1)
    private String protocolId;

    @NbSub(preProperty = "protocolId",length = 4,sortType = "DESC")
    private String addr;


    @NbSub(preProperty = "addr")
    private NBOutProApp nbOutProApp;




}
