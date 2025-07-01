package bspkrs.armorstatushud.utils;

import bspkrs.armorstatushud.ArmorStatusHUD;

import java.util.HashMap;
import java.util.Map;

/**
 * Performance profiling utility for measuring optimization impact.
 * Enable via system property: -Darmorstatushud.profile=true
 */
public class PerformanceProfiler {
    private static final boolean PROFILING_ENABLED = Boolean.getBoolean("armorstatushud.profile");
    private static final Map<String, Long> timings = new HashMap<>();
    private static final Map<String, Integer> counts = new HashMap<>();
    
    public static void startTiming(String operation) {
        if (!PROFILING_ENABLED) return;
        
        timings.put(operation, System.nanoTime());
    }
    
    public static void endTiming(String operation) {
        if (!PROFILING_ENABLED) return;
        
        Long startTime = timings.get(operation);
        if (startTime != null) {
            long duration = System.nanoTime() - startTime;
            String key = operation + "_total";
            timings.put(key, timings.getOrDefault(key, 0L) + duration);
            counts.put(operation, counts.getOrDefault(operation, 0) + 1);
        }
    }
    
    public static void logStats() {
        if (!PROFILING_ENABLED) return;
        
        ArmorStatusHUD.LOGGER.info("=== ArmorStatusHUD Performance Stats ===");
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            String operation = entry.getKey();
            int count = entry.getValue();
            long totalTime = timings.getOrDefault(operation + "_total", 0L);
            double avgTime = totalTime / (double) count / 1_000_000.0; // Convert to ms
            
            ArmorStatusHUD.LOGGER.info("{}: {} calls, avg {:.3f}ms per call", 
                operation, count, avgTime);
        }
        ArmorStatusHUD.LOGGER.info("=====================================");
    }
    
    public static void reset() {
        timings.clear();
        counts.clear();
    }
}