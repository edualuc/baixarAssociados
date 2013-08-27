/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sugadordedados;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author eduardo
 */
public class Sugador {
    static ArrayList<String> campos = new ArrayList<>();
    static String outputFile = "resultado.csv";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // configurar Baixador
        //Scanner entrada = new Scanner(System.in);
        campos.add("party-name");
        campos.add("regional");
        campos.add("telefone");
        campos.add("endereco");
        campos.add("website");
        campos.add("email");
        campos.add("naics");
        
        //System.out.println("Digite as configuracoes:");
        //String configuracao = entrada.nextLine();
        
        // Baixar páginas
        
        File input = new File("D:\\trabalho\\amcham_bookyear\\teste1\\Yearbook_agronegocio.htm");
        
        // Parser Html retorna dados
        Document documento = Jsoup.parse(input, "UTF-8", "http://www.amcham.com.br/yearbook/2013/");

        //documento = Jsoup,.parse(paginaHtml);
        Element registros = documento.getElementById("associados");
        
        ArrayList<Map> listaRegistros = extrairRegistros(registros);
        
        /*for (Map associado : listaRegistros ) {
            System.out.println(associado.toString());
        }*/
        // Gravar dados permanentemente
        gravarArquivo(outputFile, listaRegistros);
    }

    private static ArrayList<Map> extrairRegistros(Element registros) {
        ArrayList<Map> listaRegistros = new ArrayList<>();
        Map<String, String> registro = new HashMap();
        
        Elements associados = (Elements) registros.getElementsByClass("associado");
        for (Element associado : associados) {
            for (String nomeCampo : campos) {
                registro.put(nomeCampo, associado.getElementsByClass(nomeCampo).text());
            }
            listaRegistros.add(registro);
            registro = new HashMap();
        }
        return listaRegistros;
    }

    private static void gravarArquivo(String outputFile, ArrayList<Map> registros) {
	String separador = ";";
        FileWriter arquivo;
        try {
            arquivo = new FileWriter(outputFile);
            for (String nomeCampo : campos) {
                arquivo.append(nomeCampo);
                arquivo.append(separador);
            }
            for( Map<String, String> registro : registros ) {
                arquivo.append("\n");
                for (String nomeCampo : campos) {
                //for (Object campo : registro.entrySet()) {
                    arquivo.append(registro.get(nomeCampo));
                    arquivo.append(separador);
                }
            }
            arquivo.flush();
            arquivo.close();
        } catch (IOException ex) {
            Logger.getLogger(Sugador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
                              