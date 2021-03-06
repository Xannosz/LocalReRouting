package hu.xannosz.local.rerouting.core.util;

import hu.xannosz.local.rerouting.core.interfaces.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class Constants {
    private static final File MODULES = new File("modules.txt");

    public static final Set<Algorithm> ALGORITHMS = new HashSet<>();
    public static final Set<GraphType<?>> GRAPHS = new HashSet<>();
    public static final Set<Statistic> STATISTICS = new HashSet<>();
    public static final Set<MessageGenerator<?>> MESSAGE_GENERATORS = new HashSet<>();
    public static final Set<FailureGenerator<?>> FAILURE_GENERATORS = new HashSet<>();

    private static final Set<String> PACKAGES = new HashSet<>(Collections.singletonList("hu.xannosz.local.rerouting"));

    private static void load(String type, Class<? extends Annotation> annotation, Consumer<Object> adder) {
        ClassPathScanningCandidateComponentProvider algorithmsScanner =
                new ClassPathScanningCandidateComponentProvider(true);
        algorithmsScanner.addIncludeFilter(new AnnotationTypeFilter(annotation));
        for (String pack : PACKAGES) {
            System.out.printf("%nLoad %ss from package %s:%n", type, pack);
            for (BeanDefinition bd : algorithmsScanner.findCandidateComponents(pack)) {
                try {
                    adder.accept(Class.forName(bd.getBeanClassName()).getConstructor().newInstance());
                    System.out.printf("[ %s ] %s loaded.%n", type, bd.getBeanClassName());
                } catch (Exception e) {
                    System.out.printf("[ %s ] %s load failed.%n", type, bd.getBeanClassName());
                }
            }
        }
    }

    static {
        try {
            if (!MODULES.exists()) {
                FileUtils.writeLines(MODULES, PACKAGES, "\n");
            }
            if (MODULES.isDirectory()) {
                System.err.println(MODULES + " is a directory, please remove it!");
            } else {
                PACKAGES.addAll(FileUtils.readLines(MODULES, "UTF-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        load("Algorithm", hu.xannosz.local.rerouting.core.annotation.Algorithm.class, obj -> ALGORITHMS.add((Algorithm) obj));
        load("GraphType", hu.xannosz.local.rerouting.core.annotation.GraphType.class, obj -> GRAPHS.add((GraphType<?>) obj));
        load("Statistic", hu.xannosz.local.rerouting.core.annotation.Statistic.class, obj -> STATISTICS.add((Statistic) obj));
        load("Message Generator", hu.xannosz.local.rerouting.core.annotation.MessageGenerator.class, obj -> MESSAGE_GENERATORS.add((MessageGenerator<?>) obj));
        load("Failure Generator", hu.xannosz.local.rerouting.core.annotation.FailureGenerator.class, obj -> FAILURE_GENERATORS.add((FailureGenerator<?>) obj));
    }
}
