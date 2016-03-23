package com.project.passwordmanager.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by elitecore on 3/2/16.
 */
public class DataFile {

    private Map<String,List<NameAndValue>> dataFileMap = new HashMap<String,List<NameAndValue>>();

    public Map<String, List<NameAndValue>> getDataFileMap() {
        return dataFileMap;
    }

    public void setDataFileMap(Map<String, List<NameAndValue>> dataFileMap) {
        this.dataFileMap = dataFileMap;
    }


}
