/**
 * Put your copyright and license info here.
 */
package com.example.onHeapGroupBy;

import com.datatorrent.api.Context;
import com.datatorrent.api.DefaultOutputPort;
import com.datatorrent.api.InputOperator;
import com.datatorrent.common.util.BaseOperator;

import java.util.Random;

/**
 * This is a simple operator that emits random number.
 */
public class RandomNumberGenerator extends BaseOperator implements InputOperator
{
  public final int MAX = 20000000;
  private int numTuples = 100;
  private transient int count = 0;
  private static final int MIN = 0;
  //private static final int MAX = 2147483647;
  private int no;
  Random random = new Random();

  public final transient DefaultOutputPort<TransactionSchema> out = new DefaultOutputPort<TransactionSchema>();

  @Override
  public void beginWindow(long windowId)
  {
    count = 1;
  }

  @Override
  public void emitTuples()
  {
    if(count < 3000 && no <= MAX) {
      out.emit(new TransactionSchema(no, positive(random.nextInt())));
      no++;
      count++;
      if (no > MAX) no = -2147483648;
    }
  }

  @Override
  public void setup(Context.OperatorContext context) {
    //no = -2147483648;
    no = 1;
  }

  private Integer positive(Integer key) {
    return (key < 0 ? (-key) : key);
  }

  /*private int getRandomInt() {
    return (int)Math.floor(Math.random() * (MAX - MIN)) + MIN; //The maximum is exclusive and the minimum is inclusive
  }*/

  public int getNumTuples()
  {
    return numTuples;
  }

  /**
   * Sets the number of tuples to be emitted every window.
   * @param numTuples number of tuples
   */
  public void setNumTuples(int numTuples)
  {
    this.numTuples = numTuples;
  }
}

