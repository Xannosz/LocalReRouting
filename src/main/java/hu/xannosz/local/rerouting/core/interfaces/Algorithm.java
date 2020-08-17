package hu.xannosz.local.rerouting.core.interfaces;

public interface Algorithm<T> {
    String getName();

    MatrixCreator<T> getCreator();

    ReRouter<T> getReRouter();
}
