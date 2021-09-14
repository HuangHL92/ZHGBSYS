/*
 * Created Thu Jan 24 19:02:32 CST 2008 by MyEclipse Hibernate Tool.
 */ 
package com.insigma.siis.demo.entity;

import java.io.Serializable;

/**
 * A class that represents a row in the 'REWAGE' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 */
public class Rewage
    extends AbstractRewage
    implements Serializable
{
    /**
     * Simple constructor of Rewage instances.
     */
    public Rewage()
    {
    }

    /**
     * Constructor of Rewage instances given a composite primary key.
     * @param id
     */
    public Rewage(RewageKey id)
    {
        super(id);
    }

    /* Add customized code below */

}
