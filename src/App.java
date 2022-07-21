import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {

        // Fazer uma conexão HTTP (protocolo que a gente usa para acessar a web) e
        // buscar os top 250 filmes
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java/api/NASA-APOD.json";
       // String url = "https://api.nasa.gov/planetary/apod?api_key=AjJhzfqwgExe2Lq5jzPGh4ML50weeOriuTLZoBzX";
        // correto: "https://imdb-api.com/en/API/Top250Movies/k_cisv2044"

      
        // Extrair só os dados que interessam (título, poster, classificação ou
        // avaliação)
      
        // Exibir e manipular os dados como acharmos melhor
        ExtratorDeConteudo extrator =  new ExtratorDeConteudoDaNasa();

        ClienteHttp http = new ClienteHttp();
        String json = http.buscaDados(url);
        
        List<Conteudo> conteudos = extrator.extraiConteudos(json);

        GeradoraDeFigurinhas geradora = new GeradoraDeFigurinhas();

       // for (Conteudo conteudo : conteudos) {
        for (int i = 0; i < 3; i++) {

            Conteudo conteudo = conteudos.get(i);

            InputStream inputStream = new URL(conteudo.getUrlImagem()).openStream();
           
            String nomeArquivo = "saida/" + conteudo.getTitulo().replace(":", "-")  + ".png";

            geradora.cria(inputStream, nomeArquivo);

            System.out.println(conteudo.getTitulo());
            System.out.println();

        }

    }
}
