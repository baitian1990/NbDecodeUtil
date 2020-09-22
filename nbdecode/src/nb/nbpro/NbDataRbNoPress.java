package nb.nbpro;


import nb.anno.NbBitSub;
import nb.anno.NbData;
import nb.anno.NbSub;

import java.util.Date;
import java.util.HashMap;



@NbData
public class NbDataRbNoPress extends BaseEntity{

    @NbSub(length = 1)
    private String dataFlagD10;

    @NbSub(preProperty = "dataFlagD10",length = 1)
    private String dataFlagD11;

    @NbSub(preProperty = "dataFlagD11",length = 1)
    private String sNo;

    @NbSub(preProperty = "sNo",length = 4,encodeType = "HEX")
    private Integer coolCap;

    @NbSub(preProperty = "coolCap",length = 1,dict = "unitDict")
    private String coolUnit;


    @NbSub(preProperty = "coolUnit",length = 4,encodeType = "HEX")
    private Integer heatCap;


    @NbSub(preProperty = "heatCap",length = 1,dict = "unitDict")
    private String heatUnit;


    @NbSub(preProperty = "heatUnit",length = 4,encodeType = "HEX")
    private Integer power;


    @NbSub(preProperty = "power",length = 1,dict = "unitDict")
    private String powerUnit;



    @NbSub(preProperty = "powerUnit",length = 4,encodeType = "HEX")
    private Integer currFlow;


    @NbSub(preProperty = "currFlow",length = 1,dict = "unitDict")
    private String currFlowUnit;



    @NbSub(preProperty = "currFlowUnit",length = 4,encodeType = "HEX")
    private Integer totalFlow;



    @NbSub(preProperty = "totalFlow",length = 1,dict = "unitDict")
    private String totalFlowUnit;


    @NbSub(preProperty = "totalFlowUnit",length = 3,encodeType = "HEX",scale = 2)
    private Double inTemp;

    @NbSub(preProperty = "inTemp",length = 3,encodeType = "HEX",scale = 2)
    private Double returnTemp;


    @NbSub(preProperty = "returnTemp",length = 3,encodeType = "HEX")
    private Integer workPer;

    @NbSub(preProperty = "workPer",length = 7)
    private Date time;


    @NbSub(preProperty = "time",length = 2)
    private String ST;

    @NbBitSub(prop = "ST",index = 0)
    private Integer currMesStat;


    @NbBitSub(prop = "ST",index = 1)
    private Integer volStat;


    @NbBitSub(prop = "ST",index = 2)
    private Integer flowSenStat;




    @NbBitSub(prop = "ST",index = 3)
    private Integer inWaterStat;


    @NbBitSub(prop = "ST",index = 4)
    private Integer retWaterStat;

    @NbBitSub(prop = "ST",index = 5)
    private Integer eepromStat;



    @NbBitSub(prop = "ST",index = 6)
    private Integer pressSenStat;

    @NbBitSub(prop = "ST",index = 7)
    private Integer overFowStat;

    private static final HashMap<String,String> unitDict = new HashMap<>();

    static {

        unitDict.put("02","0.001 kWh");
        unitDict.put("03","0.01 kWh");
        unitDict.put("04","0.1 kWh");
        unitDict.put("05"," kWh");
        unitDict.put("06","0.01 MWh");
        unitDict.put("07","0.1 MWh");
        unitDict.put("08"," MWh");
        unitDict.put("09","10 MWh");
        unitDict.put("0A","100 MWh");
        unitDict.put("0B","0.001 MJ");
        unitDict.put("0C","0.01 MJ");
        unitDict.put("0D","0.1 MJ");
        unitDict.put("0E","1 MJ");
        unitDict.put("0F","0.01 GJ");
        unitDict.put("10","0.1 GJ");
        unitDict.put("11","1 GJ");
        unitDict.put("13","0.0001 kW");
        unitDict.put("14","0.001 kW");
        unitDict.put("15","0.01 kW");
        unitDict.put("16","0.1 kW");
        unitDict.put("17","1 kW");
        unitDict.put("31","0.0001 m3/h");

        unitDict.put("32","0.001 m3/h");
        unitDict.put("20","0.001 m3/h");
        unitDict.put("2F","0.001 m3/h");

        unitDict.put("33","0.01 m3/h");
        unitDict.put("34","0.1 m3/h");
        unitDict.put("35","1 m3/h");
        unitDict.put("27","0.00001 m3");
        unitDict.put("28","0.0001 m3");
        unitDict.put("29","0.001 m3");
        unitDict.put("2A","0.01 m3");
        unitDict.put("2B","0.1 m3");
        unitDict.put("2C","1 m3");

    }


}
