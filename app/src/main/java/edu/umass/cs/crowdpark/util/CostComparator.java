package edu.umass.cs.crowdpark.util;

import java.util.Comparator;

/**
 * Created by Tommy on 4/27/2016.
 */
public class CostComparator implements Comparator<String> {

    @Override
    public int compare(String lhs, String rhs) {
        String[] left = TweetUtil.parseTweet(lhs);
        String[] right = TweetUtil.parseTweet(rhs);

        //Compare by COST
        Double leftCost = Double.parseDouble(left[TweetUtil.COST]);
        Double rightCost = Double.parseDouble(right[TweetUtil.COST]);

        return leftCost.compareTo(rightCost);
    }
}
