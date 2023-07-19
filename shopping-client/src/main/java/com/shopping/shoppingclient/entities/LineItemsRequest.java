package com.shopping.shoppingclient.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LineItemsRequest {

    @JsonProperty("LineItems")
    private List<LineItem> lineItems;

    public LineItemsRequest() {
    }

    public LineItemsRequest(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    @Override
    public String toString() {
        return "LineItemsRequest [lineItems=" + lineItems + "]";
    }

    

}
