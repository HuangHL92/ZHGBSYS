/*
 * Created Wed Dec 19 16:04:11 CST 2007 by MyEclipse Hibernate Tool.
 */ 
package com.insigma.siis.demo.entity;

import java.io.Serializable;

/**
 * A class that represents a row in the 'EMPLOYEE' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 */
public class Employee
    extends AbstractEmployee
    implements Serializable
{
    /**
     * Simple constructor of Employee instances.
     */
    public Employee()
    {
    }

    /**
     * Constructor of Employee instances given a simple primary key.
     * @param aac001
     */
    public Employee(java.lang.String aac001)
    {
        super(aac001);
    }

    /* Add customized code below */

}
