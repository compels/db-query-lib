package net.compels.dbquerylib.datatables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import net.compels.dbquerylib.base.Criteria;
import net.compels.dbquerylib.base.Pagination;
import net.compels.dbquerylib.base.Sorter;

/**
 * Adaptação de código de
 * http://www.opencodez.com/java/datatable-with-spring-boot.htm
 * 
 * @author Danilo C. Graciano
 *
 */
public class DataTableRequest {

    private String uniqueId;

    private String draw;

    private Long start;

    private Long length;

    private String search;

    private boolean regex;

    private int maxParamsToCheck = 0;

    private List<DataTableColumnSpecs> columns;

    private DataTableColumnSpecs order;

    private Map<String, Object> extraParams;

    public Map<String, Object> getExtraParams(){

        return extraParams;
    }

    public DataTableRequest( HttpServletRequest request ) {
        prepareDataTableRequest(request);
    }

    public String getUniqueId(){

        return uniqueId;
    }

    public void setUniqueId( String uniqueId ){

        this.uniqueId = uniqueId;
    }

    public Long getStart(){

        return start;
    }

    public void setStart( Long start ){

        this.start = start;
    }

    public Long getLength(){

        return length;
    }

    public void setLength( Long length ){

        this.length = length;
    }

    public String getSearch(){

        return search;
    }

    public void setSearch( String search ){

        this.search = search;
    }

    public boolean isRegex(){

        return regex;
    }

    public void setRegex( boolean regex ){

        this.regex = regex;
    }

    public List<DataTableColumnSpecs> getColumns(){

        return columns;
    }

    public void setColumns( List<DataTableColumnSpecs> columns ){

        this.columns = columns;
    }

    public DataTableColumnSpecs getOrder(){

        return order;
    }

    public void setOrder( DataTableColumnSpecs order ){

        this.order = order;
    }

    public String getDraw(){

        return draw;
    }

    public void setDraw( String draw ){

        this.draw = draw;
    }

    private void prepareDataTableRequest( HttpServletRequest request ){

        Enumeration<String> parameterNames = request.getParameterNames();

        if ( parameterNames.hasMoreElements() ){

            this.setStart(Long.parseLong(request.getParameter("start")));
            this.setLength(Long.parseLong( ( request.getParameter("length") )));
            this.setUniqueId(request.getParameter("_"));
            this.setDraw(request.getParameter("draw"));

            this.setSearch(request.getParameter("search[value]"));
            this.setRegex(Boolean.valueOf(request.getParameter("search[regex]")));

            int sortableCol = Integer.parseInt(request.getParameter("order[0][column]"));

            List<DataTableColumnSpecs> columns = new ArrayList<DataTableColumnSpecs>();

            maxParamsToCheck = getNumberOfColumns(request);

            for (int i = 0; i < maxParamsToCheck; i++ ){
                if ( null != request.getParameter("columns[" + i + "][data]") && !"null".equalsIgnoreCase(request.getParameter("columns[" + i + "][data]")) && !isObjectEmpty(request.getParameter("columns[" + i + "][data]")) ){
                    DataTableColumnSpecs colSpec = new DataTableColumnSpecs(request, i);
                    if ( i == sortableCol ){
                        this.setOrder(colSpec);
                    }
                    columns.add(colSpec);

                }
            }

            if ( !isObjectEmpty(columns) ){
                this.setColumns(columns);
            }

            this.extraParams = new HashMap<String, Object>();
            while ( parameterNames.hasMoreElements() ){

                String paramName = parameterNames.nextElement();
                if ( isExtraParam(paramName) ){
                    this.extraParams.put(paramName, request.getParameter(paramName));
                }
            }
        }
    }

    private boolean isExtraParam( String paramName ){

        String params[] = { "start", "length", "_", "draw", "search[value]", "search[regex]", "order[0][column]", "columns[","order[" };
        for (String param : params ){
            if ( paramName.equalsIgnoreCase(param) || paramName.startsWith(param) )
                return false;
        }
        return true;

    }

    private int getNumberOfColumns( HttpServletRequest request ){

        Pattern p = Pattern.compile("columns\\[[0-9]+\\]\\[data\\]");
        @SuppressWarnings("rawtypes")
        Enumeration params = request.getParameterNames();
        List<String> lstOfParams = new ArrayList<String>();
        while ( params.hasMoreElements() ){
            String paramName = (String) params.nextElement();
            Matcher m = p.matcher(paramName);
            if ( m.matches() ){
                lstOfParams.add(paramName);
            }
        }
        return lstOfParams.size();
    }

    public Pagination getPagination(){

        Pagination pagination = new Pagination();
        pagination.setOffset(this.getStart());
        pagination.setLimit(this.getLength());
        return pagination;
    }

    public Sorter getSorter(){

        Sorter sorter = null;
        if ( !isObjectEmpty(this.getOrder()) ){
            sorter = new Sorter();
            sorter.setAttribute(this.getOrder().getName());
            sorter.setOrientation( ( this.getOrder().getSortDir().equalsIgnoreCase("ASC") ) ? Sorter.ORIENTATION_ASC : Sorter.ORIENTATION_DESC);
        }
        return sorter;
    }

    public static boolean isObjectEmpty( Object object ){

        if ( object == null )
            return true;
        else
            if ( object instanceof String ){
                if ( ( (String) object ).trim().length() == 0 ){
                    return true;
                }
            } else
                if ( object instanceof Collection ){
                    return isCollectionEmpty((Collection<?>) object);
                }
        return false;
    }

    private static boolean isCollectionEmpty( Collection<?> collection ){

        if ( collection == null || collection.isEmpty() ){
            return true;
        }
        return false;
    }

}
