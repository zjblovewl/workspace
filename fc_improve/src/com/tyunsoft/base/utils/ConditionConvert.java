
package com.tyunsoft.base.utils;

import java.util.ArrayList;
import java.util.List;

import com.tyunsoft.base.entity.SearchCondition;

public class ConditionConvert
{

    public static Object[] convert( List<SearchCondition> conditions )
    {
        List<String> search = new ArrayList<String>();
        for ( SearchCondition searchCondition : conditions )
        {
            if ( null == searchCondition.getValue() )
            {
                searchCondition.setValue( "" );
            }
            if ( null == searchCondition.getStartValue() )
            {
                searchCondition.setStartValue( "" );
            }
            if ( null == searchCondition.getEndValue() )
            {
                searchCondition.setEndValue( "" );
            }

            if ( "betweendate".equals( searchCondition.getLinkSign() ) )
            {
                search.add( "".equals( searchCondition.getStartValue() ) ? "1900-12-12"
                        : searchCondition.getStartValue() );
                search.add( "".equals( searchCondition.getEndValue() ) ? "3900-12-12"
                        : searchCondition.getEndValue() );
            }
            else if ( "betweendatetime".equals( searchCondition.getLinkSign() ) )
            {
                search.add( "".equals( searchCondition.getStartValue() ) ? "1900-12-12 12:12:12"
                        : searchCondition.getStartValue() );
                search.add( "".equals( searchCondition.getEndValue() ) ? "3900-12-12 12:12:12"
                        : searchCondition.getEndValue() );
            }
            else if ( "=".equals( searchCondition.getLinkSign() ) )
            {
                search.add( "".equals( searchCondition.getValue() ) ? "-1"
                        : searchCondition.getValue() );
                search.add( "".equals( searchCondition.getValue() ) ? "-1"
                        : searchCondition.getValue() );
            }
            else if ( SearchCondition.EQUALS_ONE.equals( searchCondition
                    .getLinkSign() ) )
            {
                search.add( searchCondition.getValue() );
            }
            else
            {
                search.add( "%" + searchCondition.getValue() + "%" );
            }
        }

        return search.toArray();
    }

}
