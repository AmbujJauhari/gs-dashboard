package com.ambuj.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Aj on 03-06-2016.
 */
public class SpaceEntry {
    private Set<String> headerColumns;
    private List<Map<String, String>> tableData = new ArrayList<>();

    public Set<String> getHeaderColumns() {
        return headerColumns;
    }

    public void setHeaderColumns(Set<String> headerColumns) {
        this.headerColumns = headerColumns;
    }

    public List<Map<String, String>> getTableData() {
        return tableData;
    }

    public void setTableData(List<Map<String, String>> tableData) {
        this.tableData = tableData;
    }

    @Override
    public String toString() {
        return "SpaceEntry{" +
                "headerColumns=" + headerColumns +
                ", tableData=" + tableData +
                '}';
    }
}
