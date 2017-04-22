/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cobadolo;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.jaunt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.id.IndonesianAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;



/**
 *
 * @author Alvin Theofilus
 */
public class CobaDolo {
    static BufferedWriter bw = null;
    static FileWriter fw = null;
    static PrintWriter out = null;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NotFound {
        BufferedReader rdr = null;
        try {
            rdr = new BufferedReader(new InputStreamReader(System.in));
            while(true)
            {
                System.out.println("Pilihan :");
                System.out.println("1. BukaLapak");
                System.out.println("2. BliBli");
                System.out.println("3. Lazada");
                System.out.println("4. Tokopedia");
                System.out.println("5. Keluar");
                String pilihan = rdr.readLine();
                if(pilihan.equals("1"))
                {
                    System.out.println("Masukkan keyword Bukalapak");
                    String keyword = rdr.readLine();
                    System.out.println("Masukkan Kategori");
                    String kat = rdr.readLine();
                    cariProdukBL2(keyword,kat);
                }
                else if(pilihan.equals("2"))
                {
                    System.out.println("Masukkan keyword BliBli");
                    String keyword = rdr.readLine();
                    System.out.println("Masukkan Kategori");
                    String kat = rdr.readLine();
                    cariProdukBli2(keyword,kat);
                }
                else if(pilihan.equals("3"))
                {
                    System.out.println("Masukkan keyword Lazada");
                    String keyword = rdr.readLine();
                    System.out.println("Masukkan Kategori");
                    String kat = rdr.readLine();
                    cariProdukLZD(keyword,kat);
                }
                else if(pilihan.equals("4"))
                {
                    System.out.println("Masukkan keyword Tokopedia");
                    String keyword = rdr.readLine();
                    cariProdukTKPD(keyword);
                }
                else if(pilihan.equals("5"))
                {
                    System.out.println("Terima Kasih");
                    System.exit(0);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(CobaDolo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void cariProdukBL2(String keyword, String kategori)
    {
        String url;
        if("semua".equals(kategori.toLowerCase()))
        {
            for(int i=1; i<5; i++)
            {
                try {
                    url = "https://www.bukalapak.com/products/s?from=omnisearch&page="+i+"&search%5Bkeywords%5D="+keyword+"&search_source=omnisearch_category&source=navbar";
                   
                    cariProdukBukaLapak(url, kategori.toLowerCase(),i);
                } catch (NotFound ex) {
                    Logger.getLogger(CobaDolo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }   
        }
        else
        {
            for(int i=1; i<5; i++)
            {
                try {
                    //url = "https://www.bukalapak.com/c/"+kategori+"?from=omnisearch&page="+i+"&search%5Bkeywords%5D="+keyword+"&search_source=omnisearch_category&source=navbar";
                    url = "https://www.bukalapak.com/c/"+kategori+"?page="+i+"&search%5Bkeywords%5D=" + keyword;
                    cariProdukBukaLapak(url, kategori.toLowerCase(),i);
                } catch (NotFound ex) {
                    Logger.getLogger(CobaDolo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    public static void cariProdukLZD(String keyword, String kategori)
    {
        String url;
        for(int i=1; i<5; i++)
        {
            try {
                url = "http://www.lazada.co.id/beli-"+kategori+"/"+keyword+"/?page="+i;
                cariProdukLazada(url, kategori.toLowerCase());
            } catch (NotFound ex) {
                Logger.getLogger(CobaDolo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
    }
    public static void cariProdukBli2(String keyword, String kategori)
    {
        //Semua
        String url;
        //
        if("semua".equals(kategori.toLowerCase()))
        {
            for(int i=0; i<=5; i++)
            {
                try {
                    url = "https://www.blibli.com/search?s="+keyword+"di+semua&o=10&i="+i*24;
                    cariProdukBliBli(url, kategori);
                } catch (NotFound ex) {
                    Logger.getLogger(CobaDolo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }   
        }
        else
        {
            for(int i=0; i<=5; i++)
            {
                try {
                    url = "https://www.blibli.com/search?s="+keyword+"+di+"+kategori+"&o=10&i="+i*24;
                    cariProdukBliBli(url, kategori);
                } catch (NotFound ex) {
                    Logger.getLogger(CobaDolo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } 
    }
    
    public static void cariProdukTKPD(String keyword) throws IOException
    {
        String url;
        for(int i=1; i<5; i++)
        {
            try {
                url = "https://www.tokopedia.com/search?utm_expid=19726872-77.PS_vybeKQkePF-pdvnqRKQ.0&st=product&q="+keyword;
                cariProdukTokopedia(url);
            } catch (NotFound ex) {
                Logger.getLogger(CobaDolo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
    }
    
    public static void cariProdukBukaLapak(String linkName,String pKategori, int x) throws NotFound
    {
        try{           
            File item = new File("D:\\SEMESTER 4\\Kuliah\\IIR\\dari piet\\Crawler_IIR\\data_crawl\\bukalapak"+"apple2"+"-"+""+".csv");           
            fw = new FileWriter(item, true);
            bw = new BufferedWriter(fw);
            //out = new PrintWriter(bw);
            if(x==0)
            {               
                fw.append("docID,namaBarang,desc,harga\r\n");
                //out.println("docID,namaBarang,desc,harga\r\n");
            }
            
            UserAgent userAgent = new UserAgent();
            userAgent.visit(linkName);   

            Elements getLink = userAgent.doc.findEach("<div class=\"product-card\">").findEach("<div class=\"product-description\">").findEach("<h3>").findEach("<a>");       //find non-nested tables   
            Elements getPrice = userAgent.doc.findEach("<div class=\"product-card\">").findEach("<div class=\"product-price\">");
            
            System.out.println(getLink.size());

            String[] tempData = new String[getLink.size()];
            long counter=0;
            //Old
            File f = new File("D:\\SEMESTER 4\\Kuliah\\IIR\\dari piet\\Crawler_IIR\\data_crawl\\bukalapak");
            
//            File fNew = new File("E:\\College File\\Semester 4\\Intelligent Information Retrieval\\Crawler_IIR\\data_crawl\\bukalapak.txt");
//            //*E:\\College File\\Semester 4\\Intelligent Information Retrieval*
//            //Ganti dengan lokasi project yang anda simpan
//            if(!fNew.exists())
//            {
//                fNew.createNewFile();
//            }
            //Old
            File[] fx = f.listFiles();
            if(fx!=null)
            {
                  for(int i=0;i<fx.length;i++)
                 {
                     if(fx[i].getName().contains(pKategori))
                       counter++;
                  }
            }
            
            int i=0;
            UserAgent userAgent2 = new UserAgent();
            for(Element tempLink : getLink)
            {
                // doc id angka 1440 gantio start id (yang mau diinput, kalo pertama kali gausah)
                tempData[i] = String.valueOf(i+((x-1)*getLink.size())+1440) + ",";
                //System.out.println(i + "((" + x + "-1) * " + getLink.size() + ") = " + String.valueOf(i+((x-1)*getLink.size())));
                
                // ambil judul
                tempData[i] += stem(tempLink.getAt("title")) +",";
                
                // ambil link
                //tempData[i] += tempLink.getAt("href")+",";
                
                // ambil desc
                userAgent2.visit(tempLink.getAt("href"));
                Element getDescription = userAgent2.doc.findEach("<div class=\"js-collapsible-product-detail qa-pd-description u-txt--break-all\">").
                        findEach("<p>");
                
                String tempDesc = getDescription.innerHTML();
                String desc = (((((tempDesc.replaceAll("<p>", "")).replaceAll("<br>", "\r\n")).replaceAll("</p>", "")).replaceAll(",", " ")).replaceAll("'", "")).replaceAll("\"", "");
                tempData[i] += stem(desc) + ",";
                
                i+=1;
            }
            
            i=0;
            
            
            for(Element tempPrice : getPrice)
            {
                // ambil harga
              tempData[i] += "Rp "+tempPrice.getAt("data-reduced-price");
              

              
              //bw = new BufferedWriter(fw);
              if((tempData[i] + "\r\n")==null)
              {
                  System.out.println("ERROOOOOORRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
              }
              fw.append(tempData[i] + "\r\n");
              fw.flush();
              //out.println(tempData[i] + "\r\n");
              //out.close();
              
              // stemming
               //char[] charArray = tempData[i].toCharArray();
                //Character[] charObjectArray = charArray.chars().mapToObj(c -> (char)c).toArray(Character[]::new);
                //IndonesianStemmer stemmer = new IndonesianStemmer();
                //int len = stemmer.stem(charArray, charArray.length, true);
                //String res = new String(charArray,0,len);
                
              //bw.write(output);
              //bw.close();
              counter++;

              System.out.println(tempData[i]);
              //System.out.println("---------------------------------------------------------");
              i+=1;
            }
            item.createNewFile();
            //out.close();
        }
        catch(ResponseException e){
          System.out.println(e);
        } catch (IOException ex) {
            Logger.getLogger(CobaDolo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void cariProdukTokopedia(String url) throws NotFound, IOException
    {
        try {   
            UserAgent userAgent = new UserAgent();
            userAgent.visit(url);
            Elements getLink = userAgent.doc.findEach("<div id=\"product-list-container\">").
                    findEach("<div id=\"promo-top-e-\"`>").
                    findEach("<div class=\"product-card\">").findEach("<a>");
            Elements getName = userAgent.doc.findEach("<div id=\"product-list-container\">").findEach("<div id=\"promo-top-e-\">").
                    findEach("<div class=\"product-card\">").
                    findEach("<div class=\"detail\">");
            Elements getPrice = userAgent.doc.findEach("<div id=\"product-list-container\">").findEach("<div id=\"promo-top-e-\">").
                    findEach("<div class=\"product-card\">").
                    findEach("<div class=\"detail\">").findEach("<span class=\"detail__price\">");
            
            String[] tempData = new String[getLink.size()];
          
            int i=0;
            for(Element tempName : getName)
            {
                tempData[i] = tempName.getAt("detail__name")+"\r\n";
                i+=1;
            }
            
            for(Element tempLink : getLink)
            {
                tempData[i] = tempLink.getAt("href")+"\r\n";
                i+=1;
            }
            
            long counter=0;
            for(Element tempPrice : getPrice)
            {
                tempData[i] = "Rp. " + tempPrice.getAt("detail__price") + "\r\n\r\n";
                File item = new File("/Users/pietsteph/Desktop/Crawler_IIR/data_crawl/tokopedia/"+"TKPD-"+counter+".txt");
                item.createNewFile();

                fw = new FileWriter(item.getAbsoluteFile());
                bw = new BufferedWriter(fw);
                bw.write(tempData[i]);
                bw.close();
                counter++;

                System.out.println(tempData[i]);
                i+=1;
            }
            out.close();
            
            
        } catch (ResponseException ex) {
            Logger.getLogger(CobaDolo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public static void cariProdukBliBli(String linkName,String pKategori) throws NotFound
    {
        try {   
            UserAgent userAgent = new UserAgent();
            userAgent.visit(linkName);
            
            Elements getLink = userAgent.doc.
            findEach("<div class=\"large-4 medium-5 small-8 columns\">").findEach("<a class=\"single-product\">");
 
            Elements getName = userAgent.doc.
            findEach("<div class=\"large-4 medium-5 small-8 columns\">").findEach("<a class=\"single-product\">").
            findEach("<div class=\"product-title\">");
            
            Elements getPrice = userAgent.doc.
            findEach("<div class=\"large-4 medium-5 small-8 columns\">").findEach("<a class=\"single-product\">").
            findEach("<div class=\"product-price\">").findEach("<span class=\"new-price-text\">");
            
            String[] simpanData = new String[getLink.size()];
            
            long counter=0;
            //Old
            File f = new File("/Users/pietsteph/Desktop/Crawler_IIR/data_crawl/blibli");
            //File fNew = new File("E:\\College File\\Semester 4\\Intelligent Information Retrieval\\Crawler_IIR\\data_crawl\\bukalapak.txt");
            //*E:\\College File\\Semester 4\\Intelligent Information Retrieval*
            //Ganti dengan lokasi project yang anda simpan
            //File fNew = new File("E:\\College File\\Semester 4\\Intelligent Information Retrieval\\Crawler_IIR\\data_crawl\\blibli.txt");
            //Old
            File[] fx = f.listFiles();
            if(fx!=null)
            {
               for(int i=0;i<fx.length;i++)
               {
                  if(fx[i].getName().contains(pKategori))
                     counter++;
              }
            }
            int i=0;
            for(Element tempName : getName)
            {
                simpanData[i] = tempName.getAt("title") + "\r\n";
                i++;
            }
            i=0;
            for(Element tempPrice : getPrice)
            {
                simpanData[i] = simpanData[i] + tempPrice.getText()+"\r\n";
                i++;
            }
            i=0;
            for(Element tempLink : getLink)
            {
                simpanData[i] = simpanData[i]+ tempLink.getAt("href");
                File item = new File("/Users/pietsteph/Desktop/Crawler_IIR/data_crawl/blibli/"+pKategori+"-"+counter+".csv");
                //*E:\\College File\\Semester 4\\Intelligent Information Retrieval*
                //Ganti dengan lokasi project yang anda simpan
                item.createNewFile();
                
                fw = new FileWriter(item.getAbsoluteFile());
		bw = new BufferedWriter(fw);
                bw.write(simpanData[i]);
                bw.close();
                counter++;
                
                System.out.println(simpanData[i]);
                i++;
            }
        } catch (ResponseException ex) {
            Logger.getLogger(CobaDolo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CobaDolo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void cariProdukLazada(String linkName,String pKategori) throws NotFound
    {
        try{         
            UserAgent userAgent = new UserAgent();
            userAgent.visit(linkName);   
          
            Elements getLink = userAgent.doc.findEach("<a class=\"c-product-card__name\">");       //find non-nested tables   
            Elements getPrice = userAgent.doc.findEach("<span class=\"c-product-card__price-final\">");
            System.out.println(getLink.size());
          
            String[] tempData = new String[getLink.size()];
            
            long counter=0;
            //Old
            File f = new File("/Users/pietsteph/Desktop/Crawler_IIR/data_crawl/lazada");
            
            //File fNew = new File("E:\\College File\\Semester 4\\Intelligent Information Retrieval\\Crawler_IIR\\data_crawl\\lazada.txt");
            //*E:\\College File\\Semester 4\\Intelligent Information Retrieval*
            //Ganti dengan lokasi project yang anda simpan
            
            //Old
            File[] fx = f.listFiles();
            if(fx!=null)
            {
               for(int i=0;i<fx.length;i++)
               {
                   if(fx[i].getName().contains(pKategori))
                      counter++;
               }
            }
            //if(!fNew.exists())
            //{
            //    fNew.createNewFile();
            //}
            
            int i=0;
          
            for(Element tempLink : getLink)
            {
                tempData[i] = tempLink.getText().substring(13) +"\r\n"+ tempLink.getAt("href")+"\r\n";
                i+=1;
            }
            i=0;
            for(Element tempPrice : getPrice)
            {
                  tempData[i] += tempPrice.getText().substring(21)+"\r\n\r\n";
                  File item = new File("/Users/pietsteph/Desktop/Crawler_IIR/data_crawl/lazada/"+pKategori+"-"+counter+".txt");
                  item.createNewFile();

                  fw = new FileWriter(item.getAbsoluteFile());
                  bw = new BufferedWriter(fw);
                  bw.write(tempData[i]);
                  bw.close();
                  counter++;
                  System.out.println(tempData[i]);
                  i+=1;
            }
        }
        catch(ResponseException e){
          System.out.println(e);
        } catch (IOException ex) {
            Logger.getLogger(CobaDolo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String stem(String str)
    {
        String output = "";
        Analyzer analyzer = new IndonesianAnalyzer();
        List<String> result = new ArrayList<String>();
        try {
            TokenStream stream  = analyzer.tokenStream(null, new StringReader(str));
            stream.reset();
            while (stream.incrementToken()) {
              result.add(stream.getAttribute(CharTermAttribute.class).toString());
            }
        } catch (IOException e) {
          // not thrown b/c we're using a string reader...
            throw new RuntimeException(e);
        }
        for(String res : result)
        {
            output += res + " ";
        }
        //output = output.replaceAll(".", " ").replaceAll(",", " ").replaceAll(";", " ").replaceAll("'", " ").replaceAll("\"", str);
        return AdvancedReplace(output);
    }
    
    public static String AdvancedReplace(String txt)
    {
        char[] mustDelete = {',', '.', ':', ';', '!', '%', '\'', '"', '-'};
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
    }
}
