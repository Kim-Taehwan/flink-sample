package com.example.flink.fraud.source;

import com.example.flink.fraud.entity.Transaction;
import org.apache.flink.api.common.io.GenericInputFormat;
import org.apache.flink.api.common.io.NonParallelInput;
import org.apache.flink.core.io.GenericInputSplit;
import org.apache.flink.types.Row;

import java.sql.Timestamp;
import java.util.Iterator;

public class TransactionRowInputFormat extends GenericInputFormat<Row> implements NonParallelInput {
    private static final long serialVersionUID = 1L;

    private transient Iterator<Transaction> transactions;

    @Override
    public void open(GenericInputSplit split) {
        transactions = TransactionIterator.bounded();
    }

    @Override
    public boolean reachedEnd() {
        return !transactions.hasNext();
    }

    @Override
    public Row nextRecord(Row reuse) {
        Transaction transaction = transactions.next();
        reuse.setField(0, transaction.getAccountId());
        reuse.setField(1, new Timestamp(transaction.getTimestamp()));
        reuse.setField(2, transaction.getAmount());

        return reuse;
    }
}
