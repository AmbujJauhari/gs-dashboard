package com.ambuj.domain;

import java.util.Arrays;

/**
 * Created by Aj on 09-06-2016.
 */
public class DetailedQuerySaveHolder {
    private String dataTypeName;
    private String spaceIdName;
    private DataHolderForType[] dataHolderForType;

    public String getDataTypeName() {
        return dataTypeName;
    }

    public void setDataTypeName(String dataTypeName) {
        this.dataTypeName = dataTypeName;
    }

    public DataHolderForType[] getDataHolderForType() {
        return dataHolderForType;
    }

    public void setDataHolderForType(DataHolderForType[] dataHolderForType) {
        this.dataHolderForType = dataHolderForType;
    }

    public String getSpaceIdName() {
        return spaceIdName;
    }

    public void setSpaceIdName(String spaceIdName) {
        this.spaceIdName = spaceIdName;
    }

    @Override
    public String toString() {
        return "DetailedQuerySaveHolder{" +
                "dataTypeName='" + dataTypeName + '\'' +
                ", spaceIdName='" + spaceIdName + '\'' +
                ", dataHolderForType=" + Arrays.toString(dataHolderForType) +
                '}';
    }
}
