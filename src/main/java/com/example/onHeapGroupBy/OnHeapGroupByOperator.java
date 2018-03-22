package com.example.onHeapGroupBy;

import com.bits.heap.OnHeapMap;
import com.datatorrent.api.Context;
import com.datatorrent.api.DefaultInputPort;
import com.datatorrent.api.DefaultOutputPort;
import com.datatorrent.common.util.BaseOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OnHeapGroupByOperator extends BaseOperator{

    public final int MAX = 20000000;

    private static final Logger LOG = LoggerFactory.getLogger(OnHeapGroupByOperator.class);

    public final transient DefaultOutputPort<TransactionSchema> output = new DefaultOutputPort<>();

    public final transient DefaultInputPort<TransactionSchema> input = new DefaultInputPort<TransactionSchema>() {
        @Override
        public void process(TransactionSchema tuple) {
            groupBy(tuple);
        }
    };

    private OnHeapMap onHeapMap;

    public OnHeapGroupByOperator() {}

    void groupBy(TransactionSchema tuple) {
        if(tuple.getId() == 1)
            LOG.info("START 1 : " + System.currentTimeMillis());
        tuple.setPrice(onHeapMap.put(tuple.getId(), tuple.getPrice()));
        output.emit(tuple);
        if(tuple.getId() == MAX) {
            LOG.info("DONE " + MAX + " : " + System.currentTimeMillis());
            throw new ShutdownException();
        }
    }

    @Override
    public void setup(Context.OperatorContext context)
    {
            onHeapMap = new OnHeapMap();
    }

    @Override
    public void endWindow() { LOG.info("data size : " + onHeapMap.size());}

}
