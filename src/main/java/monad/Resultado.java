package monad;

import java.util.function.Consumer;
import java.util.function.Function;

public class Resultado<T> {

    private final T valor;
    private final boolean temProblemas;
    private final String problema;

    public static <U> Resultado<U> eh(final U valor){
        if(valor == null) {
            return Resultado.comProblema("Valor não pode ser nulo");
        }
        return new Resultado<>(valor, null);
    }

    public static <U> Resultado<U> comProblema(final String problema){
        return new Resultado<>(null, problema);
    }

    private Resultado(final T valor, final String problema) {
        this.valor = valor;
        this.problema = problema;
        this.temProblemas = problema != null;
    }

    public<U> Resultado<U> flatMap(final Function<? super T, Resultado<U>> mapper) {
        if(this.temProblemas) {
            return Resultado.comProblema(this.problema);
        }
        final Resultado<U> resultadoDepoisDeAplicarFuncao = mapper.apply(this.valor);
        return resultadoDepoisDeAplicarFuncao;
    }

    public Resultado<T> seTemResultado(final Consumer<T> execute){
        if(this.temProblemas) {
            return this;
        }
        execute.accept(this.valor);
        return this;
    }

    public Resultado<T> senao(final Consumer<String> execute){
        if(!this.temProblemas) {
            return this;
        }
        execute.accept(this.problema);
        return this;
    }
}