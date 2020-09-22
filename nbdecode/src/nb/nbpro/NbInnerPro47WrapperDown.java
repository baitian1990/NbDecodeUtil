package nb.nbpro;


import nb.anno.NbSub;

public class NbInnerPro47WrapperDown extends BaseEntity{

    @NbSub(length = 1)
    private String dataIndex;


    @NbSub(preProperty = "dataIndex",length = 1)
    private Short dataLength;


    @NbSub(preProperty = "dataLength",lenthProperty = "dataLength")
    private NbInnerPro47DataWrapper innerProData;


}
