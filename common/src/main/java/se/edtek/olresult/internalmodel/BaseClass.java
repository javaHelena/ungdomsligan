package se.edtek.olresult.internalmodel;


public enum BaseClass {
    NOLL_50(0, 50),
    MAX_100(-2, 100),
    MAX_80(-3, 80),
    MAX_50(-4, 50),
    MAX_10(-5,10),
    MAX_0(-6, 0),
    OGILTIG(-1),
    H5(5),               //Ny 2023:
    H16_KORT(107, 80),
    D16_KORT(110, 80),
    H14_KORT(108, 80),
    D14_KORT(111, 80),
    D12_KORT(112, 80),
    H12_KORT(109, 80),
    H18_ELIT(3),
    H20_ELIT(2),
    H21_ELIT(1),
    H10(32, 100),
    H12(31, 100),
    H14(30, 100),
    H16(29, 100),
    H16_EXTRA(106,100), // Extra tävling  - Stockholm Indoor Cup
    H18(28),
    H20(25),
    H21(22),
    H35(19),
    H40(17),
    H45(15),
    H50(14),
    H55(12),
    H60(11),
    H65(10),
    H70(9),
    H75(8),
    H80(7),
    D18_ELIT(6),
    D21_ELIT(4),
    D10(57, 100),
    D12(56, 100),
    D14(55, 100),
    D16(54, 100),
    D16_EXTRA(105,100), // Extra tävling  - Stockholm Indoor Cup,
    D18(53),
    D20(50),
    D21(47),
    D35(44),
    D40(42),
    D45(40),
    D50(39),
    D55(37),
    D60(36),
    D65(35),
    D70(34),
    D2023(33),              //Ny 2023
    INSKOLNING(62, 10),
    U1(61, 50),
    U2(60, 50),
    A2023(83),              //Ny 2023
    ÖM1(94),
    SIC_M_LÄTT(95),
    SIC_M_SVÅR(96),
    ÖM4(97),
    ÖM5(98),
    ÖM6(99),                //Ny 2023
    ÖM7(100),
    ÖM8(101),
    ÖM9(102),
    ÖM103(103),              //Ny 2023
    ÖM104(104),             //Ny 2023
    INSK_2KM(123, 10),
    KLASS_E(125, 50),

    //Nya 2021:
    MO_M_LÄTT(124, 10),     //Motionsorientering
    MO_126(126, 100),       //Motionsorientering
    MO_MESVÅR3(127, 100),   //Motionsorientering
    MO_MESVÅR5(128, 100),   //Motionsorientering
    MO_SVÅR(130, 100),      //Motionsorientering
    MO_2023_A (131, 100),      //Ny 2023
    MO_2023_C (133, 100),      //Ny 2023

    //Nya 2022:
    MO_SVÅR_3(129,100);



    private int k;
    private int maxpoang;

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
