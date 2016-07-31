package monad;

import java.util.Optional;

public class Endereco {

    private final Cidade cidade;

    public static Endereco endereco(final Cidade cidade) {
        return new Endereco(cidade);
    }

    public Endereco(final Cidade cidade) {
        this.cidade = cidade;
    }

    public Cidade cidade() {
        return this.cidade;
    }

    public Optional<Cidade> maybeCidade() {
        return Optional.ofNullable(this.cidade);
    }

    public Resultado<Cidade> queryCidade(){
        if(this.cidade != null){
            return Resultado.eh(this.cidade);
        }
        return Resultado.comProblema("Cidade não encontrada");
    }

}
