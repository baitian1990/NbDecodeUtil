package nb.nbpro;


import nb.anno.NbSub;


@NbSub(csVer = true)
public class NbOutProLink extends BaseEntity{

    @NbSub(length = 1)
    private String startFrame = "68";

    @NbSub(preProperty = "startFrame",length = 2)
    private Integer len;

    @NbSub(preProperty = "len",length = 1)
    private String startFrameCon = "68";


    @NbSub(preProperty = "startFrameCon",lenthProperty = "len")
    private NbOutProAppWrapper nbOutProAppWrapper;


    @NbSub(preProperty = "nbOutProAppWrapper",length = 1,csStart = "nbOutProAppWrapper",csEnd = "nbOutProAppWrapper")
    private String csCode;


    @NbSub(preProperty = "csCode",length = 1)
    private String endCode = "16";


}
