package org.avaje.metric.log4j;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.avaje.metric.CounterMetric;
import org.avaje.metric.MetricManager;
import org.avaje.metric.MetricName;

/**
 * A Log4J Appender which has Counter Metrics for FATAL, ERROR, WARN and INFO
 * log events.
 */
public class LogMetricAppender extends AppenderSkeleton {

  private final CounterMetric infoMetric;
  private final CounterMetric warnMetric;
  private final CounterMetric errorMetric;
  private final CounterMetric fatalMetric;

  public LogMetricAppender() {

    fatalMetric = MetricManager.getCounterMetric(new MetricName("app", "log", "fatal"));
    errorMetric = MetricManager.getCounterMetric(new MetricName("app", "log", "error"));
    warnMetric = MetricManager.getCounterMetric(new MetricName("app", "log", "warn"));
    infoMetric = MetricManager.getCounterMetric(new MetricName("app", "log", "info"));
  }

  @Override
  protected void append(LoggingEvent event) {

    switch (event.getLevel().toInt()) {
    case Level.INFO_INT:
      infoMetric.markEvent();
      break;
    case Level.WARN_INT:
      warnMetric.markEvent();
      break;
    case Level.ERROR_INT:
      errorMetric.markEvent();
      break;
    case Level.FATAL_INT:
      fatalMetric.markEvent();
      break;

    default:
      // ignore other levels
      break;
    }
  }

  @Override
  public void close() {
    // nothing doing
  }

  @Override
  public boolean requiresLayout() {
    return false;
  }
}
