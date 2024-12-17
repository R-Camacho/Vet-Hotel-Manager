package hva.util;

public interface Visitable {

    <T> T accept(Visitor<T> visitor);
}
