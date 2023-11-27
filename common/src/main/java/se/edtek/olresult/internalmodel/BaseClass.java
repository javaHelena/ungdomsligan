package se.edtek.olresult.internalmodel;

import java.util.HashMap;
import java.util.Map;

public enum BaseClass {
    MAX_0(-6, 0),
    MAX_10(-5,10),
    MAX_50(-4, 50),
    MAX_80(-3, 80),
    MAX_100(-2, 100),
    OGILTIG(-1),

    NOLL_50(0, 50),

    H21_ELIT(1),
    H20_ELIT(2),
    H18_ELIT(3),
    H5(5),               //Ny 2023:
    H80(7),
    H75(8),
    H70(9),
    H65(10),
    H60(11),
    H55(12),
    H50(14),
    H45(15),
    H40(17),
    H35(19),
    H21(22,100), //Ok att anmäla sig till högre tävlingsklasser
    H20(25,100), //Ok att anmäla sig till högre tävlingsklasser
    H18(28, 100), //Ok att anmäla sig till högre tävlingsklasser
    H16(29, 100),
    H14(30, 100),
    H12(31, 100),
    H10(32, 100),
    H16_EXTRA(106,100), // Extra tävling  - Stockholm Indoor Cup
    H16_KORT(107, 80),
    H14_KORT(108, 80),
    H12_KORT(109, 80),

    D21_ELIT(4),
    D18_ELIT(6),
    D2023(33),              //Ny 2023
    D70(34),
    D65(35),
    D60(36),
    D55(37),
    D50(39),
    D45(40),
    D40(42),
    D35(44),
    D21(47, 100),   //Ok att anmäla sig till högre tävlingsklasser),
    D20(50, 100),   //Ok att anmäla sig till högre tävlingsklasser),
    D18(53,100),    //Ok att anmäla sig till högre tävlingsklasser),
    D16(54, 100),
    D14(55, 100),
    D12(56, 100),
    D10(57, 100),
    D16_EXTRA(105,100), // Extra tävling  - Stockholm Indoor Cup,
    D16_KORT(110, 80),
    D14_KORT(111, 80),
    D12_KORT(112, 80),

    U2(60, 50),
    U1(61, 50),
    INSKOLNING(62, 10),

    A2023(83),                            //Ny 2023

    ÖM1(94, 10),
    SIC_M_LÄTT(95, 10),
    SIC_M_SVÅR(96, 10),
    ÖM4(97, 10),
    ÖM5(98, 10),
    ÖM6(99, 10),                //Ny 2023
    ÖM7(100, 10),
    ÖM8(101, 10),
    ÖM9(102, 10),
    ÖM103(103, 10),             //Ny 2023
    ÖM104(104, 10),             //Ny 2023

    INSK_2KM(123, 10),          //Ny 2023
    MO_M_LÄTT(124, 10),         //Ny 2021
    KLASS_E(125, 10),           //Ny 2023
    MO_126(126, 10),            //Ny 2021
    MO_MESVÅR3(127, 10),        //Ny 2021
    MO_MESVÅR5(128, 10),        //Ny 2021
    MO_SVÅR_3(129,10),          //Ny 2022
    MO_SVÅR(130, 10),           //Ny 2021
    MO_2023_A (131, 10),        //Ny 2023
    MO_2023_C (133, 10);        //Ny 2023

    private int k;
    private int maxpoang;

    private static final Map<Integer, BaseClass> BY_BASECLASS_ID_MAP = new HashMap<>();

    static {
        for (BaseClass baseClass : values()) {
            BY_BASECLASS_ID_MAP.put(baseClass.k, baseClass);
        }
    }

    public static BaseClass fromBaseClassId(int k) {
        return BY_BASECLASS_ID_MAP.get(k);
    }

    BaseClass(int k, int maxpoang) {
        this.maxpoang = maxpoang;
        this.k = k;
    }

    BaseClass(int k) {
        this(k, 0);
    }

    public int getKlass() {
        return k;
    }

    public int getMaxpoang() {
        return maxpoang;
    }
}
