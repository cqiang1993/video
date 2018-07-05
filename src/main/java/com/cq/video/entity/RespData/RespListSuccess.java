package com.cq.video.entity.RespData;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

public class RespListSuccess<T> {
    private Integer total;

    private List<T> items = new ArrayList<>();

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items.clear();
        this.items.addAll(items);
    }
}
