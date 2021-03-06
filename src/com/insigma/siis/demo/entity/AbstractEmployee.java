/*
 * WARNING: DO NOT EDIT THIS FILE. This is a generated file that is synchronized
 * by MyEclipse Hibernate tool integration.
 *
 * Created Wed Dec 19 16:04:11 CST 2007 by MyEclipse Hibernate Tool.
 */
package com.insigma.siis.demo.entity;

import java.io.Serializable;

/**
 * A class that represents a row in the EMPLOYEE table. 
 * You can customize the behavior of this class by editing the class, {@link Employee()}.
 * WARNING: DO NOT EDIT THIS FILE. This is a generated file that is synchronized
 * by MyEclipse Hibernate tool integration.
 */
public abstract class AbstractEmployee 
    implements Serializable
{
    /** The cached hash code value for this instance.  Settting to 0 triggers re-calculation. */
    private int hashValue = 0;

    /** The composite primary key value. */
    private java.lang.String aac001;

    /** The value of the simple aac002 property. */
    private java.lang.String aac002;

    /** The value of the simple aac003 property. */
    private java.lang.String aac003;

    /** The value of the simple aac004 property. */
    private java.lang.String aac004;

    /** The value of the simple aac006 property. */
    private java.util.Date aac006;

    /**
     * Simple constructor of AbstractEmployee instances.
     */
    public AbstractEmployee()
    {
    }

    /**
     * Constructor of AbstractEmployee instances given a simple primary key.
     * @param aac001
     */
    public AbstractEmployee(java.lang.String aac001)
    {
        this.setAac001(aac001);
    }

    /**
     * Return the simple primary key value that identifies this object.
     * @return java.lang.String
     */
    public java.lang.String getAac001()
    {
        return aac001;
    }

    /**
     * Set the simple primary key value that identifies this object.
     * @param aac001
     */
    public void setAac001(java.lang.String aac001)
    {
        this.hashValue = 0;
        this.aac001 = aac001;
    }

    /**
     * Return the value of the AAC002 column.
     * @return java.lang.String
     */
    public java.lang.String getAac002()
    {
        return this.aac002;
    }

    /**
     * Set the value of the AAC002 column.
     * @param aac002
     */
    public void setAac002(java.lang.String aac002)
    {
        this.aac002 = aac002;
    }

    /**
     * Return the value of the AAC003 column.
     * @return java.lang.String
     */
    public java.lang.String getAac003()
    {
        return this.aac003;
    }

    /**
     * Set the value of the AAC003 column.
     * @param aac003
     */
    public void setAac003(java.lang.String aac003)
    {
        this.aac003 = aac003;
    }

    /**
     * Return the value of the AAC004 column.
     * @return java.lang.String
     */
    public java.lang.String getAac004()
    {
        return this.aac004;
    }

    /**
     * Set the value of the AAC004 column.
     * @param aac004
     */
    public void setAac004(java.lang.String aac004)
    {
        this.aac004 = aac004;
    }

    /**
     * Return the value of the AAC006 column.
     * @return java.util.Date
     */
    public java.util.Date getAac006()
    {
        return this.aac006;
    }

    /**
     * Set the value of the AAC006 column.
     * @param aac006
     */
    public void setAac006(java.util.Date aac006)
    {
        this.aac006 = aac006;
    }

    /**
     * Implementation of the equals comparison on the basis of equality of the primary key values.
     * @param rhs
     * @return boolean
     */
    public boolean equals(Object rhs)
    {
        if (rhs == null)
            return false;
        if (! (rhs instanceof Employee))
            return false;
        Employee that = (Employee) rhs;
        if (this.getAac001() == null || that.getAac001() == null)
            return false;
        return (this.getAac001().equals(that.getAac001()));
    }

    /**
     * Implementation of the hashCode method conforming to the Bloch pattern with
     * the exception of array properties (these are very unlikely primary key types).
     * @return int
     */
    public int hashCode()
    {
        if (this.hashValue == 0)
        {
            int result = 17;
            int aac001Value = this.getAac001() == null ? 0 : this.getAac001().hashCode();
            result = result * 37 + aac001Value;
            this.hashValue = result;
        }
        return this.hashValue;
    }
}
