package server.managers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

public class FutureManager {
    private static final Collection<Future<ConnectionManagerPool>> forkJoinPool = new ArrayList<>();
    private static final Logger futureManagerLogger = LogManager.getLogger(FutureManager.class);
    private static final Logger serverLogger = LogManager.getLogger("server");

    public static void addNewForkJoinPoolFuture(Future<ConnectionManagerPool> future){
        forkJoinPool.add(future);
    }

    public static void checkAllFutures(){
        if (!forkJoinPool.isEmpty()) {
            serverLogger.debug("------------------------СПИСОК ВСЕХ ПОТОКОВ---------------------------");
            forkJoinPool.forEach(s -> futureManagerLogger.debug(s.toString()));
            serverLogger.debug("-------------------------------КОНЕЦ----------------------------------");
        }
        forkJoinPool.stream()
                .filter(Future::isDone)
                .forEach(f -> {
                    try {
                        ConnectionManager.submitNewResponse(f.get());
                    } catch (InterruptedException | ExecutionException e) {
                        serverLogger.info("---------------------------------------------------");
                    }
                });
        forkJoinPool.removeIf(Future::isDone);
    }
}
