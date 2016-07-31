package monad;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class Pessoa {

    private final Endereco endereco;

    public static class PessoaComNome{
        public String nome;
        public Pessoa pessoa;
        public PessoaComNome(final String nome, final Pessoa pessoa) {
            this.nome = nome;
            this.pessoa = pessoa;
        }
    }

    public static Map<String, Pessoa> pessoas(final PessoaComNome... pessoas){
        final LinkedHashMap<String, Pessoa> mapaDePessoas = new LinkedHashMap<>();
        for (final PessoaComNome pessoaComNome : pessoas) {
            mapaDePessoas.put(pessoaComNome.nome, pessoaComNome.pessoa);
        }
        return mapaDePessoas;
    }

    public static PessoaComNome pessoa(final String nome, final Endereco endereco) {
        final Pessoa pessoa = new Pessoa(endereco);
        final PessoaComNome pessoaComNome = new Pessoa.PessoaComNome(nome, pessoa);
        return pessoaComNome;
    }

    public Pessoa(final Endereco endereco) {
        this.endereco = endereco;
    }

    public Endereco endereco() {
        return this.endereco;
    }

    public Optional<Endereco> maybeEndereco() {
        return Optional.ofNullable(this.endereco);
    }

    public Resultado<Endereco> queryEndereco(){
        if(this.endereco != null){
            return Resultado.eh(this.endereco);
        }
        return Resultado.comProblema("Endereço não encontrado");
    }
}
