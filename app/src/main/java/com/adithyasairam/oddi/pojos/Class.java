package com.adithyasairam.oddi.pojos;

/**
 * Created by AdiSai on 9/23/17.
 */

public class Class
{
    private String name;
    private String starT;
    private String endT;

    public Class (String n, String s, String e)
    {
        name = n;
        starT = s;
        endT = e;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String n)
    {
        this.name = n;
    }

    public String getStarT()
    {
        return starT;
    }

    public void setstarT (String t)
    {
        this.starT = t;
    }

    public String getendT()
    {
        return endT;
    }

    public void setendT(String e)
    {
        this.endT = e;
    }

    public String toString()
    {
        return name + " " +  starT + " " +  endT;
    }
}
