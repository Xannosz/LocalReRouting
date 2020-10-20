package hu.xannosz.local.rerouting.core.interfaces;

public interface Algorithm<T> {
    String getName();

    MatrixCreator getCreator();

    ReRouter getReRouter();
}
