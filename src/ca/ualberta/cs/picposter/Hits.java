/*
 *     * Taken from
     * https://github.com/homynyk/ESDemo/blob/master/ESDemo/src/ca/ualberta
 */
package ca.ualberta.cs.picposter;

import java.util.Collection;

public class Hits<T> {
    int total;
    double max_score;
    Collection<ElasticSearchResponse<T>> hits;

    public Collection<ElasticSearchResponse<T>> getHits() {
	return hits;
    }

    public String toString() {
	return (super.toString() + "," + total + "," + max_score + "," + hits);
    }
}
