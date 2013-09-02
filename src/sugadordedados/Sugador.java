/*
 * 02-09-2013
 * @edualuc
 */
package sugadordedados;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
    private static final String classAssociado = "associado";
    private static final String separador = ";";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // configurar Baixador
        campos.add("party-name");
        campos.add("regional");
        campos.add("telefone");
        campos.add("endereco");
        campos.add("website");
        campos.add("email");
        campos.add("naics");
        //Scanner entrada = new Scanner(System.in);
        //System.out.println("Digite as configuracoes:");
        //String configuracao = entrada.nextLine();
        
        /*
         * Baixar páginas
         */
        File paginasBaixadas[], pastaComPaginas;
        
        //paginaBaixada = baixarPagina(filtro);
        
        pastaComPaginas = new File("D:\\trabalho\\amcham_bookyear\\arquivos\\");
        
        paginasBaixadas = pastaComPaginas.listFiles();
        /*
         * Parser Html retorna dados
         */
        for(File paginaBaixada : paginasBaixadas) {
            
            Document documento = Jsoup.parse(paginaBaixada, "UTF-8");
            Element registros = documento.getElementById("associados");
            if (registros != null) {
                ArrayList<Map> listaRegistros = extrairRegistros(registros);

        /*
         * Gravar dados permanentemente
         */
                gravarArquivo(paginaBaixada.getName(), listaRegistros);
            } else {
                System.err.print("Arquivo vazio: ");
                System.err.println(paginaBaixada.getName());
            }
        }
        
    }

    private static ArrayList<Map> extrairRegistros(Element bodyHtml) {
        ArrayList<Map> listaRegistros = new ArrayList<>();
        Map<String, String> registro;
        
        Elements associados = (Elements) bodyHtml.getElementsByClass(classAssociado);
        
        for (Element associado : associados) {
            registro = new HashMap();
            for (String nomeCampo : campos) {
                registro.put(nomeCampo, associado.getElementsByClass(nomeCampo).text());
            }
            listaRegistros.add(registro);
        }
        return listaRegistros;
    }

    private static void gravarArquivo(String outputFile, ArrayList<Map> registros) {
        FileWriter arquivo;
        try {
            arquivo = new FileWriter("arquivos/"+outputFile+".csv");
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
            System.err.println(ex.getMessage());
            System.err.println(ex.getStackTrace());
        }
    }

    private static File baixarPagina(String filtro) {
        HttpURLConnection con;
        try {
            con = (HttpURLConnection) new URL("http://www.amcham.com.br/").openConnection();
            con.setRequestMethod("POST");
            //con.getOutputStream().write("LOGIN".getBytes("UTF-8"));
            con.getInputStream();   
        } catch (MalformedURLException ex) {
            Logger.getLogger(Sugador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Sugador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
                              