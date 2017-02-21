/*
 * Copyright (c) 2017, CESAR.
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD license. See the LICENSE file for details.
 *
 *
 */

package br.org.cesar.knot.lib.model;

/**
 * Use this class to find a Thing's data between two dates
 */

public class KnotQueryData {

    /**
     * Time to start from
     */
    private KnotQueryDateData mStartDate;

    /**
     * Time to end
     */
    private KnotQueryDateData mFinishDate;

    /**
     * Maximum number of records to return
     */
    private int mAmoutOfCode;


    /**
     * Get the initial date of the query that are building
     * @return KnotQueryDateData object
     */
    public KnotQueryDateData getStartDate() {
        return mStartDate;
    }

    /**
     * Set the start date of the query that are building
     * @param startDate The start date of the query that are building
     * @return KnotQueryData
     */
    public KnotQueryData setStartDate(KnotQueryDateData startDate) {
        this.mStartDate = startDate;

        return this;
    }

    /**
     * Get the final date of the query
     * @return KnotQueryDateData
     */
    public KnotQueryDateData getFinishDate() {
        return mFinishDate;
    }

    /**
     * Set the final date of the query that are building
     *
     * @param finishDate  The final date of the query that are building
     * @return KnotQueryData
     */
    public KnotQueryData setFinishDate(KnotQueryDateData finishDate) {
        this.mFinishDate = finishDate;

        return this;
    }

    /**
     * Get the Maximum number of records that will be returned in the query
     * @return the Maximum number of records that will be returned
     */
    public int getLimit() {
        return mAmoutOfCode;
    }

    /**
     * Set the Maximum number of records that will be returned in the query
     *
     * @param limit the Maximum number of records that will be returned.
     *              The value -1 is used to get all data of the a thing
     * @return KnotQueryData object
     */
    public KnotQueryData setLimit(int limit) {
        this.mAmoutOfCode = limit;
        return  this;
    }
}
