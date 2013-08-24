/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sugadordedados;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.ElementIterator;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

/**
 *
 * @author eduardo
 */
public class Sugador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // configurar Baixador
        //Scanner entrada = new Scanner(System.in);
        
        //System.out.println("Digite as configuracoes:");
        //String configuracao = entrada.nextLine();
        
        // Baixar páginas
        
        String nomeDoArquivo = "D:\\trabalho\\amcham_bookyear\\teste1\\Yearbook_agronegocio.htm";
        // Parser Html retorna dados
        String paginaHtml = "";
        FileReader arquivo;
        Reader arquivoSalvo;
        String linha = "";
        HTMLEditorKit kit = new HTMLEditorKit(); 
        HTMLDocument doc = (HTMLDocument) kit.createDefaultDocument(); 
        doc.putProperty("IgnoreCharsetDirective", Boolean.TRUE);
        try {
            arquivo = new FileReader(nomeDoArquivo);
            arquivoSalvo = new BufferedReader(arquivo);
            kit.read(arquivoSalvo, doc, 0);
            //linha = arquivoSalvo.readLine();
            /*
            while(linha != null) { 
                paginaHtml += linha;
                linha = arquivoSalvo.readLine();
            }*/
        } catch (FileNotFoundException ex) {
            System.err.printf("Erro na abertura da arquivo: %s\n", ex.getMessage());
        } catch (IOException ex) {
            System.err.printf("Erro na leitura do arquivo: %s\n", ex.getMessage());
        } catch (BadLocationException ex) {
            System.err.printf("Erro na leitura do arquivo: %s\n", ex.getMessage());
        }
        
        //System.out.println(paginaHtml);
        
        //URL url = new URL( "http://java.sun.com" ); 
        //Reader HTMLReader = new InputStreamReader(url.openConnection().getInputStream());
        //kit.read(HTMLReader, doc, 0);
        

        //  Get an iterator for all HTML tags.
        ElementIterator it = new ElementIterator(doc); 
        Element elem; 
        elem = it.next();
        while( elem != null  )
        { 
            if( elem.getName().equals("div") )
            { 
                String s = (String) elem.getAttributes().getAttribute(HTML.Attribute.CLASS);
                if( s != null ) {
                    if (elem.getAttributes().getAttribute(HTML.Attribute.CLASS).equals("party-name")) {
                        System.out.println("----" + elem);
                    }
                    //System.out.println(s);
                }
            } 
            elem = it.next();
        }
        // Gravar dados permanentemente
    }
}
                              