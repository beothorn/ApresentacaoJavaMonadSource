package monad;

public class Cidade {

    private final String nome;

    public static Cidade cidade(final String nome){
        return new Cidade(nome);
    }

    public Cidade(final String name) {
        this.nome = name;
    }

    @Override
    public String toString() {
        return this.nome;
    }

}
