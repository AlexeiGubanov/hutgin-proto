package com.hutgin.dao.criteria;

public interface Criteria {

    public Criteria addOrder(Order order);

    public Criteria setLimit(int limit);

    public Criteria setOffset(int offset);
}
