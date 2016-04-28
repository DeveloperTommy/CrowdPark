package edu.umass.cs.crowdpark.util;

import java.util.Comparator;

/**
 * Created by Tommy on 4/27/2016.
 */
public class SpaceComparator implements Comparator<String> {
    @Override
    public int compare(String lhs, String rhs) {
        String[] left = TweetUtil.parseTweet(lhs);
        String[] right = TweetUtil.parseTweet(rhs);

        Double leftSpace = Double.parseDouble(left[TweetUtil.SPACE]);
        Double rightSpace = Double.parseDouble(right[TweetUtil.SPACE]);

        return leftSpace.compareTo(rightSpace);
    }
}
