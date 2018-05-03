package net.compels.dbquerylib.base;

import java.util.List;

public interface BaseDAO<E extends BaseEntity> {

    public E create( E e ) throws Exception;

    public void update( E e ) throws Exception;

    public void delete( List<Criteria> criteriaList ) throws Exception;

    public List<E> read( List<Criteria> criteriaList , List<Sorter> sorterList , Pagination pagination ) throws Exception;

    public long count( List<Criteria> criteriaList ) throws Exception;

    public Query map( Query query , E e );

}
