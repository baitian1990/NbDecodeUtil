package nb.nbpro;



import nb.anno.NBDicKey;
import nb.anno.NbBitSub;
import nb.anno.NbData;
import nb.anno.NbSub;

import java.util.Date;


@NbData
@NBDicKey(dict = "dict",key = "9020**",index = 1,prop = "dataFlg")
public class NbDataValve extends BaseEntity{

    @NbSub(length = 3)
    private String dataFlg;

    @NbSub(preProperty = "dataFlg", length = 2)
    private String electric;


    @NbSub(preProperty = "electric", length = 2)
    private String powerOnlineTime;


    @NbSub(preProperty = "powerOnlineTime", length = 2, encodeType = "HEX", scale = 2)
    private Double tempIn2Hours;


    @NbSub(preProperty = "tempIn2Hours", length = 2, encodeType = "HEX", scale = 2)
    private Double intTemp;



    @NbSub(preProperty = "intTemp", length = 2, encodeType = "HEX", scale = 2)
    private Double returnTemp;


    @NbSub(preProperty = "returnTemp", length = 4, encodeType = "HEX")
    private Integer workTime;



    @NbSub(preProperty = "workTime", length = 4, encodeType = "HEX")
    private Integer tapOnTime;



    @NbSub(preProperty = "tapOnTime", length = 4, encodeType = "HEX")
    private Integer tapToggleTimes;


    @NbSub(preProperty = "tapToggleTimes", length = 4, encodeType = "HEX")
    private Integer totalHeat;


    @NbSub(preProperty = "totalHeat", length = 1)
    private String totalHeatUnit;


    @NbSub(preProperty = "totalHeatUnit", length = 2, encodeType = "HEX")
    private Integer tapOpenRadius;


    @NbSub(preProperty = "tapOpenRadius", length = 7)
    private Date time;


    @NbSub(preProperty = "time", length = 2)
    private String ST;



    @NbBitSub(prop = "ST", index = 0, len = 2)
    private Integer valveStat;


    @NbBitSub(prop = "ST", index = 2, len = 1)
    private Integer valveTimeout;


    @NbBitSub(prop = "ST", index = 3, len = 1)
    private Integer valveToggleStat;


    @NbBitSub(prop = "ST", index = 4, len = 1)
    private Integer inWaterStat;



    @NbBitSub(prop = "ST", index = 5, len = 1)
    private Integer retWaterStat;



    @NbBitSub(prop = "ST", index = 6, len = 1)
    private Integer volStat;

    @NbBitSub(prop = "ST", index = 13, len = 1)
    private Integer outVolBreak;


    @NbBitSub(prop = "ST", index = 15, len = 1)
    private Integer openLidAlarm;

    @NbBitSub(prop = "ST", index = 8, len = 2)
    private Integer valveCtl;
}
