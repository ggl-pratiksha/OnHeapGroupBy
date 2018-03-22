/**
 * Put your copyright and license info here.
 */
package com.example.onHeapGroupBy;

import org.apache.hadoop.conf.Configuration;

import com.datatorrent.api.annotation.ApplicationAnnotation;
import com.datatorrent.api.StreamingApplication;
import com.datatorrent.api.DAG;
import com.datatorrent.lib.io.ConsoleOutputOperator;

@ApplicationAnnotation(name="OnHeapGroupBy")
public class Application implements StreamingApplication
{

  @Override
  public void populateDAG(DAG dag, Configuration conf)
  {
    RandomNumberGenerator randomGenerator = dag.addOperator("randomGenerator", RandomNumberGenerator.class);
    randomGenerator.setNumTuples(500);

    OnHeapGroupByOperator onHeapGroupByOperator = dag.addOperator("OnHeapGroupByOperator", new OnHeapGroupByOperator());
    ConsoleOutputOperator console = dag.addOperator("console", new ConsoleOutputOperator());

    dag.addStream("randomData", randomGenerator.out, onHeapGroupByOperator.input);
    dag.addStream("groupBy", onHeapGroupByOperator.output, console.input);
  }
}
