package com.example.flink.fraud.source;


import com.example.flink.fraud.entity.Transaction;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

final class TransactionIterator implements Iterator<Transaction>, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Timestamp INITIAL_TIMESTAMP = Timestamp.valueOf("2019-01-01 00:00:00");

    private static final long SIX_MINUTES = 6 * 60 * 1000;

    private final boolean bounded;

    private int index = 0;

    private long timestamp;

    static TransactionIterator bounded() {
        return new TransactionIterator(true);
    }

    static TransactionIterator unbounded() {
        return new TransactionIterator(false);
    }

    private TransactionIterator(boolean bounded) {
        this.bounded = bounded;
        this.timestamp = INITIAL_TIMESTAMP.getTime();
    }

    @Override
    public boolean hasNext() {
        if (index < data.size()) {
            return true;
        } else if (!bounded) {
            index = 0;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Transaction next() {
        Transaction transaction = data.get(index++);
        transaction.setTimestamp(timestamp);
        timestamp += SIX_MINUTES;
        return transaction;
    }

    private static List<Transaction> data =
            Arrays.asList(
                    //acount 1
                    new Transaction(1, 0L, 13),
                    new Transaction(2, 0L, 25),
                    new Transaction(3, 0L, 13),
                    new Transaction(4, 0L, 25)
//                    new Transaction(1, 0L, 0.09),
//                    new Transaction(1, 0L, 510),
//                    new Transaction(1, 0L, 102.62),
//                    new Transaction(1, 0L, 91.5),
//                    new Transaction(1, 0L, 0.02),
//                    new Transaction(2, 0L, 0.02),
//                    new Transaction(2, 0L, 30.01),
//                    new Transaction(2, 0L, 722.83),
//                    new Transaction(2, 0L, 31.92),
//                    new Transaction(1, 0L, 30.01),
//                    new Transaction(1, 0L, 701.83),
//                    new Transaction(1, 0L, 31.92),
//                    new Transaction(2, 0L, 512),
//                    new Transaction(2, 0L, 102.62),
//                    new Transaction(2, 0L, 91.5)
            );
}
