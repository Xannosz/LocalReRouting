package hu.xannosz.local.rerouting.core.util;

import hu.xannosz.local.rerouting.core.algorithm.Algorithm;
import hu.xannosz.local.rerouting.core.graph.GraphType;
import hu.xannosz.local.rerouting.core.statistic.Statistic;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.HashSet;
import java.util.Set;

public class Constants {
    public static final Set<Algorithm<?>> ALGORITHMS = new HashSet<>();
    public static final Set<GraphType<?>> GRAPHS = new HashSet<>();
    public static final Set<Statistic> STATISTICS = new HashSet<>();

    static {
        ClassPathScanningCandidateComponentProvider algorithmsScanner =
                new ClassPathScanningCandidateComponentProvider(true);
        algorithmsScanner.addIncludeFilter(new AnnotationTypeFilter(hu.xannosz.local.rerouting.core.annotation.Algorithm.class));
        for (BeanDefinition bd : algorithmsScanner.findCandidateComponents("hu.xannosz")) {
            try {
                ALGORITHMS.add((Algorithm<?>) Class.forName(bd.getBeanClassName()).getConstructor().newInstance(new Object[]{}));
                System.out.printf("%s algorithm loaded.%n", bd.getBeanClassName());
            } catch (Exception e) {
                System.out.printf("%s algorithm load failed.%n", bd.getBeanClassName());
            }
        }

        ClassPathScanningCandidateComponentProvider graphScanner =
                new ClassPathScanningCandidateComponentProvider(true);
        graphScanner.addIncludeFilter(new AnnotationTypeFilter(hu.xannosz.local.rerouting.core.annotation.GraphType.class));
        for (BeanDefinition bd : graphScanner.findCandidateComponents("hu.xannosz")) {
            try {
                GRAPHS.add((GraphType<?>) Class.forName(bd.getBeanClassName()).getConstructor().newInstance(new Object[]{}));
                System.out.printf("%s graph type loaded.%n", bd.getBeanClassName());
            } catch (Exception e) {
                System.out.printf("%s graph type load failed.%n", bd.getBeanClassName());
            }
        }

        ClassPathScanningCandidateComponentProvider statisticScanner =
                new ClassPathScanningCandidateComponentProvider(true);
        statisticScanner.addIncludeFilter(new AnnotationTypeFilter(hu.xannosz.local.rerouting.core.annotation.Statistic.class));
        for (BeanDefinition bd : statisticScanner.findCandidateComponents("hu.xannosz")) {
            try {
                STATISTICS.add((Statistic) Class.forName(bd.getBeanClassName()).getConstructor().newInstance(new Object[]{}));
                System.out.printf("%s statistic loaded.%n", bd.getBeanClassName());
            } catch (Exception e) {
                System.out.printf("%s statistic load failed.%n", bd.getBeanClassName());
            }
        }
    }
}
