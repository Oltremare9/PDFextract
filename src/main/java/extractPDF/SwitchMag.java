package extractPDF;

import extractPDF.CSV.WriteCSV;
import extractPDF.Magazines.*;


import java.io.File;

public class SwitchMag {

    private static WriteCSV writeCSV;

    public static void choose(File file,WriteCSV writeCSV) {
        AllMagazines magazine=null;
        String parentName = file.getParent();
        int index = parentName.lastIndexOf("\\");
        String mag="";
                parentName= parentName.substring(index + 1);

        mag = parentName.substring(0, parentName.length() - 4);
//        System.out.println(mag);
        switch (MagazineName.valueOf(mag)) {
//            case 财经论丛:
//                magazine=new CollectedEssaysonFinanceandEconomics();
//                ExtractFromDifMagazines.extract(magazine,file,writeCSV);
//                break;
//            case 财经问题研究://ok
//                magazine = new ResearchonFinancialandEconomicIssues();
//                ExtractFromDifMagazines.extract(magazine,file,writeCSV);
//                break;
//            case 金融理论与实践://ok
//                magazine=new FinanceTheoryAndPractice_Jinrong();
//                ExtractFromDifMagazines.extract(magazine,file,writeCSV);
//                break;
//            case 财经理论与实践://ok
//                magazine=new FinanceTheortAndPractice_Caijing();
//                ExtractFromDifMagazines.extract(magazine,file,writeCSV);
//                break;
//            case 当代财经:
//                magazine=new ContemporaryFinanceEconomics();
//                ExtractFromDifMagazines.extract(magazine,file,writeCSV);
//                break;
//            case 东北财经大学学报:
//                magazine=new NorthEastFinanceUniversity();
//                ExtractFromDifMagazines.extract(magazine,file,writeCSV);
//                break;
//            case 江西财经大学学报:
//                magazine=new JiangxiFinanceUniversity();
//                ExtractFromDifMagazines.extract(magazine,file,writeCSV);
//                break;
//            case 上海财经大学学报:
//                magazine=new ShanghaiFinanceUniversity();
//                ExtractFromDifMagazines.extract(magazine,file,writeCSV);
//                break;
//            case 国际金融研究:
//                magazine=new ResearchonInternationalFinancialandEconomic();
//                ExtractFromDifMagazines.extract(magazine,file,writeCSV);
//                break;
//            case 金融发展研究:
//                magazine=new ResearchonFinancialDevelopment();
//                ExtractFromDifMagazines.extract(magazine,file,writeCSV);
//                break;
//            case 金融与经济://只处理09年及以后的 且摘要为空
//                String name=file.getParent();
//                int year=Integer.parseInt(name.substring(name.length()-4));
//                if(year>2008) {
//                    magazine = new FinanceAndEconomics();
//                    ExtractFromDifMagazines.extract(magazine, file, writeCSV);
//                }
//                break;
//            case 南方金融:
//                magazine=new AllMagazines();
//                ExtractFromDifMagazines.extract(magazine,file,writeCSV);
//                break;
//            case 武汉金融:
//                magazine=new AllMagazines();
//                ExtractFromDifMagazines.extract(magazine,file,writeCSV);
//                break;
//            case 贵州财经大学学报:
//                magazine=new AllMagazines();
//                ExtractFromDifMagazines.extract(magazine,file,writeCSV);
//                break;
//            case 金融理论探索:
//                magazine=new AllMagazines();
//                ExtractFromDifMagazines.extract(magazine,file,writeCSV);
//                break;
//            case 哈尔滨商业大学学报:
//                magazine=new HaerbinFinanceUniversitu();
//                ExtractFromDifMagazines.extract(magazine,file,writeCSV);
//                break;
//            case 海南金融:
//                magazine=new HainanFinance();
//                ExtractFromDifMagazines.extract(magazine,file,writeCSV);
//                break;
//            case 辽东学院学报:
//                magazine=new LiaodongCollege();
//                ExtractFromDifMagazines.extract(magazine,file,writeCSV);
//                break;
//            case 地方财政研究:
//                magazine=new LocalFinanceResearch();
//                ExtractFromDifMagazines.extract(magazine,file,writeCSV);
//                break;
//            case 内蒙古财经大学学报:
//                magazine=new NeimengFinanceUniversity();
//                ExtractFromDifMagazines.extract(magazine,file,writeCSV);
//                break;
            case 青海金融:
                magazine=new QinghaiFinance();
                ExtractFromDifMagazines.extract(magazine,file,writeCSV);
                break;
        }
    }
}
