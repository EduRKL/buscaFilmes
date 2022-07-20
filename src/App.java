import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {

        // Fazer uma conexão HTTP (protocolo que a gente usa para acessar a web) e
        // buscar os top 250 filmes
        String url = "https://api.mocki.io/v2/549a5d8b/Top250Movies";
        // correto: "https://imdb-api.com/en/API/Top250Movies/k_cisv2044"

        URI endereco = URI.create(url);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // Extrair só os dados que interessam (título, poster, classificação ou
        // avaliação)
        JsonParser parser = new JsonParser();
        List<Map<String, String>> listaDeFIlmes = parser.parse(body);

        // Exibir e manipular os dados como acharmos melhor
        GeradoraDeFigurinhas geradora = new GeradoraDeFigurinhas();
        for (Map<String, String> filme : listaDeFIlmes) {

            String urlImagem = filme.get("image");
            String titulo = filme.get("title");

            InputStream inputStream = new URL(urlImagem).openStream();
           
            String nomeArquivo = titulo.replace(":", "-")  + ".png";

            geradora.cria(inputStream, nomeArquivo);

            System.out.println(titulo);
            System.out.println();

        }

    }
}
