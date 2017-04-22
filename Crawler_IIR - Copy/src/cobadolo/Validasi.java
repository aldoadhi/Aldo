/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cobadolo;

import com.jaunt.NotFound;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aldo
 */
public class Validasi {
    public static void main(String[] args) throws NotFound {
        try {
            Path path = Paths.get("D:\\SEMESTER 4\\Kuliah\\IIR\\dari piet\\Crawler_IIR\\data_crawl\\bukalapak"+"kamera"+"-"+""+".csv");
            Scanner scanner = new Scanner(path);
            while(scanner.hasNext())
            {
                int counter = 0;
                String rec = scanner.nextLine();
                //Scanner record = new Scanner(rec);
                //int countComma = StringUtils.countMatches(rec,",");
                char[] temp = rec.toCharArray();
                for(char item : temp)
                {
                    if(item==',')
                    {
                        counter++;
                    }                   
                }
                System.out.println(rec);
                if(counter==3)
                {
                    System.out.println("REC OK!");                    
                }
                else
                {
                    System.out.println("INVALID FORMAT!");
                }
                
            }
        } catch (IOException ex) {
            Logger.getLogger(Validasi.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*System.out.println(AdvancedReplace("BURUAN !!!! diskon 50% g\\ita60,ib/an/ez jem flo-wer putih ampli 8inch kabel jack 3m ,sepaket gitar ibanez jem flower putih ampli 8.5inch; kabel jack: 3m bodi 'mahogan' neck \"mappel fretboart rosewood pick up g amp b korea erick music lokasi jalan mutiara no 10 bendung hilir jakarta pusat belalang sma 35 samping gor buka jam 8 pagi-jam 12 malam layan irim seluruh indonesia"));
    }
    
    public static String AdvancedReplace(String txt)
    {
        char[] mustDelete = {',', '.', ':', ';', '!', '%', '\'', '"', '-', '/', '\\'};
        char[] tempArr = txt.toCharArray();
        for(int i = 0; i<tempArr.length; i++)
        {
            for(char del : mustDelete)
            {
                if(tempArr[i]==del)
                {
                    tempArr[i]=' ';
                }
            }
        }
        return new String(tempArr);
    }*/
}}
