package se.edtek.olresult.internalmodel;


public enum BaseClass {
    NOLL(0, 50),
    MAX_100(-2, 100),
    MAX_80(-3, 80),
    MAX_50(-4, 50),
    MAX_10(-5,10),
    MAX_0(-6, 0),
    H16_EXTRA(106,100), // Extra tävling  - Stockholm Indoor Cup
    D16_EXTRA(105,100), // Extra tävling  - Stockholm Indoor Cup,
    ÖM4_RÅNÄS(95),
    OGILTIG(-1),
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
    INSKOLNING(62, 10),
    U1(61, 50),
    U2(60, 50),
    ÖM1(94),
    ÖM3(96),
    ÖM4(97),
    ÖM5(98),
    ÖM7(100),
    ÖM8(101),
    ÖM9(102);



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
