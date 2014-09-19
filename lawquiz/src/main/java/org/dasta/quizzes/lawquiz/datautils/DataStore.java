/**
 * Created at Sep 19, 2014, 11:47
 *
 * File DataStore.java
 */
package org.dasta.quizzes.lawquiz.datautils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.*;
import java.util.*;


/**
 * TODO Document me
 *
 * @author Adrian Iasinschi (adrian.iasinschi@kepler-rominfo.com)
 * @since 1.0
 */
public class DataStore {
    private static final String LOGOWNER = "DataStore";
    private static List<Question> allQuestions = new ArrayList<Question>();
    private static int current = 0;
    private static boolean isRandom = false;
    private static Random rndGen = new Random();

    public static Question nextQuestion() {
        if ((allQuestions == null) || (allQuestions.isEmpty())) {
            return null;
        }
        if (isRandom) {
            current += rndGen.nextInt();
        } else {
            current++;
        }
        current = current % allQuestions.size();
        return allQuestions.get(current);
    }

    public static void randomize() {
        isRandom = false;
    }

    private static void debug(String owner, String msg){
        Log.d(owner, msg);
        //System.out.println(owner + ":" + msg);
    }

    public static boolean parseQuestions(StringBuffer content){
        Question question;
        int start = 0;
        while(start < content.length()){
            debug(LOGOWNER, "Record Number: " + (allQuestions.size() + 1));
            int end = content.indexOf("a)", start);
            String sq = content.substring(start, end);
            sq = sq.substring(sq.indexOf(".") + 1);
            start = end;
            question = new Question(sq);
            debug(LOGOWNER, "Question:" + sq);
            // first answer
            end = content.indexOf("b)", start);
            sq = content.substring(start, end);
            sq = sq.substring(sq.indexOf(")") + 1);
            start = end;
            question.addAnswerText(sq);
            debug(LOGOWNER, "ans a):" + sq);
            // first answer
            end = content.indexOf("c)", start);
            sq = content.substring(start, end);
            sq = sq.substring(sq.indexOf(")") + 1);
            start = end;
            question.addAnswerText(sq);
            debug(LOGOWNER, "ans b):" + sq);
            // first answer
            end = content.indexOf(".", start) > 0 ? content.indexOf(".", start) - 2
                                                  : content.length();
            sq = content.substring(start, end);
            sq = sq.substring(sq.indexOf(")") + 1);
            start = end;
            question.addAnswerText(sq);
            debug(LOGOWNER, "ans c):" + sq);
            allQuestions.add(question);
        }
        return true;
    }

    public static boolean initialize(Context context, String fileName) {
        StringBuffer content = new StringBuffer("");
        AssetManager am = context.getAssets();
        try {
            InputStream is = am.open(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = br.readLine()) != null){
                // process line
                if (line.trim().length() == 0) {
                    continue;
                } else {
                    content.append(line);
                }
            }
            br.close();
        } catch (IOException ex){
            return false;
        }
        return parseQuestions(content);
    }


    public static StringBuffer defaultcontent = new StringBuffer("\n" +
            "1. Prin act juridic civil se înţelege:\n" +
            "a) manifestarea de voinţă făcută cu intenţia de a produce efecte juridice;\n" +
            "b) manifestarea de voinţă făcută cu intenţia de a naşte un raport juridic civil concret;  \n" +
            "c) manifestarea de voinţă făcută cu intenţia de a modifica un raport juridic civil concret;  \n" +
            "\n" +
            "2. Este un act act juridic civil:\n" +
            "a) manifestarea de voinţă făcută cu intenţia de a naşte un raport juridic civil concret; \n" +
            "b) manifestarea de voinţă făcută cu intenţia de a stinge un raport juridic civil concret;\n" +
            "c) numai manifestarea de voinţă făcută cu intenţia de a naşte un raport juridic civil concret;  \n" +
            "\n" +
            "3. Sunt elementele caracteristice actului juridic civil:\n" +
            "a) prezenţa unei manifestări de voinţă, care poate să provină numai de la mai multe persoane fizice ori \n" +
            "juridice;\n" +
            "b) prezenţa unei manifestări de voinţă, care poate să provină de la una sau de la mai multe persoane \n" +
            "fizice ori juridice;\n" +
            "c) prezenţa unei manifestări de voinţă, care poate să provină numai de o persoane fizică ori juridică;  \n" +
            "\n" +
            "4. Sunt elementele caracteristice actului juridic civil:\n" +
            "a) efectele juridice urmărite pot consta în a da naştere, a modifica sau a stinge un raport juridic civil \n" +
            "concret;\n" +
            "b) manifestarea de voinţă este exprimată cu intenţia de a produce efecte juridice civile;\n" +
            "c) manifestarea de voinţă nu este exprimată cu intenţia de a produce efecte juridice civile;  \n" +
            "\n" +
            "5. Faptul juridic civil:\n" +
            "a) este săvârşit fără intenţia de a se produce efecte juridice, efecte care însă se produc în temeiul legii;\n" +
            "b) este săvârşit fără intenţia de a se produce efecte juridice, efecte care nu se produc; \n" +
            "c) este săvârşit cu intenţia de a se produce efecte juridice;\n" +
            "\n" +
            "6. Pentru sensul de operaţiune juridică, se utilizează formula:\n" +
            "a) negotium;\n" +
            "b) instrumentum probationis;\n" +
            "c) negotium iuris; \n" +
            "\n" +
            "7. Cuvântul „act”:\n" +
            "a) are doar înţelesul de operaţiune juridică;\n" +
            "b) poate avea două înţelesuri;\n" +
            "c) nu poate avea două înţelesuri; \n" +
            "\n" +
            "8. Pentru sensul deînscrisul constatator al manifestării de voinţă, se utilizează formula:\n" +
            "a) negotium;\n" +
            "b) instrumentum probationis;\n" +
            "c) negotium iuris; \n" +
            "\n" +
            "9. In funcţie de numărul părţilor,actele juridice civile se clasifică în:\n" +
            "a) unilaterale, bilaterale şi plurilaterale (multilaterale);\n" +
            "b) constitutive, translative şi declarative;\n" +
            "c) de conservare, de administrare şi de dispoziţie;\n" +
            "\n" +
            "10. Actul juridic unilateral: \n" +
            "a) este rezultatul voinţei unei singure părţi;\n" +
            "b) nu este numai rezultatul voinţei unei singure părţi;\n" +
            "c) se subclasifică în acte supuse comunicării şi acte nesupuse comunicării;  \n" +
            "\n" +
            "11. Categoria actelor juridice civile unilaterale include: \n" +
            "a) testamentul, acceptarea moştenirii, renunţarea la moştenire, denunţarea unui contract de către una \n" +
            "dintre părţi;\n" +
            "b) oferta de a contracta, promisiunea publică de recompensă, oferta de purgă;\n" +
            "c) ratificarea unui act juridic încheiat în lipsa ori cu depăşirea împuternicirii de a reprezenta, confirmarea \n" +
            "unui act juridic anulabil, mărturisirea;  \n" +
            "\n" +
            "12. Categoria actelor juridice civile unilaterale include: \n" +
            "a) testamentul;\n" +
            "b) acceptarea moştenirii;\n" +
            "c) renunţarea la moştenire;\n" +
            "\n" +
            "13. Categoria actelor juridice civile unilaterale include:\n" +
            "a) denunţarea unui contract de către una dintre părţi;\n" +
            "b) ratificarea unui act juridic încheiat în lipsa împuternicirii de a reprezenta;\n" +
            "c) ratificarea unui act juridic încheiat cu depăşirea împuternicirii de a reprezenta;  \n" +
            "\n" +
            "14. Categoria actelor juridice civile unilaterale include:\n" +
            "a) confirmarea unui act juridic anulabil;\n" +
            "b) mărturisirea; \n" +
            "c) oferta de purgă; \n" +
            "\n" +
            "15. Categoria actelor juridice civile unilaterale include:\n" +
            "a) denunţarea unui contract de către una dintre părţi\n" +
            "b) renunţarea la moştenire;\n" +
            "c) ratificarea unui act juridic încheiat cu depăşirea împuternicirii de a reprezenta;   \n" +
            "\n" +
            "16. Categoria actelor juridice civile unilaterale include:\n" +
            "a) confirmarea unui act juridic anulabil;\n" +
            "b) oferta de purgă;\n" +
            "c) testamentul; \n" +
            "\n" +
            "17. Categoria actelor juridice civile unilaterale include:\n" +
            "a) oferta de purgă\n" +
            "b) mărturisirea; \n" +
            "c) denunţarea unui contract de către una dintre părţi; \n" +
            "\n" +
            "18. Sunt acte supuse comunicării:\n" +
            "a) oferta;\n" +
            "b) promisiunea publică de recompensă;\n" +
            "c) testamentul; \n" +
            "\n" +
            "19. Sunt acte supuse comunicării:\n" +
            "a) denunţarea unilaterală a contractului de mandat\n" +
            "b) testamentul; \n" +
            "c) promisiunea publică de recompensă; \n" +
            "\n" +
            "20. Actul juridic bilateral:\n" +
            "a) reprezintă voinţa concordantă a două părţi; \n" +
            "b) este rezultatul voinţei concordante a trei sau mai multor părţi;\n" +
            "c) nu este rezultatul voinţei concordante a trei sau mai multor părţi; \n" +
            "\n" +
            "21. Sunt acte juridice bilaterale: \n" +
            "a) contractul de donaţie;\n" +
            "b) contractul de vânzare; \n" +
            "c) contractul de schimb;  \n" +
            "\n" +
            "22. Sunt acte juridice bilaterale: \n" +
            "a) contractul de mandat;\n" +
            "b) contractul de locaţiune;\n" +
            "c) contractul de schimb;  \n" +
            "\n" +
            "23. Actul juridic plurilateral:\n" +
            "a) reprezintă voinţa concordantă a două părţi; \n" +
            "b) este rezultatul voinţei concordante a trei sau mai multor părţi;\n" +
            "c) nu este rezultatul voinţei concordante a trei sau mai multor părţi;  \n" +
            "\n" +
            "24. Este act juridic plurilateral:\n" +
            "a) orice contract de societate civilă;\n" +
            "b) contractul de societate civilă, dacă a fost încheiat de cel puţin trei asociaţi;\n" +
            "c) nu este orice contract de societate civilă;\n" +
            "\n" +
            "25. Este act juridic plurilateral:\n" +
            "a) convenţia de partaj atunci când sunt trei sau mai mulţi copărtaşi;\n" +
            "b) contractul de tranzacţie încheiat de cel puţin trei părţi;\n" +
            "c) contractul de joc sau prinsoare dintre trei sau mai multe persoane; \n" +
            "\n" +
            "26. Spre exemplu, dacă doi coproprietari fac o ofertă de vânzare, suntem în prezenţa unui:\n" +
            "a) act juridic unilateral;\n" +
            "b) act juridic bilateral;\n" +
            "c) act juridic plurilateral; \n" +
            "\n" +
            "27. Contractul unilateral este acel contract:\n" +
            "a) care dă naştere la obligaţii numai pentru una dintre părţi, cealaltă parte având numai calitatea de \n" +
            "creditor;\n" +
            "b) care se caracterizează prin reciprocitatea obligaţiilor ce revin părţilor şi prin interdependenţa \n" +
            "obligaţiilor reciproce, deci fiecare parte are atât calitatea de creditor, cât şi calitatea de debitor;\n" +
            "c) care dă naştere la obligaţii nu numai pentru una dintre părţi;\n" +
            "\n" +
            "28. Este contract unilateral:\n" +
            "a) contractul de donaţie;\n" +
            "b) contractul de împrumut de folosinţă;\n" +
            "c) contractul de împrumut de consumaţie;  \n" +
            "\n" +
            "29. Este contract unilateral:\n" +
            "a) contractul de împrumut de folosinţă\n" +
            "b) promisiunea unilaterala de vânzare;\n" +
            "c) promisiunea unilaterala de cumpărare;\n" +
            "\n" +
            "30. Contractul bilateral:\n" +
            "a) este numit şi contract sinalagmatic;\n" +
            "b) se caracterizează prin reciprocitatea obligaţiilor ce revin părţilor şi prin interdependenţa obligaţiilor \n" +
            "reciproce;\n" +
            "c) fiecare parte are atât calitatea de creditor, cât şi calitatea de debitor; \n" +
            "\n" +
            "31. Clasificarea actelor juridice în unilaterale şi bilaterale:\n" +
            "a) se face după criteriul numărului părţilor;\n" +
            "b) se face după criteriul conţinutului lor;\n" +
            "c) nu se face după criteriul numărului părţilor; \n" +
            "\n" +
            "32. Clasificarea contractelor în unilaterale şi bilaterale:\n" +
            "a) se face după criteriul numărului părţilor;\n" +
            "b) se face după criteriul conţinutului lor;\n" +
            "c) nu se face după criteriul numărului părţilor;   \n" +
            "\n" +
            "33. Fac parte din categoria actelor juridice bilaterale sau plurilaterale:\n" +
            "a) contractele unilaterale;\n" +
            "b) toate contractele;\n" +
            "c) unele contracte;\n" +
            "\n" +
            "34. Actele juridice unilaterale:\n" +
            "a) nu sunt contracte;\n" +
            "b) sunt consecinţa unui acord de voinţă;\n" +
            "c) sunt rezultatul manifestării unilaterale de voinţă; \n" +
            "\n" +
            "35. Actele juridice unilaterale:\n" +
            "a) sunt contracte;\n" +
            "b) nu sunt consecinţa unui acord de voinţă;\n" +
            "c) nu sunt rezultatul manifestării unilaterale de voinţă;  \n" +
            "\n" +
            "36. Cât priveşte formarea valabilă a actelor juridice unilaterale:\n" +
            "a) cercetarea valabilităţii voinţei unice este nu numai necesară, dar şi suficientă;\n" +
            "b) cercetarea valabilităţii voinţei unice este numai necesară, nu şi suficientă;\n" +
            "c) cercetarea valabilităţii voinţei unice este nu este necesară;  \n" +
            "\n" +
            "37. Cât priveşte formarea valabilă a actelor juridice bilaterale sau plurilaterale:\n" +
            "a) cercetarea valabilităţii voinţei unice este nu numai necesară, dar şi suficientă;\n" +
            "b) nu trebuie să se cerceteze fiecare dintre cele două sau mai multe manifestări de voinţă;\n" +
            "c) trebuie să se cerceteze fiecare dintre cele două sau mai multe manifestări de voinţă; \n" +
            "\n" +
            "38. Actele juridice bilaterale sau plurilaterale:\n" +
            "a) pot fi revocate de comun acord de către părţi, deci printr-un act simetric celui de constituire;\n" +
            "b) nu pot fi revocate de comun acord de către părţi;\n" +
            "c) nu pot fi revocate; \n" +
            "\n" +
            "39. Asupra actelor juridice unilaterale:\n" +
            "a) nu se poate reveni prin manifestarea de voinţă în sens contrar a autorului actului, fara excepţie;\n" +
            "b) nu se poate reveni prin manifestarea de voinţă în sens contrar a autorului actului, cu excepţia \n" +
            "cazurilor expres prevăzute de lege;\n" +
            "c) se poate reveni intotdeauna prin manifestarea de voinţă în sens contrar a autorului actului;\n" +
            "\n" +
            "40. După scopul urmărit la încheierea lor, deosebim: \n" +
            "a) acte juridice cu titlu oneros şi acte juridice cu titlu gratuit;\n" +
            "b) acte juridice constitutive, acte juridice translative şi acte juridice declarative;\n" +
            "c) acte juridice de conservare, acte juridice de administrare şi acte juridice de dispoziţie;\n" +
            "\n" +
            "41. Actul juridic cu titlu oneros:\n" +
            "a) este acela prin care fiecare parte urmăreşte să îşi procure un avantaj în schimbul obligaţiilor asumate;\n" +
            "b) este acela prin care una dintre părţi urmăreşte să procure celeilalte părţi un beneficiu, fără a obţine în \n" +
            "schimb vreun avantaj;\n" +
            "c) nu este acela prin care una dintre părţi urmăreşte să procure celeilalte părţi un beneficiu, fără a \n" +
            "obţine în schimb vreun avantaj;\n" +
            "\n" +
            "42. Sunt acte juridice civile cu titlu gratuit:\n" +
            "a) mandatul gratuit;\n" +
            "b) donaţia, comodatul, împrumutul de consumaţie fără dobândă;\n" +
            "c) contractul de voluntariat;\n" +
            "\n" +
            "43. Sunt acte juridice civile cu titlu gratuit:\n" +
            "a) contractul de voluntariat;\n" +
            "b) depozitul neremunerat;\n" +
            "c) legatul;\n" +
            "\n" +
            "44. Referitor la actele juridice cu titlu oneros şi actele juridice cu titlu gratuit:\n" +
            "a) legea este în general mai exigentă atunci când este vorba de acte juridice cu titlu gratuit;\n" +
            "b) legea este în general mai exigentă atunci când este vorba de acte juridice cu titlu oneros; \n" +
            "c) legea nu este în general mai exigentă atunci când este vorba de acte juridice cu titlu gratuit; \n" +
            "\n" +
            "45. Actele cu titlu gratuit:\n" +
            "a) sunt acelea prin care una dintre părţi urmăreşte să procure celeilalte părţi un beneficiu, fără a obţine \n" +
            "în schimb vreun avantaj;\n" +
            "b) pot fi încheiate de către persoanele lipsite de capacitate de exerciţiu sau cu capacitate de exerciţiu \n" +
            "restrânsă prin reprezentantul legal sau, după caz, cu autorizarea prealabilă a ocrotitorului legal;\n" +
            "c) nu pot fi încheiate de către persoanele lipsite de capacitate de exerciţiu sau cu capacitate de exerciţiu \n" +
            "restrânsă nici prin reprezentantul legal sau, după caz, nici cu autorizarea prealabilă a ocrotitorului legal; \n" +
            "\n" +
            "46. In cazul actelor cu titlu oneros şi cu titlu gratuit:\n" +
            "a) spre a fi vorba de o eroare-viciu de consimţământ, în cazul actelor cu titlu oneros este necesară o \n" +
            "cerinţă suplimentară faţă de actele cu titlu gratuit;\n" +
            "b) problema leziunii nu se pune în actele juridice cu titlu gratuit; \n" +
            "c) problema leziunii se pune în actele juridice cu titlu gratuit;  \n" +
            "\n" +
            "47. In cazul actelor cu titlu oneros:\n" +
            "a) se poate pune problema leziunii;\n" +
            "b) obligaţiile părţilor sunt reglementate cu mai multă severitate;\n" +
            "c) obligaţiile părţilor nu sunt reglementate cu mai multă severitate;  \n" +
            "\n" +
            "48. Garanţia împotriva evicţiunii:\n" +
            "a) apare, de regulă, în actele cu titlu gratuit şi numai în mod excepţional în actele cu titlu oneros;\n" +
            "b) apare, de regulă, în actele cu titlu oneros şi numai în mod excepţional în actele cu titlu gratuit;\n" +
            "c) nu apare niciodată în actele cu titlu gratuit; \n" +
            "\n" +
            "49. In cazul actelor cu titlu oneros:\n" +
            "a) răspunderea se apreciază cu mai multă severitate;\n" +
            "b) răspunderea nu se apreciază cu mai multă severitate;\n" +
            "c) nu se poate pune problema leziunii; \n" +
            "\n" +
            "50. Dacă mandatul este cu titlu oneros:\n" +
            "a) mandatarul este ţinut să execute mandatul cu diligenţa unui bun proprietar;\n" +
            "b) mandatarul este obligat să-l îndeplinească cu diligenţa pe care o manifestă în propriile afaceri;\n" +
            "c) mandatarul nu este ţinut să execute mandatul cu diligenţa unui bun proprietar;  \n" +
            "\n");



/*

    public static void main(String[] args){
        readQuestions(content);
    }

*/
}
