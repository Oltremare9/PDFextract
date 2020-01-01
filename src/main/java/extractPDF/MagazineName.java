package extractPDF;

enum MagazineName {
    //
    内蒙古财经大学学报,
    地方财政研究,
    青海金融,
    金融经济学研究,
    现代财经天津财经大学学报,
    中央财经大学学报,
    上海金融,
    辽东学院学报,
    海南金融,
    哈尔滨商业大学学报,
    金融理论探索,
    贵州财经大学学报,
    武汉金融,
    南方金融,
    金融与经济,
    证券市场导报,
    金融发展研究,
    上海财经大学学报,
    江西财经大学学报,
    国际金融研究,
    东北财经大学学报,
    当代财经,
    金融理论与实践,
    财经问题研究,
    财经论丛,
    财经理论与实践;

    public static MagazineName getMag(String mag) {
        return valueOf(mag);
    }
}