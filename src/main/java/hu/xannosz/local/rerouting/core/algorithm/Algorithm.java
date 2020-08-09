package hu.xannosz.local.rerouting.core.algorithm;

public interface Algorithm<T> {
    String getName();

    MatrixCreator<T> getCreator();

    ReRouter<T> getReRouter();
}
