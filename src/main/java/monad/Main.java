package monad;

import static monad.Cidade.cidade;
import static monad.Endereco.endereco;
import static monad.Pessoa.pessoa;
import static monad.Pessoa.pessoas;

import java.util.Map;
import java.util.Optional;

public class Main {

    public static void main(final String[] args) {
        final Map<String, Pessoa> pessoas = pessoas(
            pessoa(//João tem endereço e cidade
                "João",
                endereco(
                    cidade("Curitiba")
                )
            ),
            pessoa(//Maria tem endereço mas não tem cidade
                "Maria",
                endereco(
                    null
                )
            ),
            pessoa(//Pedro não tem endereço
                "Pedro",
                null
            )
        );

        final String nome = "João";
        compararComNull(pessoas, nome);
        usarIsPresent(pessoas, nome);
        usarFlatMapComOptional(pessoas, nome);
        usarFlatMapComResultado(pessoas, nome);
    }

    private static void compararComNull(
        final Map<String, Pessoa> pessoas,
        final String nome
    ) {
        System.out.println("Comparar com null");
        final Pessoa pessoa = pessoas.get(nome);

        if (pessoa != null) {
            final Endereco address = pessoa.endereco();
            if (address != null) {
                final Cidade city = address.cidade();
                if (city != null) {
                    process(city);
                }
            }
        }
    }

    private static void usarIsPresent(
        final Map<String, Pessoa> pessoas,
        final String nome
    ) {
        System.out.println("Usar is present");
        final Optional<Pessoa> pessoa = Optional.ofNullable(pessoas.get(nome));

        if (pessoa.isPresent()) {
            final Optional<Endereco> address = pessoa.get().maybeEndereco();
            if (address.isPresent()) {
                final Optional<Cidade> city = address.get().maybeCidade();
                if (city.isPresent()) {
                    process(city.get());
                }
            }
        }
    }

    private static void usarFlatMapComOptional(
        final Map<String, Pessoa> pessoas,
        final String nome
    ) {
        System.out.println("Usar flat map com Optional");
        Optional.ofNullable(pessoas.get(nome))
            .flatMap(Pessoa::maybeEndereco)
            .flatMap(Endereco::maybeCidade)
            .ifPresent(Main::process);
    }

    private static void usarFlatMapComResultado(
        final Map<String, Pessoa> pessoas,
        final String nome
    ) {
        System.out.println("Usar flat map com resultado");
        Resultado.eh(pessoas.get(nome))
            .flatMap(Pessoa::queryEndereco)
            .flatMap(Endereco::queryCidade)
            .seTemResultado(Main::process)
            .senao((problema) -> System.out.println("Ops, ocorreu um problema:\n"+problema) );
    }

    private static void process(final Cidade cidade) {
        System.out.println(cidade);
    }

}
