package nb.nbpro;


import nb.anno.NbData;
import nb.anno.NbSub;
import nb.nbpro.hmeterunitdata.HMeterEmpty;
import nb.nbpro.valveunitdata.*;

import java.util.HashMap;



@NbData
@NbSub(csVer = true)
public class NbInnerPro47DataWrapper extends BaseEntity{


    @NbSub(length = 1)
    private String startFrame = "68";

    @NbSub(preProperty = "startFrame", length = 1)
    private String devType;

    @NbSub(preProperty = "devType", length = 7, sortType = "DESC")
    private String deviceAddr;

    @NbSub(preProperty = "deviceAddr", length = 1)
    private String ctlCode;

    @NbSub(preProperty = "ctlCode", length = 1)
    private Integer dataLen;


    @NbSub(preProperty = "dataLen", lenthProperty = "dataLen", dict = "dict", propKey = {"devType", "ctlCode", "dataLen"})
    private Object data;

    /**
     * 校验码
     */
    @NbSub(preProperty = "data", length = 1, csStart = "startFrame", csEnd = "data")
    private String verCode;
    /**
     * 结束码
     */
    @NbSub(preProperty = "verCode", length = 1)
    private String endCode = "16";

    private static final HashMap<String, Class> dict = new HashMap<>();

    static {
        dict.put("46AC*******", ValveEmpty.class);
        dict.put("46BBFF*****", ValveEmpty.class);
        dict.put("46DDAA*****", ValveEmpty.class);

        dict.put("20**48", NbDataRbInPress.class);
        dict.put("20**46", NbDataRbNoPress.class);
        dict.put("20013", HMeterEmpty.class);

        dict.put("469020******", NbDataValve.class);
        dict.put("46A017*****", ValveA017Ret.class);

        dict.put("46A02C*****", ValveEmpty.class);

        dict.put("46A02D*****", ValveA02C.class);


        dict.put("46AB04*****", ValveAC04.class);
        dict.put("46AB01******", ValveAC01.class);
        dict.put("46AB08*****", ValveAC08.class);
        dict.put("46AB09*****", ValveAC09.class);
        dict.put("46AB0A*****", ValveAC0A.class);
        dict.put("46AB0C*****", ValveAC0C.class);
        dict.put("46AB0D*****", ValveAC0D.class);
        dict.put("46AB0E*****", ValveAC0E.class);
        dict.put("46AB0F*****", ValveAC0F.class);



    }


}
